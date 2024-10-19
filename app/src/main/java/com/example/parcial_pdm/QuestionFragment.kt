package com.example.parcial_pdm

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController

class QuestionFragment : Fragment() {

    private lateinit var questions: List<Question>
    private var currentQuestionIndex = 0
    private var score = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_question, container, false)

        questions = listOf(
            Question(R.string.question_1, R.array.question_1_options, 0, R.string.question_1_explanation),
            Question(R.string.question_2, R.array.question_2_options, 0, R.string.question_2_explanation),
            // Add more questions here
        )

        displayQuestion(view)

        view.findViewById<Button>(R.id.submitButton).setOnClickListener {
            val selectedId = view.findViewById<RadioGroup>(R.id.optionsRadioGroup).checkedRadioButtonId
            if (selectedId != -1) {
                val selectedAnswer = view.findViewById<RadioButton>(selectedId)
                val answerIndex = view.findViewById<RadioGroup>(R.id.optionsRadioGroup).indexOfChild(selectedAnswer)
                if (answerIndex == questions[currentQuestionIndex].correctAnswerIndex) {
                    score++
                }
                val action = QuestionFragmentDirections.actionQuestionFragmentToAnswerFragment(
                    questionId = questions[currentQuestionIndex].questionId,
                    explanationId = questions[currentQuestionIndex].explanationId,
                    isCorrect = answerIndex == questions[currentQuestionIndex].correctAnswerIndex
                )
                findNavController().navigate(action)
            }
        }

        return view
    }

    private fun displayQuestion(view: View) {
        val question = questions[currentQuestionIndex]
        view.findViewById<TextView>(R.id.questionTextView).setText(question.questionId)
        val optionsRadioGroup = view.findViewById<RadioGroup>(R.id.optionsRadioGroup)
        optionsRadioGroup.removeAllViews()
        val options = resources.getStringArray(question.optionsArrayId)
        options.forEach { option ->
            val radioButton = RadioButton(context)
            radioButton.text = option
            optionsRadioGroup.addView(radioButton)
        }
    }

    fun nextQuestion() {
        currentQuestionIndex++
        if (currentQuestionIndex < questions.size) {
            displayQuestion(requireView())
        } else {
            val action = QuestionFragmentDirections.actionQuestionFragmentToResultFragment(score, questions.size)
            findNavController().navigate(action)
        }
    }
}

data class Question(
    val questionId: Int,
    val optionsArrayId: Int,
    val correctAnswerIndex: Int,
    val explanationId: Int
)