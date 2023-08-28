package com.xojiakbar.taskmanager.fragments.crate_tasks

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.DatePicker
import android.widget.TimePicker
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import com.xojiakbar.taskmanager.R
import com.xojiakbar.taskmanager.Utils.BaseRouter
import com.xojiakbar.taskmanager.Utils.LoadingDialog
import com.xojiakbar.taskmanager.Utils.Preferences
import com.xojiakbar.taskmanager.api.result.ErrorResult
import com.xojiakbar.taskmanager.data.beans.task_bean.Task
import com.xojiakbar.taskmanager.databinding.FragmentCreateTaskBinding
import java.util.Calendar
import kotlin.collections.HashMap
import kotlin.collections.arrayListOf
import kotlin.collections.set
import kotlin.collections.toMutableList


class CreateTaskFragment : Fragment(), BaseRouter<Any> {

    private var _binding: FragmentCreateTaskBinding? = null
    private val binding get() = _binding!!
    private var loadingDialog = LoadingDialog.newInstance()

    private var viewModel: CreateTaskViewModel? = null
    private var projectGrId: Int = -1
    private var projectId: Int = -1
    private var tasksGrId: Int = -1
    private var tasksTypeId: Int = -1
    private var prioritiesId: Int = -1
    private var levelOfComplexity: Int = 0
    private var startingDate: String = ""
    private var time: Double = 0.0
    private var projectName: String = ""
    private var description: String = ""

