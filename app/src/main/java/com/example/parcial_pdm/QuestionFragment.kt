package com.example.parcial_pdm

import android.os.Bundle
import android.os.CountDownTimer
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ProgressBar
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController

class QuestionFragment : Fragment() {

    private lateinit var questions: List<Question>
    private var currentQuestionIndex = 0
    private var score = 0
    private var timer: CountDownTimer? = null
    private var isFirstQuestion = true

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_question, container, false)

        questions = listOf(
            Question(R.string.question_1, R.array.question_1_options, 0, R.string.question_1_explanation),
            Question(R.string.question_2, R.array.question_2_options, 1, R.string.question_2_explanation),
            Question(R.string.question_3, R.array.question_3_options, 3, R.string.question_3_explanation),
            Question(R.string.question_4, R.array.question_4_options, 2, R.string.question_4_explanation),
            Question(R.string.question_5, R.array.question_5_options, 1, R.string.question_5_explanation),
            Question(R.string.question_6, R.array.question_6_options, 0, R.string.question_6_explanation),
            Question(R.string.question_7, R.array.question_7_options, 3, R.string.question_7_explanation),
            Question(R.string.question_8, R.array.question_8_options, 0, R.string.question_8_explanation),
            Question(R.string.question_9, R.array.question_9_options, 2, R.string.question_9_explanation),
            Question(R.string.question_10, R.array.question_10_options, 0, R.string.question_10_explanation)
        )

        displayQuestion(view)

        view.findViewById<Button>(R.id.submitButton).setOnClickListener {
            checkAnswer(view)
        }

        return view
    }

    private fun displayQuestion(view: View) {
        if (currentQuestionIndex < questions.size) {
            val question = questions[currentQuestionIndex]
            view.findViewById<TextView>(R.id.questionTextView).setText(question.questionId)
            val optionsRadioGroup = view.findViewById<RadioGroup>(R.id.optionsRadioGroup)
            optionsRadioGroup.removeAllViews()
            val options = resources.getStringArray(question.optionsArrayId)
            optionsRadioGroup.clearCheck()
            options.forEach { option ->
                val radioButton = RadioButton(context)
                radioButton.text = option
                optionsRadioGroup.addView(radioButton)
            }

            val progressBar = view.findViewById<ProgressBar>(R.id.progressBar)
            progressBar.max = questions.size
            progressBar.progress = currentQuestionIndex + 1

            startTimer(view)
        } else {
            navigateToResult()
        }
    }

    private fun startTimer(view: View) {
        val timerTextView = view.findViewById<TextView>(R.id.timerTextView)

        timer?.cancel()

        timer = object : CountDownTimer(15000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                timerTextView.text = "Time left: ${millisUntilFinished / 1000}s"
            }

            override fun onFinish() {
                moveToNextQuestion()
            }
        }.start()
    }

    private fun checkAnswer(view: View) {
        val selectedId = view.findViewById<RadioGroup>(R.id.optionsRadioGroup).checkedRadioButtonId

        if (selectedId != -1) {
            timer?.cancel()
            val selectedAnswer = view.findViewById<RadioButton>(selectedId)
            val answerIndex = view.findViewById<RadioGroup>(R.id.optionsRadioGroup).indexOfChild(selectedAnswer)
            if (answerIndex == questions[currentQuestionIndex].correctAnswerIndex) {
                score++
            }
            navigateToAnswer(answerIndex == questions[currentQuestionIndex].correctAnswerIndex)
        }

    }

    private fun navigateToAnswer(isCorrect: Boolean) {
        val action = QuestionFragmentDirections.actionQuestionFragmentToAnswerFragment(
            questionId = questions[currentQuestionIndex].questionId,
            explanationId = questions[currentQuestionIndex].explanationId,
            isCorrect = isCorrect
        )
        findNavController().navigate(action)
    }

    private fun moveToNextQuestion() {
        currentQuestionIndex++
        if (currentQuestionIndex < questions.size) {
            view?.let { displayQuestion(it) }
        } else {
            navigateToResult()
        }
    }

    private fun navigateToResult() {
        val action = QuestionFragmentDirections.actionQuestionFragmentToResultFragment(score, questions.size)
        findNavController().navigate(action)
    }

    override fun onResume() {
        super.onResume()
        if (isFirstQuestion) {
            isFirstQuestion = false
            displayQuestion(requireView())
        } else {
            moveToNextQuestion()
        }
    }

    override fun onPause() {
        super.onPause()
        timer?.cancel()
    }
}

data class Question(
    val questionId: Int,
    val optionsArrayId: Int,
    val correctAnswerIndex: Int,
    val explanationId: Int
)