package com.example.know_everything

import android.app.ActivityOptions
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.know_everything.databinding.FragmentWelcomeBinding
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.snackbar.Snackbar
import java.text.SimpleDateFormat
import java.util.Calendar

class WelcomeFragment : Fragment() {
    private var _binding: FragmentWelcomeBinding? = null
    private val binding: FragmentWelcomeBinding
        get() {
            return _binding!!
        }

    val dateFormat = SimpleDateFormat("dd.MM.yyyy")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentWelcomeBinding.inflate(inflater)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        binding.buttonStart.setOnClickListener {
            findNavController().navigate(R.id.action_WelcomeFragment_to_SurveyFragment)
        }

        val calendar = Calendar.getInstance()
        binding.buttonDateOfBirth.setOnClickListener {
            val dateDialog = MaterialDatePicker.Builder.datePicker()
                .setTitleText(resources.getString(R.string.open_dateBirth_picker))
                .build()
            dateDialog.addOnPositiveButtonClickListener { time ->
                calendar.timeInMillis = time
                Snackbar.make(binding.buttonDateOfBirth, dateFormat.format(calendar.time), Snackbar.LENGTH_SHORT).show()
            }
            dateDialog.show(parentFragmentManager, "Date")
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}