    private var day = 0
    private var month = 0
    private var year = 0
    private var hour = 0


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCreateTaskBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this)[CreateTaskViewModel::class.java]
        initResOrg()
        binding.startingDate.text = getDate()
        binding.radioGroup.setOnCheckedChangeListener { group, checkedId ->
            prioritiesId = when (checkedId) {
                R.id.hard -> {
                    1
                }

                R.id.easy -> {
                    2
                }

                else -> 3
            }
        }

        binding.layoutRL.setOnClickListener {
            showDate()
        }

        binding.btnSend.setOnClickListener {
            sentTask()
        }
        viewModel?.router = this
    }

    private fun getDate(): String {
        val cal = Calendar.getInstance()
        day = cal.get(Calendar.DAY_OF_MONTH)
        month = cal.get(Calendar.MONTH) + 1
        year = cal.get(Calendar.YEAR)
        hour = cal.get(Calendar.HOUR_OF_DAY)
        return (if (day < 10) "0$day." else "$day.") + (if (month < 10) "0$month." else "$month.") + "$year " + "$hour:00:00"
    }

    private fun sentTask() {
        levelOfComplexity =
            if (binding.level.text.isEmpty()) 0 else binding.level.text.toString().toInt()
        time = if (binding.time.text.isEmpty()) 0.0 else binding.time.text.toString().toDouble()
        description = binding.description.text.toString()
        projectName = binding.taskName.text.toString()
        if (projectGrId == -1) {
            Toast.makeText(requireContext(), "Loyiha guruhini nomini kiriting", Toast.LENGTH_SHORT)
                .show()
            return
        } else if (projectId == -1) {
            Toast.makeText(requireContext(), "Loyiha nomini kiriting", Toast.LENGTH_SHORT).show()
            return
        } else if (tasksGrId == -1) {
            Toast.makeText(
                requireContext(), "Topshiriq guruhini nomini kiriting", Toast.LENGTH_SHORT
            ).show()
            return
        } else if (tasksTypeId == -1) {
            Toast.makeText(requireContext(), "Task turini kiriting", Toast.LENGTH_SHORT).show()
            return
        } else if (prioritiesId == -1) {
            Toast.makeText(requireContext(), "Muhumlik darajasini kiriting", Toast.LENGTH_SHORT)
                .show()
            return
        } else if (projectName == "") {
            Toast.makeText(requireContext(), "Topshiriq nomini kiriting", Toast.LENGTH_SHORT).show()
            return
        }


        val task: Task = Task()
        task.branches_id = Preferences.getBranchesId()
        task.name = projectName
        task.planned_start_date = startingDate
        task.project_groups_id = projectGrId
        task.projects_id = projectId
        task.task_groups_id = tasksGrId
        task.task_priorities_id = prioritiesId
        task.task_types_id = tasksTypeId
        task.time_leave = time
        task.hard_index = levelOfComplexity
        task.description = description
        task.file_ids = arrayListOf()
        viewModel?.createTask(task)
    }


    private fun initResOrg() {
        val projectGrMap = HashMap<Int, String>()
        viewModel?.getProjectGr()?.observe(viewLifecycleOwner) {
            projectGrMap[-1] = "Tanlang"
            for (i in it) {
                projectGrMap[i.id!!] = i.name!!
            }
            val adapterAuto = ArrayAdapter<String>(
                requireContext(),
                R.layout.spinner_dropdown_item,
                projectGrMap.values.toMutableList()
            )
            binding.projectsGR.adapter = adapterAuto

        }
        binding.projectsGR.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>, view: View, position: Int, id: Long
            ) {
                val selectedValue = parent.getItemAtPosition(position).toString()
                for (i in projectGrMap) {
                    if (i.value == selectedValue) {
                        projectGrId = i.key
                    }
                }
                binding.taskGrName.setSelection(-1)
                binding.projects.setSelection(-1)
                binding.taskType.setSelection(-1)
                projectId = -1
                tasksGrId = -1
                tasksTypeId = -1
                initProject(projectGrId)
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                // Empty
            }
        }

    }

    private fun initProject(projectGroupId: Int) {
        val projectGrMap = HashMap<Int, String>()
        viewModel?.getProject(projectGroupId)?.observe(viewLifecycleOwner) {
            projectGrMap[-1] = "Tanlang"
            for (i in it) {
                projectGrMap[i.id!!] = i.name!!
            }
            val adapterAuto = ArrayAdapter<String>(
                requireContext(),
                R.layout.spinner_dropdown_item,
                projectGrMap.values.toMutableList()
            )
            binding.projects.adapter = adapterAuto
        }
        binding.projects.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>, view: View, position: Int, id: Long
            ) {
                val selectedValue = parent.getItemAtPosition(position).toString()
                for (i in projectGrMap) {
                    if (i.value == selectedValue) {
                        projectId = i.key
                    }
                }
                binding.taskType.setSelection(-1)
                binding.taskGrName.setSelection(-1)
                tasksGrId = -1
                tasksTypeId = -1
                initTaskGrName(projectId)
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                // Empty
            }
        }

    }

    private fun initTaskGrName(projectId: Int) {
        val projectGrMap = HashMap<Int, String>()
        viewModel?.getTaskGrName(projectId)?.observe(viewLifecycleOwner) {
            projectGrMap[-1] = "Tanlang"
            for (i in it) {
                projectGrMap[i.id!!] = i.name!!
            }
            val adapterAuto = ArrayAdapter<String>(
                requireContext(),
                R.layout.spinner_dropdown_item,
                projectGrMap.values.toMutableList()
            )
            binding.taskGrName.onItemSelectedListener =
                object : AdapterView.OnItemSelectedListener {
                    override fun onItemSelected(
                        parent: AdapterView<*>, view: View, position: Int, id: Long
                    ) {
                        val selectedValue = parent.getItemAtPosition(position).toString()
                        for (i in projectGrMap) {
                            if (i.value == selectedValue) {
                                tasksGrId = i.key
                            }
                        }
                        binding.taskType.setSelection(-1)
                        tasksTypeId = -1
                        initTaskType()
                    }

                    override fun onNothingSelected(parent: AdapterView<*>?) {
                    }
                }
            binding.taskGrName.adapter = adapterAuto
        }
    }

    private fun initTaskType() {
        viewModel?.gettaskType()?.observe(viewLifecycleOwner) {
            val projectGrMap = HashMap<Int, String>()
            projectGrMap[-1] = "Tanlang"
            for (i in it) {
                projectGrMap[i.id!!] = i.name!!
            }
            val adapterAuto = ArrayAdapter<String>(
                requireContext(),
                R.layout.spinner_dropdown_item,
                projectGrMap.values.toMutableList()
            )
            binding.taskType.adapter = adapterAuto
            binding.taskType.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>, view: View, position: Int, id: Long
                ) {
                    val selectedValue = parent.getItemAtPosition(position).toString()
                    for (i in projectGrMap) {
                        if (i.value == selectedValue) {
                            tasksTypeId = i.key
                        }
                    }
                }
                override fun onNothingSelected(parent: AdapterView<*>?) {
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    fun showDate() {
        DatePickerDialog(
            requireContext(),
            R.style.MyDatePickerDialogTheme,
            DatePickerDialog.OnDateSetListener { view, year, m, dayOfMonth ->
                var month = m + 1
                startingDate =
                    (if (dayOfMonth < 10) "0$dayOfMonth." else "$dayOfMonth.") + (if (month < 10) "0$month." else "$month.") + "$year "
                showTime()
            },
            year,
            month - 1,
            day
        ).show()
    }
    fun showTime() {
        TimePickerDialog(
            requireContext(),
            R.style.MyDatePickerDialogTheme,
            TimePickerDialog.OnTimeSetListener { view, hourOfDay, minute ->
                if (startingDate.length == 11) {
                    startingDate += "$hourOfDay:00:00"
                    binding.startingDate.text = startingDate
                }
            },
            hour,
            0,
            true
        ).show()
    }


    override fun setLoading(title: String?) {
        loadingDialog.show(requireActivity().supportFragmentManager, title)
    }

    override fun onError(errorMsg: ErrorResult?) {
        try {
            loadingDialog.dismiss()
        } catch (_: Exception) {
        }
    }

    override fun onSuccess(response: Any?) {
        binding.projectsGR.setSelection(-1)
        binding.taskGrName.setSelection(-1)
        binding.projects.setSelection(-1)
        binding.taskType.setSelection(-1)
        binding.level.text.clear()
        binding.time.text.clear()
        binding.taskName.text.clear()
        binding.description.text.clear()
        projectName = ""
        startingDate = ""
        projectGrId = -1
        projectId = -1
        tasksGrId = -1
        prioritiesId = -1
        tasksTypeId = -1
        time = 0.0
        levelOfComplexity = -1
        description = ""
        try {
            loadingDialog.dismiss()
        } catch (_: Exception) {
        }
        val navHostFragment =
            requireActivity().supportFragmentManager.findFragmentById(R.id.nav_host_fragment_home) as NavHostFragment
        val navController = navHostFragment.navController
        navController.navigate(R.id.dashboardFragment)

    }
}