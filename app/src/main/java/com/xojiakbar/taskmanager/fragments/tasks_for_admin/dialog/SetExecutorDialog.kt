package com.xojiakbar.taskmanager.fragments.tasks_for_admin.dialog

import android.app.DatePickerDialog
import android.app.Dialog
import android.app.TimePickerDialog
import android.content.res.Resources
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.core.view.marginEnd
import androidx.core.view.marginLeft
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.xojiakbar.taskmanager.R
import com.xojiakbar.taskmanager.data.beans.task_bean.Task
import com.xojiakbar.taskmanager.data.local.entity.TasksEntity
import com.xojiakbar.taskmanager.databinding.SetExecutorBinding
import java.util.Calendar


class SetExecutorDialog(val entity: TasksEntity) : BottomSheetDialogFragment() {
    private var _binding: SetExecutorBinding? = null
    private val binding get() = _binding!!
    lateinit var dialog :BottomSheetDialog
    lateinit var viewModel : SetExecutorViewModel
    private var day = 0
    private var month = 0
    private var year = 0
    private var hour = 0
    var startingDate = ""
    var executerId = -1
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = SetExecutorBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(this)[SetExecutorViewModel::class.java]
        startingDate = getDate()
        binding.startingDate.text = startingDate
        binding.expiredDate.text = startingDate
        binding.layoutRL.setOnClickListener {
           showDialog(true)
        }
        binding.layoutRL2.setOnClickListener {
            showDialog(false)
        }
        binding.btnBack.setOnClickListener {
            dialog.dismiss()
        }
        binding.btnSend.setOnClickListener {
            if (executerId == -1)
                Toast.makeText(requireContext(), "Bajaruvchini tanlang", Toast.LENGTH_SHORT).show()
            else {
                val task = Task()
                task.curr_executor_id = executerId
                task.planned_start_date = binding.startingDate.text.toString()
                task.expired_date = binding.expiredDate.text.toString()
                task.id = entity.id
                task.isPushTasksTime = 0
                task.branches_id = 87
                viewModel.attachExecutor(task)
            }
        }

        return binding.root
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        dialog = super.onCreateDialog(savedInstanceState) as BottomSheetDialog
        return dialog
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val bottomSheetBehavior = BottomSheetBehavior.from(view.parent as View)
        bottomSheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED
        binding.attachExecutor.minHeight = Resources.getSystem().displayMetrics.heightPixels
        initexecutors()
    }
    private fun getDate(): String {
        val cal = Calendar.getInstance()
        day = cal.get(Calendar.DAY_OF_MONTH)
        month = cal.get(Calendar.MONTH) + 1
        year = cal.get(Calendar.YEAR)
        hour = cal.get(Calendar.HOUR_OF_DAY)
        return (if (day < 10) "0$day." else "$day.") + (if (month < 10) "0$month." else "$month.") + "$year " + "$hour:00:00"
    }

    fun showDialog(isStartedDate:Boolean)  {
        var date = ""
        DatePickerDialog(
            requireContext(), R.style.MyDatePickerDialogTheme,
            DatePickerDialog.OnDateSetListener { view, year, m, dayOfMonth ->
                var month = m + 1
                date =
                    (if (dayOfMonth < 10) "0$dayOfMonth." else "$dayOfMonth.") + (if (month < 10) "0$month." else "$month.") + "$year "
               TimePickerDialog(
                    requireContext(),R.style.MyDatePickerDialogTheme,
                    TimePickerDialog.OnTimeSetListener { view, hourOfDay, minute ->
                            date += "$hourOfDay:00:00"
                        if (isStartedDate)
                            binding.startingDate.text = date
                        else
                            binding.expiredDate.text =date
                    },
                    hour,
                    0,
                    true
                ).show()
            },
            year,
            month,
            day
        ).show()

    }
    private fun initexecutors() {
        viewModel?.getExecutors()?.observe(viewLifecycleOwner) {
            val projectGrMap = HashMap<Int, String>()
            projectGrMap[-1] = "Tanlang"
            for (i in it) {
                projectGrMap[i.id!!] = i.fio!!
            }
            val adapterAuto = ArrayAdapter<String>(
                requireContext(),
                R.layout.spinner_dropdown_item,
                projectGrMap.values.toMutableList()
            )
            binding.excutorName.adapter = adapterAuto
            binding.excutorName.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>, view: View, position: Int, id: Long
                ) {
                    val selectedValue = parent.getItemAtPosition(position).toString()
                    for (i in projectGrMap) {
                        if (i.value == selectedValue) {
                            executerId = i.key
                        }
                    }
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {
                }
            }
        }
    }

}