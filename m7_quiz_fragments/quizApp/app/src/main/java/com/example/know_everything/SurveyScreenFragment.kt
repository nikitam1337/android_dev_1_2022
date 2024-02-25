package com.example.know_everything

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.know_everything.databinding.FragmentSurveyScreenBinding
import com.example.super_quiz.quiz.QuizStorage

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class SurveyScreenFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private var _binding: FragmentSurveyScreenBinding? = null
    private val binding: FragmentSurveyScreenBinding
        get() {
            return _binding!!
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentSurveyScreenBinding.inflate(inflater)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnBack.setOnClickListener {
            findNavController().navigate(R.id.action_SurveyFragment_to_WelcomeFragment)
        }
        binding.btnToResult.setOnClickListener {
            val bundle = Bundle().apply {
                putInt(
                    "param1",
                    getIndexOfAnswer(binding.radioGroupQuestion1.checkedRadioButtonId, 1)
                )
                putInt(
                    "param2",
                    getIndexOfAnswer(binding.radioGroupQuestion2.checkedRadioButtonId, 2)
                )
                putInt(
                    "param3",
                    getIndexOfAnswer(binding.radioGroupQuestion3.checkedRadioButtonId, 3)
                )
            }
            findNavController().navigate(
                R.id.action_SurveyFragment_to_ResultFragment, args = bundle
            )
        }

        //filling textViews
        binding.tvFirstQuestion.text =
            QuizStorage.getQuiz(QuizStorage.Locale.Ru).questions[0].question
        binding.tvSecondQuestion.text =
            QuizStorage.getQuiz(QuizStorage.Locale.Ru).questions[1].question
        binding.tvThirdQuestion.text =
            QuizStorage.getQuiz(QuizStorage.Locale.Ru).questions[2].question
        //filling radioButtons
        binding.optAnswer1Q1.text =
            QuizStorage.getQuiz(QuizStorage.Locale.Ru).questions[0].answers[0]
        binding.optAnswer2Q1.text =
            QuizStorage.getQuiz(QuizStorage.Locale.Ru).questions[0].answers[1]
        binding.optAnswer3Q1.text =
            QuizStorage.getQuiz(QuizStorage.Locale.Ru).questions[0].answers[2]
        binding.optAnswer4Q1.text =
            QuizStorage.getQuiz(QuizStorage.Locale.Ru).questions[0].answers[3]
        binding.optAnswer1Q2.text =
            QuizStorage.getQuiz(QuizStorage.Locale.Ru).questions[1].answers[0]
        binding.optAnswer2Q2.text =
            QuizStorage.getQuiz(QuizStorage.Locale.Ru).questions[1].answers[1]
        binding.optAnswer3Q2.text =
            QuizStorage.getQuiz(QuizStorage.Locale.Ru).questions[1].answers[2]
        binding.optAnswer4Q2.text =
            QuizStorage.getQuiz(QuizStorage.Locale.Ru).questions[1].answers[3]
        binding.optAnswer1Q3.text =
            QuizStorage.getQuiz(QuizStorage.Locale.Ru).questions[2].answers[0]
        binding.optAnswer2Q3.text =
            QuizStorage.getQuiz(QuizStorage.Locale.Ru).questions[2].answers[1]
        binding.optAnswer3Q3.text =
            QuizStorage.getQuiz(QuizStorage.Locale.Ru).questions[2].answers[2]
        binding.optAnswer4Q3.text =
            QuizStorage.getQuiz(QuizStorage.Locale.Ru).questions[2].answers[3]
    }

    private fun getIndexOfAnswer(id: Int, radioGroup: Int): Int {
        var resultIndex = 0
        when (radioGroup) {
            1 -> {
                when (id) {
                    binding.optAnswer1Q1.id -> resultIndex = 0
                    binding.optAnswer2Q1.id -> resultIndex = 1
                    binding.optAnswer3Q1.id -> resultIndex = 2
                    binding.optAnswer4Q1.id -> resultIndex = 3
                }
            }

            2 -> {
                when (id) {
                    binding.optAnswer1Q2.id -> resultIndex = 0
                    binding.optAnswer2Q2.id -> resultIndex = 1
                    binding.optAnswer3Q2.id -> resultIndex = 2
                    binding.optAnswer4Q2.id -> resultIndex = 3
                }
            }

            3 -> {
                when (id) {
                    binding.optAnswer1Q3.id -> resultIndex = 0
                    binding.optAnswer2Q3.id -> resultIndex = 1
                    binding.optAnswer3Q3.id -> resultIndex = 2
                    binding.optAnswer4Q3.id -> resultIndex = 3
                }
            }
        }
        return resultIndex
    }
}