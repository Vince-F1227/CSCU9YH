
package com.example.noteapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider

class Page2Fragment : Fragment() {

    lateinit var viewModel: MyViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        viewModel = ViewModelProvider(requireActivity())[MyViewModel::class.java]

        val v = inflater.inflate(
            R.layout.page2_fragment,
            container,
            false
        )

        val notesContainer =
            v.findViewById<LinearLayout>(R.id.notesContainer)

        viewModel.notes.observe(viewLifecycleOwner) { notes ->

            notesContainer.removeAllViews()

            if (notes.isEmpty()) {
                val emptyMessage = TextView(requireContext())
                emptyMessage.text = "No notes saved yet."
                emptyMessage.textSize = 18f

                notesContainer.addView(emptyMessage)

            } else {

                notes.forEach { note ->

                    val card = CardView(requireContext())

                    card.radius = 20f
                    card.setCardBackgroundColor(
                        android.graphics.Color.parseColor("#FDC5C8")
                    )
                    card.cardElevation = 6f

                    val text = TextView(requireContext())

                    text.text = note
                    text.textSize = 16f
                    text.setTextColor(
                        android.graphics.Color.BLACK
                    )
                    text.setPadding(24, 24, 24, 24)

                    card.addView(text)

                    val params = LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                    )

                    params.setMargins(0, 0, 0, 20)

                    notesContainer.addView(card, params)
                }
            }
        }

        return v
    }
}
