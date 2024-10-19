package com.example.parcial_pdm


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs

class AnswerFragment : Fragment() {

    private val args: AnswerFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_answer, container, false)

        view.findViewById<TextView>(R.id.resultTextView).text =
            if (args.isCorrect) getString(R.string.correct_text) else getString(R.string.incorrect_text)

        view.findViewById<TextView>(R.id.explanationTextView).setText(args.explanationId)

        view.findViewById<Button>(R.id.nextButton).setOnClickListener {
            findNavController().popBackStack()
            (parentFragment as? QuestionFragment)?.nextQuestion()
        }

        return view
    }
}