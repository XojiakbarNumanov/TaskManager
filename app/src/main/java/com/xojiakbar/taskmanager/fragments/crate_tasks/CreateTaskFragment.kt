package com.xojiakbar.taskmanager.fragments.crate_tasks

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.DatePicker
import android.widget.TimePicker
import android.widget.Toast
import androidx.core.view.get
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.xojiakbar.taskmanager.R
import com.xojiakbar.taskmanager.Utils.Preferences
import com.xojiakbar.taskmanager.data.beans.task_bean.Task
import com.xojiakbar.taskmanager.data.beans.task_bean.Tasks
import com.xojiakbar.taskmanager.databinding.FragmentCreateTaskBinding
import java.time.Year
import java.util.Calendar

class CreateTaskFragment : Fragment(), DatePickerDialog.OnDateSetListener,
    TimePickerDialog.OnTimeSetListener {

    private var _binding: FragmentCreateTaskBinding? = null
    private val binding get() = _binding!!
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
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
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
            DatePickerDialog(requireContext(), this, year, month - 1, day).show()
        }

        binding.btnSend.setOnClickListener {
            sentTask()
        }
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
        levelOfComplexity = if (binding.level.text.isEmpty() ) 0 else binding.level.text.toString().toInt()
        time = if (binding.time.text.isEmpty() ) 0.0 else binding.time.text.toString().toDouble()
        description = binding.description.text.toString()
        projectName = binding.taskName.text.toString()
        if (projectGrId == -1) {
            Toast.makeText(requireContext(), "Loyiha guruhini nomini kiriting", Toast.LENGTH_SHORT)
                .show()
            return
        } else if (projectId == -1) {
            Toast.makeText(requireContext(), "Loyiha nomini kiriting", Toast.LENGTH_SHORT)
                .show()
            return
        } else if (tasksGrId == -1) {
            Toast.makeText(
                requireContext(),
                "Topshiriq guruhini nomini kiriting",
                Toast.LENGTH_SHORT
            )
                .show()
            return
        } else if (tasksTypeId == -1) {
            Toast.makeText(requireContext(), "Task turini kiriting", Toast.LENGTH_SHORT)
                .show()
            return
        } else if (prioritiesId == -1) {
            Toast.makeText(requireContext(), "Muhumlik darajasini kiriting", Toast.LENGTH_SHORT)
                .show()
            return
        } else if (projectName == "") {
            Toast.makeText(requireContext(), "Topshiriq nomini kiriting", Toast.LENGTH_SHORT)
                .show()
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
        task.file_ids  = arrayListOf()
        viewModel?.createTask(task)
    }


    private fun initResOrg() {
        viewModel?.getProjectGr()?.observe(viewLifecycleOwner) {
            val projectGrMap = HashMap<String, Int>()
            for (i in it) {
                projectGrMap[i.name!!] = i.id!!
            }
            val adapterAuto = ArrayAdapter<String>(
                requireContext(),
                R.layout.spinner_dropdown_item,
                projectGrMap.keys.toMutableList()
            )
            binding.projectsGR.setAdapter(adapterAuto)
            binding.projectsGR.threshold = 1
            binding.projectsGR.setOnFocusChangeListener { view, b -> binding.projectsGR.showDropDown() }
            binding.projectsGR.setOnClickListener { view -> binding.projectsGR.showDropDown() }
            binding.projectsGR.setOnItemClickListener { adapterView, view, i, l ->
                projectGrId = projectGrMap[binding.projectsGR.text.toString()]!!
                binding.taskGrName.text.clear()
                binding.projects.text.clear()
                binding.taskType.text.clear()
                projectId = -1
                tasksGrId = -1
                tasksTypeId = -1
                initProject(projectGrId)
            }
        }
    }

    private fun initProject(projectGroupId: Int) {
        viewModel?.getProject(projectGroupId)?.observe(viewLifecycleOwner) {
            val projectGrMap = HashMap<String, Int>()
            for (i in it) {
                projectGrMap[i.name!!] = i.id!!
            }
            val adapterAuto = ArrayAdapter<String>(
                requireContext(),
                R.layout.spinner_dropdown_item,
                projectGrMap.keys.toMutableList()
            )
            binding.projects.setAdapter(adapterAuto)
            binding.projects.threshold = 1
            binding.projects.setOnFocusChangeListener { view, b -> binding.projects.showDropDown() }
            binding.projects.setOnClickListener { view -> binding.projects.showDropDown() }
            binding.projects.setOnItemClickListener { adapterView, view, i, l ->
                projectId = projectGrMap[binding.projects.text.toString()]!!
                binding.taskType.text.clear()
                binding.taskGrName.text.clear()
                tasksGrId = -1
                tasksTypeId = -1
                initTaskGrName(projectId)
            }
        }
    }

    private fun initTaskGrName(projectId: Int) {
        viewModel?.getTaskGrName(projectId)?.observe(viewLifecycleOwner) {
            val projectGrMap = HashMap<String, Int>()
            for (i in it) {
                projectGrMap[i.name!!] = i.id!!
            }
            val adapterAuto = ArrayAdapter<String>(
                requireContext(),
                R.layout.spinner_dropdown_item,
                projectGrMap.keys.toMutableList()
            )
            binding.taskGrName.setAdapter(adapterAuto)
            binding.taskGrName.threshold = 1
            binding.taskGrName.setOnFocusChangeListener { view, b -> binding.taskGrName.showDropDown() }
            binding.taskGrName.setOnClickListener { view -> binding.taskGrName.showDropDown() }
            binding.taskGrName.setOnItemClickListener { adapterView, view, i, l ->
                tasksGrId = projectGrMap[binding.taskGrName.text.toString()]!!
                tasksTypeId = -1
                binding.taskType.text.clear()
                initTaskType()
            }
        }
    }

    private fun initTaskType() {
        viewModel?.gettaskType()?.observe(viewLifecycleOwner) {
            val projectGrMap = HashMap<String, Int>()
            for (i in it) {
                projectGrMap[i.name!!] = i.id!!
            }
            val adapterAuto = ArrayAdapter<String>(
                requireContext(),
                R.layout.spinner_dropdown_item,
                projectGrMap.keys.toMutableList()
            )
            binding.taskType.setAdapter(adapterAuto)
            binding.taskType.threshold = 1
            binding.taskType.setOnFocusChangeListener { view, b -> binding.taskType.showDropDown() }
            binding.taskType.setOnClickListener { view -> binding.taskType.showDropDown() }
            binding.taskType.setOnItemClickListener { adapterView, view, i, l ->
                tasksTypeId = projectGrMap[binding.taskType.text.toString()]!!
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onDateSet(view: DatePicker?, year: Int, m: Int, dayOfMonth: Int) {
        var month = m + 1
        startingDate =
            (if (dayOfMonth < 10) "0$dayOfMonth." else "$dayOfMonth.") + (if (month < 10) "0$month." else "$month.") + "$year "
        TimePickerDialog(requireContext(), this, hour, 0, true).show()
    }

    override fun onTimeSet(view: TimePicker?, hourOfDay: Int, minute: Int) {
        if (startingDate.length == 11) {
            startingDate += "$hourOfDay:00:00"
            binding.startingDate.text = startingDate
        }
    }
}