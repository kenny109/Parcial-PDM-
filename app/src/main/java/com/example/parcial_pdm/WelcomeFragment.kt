package com.example.parcial_pdm


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController

class WelcomeFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_welcome, container, false)

        view.findViewById<TextView>(R.id.gameDescriptionTextView).text =
            getString(R.string.game_description)

        view.findViewById<Button>(R.id.startButton).setOnClickListener {
            findNavController().navigate(R.id.action_welcomeFragment_to_questionFragment)
        }

        return view
    }
}