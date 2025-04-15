package com.example.expensemad411

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController

class ExpenseDetailsFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_expense_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val expenseName = arguments?.getString("expenseName") ?: "No Name"
        val expenseAmount = arguments?.getFloat("expenseAmount")?.toDouble() ?: 0.0
        val expenseDate = arguments?.getString("expenseDate") ?: "No Date"

        view.findViewById<TextView>(R.id.expenseNameTextView).text = "Name: $expenseName"
        view.findViewById<TextView>(R.id.expenseAmountTextView).text = "Amount: $$expenseAmount"
        view.findViewById<TextView>(R.id.expenseDateTextView).text = "Date: $expenseDate"

        view.findViewById<Button>(R.id.backButton).setOnClickListener {
            findNavController().popBackStack()
        }
    }
}