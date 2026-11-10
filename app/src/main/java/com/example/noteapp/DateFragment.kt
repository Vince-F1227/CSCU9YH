package com.example.noteapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CalendarView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

class DateFragment : Fragment() {
    lateinit var viewModel: MyViewModel
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        viewModel = activity?.run { ViewModelProvider(this)[MyViewModel::class.java]
        } ?: throw Exception("Invalid Activity")

        val v = inflater.inflate(R.layout.date_fragment, container, false)

        val cal = v.findViewById<CalendarView>(R.id.calendarView)
        val but = v.findViewById<Button>(R.id.sbutton)
        var date = cal.date
        
        cal.setOnDateChangeListener { _, year, month, day ->

            val SDate = Calendar.getInstance()
            SDate.set(year, month, day)

            date = SDate.timeInMillis
        }

        but.setOnClickListener {
            val FDate = SimpleDateFormat("dd/MM/yy",
                Locale.getDefault()
            ).format(Date(date))

            viewModel.saveDate(FDate)
        }

        return v;
    }
}