package com.example.know_everything

import android.animation.AnimatorInflater
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.know_everything.databinding.FragmentResultBinding
import com.example.super_quiz.quiz.Quiz
import com.example.super_quiz.quiz.QuizStorage
import java.util.Locale

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"
private const val ARG_PARAM3 = "param3"

/**
 * A simple [Fragment] subclass.
 * Use the [ResultScreenFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ResultScreenFragment : Fragment() {

    private var _binding: FragmentResultBinding? = null
    private val binding get() = _binding!!

    // TODO: Rename and change types of parameters
    private var param1: Int = 0
    private var param2: Int = 0
    private var param3: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getInt(ARG_PARAM1)
            param2 = it.getInt(ARG_PARAM2)
            param3 = it.getInt(ARG_PARAM3)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentResultBinding.inflate(inflater)
        binding.textviewResult.text = getResultText(param1, param2, param3)
        binding.buttonRestart.setOnClickListener {
            findNavController().navigate(R.id.action_ResultFragment_to_SurveyFragment)
        }

        ObjectAnimator.ofArgb(
            binding.textViewTitleResultFragment,
            "textColor", Color.BLACK, Color.parseColor("#9E13AD")
        ).apply {
            duration = 5000
            repeatCount = ObjectAnimator.INFINITE
            repeatMode = ObjectAnimator.REVERSE
        }.start()

        (AnimatorInflater.loadAnimator(
            this.context,
            R.animator.custom_multianimation
        ) as AnimatorSet).apply {
            setTarget(binding.buttonRestart)
            start()
        }





        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun getResultText(
        param1: Int?, param2: Int?, param3: Int?
    ): String {
        val quiz = QuizStorage.getQuiz(Locale.getDefault())
        return "${getString(R.string.survey_results)}\n" +
                "${getString(R.string.first_question)} ${quiz.questions[0].question}\n" +
                "${getString(R.string.answer)} ${quiz.questions[0].answers[param1!!]}\n" +
                "${getString(R.string.feedback)} ${quiz.questions[0].feedback[param1]}\n" +
                "\n" +
                "${getString(R.string.second_question)} ${quiz.questions[1].question}\n" +
                "${getString(R.string.answer)} ${quiz.questions[1].answers[param2!!]}\n" +
                "${getString(R.string.feedback)} ${quiz.questions[1].feedback[param2]}\n" +
                "\n" +
                "${getString(R.string.third_question)} ${quiz.questions[2].question}\n" +
                "${getString(R.string.answer)} ${quiz.questions[2].answers[param3!!]}\n" +
                "${getString(R.string.feedback)} ${quiz.questions[2].feedback[param3]}\n"

    }
}