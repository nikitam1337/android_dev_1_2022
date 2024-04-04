package com.example.recyclerview.presentation.fragments

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import com.bumptech.glide.Glide
import com.example.recyclerview.R
import com.example.recyclerview.data.model.MarsPhoto
import com.example.recyclerview.databinding.FragmentMarsPhotoBinding


class MarsPhotoFragment : Fragment() {
    private lateinit var param: MarsPhoto

    private var _binding: FragmentMarsPhotoBinding? = null
    private val binding
        get() = _binding!!


    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param = it.getParcelable(KEY_PARAM, MarsPhoto::class.java)!!
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMarsPhotoBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        showMarsPhoto()
    }

    @SuppressLint("SetTextI18n")
    private fun showMarsPhoto() {
        Glide
            .with(requireContext())
            .load(param.imgSrc)
            .into(binding.image)

        with(binding) {
            rover.text = "Rover: ${param.rover.name}"
            camera.text = "Camera: ${param.camera.name}"
            sol.text = "Sol: ${param.sol}"
            date.text = "Date: ${param.earthDate}"
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        const val KEY_PARAM = "KEY_PARAM"
    }

}