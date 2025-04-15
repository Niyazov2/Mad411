package com.example.expensemad411.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.expensemad411.R

class FooterFragment : Fragment() {

    // Global variables
    private lateinit var totalTextView: TextView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_footer, container, false)
        totalTextView = view.findViewById(R.id.textViewTotal)
        return view
    }

    fun updateTotal(total: Double) {
        totalTextView.text = "Total Expenses: $${"%.2f".format(total)}"
    }
}