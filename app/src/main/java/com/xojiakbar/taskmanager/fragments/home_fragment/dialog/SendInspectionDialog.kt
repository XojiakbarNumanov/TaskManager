package com.xojiakbar.taskmanager.fragments.home_fragment.dialog

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.app.Activity.RESULT_OK
import android.app.Dialog
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.FileProvider
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.squareup.picasso.Picasso
import com.xojiakbar.taskmanager.R
import com.xojiakbar.taskmanager.adapter.RecyclerAdapter
import com.xojiakbar.taskmanager.api.result.ErrorResult
import com.xojiakbar.taskmanager.data.beans.task_bean.Task
import com.xojiakbar.taskmanager.databinding.DialogSendInspectionBinding
import com.xojiakbar.taskmanager.databinding.ItemImgBinding
import com.xojiakbar.taskmanager.fragments.home_fragment.dialog.item.ItemUIController
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import kotlin.jvm.Throws


class SendInspectionDialog(task: Task) : DialogFragment(), SendInspectionDialogRouter {
    private val CHOOSE_GALLERY = 0
    private val CHOOSE_CAMERA = 1
    private val CAMERA_RESULT_CODE = 1
    var vFilename: String = ""
    private lateinit var currentPhotoPath :String
    var imageFile: ArrayList<File> = ArrayList()
    private var adapter: RecyclerAdapter<File>? = null
    lateinit var task: Task
    private var viewModel: SendInspectionDialogViewModel? = null
    private var cameraActivityResultLauncher=
    registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == RESULT_OK) {
            val data: Intent? = result.data
            // Process the captured image
            val imageUri: Uri? = data?.data
            Toast.makeText(requireContext(), data?.data.toString(), Toast.LENGTH_SHORT).show()
        }
    }


    private var _binding: DialogSendInspectionBinding? = null
    private val binding get() = _binding
    private val galleryActivityResultLauncher: ActivityResultLauncher<Intent> =
        registerForActivityResult(
            ActivityResultContracts.StartActivityForResult(),
            galleryPhotosActivityResult()
        )


    init {
        task.task_statuses_id = 6
        this.task = task
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = DialogSendInspectionBinding.inflate(inflater, container, false)
        adapter = RecyclerAdapter(R.layout.item_img, getAdapterListener())!!
        binding?.recyclerView?.layoutManager = GridLayoutManager(requireContext(), 3)
        viewModel = ViewModelProvider(this)[SendInspectionDialogViewModel::class.java]
        binding?.recyclerView?.adapter = adapter
        viewModel?.router = this
        binding?.addFile?.setOnClickListener {
            onAddPhoto()
        }
        binding?.close?.setOnClickListener {
            dismiss()
        }
        binding?.save?.setOnClickListener {
            viewModel?.uploadResources(imageFile, task, 0)
            dismiss()
        }
        return binding?.root
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return super.onCreateDialog(savedInstanceState)

    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    fun onAddPhoto() {
        showChooseDialog()
    }

    fun requestPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (!(ActivityCompat.checkSelfPermission(
                    requireContext(),
                    Manifest.permission.READ_MEDIA_IMAGES
                ) == PackageManager.PERMISSION_GRANTED
                        && ActivityCompat.checkSelfPermission(
                    requireContext(),
                    Manifest.permission.CAMERA
                ) == PackageManager.PERMISSION_GRANTED)
            ) {

                ActivityCompat.requestPermissions(
                    requireActivity(),
                    arrayOf(Manifest.permission.READ_MEDIA_IMAGES, Manifest.permission.CAMERA),
                    1
                )
            }
        } else {
            if (!(ActivityCompat.checkSelfPermission(
                    requireContext(),
                    Manifest.permission.READ_EXTERNAL_STORAGE
                ) == PackageManager.PERMISSION_GRANTED
                        && ActivityCompat.checkSelfPermission(
                    requireContext(),
                    Manifest.permission.CAMERA
                ) == PackageManager.PERMISSION_GRANTED)
            ) {

                ActivityCompat.requestPermissions(
                    requireActivity(),
                    arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.CAMERA),
                    1
                )
            }
        }
    }

    fun showChooseDialog() {
        requestPermission()
        MaterialAlertDialogBuilder(requireContext())
            .setTitle(getString(R.string.choose))
            .setItems(arrayOf("Galary", "Camera")) { dialog, which ->
                when (which) {
                    CHOOSE_GALLERY -> chooseGallery()
                    CHOOSE_CAMERA -> {
                        chooseCamera()
                    }
                }
            }.show()
    }

    private fun chooseCamera() {
        val file = try {
            createImageFile()
        }catch (e:Exception)
        {
            null
        }
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        intent.resolveActivity(requireContext().packageManager)
        val uriForFile= file?.let {
            FileProvider.getUriForFile(requireContext(),"com.xojiakbar.taskmanager.provider",
                it
            )
        }
        intent.putExtra(MediaStore.EXTRA_OUTPUT,uriForFile)
        startActivityForResult(intent ,CAMERA_RESULT_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (::currentPhotoPath.isInitialized)
        {
            imageFile.add(File(currentPhotoPath))
            adapter?.setList(imageFile)
            adapter?.notifyDataSetChanged()
        }
    }
    @Throws(IOException::class)
    private fun createImageFile() : File{
        val timeStamp : String = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.US).format(Date())
        val storageDir : File = requireContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES)!!
        return File.createTempFile("${System.currentTimeMillis()}",".jpg",storageDir).apply {
            currentPhotoPath = absolutePath
        }
    }


    fun chooseGallery() {
        requestPermission()
        val pickPhoto = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        pickPhoto.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
        pickPhoto.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        galleryActivityResultLauncher.launch(pickPhoto)

    }


    private fun galleryPhotosActivityResult(): ActivityResultCallback<ActivityResult> {
        return ActivityResultCallback<ActivityResult> { result: ActivityResult ->
            if (result.resultCode == Activity.RESULT_OK) {
                try {
                    val data = result.data
                    if (data != null) {
                        val clipData = data.clipData
                        if (clipData != null) {
                            val count = clipData.itemCount
                            for (i in 0 until count) {
                                val selectedImage = clipData.getItemAt(i).uri
                                imageFile.add(File(getPath(selectedImage)!!))
                                adapter?.setList(imageFile)
                                adapter?.notifyDataSetChanged()
                            }
                        } else {
                            val selectedImage = data.data
                            imageFile.add(File(getPath(selectedImage)!!))
                            adapter?.setList(imageFile)
                            adapter?.notifyDataSetChanged()
                        }
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }

    }

    fun getPath(uri: Uri?): String? {
        val projection = arrayOf(MediaStore.Images.Media.DATA)
        val cursor = requireActivity().contentResolver.query(uri!!, projection, null, null, null)
        cursor!!.moveToFirst()
        val columnIndex = cursor.getColumnIndex(projection[0])
        val filePath = cursor.getString(columnIndex)
        cursor.close()
        return filePath
    }


    override fun setLoading(title: String?) {
    }

    override fun onSuccess(response: Any?) {

    }

    override fun onError(errorMsg: ErrorResult?) {
    }

    private fun getAdapterListener(): RecyclerAdapter.AdapterListener<File> {
        return object : RecyclerAdapter.AdapterListener<File> {

            override fun setController(dataBinding: ViewDataBinding?) {
                val controller = ItemUIController(requireContext())
                controller.router = this@SendInspectionDialog
                (dataBinding as ItemImgBinding).controller = controller
            }

            @SuppressLint("NotifyDataSetChanged")
            override fun bindItem(
                dataBinding: ViewDataBinding?,
                item: File,
                position: Int?
            ) {
                (dataBinding as ItemImgBinding).controller!!.setimage(item)
                Picasso.get().load(
                    item.absoluteFile
                ).into(dataBinding.images)
                dataBinding.remove.setOnClickListener {
                    imageFile.remove(imageFile[position!!])
                    adapter?.setList(imageFile)
                    adapter?.notifyDataSetChanged()
                }
                dataBinding.executePendingBindings()
            }
        }
    }
}