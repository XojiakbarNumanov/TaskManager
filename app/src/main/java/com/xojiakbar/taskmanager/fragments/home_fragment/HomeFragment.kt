package com.xojiakbar.taskmanager.fragments.home_fragment

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.IFillFormatter
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import com.github.mikephil.charting.interfaces.dataprovider.LineDataProvider
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet
import com.github.mikephil.charting.utils.Utils
import com.thanh.ha.piechart.PieItem
import com.xojiakbar.taskmanager.R
import com.xojiakbar.taskmanager.Utils.LoadingDialog
import com.xojiakbar.taskmanager.api.result.ErrorResult
import com.xojiakbar.taskmanager.data.local.entity.ReportTasksEntity
import com.xojiakbar.taskmanager.data.local.entity.TasksEntity
import com.xojiakbar.taskmanager.databinding.FragmentHomeBinding
import java.text.SimpleDateFormat
import java.util.Date


class HomeFragment : Fragment(), HomeRouter {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private var viewModel: HomeViewModel? = null
    private lateinit var loadingDialog: LoadingDialog

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(this)[HomeViewModel::class.java]
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initPieChart()
        initLineChart()
        initBarChart()


    }

    @SuppressLint("SimpleDateFormat")
    private fun initLineChart() {
        viewModel?.getInfoForLineChart()?.observe(viewLifecycleOwner) {
            val dateFormat = SimpleDateFormat("dd.MM")
            val days = ArrayList<String>()
            val dataSets = ArrayList<ILineDataSet>()
            val entryList = ArrayList<Entry>()
            for (i in 0 until it.size) {
                val taskcount = it[i].tasks_cnt
                entryList.add(Entry(i.toFloat(), taskcount!!.toFloat()))

                val date = it[i].day?.let { it1 ->
                    Date(it1)
                    val formattedDate = dateFormat.format(it1)
                    days.add(formattedDate)
                }

            }
            val set1 = LineDataSet(entryList, "")
            val xAxisFormatter = IndexAxisValueFormatter(days)
            binding.lineBarChart.xAxis.valueFormatter = xAxisFormatter


            set1.mode = LineDataSet.Mode.HORIZONTAL_BEZIER
            set1.cubicIntensity = 0.2f
            set1.setDrawFilled(true)
            set1.setDrawCircles(true)
            set1.circleRadius = 3f
            set1.setDrawCircleHole(false)
            set1.lineWidth = 1.8f
            set1.setCircleColor(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.color_primary_light
                )
            )
            set1.color = ContextCompat.getColor(requireContext(), R.color.color_primary)
            set1.fillColor = ContextCompat.getColor(requireContext(), R.color.color_primary)
            if (Utils.getSDKInt() >= 18) {
                // drawables only supported on api level 18 and above
                val drawable =
                    ContextCompat.getDrawable(requireContext(), R.drawable.gradient_color)
                set1.fillDrawable = drawable
            } else {
                set1.fillColor = Color.BLACK
            }
            set1.fillAlpha = 100
            set1.setDrawHorizontalHighlightIndicator(true)
            set1.fillFormatter =
                IFillFormatter { dataSet: ILineDataSet?, dataProvider: LineDataProvider? ->
                    binding.lineBarChart.getAxisLeft().getAxisMinimum()
                }

            dataSets.add(set1)
            set1.highLightColor = Color.rgb(244, 117, 117)

            val data = LineData(dataSets)
            data.setValueTextSize(9f)
            data.setDrawValues(false)

            binding.lineBarChart.data = data
            binding.lineBarChart.animateX(1500)
            binding.lineBarChart.description.isEnabled = false
            binding.lineBarChart.invalidate()
            binding.lineBarChart.animate()


        }
    }


    private fun initPieChart() {
        viewModel?.getInfoForPieChart()?.observe(viewLifecycleOwner) {
            if (it !=null)
            {

                var allTasks =
                    (it.done_tasks_cnt!! + it.accepted_tasks_cnt!! + it.in_progress_tasks_cnt!! + it.not_accepted_tasks_cnt!! + it.pause_tasks_cnt!! + it.returned_tasks_cnt!!).toFloat()
                var doneTasksPersent: Float =
                    it.done_tasks_cnt!!.toFloat() * 100 / allTasks
                var processTasksPersent: Float =
                    (it.pause_tasks_cnt!! + it.in_progress_tasks_cnt!!).toFloat() * 100 / allTasks
                var newTasksPersent: Float =
                    (it.returned_tasks_cnt!! + it.not_accepted_tasks_cnt!! + it.accepted_tasks_cnt!!).toFloat() * 100 / allTasks
                val list = listOf(
                    PieItem(
                        doneTasksPersent * 3.6f,
                        resources.getColor(R.color.color_primary_dark)
                    ),
                    PieItem(processTasksPersent * 3.6f, resources.getColor(R.color.color_primary)),
                    PieItem(
                        newTasksPersent * 3.6f,
                        resources.getColor(R.color.color_primary_light)
                    ),
                )
                binding.pieChart.submitList(list)
                binding.pieChart.animateProgress(0, 360)
            }
        }
    }

    fun initBarChart() {
        val values: MutableList<BarEntry> = ArrayList()

        viewModel?.getProjectGr()?.observe(viewLifecycleOwner){
            for (i in 0 until it.size)
            {
                values.add(BarEntry((i+1).toFloat(),it[i].tasks_cnt!!.toFloat()))
            }
            val barData: BarDataSet
            if (binding.barChart.data != null &&
                binding.barChart.data.dataSetCount > 0
            ) {
                barData = binding.barChart.data.getDataSetByIndex(0) as BarDataSet
                barData.values = values
                binding.barChart.data.notifyDataChanged()
                binding.barChart.notifyDataSetChanged()
            } else {
                barData = BarDataSet(values, "Data Set")
                barData.setColors(resources.getColor(R.color.color_primary_light))
                barData.setDrawValues(false)
                val dataSets = java.util.ArrayList<IBarDataSet>()
                dataSets.add(barData)
                val data = BarData(dataSets)
                binding.barChart.data = data
                binding.barChart.setFitBars(true)
            }
            binding.barChart.animateY(1500,Easing.EaseInOutQuad)
            binding.barChart.invalidate()
            binding.barChart.animate()
        }
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun changeStatus(id: TasksEntity) {
    }

    override fun showDialog(task: TasksEntity) {
    }


    override fun setLoading(tag: String) {
        loadingDialog.show(requireFragmentManager(), tag)
    }

    override fun onSuccess(response: Any?) {
        try {
            loadingDialog.dismiss()
        } catch (_: Exception) {
        }
    }

    override fun onError(errorMsg: ErrorResult?) {
        try {
            loadingDialog.dismiss()
        } catch (_: Exception) {
        }

    }

}