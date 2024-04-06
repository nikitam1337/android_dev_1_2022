package com.example.attractions.presentation.fragments

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.attractions.R
import com.example.attractions.data.model.Photo
import com.example.attractions.databinding.FragmentTakePhotoBinding
import com.example.attractions.presentation.viewmodels.ListPhotoAdapter
import com.example.attractions.presentation.viewmodels.TakePhotoViewModel
import com.example.attractions.presentation.viewmodels.TakePhotoViewModelFactory
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Locale
import java.util.concurrent.Executor
import javax.inject.Inject

@AndroidEntryPoint
class TakePhotoFragment : Fragment() {

    private var imageCapture: ImageCapture? = null
    private lateinit var executor: Executor
    private val launcher =
        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { map ->
            if (map.values.all { it }) {
                startCamera()
            }
        }

    private var _binding: FragmentTakePhotoBinding? = null
    private val binding
        get() = _binding!!

    @Inject
    lateinit var takePhotoViewModelFactory: TakePhotoViewModelFactory
    private val viewModel: TakePhotoViewModel by viewModels { takePhotoViewModelFactory }

    @SuppressLint("WeekBasedYear")
    private val name =
        SimpleDateFormat(FILE_NAME_FORMAT, Locale.getDefault()).format(System.currentTimeMillis())

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTakePhotoBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        executor = ContextCompat.getMainExecutor(requireContext())
        checkPermission()
        clickButtonTakeNewPhoto()
        clickButtonSavePhoto()

    }

    private fun checkPermission() {
        val isAllGranted = REQUEST_PERMISSIONS.all { permission ->
            ContextCompat.checkSelfPermission(
                requireContext(),
                permission
            ) == PackageManager.PERMISSION_GRANTED
        }
        if (isAllGranted) {
            startCamera()
        } else {
            launcher.launch(REQUEST_PERMISSIONS)
        }
    }

    private fun clickButtonTakeNewPhoto() {
        binding.buttonTakeNewPhoto.setOnClickListener {
            binding.image.setImageDrawable(null)
            takePhoto()
        }
    }

    private fun clickButtonSavePhoto() {
        binding.buttonSavePhoto.setOnClickListener {
            findNavController().navigate(R.id.action_TakePhotoFragment_to_ListPhotoFragment)
        }
    }


    private fun takePhoto() {
        val imageCapture = imageCapture ?: return

        val contentValues = ContentValues().apply {
            put(MediaStore.MediaColumns.DISPLAY_NAME, name)
            put(MediaStore.MediaColumns.MIME_TYPE, VALUE_TYPE)
        }

        val outputOptions = ImageCapture.OutputFileOptions.Builder(
            requireContext().contentResolver,
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
            contentValues
        ).build()

        lifecycleScope.launch {
            imageCapture.takePicture(
                outputOptions,
                executor,
                object : ImageCapture.OnImageSavedCallback {
                    override fun onImageSaved(outputFileResults: ImageCapture.OutputFileResults) {
                        Glide.with(requireActivity())
                            .load(outputFileResults.savedUri)
                            .fitCenter()
                            .into(binding.image)

                        val datePhoto = SimpleDateFormat(FILE_NAME_FORMAT, Locale.getDefault()).format(System.currentTimeMillis())
                        val photo = Photo(viewModel.getUriLastPhoto(requireContext()), datePhoto)
                        viewModel.insertPhoto(photo)
                    }

                    override fun onError(exception: ImageCaptureException) {
                        Toast.makeText(
                            requireContext(),
                            getString(R.string.Error), Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            )
        }
    }


    private fun startCamera() {
        val cameraProviderFuture = ProcessCameraProvider.getInstance(requireContext())
        cameraProviderFuture.addListener({
            val cameraProvider = cameraProviderFuture.get()

            val preview = Preview.Builder().build()
            preview.setSurfaceProvider(binding.viewFinder.surfaceProvider)
            imageCapture = ImageCapture.Builder().build()

            cameraProvider.unbindAll()
            cameraProvider.bindToLifecycle(
                viewLifecycleOwner,
                CameraSelector.DEFAULT_BACK_CAMERA,
                preview,
                imageCapture
            )
        }, executor)
    }


    companion object {
        private const val FILE_NAME_FORMAT = "dd.MM.YYYY HH:mm:ss"
        private const val VALUE_TYPE = "image/jpeg"
        private val REQUEST_PERMISSIONS: Array<String> = buildList {
            add(android.Manifest.permission.CAMERA)
            if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.P) {
                add(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
            }
        }.toTypedArray()
    }
}