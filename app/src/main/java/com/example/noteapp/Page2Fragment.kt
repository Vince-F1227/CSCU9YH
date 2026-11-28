
package com.example.noteapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
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

                notes.forEachIndexed { index, note ->

                    val card = CardView(requireContext())

                    card.radius = 20f
                    card.setCardBackgroundColor(android.graphics.Color.parseColor("#FDC5C8"))
                    card.cardElevation = 6f

                    val noteLayout = LinearLayout(requireContext())
                    noteLayout.orientation = LinearLayout.VERTICAL
                    noteLayout.setPadding(24, 24, 24, 24)

                    val noteText = TextView(requireContext())
                    noteText.text = note.text
                    noteText.textSize = 18f
                    noteText.setTextColor(android.graphics.Color.BLACK)

                    val dateText = TextView(requireContext())
                    dateText.text = "Date saved: ${note.date}"
                    dateText.textSize = 12f
                    dateText.setTextColor(android.graphics.Color.DKGRAY)

                    noteLayout.addView(noteText)
                    noteLayout.addView(dateText)

                    card.addView(noteLayout)

                    val params = LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT)

                    params.setMargins(0, 0, 0, 20)

                    notesContainer.addView(card, params)

                    card.setOnClickListener {

                        AlertDialog.Builder(requireContext())
                            .setTitle("Delete note?")
                            .setMessage("Are you sure you want to delete this note?")
                            .setNegativeButton("Cancel", null)
                            .setPositiveButton("Delete") { _, _ ->

                                viewModel.deleteNote(index)

                                try {
                                    val updatedNotes =
                                        viewModel.notes.value ?: emptyList()

                                    val jsonArray =
                                        org.json.JSONArray()

                                    updatedNotes.forEach { savedNote ->
                                        val jsonObject =
                                            org.json.JSONObject()

                                        jsonObject.put(
                                            "text",
                                            savedNote.text
                                        )
                                        jsonObject.put(
                                            "date",
                                            savedNote.date
                                        )

                                        jsonArray.put(jsonObject)
                                    }

                                    val fileOutput =
                                        requireContext().openFileOutput(
                                            "savedNotes.txt",
                                            android.content.Context.MODE_PRIVATE
                                        )

                                    fileOutput.write(
                                        jsonArray.toString().toByteArray()
                                    )
                                    fileOutput.close()

                                } catch (e: Exception) {
                                    e.printStackTrace()
                                }
                            }
                            .show()
                    }
                }
            }
        }

        return v
    }
}
