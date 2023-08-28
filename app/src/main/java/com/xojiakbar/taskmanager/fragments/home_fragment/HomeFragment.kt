package com.xojiakbar.taskmanager.fragments.home_fragment

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.components.YAxis
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
import com.google.android.material.tabs.TabLayout
import com.thanh.ha.piechart.PieItem
import com.xojiakbar.taskmanager.R
import com.xojiakbar.taskmanager.Utils.LoadingDialog
import com.xojiakbar.taskmanager.api.result.ErrorResult
import com.xojiakbar.taskmanager.data.local.entity.TasksEntity
import com.xojiakbar.taskmanager.databinding.FragmentHomeBinding
import java.text.SimpleDateFormat
import java.util.Date


class HomeFragment : Fragment(), HomeRouter {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private var viewModel: HomeViewModel? = null
    private lateinit var loadingDialog: LoadingDialog
    private var isDayly = true

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(this)[HomeViewModel::class.java]
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initPieChart()
        initBarChart()
        initLineChart(isDayly)
        binding.tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                isDayly = tab?.position == 0
                initLineChart(isDayly)
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
            }
        })

    }

    @SuppressLint("SimpleDateFormat")
    private fun initLineChart(isDayly: Boolean) {
        viewModel?.getInfoForLineChart(isDayly)?.observe(viewLifecycleOwner) {
            val dateFormat = SimpleDateFormat("dd.MM")
            val days = ArrayList<String>()
            val months = ArrayList<String>()
            val dataSets = ArrayList<ILineDataSet>()
            val entryList = ArrayList<Entry>()
            for (i in 0 until it.size) {
                val taskcount = it[i].tasks_cnt
                entryList.add(Entry(i.toFloat(), taskcount!!.toFloat()))

                it[i].day?.let { it1 ->
                    Date(it1)
                    val formattedDate = dateFormat.format(it1)
                    days.add(formattedDate)
                }
                it[i].month?.let { it1 ->
                    days.add(it1)
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
                    binding.lineBarChart.axisLeft.axisMinimum
                }

            dataSets.add(set1)
            set1.highLightColor = Color.rgb(244, 117, 117)

            val data = LineData(dataSets)
            data.setValueTextSize(9f)
            data.setDrawValues(false)

            // enable touch gestures
            binding.lineBarChart.setTouchEnabled(true)

            // enable scaling and dragging
            binding.lineBarChart.isDragEnabled = true
            binding.lineBarChart.setScaleEnabled(true)


            val x: XAxis = binding.lineBarChart.xAxis
            x.position = XAxis.XAxisPosition.BOTTOM
            x.setDrawGridLines(false)
            x.granularity = 1f


            val y: YAxis = binding.lineBarChart.axisLeft
            y.setLabelCount(6, false)
            y.textColor = Color.BLACK
            y.setDrawGridLines(true)
            y.axisLineColor = Color.WHITE

            binding.lineBarChart.legend.isEnabled = false
            binding.lineBarChart.axisRight.setDrawLabels(false)
            binding.lineBarChart.data = data
            binding.lineBarChart.animateX(1500)
            binding.lineBarChart.description.isEnabled = false
            binding.lineBarChart.invalidate()
            binding.lineBarChart.animate()
        }
    }


    @SuppressLint("SetTextI18n")
    private fun initPieChart() {
        viewModel?.getTaskCnt()?.observe(viewLifecycleOwner) {
            if (it != null) {

                val allTasks =it.monthly_all_cnt!!
                val doneTaskCnt = it.monthly_done_cnt!! + it.monthly_review_cnt!! + it.monthly_ranked_cnt!!
                val processTaskCnt = it.monthly_pause_cnt!! + it.monthly_returned_cnt!! + it.monthly_process_cnt!! + it.monthly_accepted_cnt!!
                val newTaskCnt =it.monthly_new_cnt!! + it.monthly_setted_cnt!!
                val doneTasksPersent: Float =
                    doneTaskCnt.toFloat() * 100 / allTasks
                val processTasksPersent: Float =
                    processTaskCnt.toFloat() * 100 / allTasks
                val newTasksPersent: Float =
                    newTaskCnt.toFloat() * 100 / allTasks
                val list = listOf(
                    PieItem(
                        doneTasksPersent * 3.6f,
                        resources.getColor(R.color.color_primary_dark)),
                    PieItem(
                        processTasksPersent * 3.6f,
                        resources.getColor(R.color.color_primary)),
                    PieItem(
                        newTasksPersent * 3.6f,
                        resources.getColor(R.color.color_primary_light)),
                )
                binding.percentDone.text = "$doneTaskCnt"
                binding.percentProcess.text = "$processTaskCnt"
                binding.percentAc.text = "$newTaskCnt"
                binding.pieChart.submitList(list)
                binding.pieChart.animateProgress(0, 360)
            }
        }
    }

    fun initBarChart() {
        val barDataSetList: MutableList<BarDataSet> = ArrayList()
        val colorList = arrayListOf(
            Color.BLUE,
            Color.RED,
            Color.GREEN,
            Color.CYAN,
            Color.MAGENTA,
            Color.BLUE,
            Color.BLUE
        )

        viewModel?.getProjectGr()?.observe(viewLifecycleOwner) {
            barDataSetList.clear()
            for (i in 0 until it.size) {
                barDataSetList.add(
                    BarDataSet(
                        arrayListOf(
                            BarEntry(
                                (i + 1).toFloat(),
                                it[i].tasks_cnt!!.toFloat()
                            )
                        ), it[i].name.toString()
                    )
                )
                barDataSetList[i].color = colorList[i]
            }
            if (binding.barChart.data != null && binding.barChart.data.dataSetCount > 0) {
                binding.barChart.data.clearValues()
                binding.barChart.data.notifyDataChanged()
                binding.barChart.notifyDataSetChanged()
            }


            val dataSets: MutableList<IBarDataSet> = barDataSetList.toMutableList()
            val data = BarData(dataSets)
            data.barWidth = 0.30f
            binding.barChart.data = data
            binding.barChart.setFitBars(true)

            val x: XAxis = binding.barChart.xAxis
            x.position = XAxis.XAxisPosition.BOTTOM
            x.setDrawGridLines(false)
            x.granularity = 1f


            val y: YAxis = binding.barChart.axisLeft
            y.setLabelCount(6, false)
            y.textColor = Color.BLACK
            y.setDrawGridLines(true)
            y.axisLineColor = Color.WHITE

            binding.barChart.legend.isEnabled = true
            binding.barChart.axisRight.setDrawLabels(false)
            binding.barChart.description.isEnabled = false
            binding.barChart.animateY(1500, Easing.EaseInOutQuad)
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