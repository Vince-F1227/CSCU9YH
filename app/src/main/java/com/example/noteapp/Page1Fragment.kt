package com.example.noteapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider

class Page1Fragment : Fragment() {
    lateinit var viewModel: MyViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        viewModel = activity?.run {
            ViewModelProvider(this)[MyViewModel::class.java]
        } ?: throw Exception("Invalid Activity")

        val view = inflater.inflate(R.layout.page1_fragment, container, false)
        val but = view.findViewById<Button>(R.id.vbutton)
        val but2 = view.findViewById<Button>(R.id.vbutton2)
        val date = view.findViewById<TextView>(R.id.date)
        val value = view.findViewById<EditText>(R.id.editValue)

        but.setOnClickListener {
            val note = value.text.toString()

            if (note.isNotBlank()) {
                viewModel.saveNote(note)

                try {
                    val fileOutput = requireContext().openFileOutput("savedNotes.txt",android.content.Context.MODE_PRIVATE)

                    fileOutput.write(note.toByteArray())
                    fileOutput.close()

                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }

        but2.setOnClickListener {
            value.text.clear()
        }

        viewModel.date.observe(viewLifecycleOwner, Observer { SDate ->
            date.text = SDate
        })

        viewModel.value.observe(viewLifecycleOwner, Observer { savedNote ->
            value.setText(savedNote)
        })

        return view
    }
}