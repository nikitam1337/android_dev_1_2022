package com.example.myapp14.view

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.example.myapp14.databinding.FragmentMainBinding
import com.example.myapp14.viewmodel.MainViewModel
import com.example.myapp14.viewmodel.State
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


class MainFragment : Fragment() {

    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!
    private val viewModel: MainViewModel by viewModels()

    companion object {
        fun newInstance() = MainFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (savedInstanceState == null) {
            viewModel.getUser()
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.state.collectLatest { state ->
                when (state) {
                    is State.Loading -> {
                        binding.progress.isVisible = true
                    }

                    is State.Success -> {
                        binding.progress.isVisible = false
                    }

                    is State.Error -> {
                        binding.progress.isVisible = false
                        Toast.makeText(context, state.nameError, Toast.LENGTH_LONG).show()
                    }
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.user.collect { personsList ->
                Log.d("@@@", "${personsList}")
                personsList?.results?.firstOrNull()?.let { user ->
                    binding.gender.text = "Gender: ${user.gender}"
                    binding.name.text = "Name: ${user.name.first} ${user.name.last}"
                    binding.location.text =
                        "Location: ${user.location.street.name}, ${user.location.city}, ${user.location.state}, ${user.location.country}"
                    binding.email.text = "Email: ${user.email}"
                    binding.dob.text = "Date of Birth: ${user.dob.age}"
                    binding.phone.text = "Phone: ${user.phone}"
                    Glide.with(this@MainFragment)
                        .load(user.picture.large)
                        .into(binding.imageView)
                }
            }
        }

        binding.buttonUpdate.setOnClickListener {
            viewModel.getUser()
        }

        binding.buttonReload.setOnClickListener {
            viewModel.getUser()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}