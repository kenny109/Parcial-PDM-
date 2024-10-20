package com.example.parcial_pdm

import android.os.Bundle
import android.os.CountDownTimer
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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

        // Mostrar el resultado (correcto o incorrecto)
        view.findViewById<TextView>(R.id.resultTextView).text =
            if (args.isCorrect) getString(R.string.correct_text) else getString(R.string.incorrect_text)

        // Mostrar la explicación de la respuesta
        view.findViewById<TextView>(R.id.explanationTextView).setText(args.explanationId)

        // Iniciar un temporizador para cambiar automáticamente a la siguiente pregunta en 5 segundos
        object : CountDownTimer(5000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
            }

            override fun onFinish() {
                findNavController().popBackStack()
            }
        }.start()

        return view
    }
}
