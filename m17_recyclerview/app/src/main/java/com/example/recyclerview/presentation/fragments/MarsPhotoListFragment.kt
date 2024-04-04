package com.example.recyclerview.presentation.fragments

import android.annotation.SuppressLint
import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.recyclerview.R
import com.example.recyclerview.data.model.MarsPhoto
import com.example.recyclerview.databinding.FragmentMarsPhotoListBinding
import com.example.recyclerview.presentation.MarsPhotoAdapter
import com.example.recyclerview.presentation.viewmodels.MarsPhotoListViewModel
import com.example.recyclerview.presentation.viewmodels.MarsPhotoListViewModelFactory
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@AndroidEntryPoint
class MarsPhotoListFragment : Fragment() {
    private var _binding: FragmentMarsPhotoListBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var marsPhotoListViewModelFactory: MarsPhotoListViewModelFactory
    private val marsPhotoListViewModel: MarsPhotoListViewModel by viewModels { marsPhotoListViewModelFactory }

    private val marsPhotoAdapter = MarsPhotoAdapter { marsPhoto -> onItemClick(marsPhoto) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMarsPhotoListBinding.inflate(inflater, container, false)
        return binding.root
    }

    @SuppressLint("ResourceType")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        createMarsPhotoAdapter()
    }

    private fun createMarsPhotoAdapter() {
        binding.recyclerView.adapter = marsPhotoAdapter
        takeListPhotoMars()
    }

    private fun takeListPhotoMars() {
        marsPhotoListViewModel.listPhotoMars.onEach {
            marsPhotoAdapter.setList(it.photos)
        }.launchIn(viewLifecycleOwner.lifecycleScope)
    }

    private fun onItemClick(item: MarsPhoto) {
        val bundle = Bundle().apply {
            putParcelable(MarsPhotoFragment.KEY_PARAM, item)
        }
        findNavController().navigate(R.id.action_ListPhotoFragment_to_ItemPhotoMarsFragment, bundle)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}