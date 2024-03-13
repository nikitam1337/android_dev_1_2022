package com.example.mvvm

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.mvvm.databinding.FragmentMainBinding
import android.text.Editable
import android.text.TextWatcher

class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
    }

    private val viewModel: MainViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentMainBinding.inflate(inflater, container, false)

        binding.buttonSearch.isEnabled = false

        viewModel.isSearching.observe(viewLifecycleOwner, { isSearching ->
            binding.progress.visibility = if (isSearching) View.VISIBLE else View.GONE
        })

        viewModel.searchResult.observe(viewLifecycleOwner, { result ->
            binding.searchResults.text = result
        })

        binding.searchEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
            }

            // После изменения текста
            override fun afterTextChanged(s: Editable) {
                binding.buttonSearch.isEnabled = s.length >= 3 && !viewModel.isSearchInProgress
            }
        })

        binding.buttonSearch.setOnClickListener {
            val query = binding.searchEditText.text.toString()
            viewModel.onButtonSearchClick(query)
        }

        return binding.root
    }
}