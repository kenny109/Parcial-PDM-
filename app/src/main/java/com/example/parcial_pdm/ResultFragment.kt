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

class ResultFragment : Fragment() {

    private val args: ResultFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_result, container, false)

        view.findViewById<TextView>(R.id.scoreTextView).text =
            getString(R.string.score_text, args.score)

        view.findViewById<Button>(R.id.restartButton).setOnClickListener {
            findNavController().navigate(R.id.action_resultFragment_to_welcomeFragment)
        }

        return view
    }
}
