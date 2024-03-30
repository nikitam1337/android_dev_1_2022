package com.example.roomapp.ui.fragments

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.roomapp.App
import com.example.roomapp.R
import com.example.roomapp.viewmodel.MainViewModel
import com.example.roomapp.databinding.FragmentMainBinding
import com.example.roomapp.viewmodel.State
import kotlinx.coroutines.launch

class MainFragment : Fragment() {
    private val viewModel: MainViewModel by viewModels {
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                val wordDao = (requireContext().applicationContext as App).db.wordDao()
                return MainViewModel(wordDao) as T
            }
        }
    }

    private var _binding: FragmentMainBinding? = null
    private val binding by lazy { _binding!! }

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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.getFirst5Words.collect { list ->
                binding.dataFromBase.text = list.joinToString("\n")
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.state.collect {
                when (it) {
                    State.ERROR -> {
                        binding.buttonAdd.isEnabled = false
                        binding.outlinedTextField.isErrorEnabled = true
                        binding.outlinedTextField.error = resources.getString(R.string.error_text)
                    }

                    State.SUCCESS -> {
                        binding.buttonAdd.isEnabled = true
                        binding.outlinedTextField.isErrorEnabled = false
                    }

                    State.START -> binding.buttonAdd.isEnabled = false
                }
            }
        }

        binding.buttonAdd.setOnClickListener {
            viewModel.onAdd(binding.enteredText.text.toString())
        }

        binding.buttonClear.setOnClickListener {
            viewModel.onDelete()
        }

        binding.enteredText.doOnTextChanged { text, _, _, _ ->
            viewModel.changeState(text)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}