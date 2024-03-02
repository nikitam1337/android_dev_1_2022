package com.example.know_everything

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.know_everything.databinding.FragmentResultBinding
import com.example.know_everything.databinding.FragmentSurveyScreenBinding
import com.example.super_quiz.quiz.QuizStorage

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
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun getResultText(
        param1: Int?, param2: Int?, param3: Int?
    ): String {
        return "Результаты опроса :\n" +
                "Первый вопрос: ${QuizStorage.getQuiz(QuizStorage.Locale.Ru).questions[0].question}\n" +
                "Ответ: ${QuizStorage.getQuiz(QuizStorage.Locale.Ru).questions[0].answers[param1!!]}\n" +
                "Feedback: ${QuizStorage.getQuiz(QuizStorage.Locale.Ru).questions[0].feedback[param1!!]}\n" +
                "\n"+
                "Второй вопрос: ${QuizStorage.getQuiz(QuizStorage.Locale.Ru).questions[1].question}\n" +
                "Ответ: ${QuizStorage.getQuiz(QuizStorage.Locale.Ru).questions[1].answers[param2!!]}\n" +
                "Feedback: ${QuizStorage.getQuiz(QuizStorage.Locale.Ru).questions[1].feedback[param3!!]}\n" +
                "\n"+
                "Третий вопрос: ${QuizStorage.getQuiz(QuizStorage.Locale.Ru).questions[2].question}\n" +
                "Ответ: ${QuizStorage.getQuiz(QuizStorage.Locale.Ru).questions[2].answers[param3!!]}\n" +
                "Feedback: ${QuizStorage.getQuiz(QuizStorage.Locale.Ru).questions[2].feedback[param3!!]}\n"

    }
}