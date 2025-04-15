package com.example.expensemad411.fragments

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.expensemad411.R
import java.util.Calendar
import java.util.Locale

class AddExpenseFragment : Fragment() {

    private var expenseIndex: Int = -1
    private lateinit var expenseDateEditText: EditText
    private val calendar = Calendar.getInstance()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_add_expense, container, false)
        val expenseNameEditText: EditText = view.findViewById(R.id.expenseName)
        val expenseAmountEditText: EditText = view.findViewById(R.id.expenseAmount)
        expenseDateEditText = view.findViewById(R.id.expenseDate)
        val saveButton: Button = view.findViewById(R.id.saveButton)

        expenseDateEditText.apply {
            isFocusable = false
            setOnClickListener { showDatePickerDialog() }
        }

        arguments?.let {
            expenseIndex = it.getInt("expenseIndex", -1)
            val expenseName = it.getString("expenseName", "")
            val expenseAmount = it.getDouble("expenseAmount", 0.0)
            val expenseDate = it.getString("expenseDate", "")
            expenseNameEditText.setText(expenseName)
            expenseAmountEditText.setText(expenseAmount.toString())
            expenseDateEditText.setText(expenseDate)
        }

        saveButton.setOnClickListener {
            val name = expenseNameEditText.text.toString()
            val amountText = expenseAmountEditText.text.toString()
            val date = expenseDateEditText.text.toString()
            val expenseAmount = amountText.toDoubleOrNull() ?: 0.0
            val bundle = Bundle().apply {
                putInt("expenseIndex", expenseIndex)  // will be -1 for new expenses
                putString("expenseName", name)
                putDouble("expenseAmount", expenseAmount)
                putString("expenseDate", date)
            }
            findNavController().previousBackStackEntry?.savedStateHandle?.set("newExpense", bundle)
            findNavController().popBackStack()
        }
        return view
    }

    private fun showDatePickerDialog() {
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)
        DatePickerDialog(requireContext(), { _, selectedYear, selectedMonth, selectedDay ->
            val formattedDate = String.Companion.format(
                Locale.getDefault(),
                "%02d/%02d/%04d",
                selectedDay,
                selectedMonth + 1,
                selectedYear
            )
            expenseDateEditText.setText(formattedDate)
        }, year, month, day).show()
    }
}