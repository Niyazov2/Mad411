package com.example.expensemad411.fragments

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.expensemad411.R
import com.example.expensemad411.network.RetrofitInstance
import kotlinx.coroutines.launch
import java.util.Calendar
import java.util.Locale

class AddExpenseFragment : Fragment() {

    private var expenseIndex: Int = -1
    private lateinit var expenseDateEditText: EditText
    private lateinit var spinnerCurrency: Spinner
    private lateinit var tvConvertedCost: TextView

    private val calendar = Calendar.getInstance()
    private val currencyList = listOf("CAD", "USD", "EUR", "GBP")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_add_expense, container, false)
        val expenseNameEditText: EditText = view.findViewById(R.id.expenseName)
        val expenseAmountEditText: EditText = view.findViewById(R.id.expenseAmount)
        expenseDateEditText = view.findViewById(R.id.expenseDate)
        spinnerCurrency = view.findViewById(R.id.spinnerCurrency)
        tvConvertedCost = view.findViewById(R.id.tvConvertedCost)
        val saveButton: Button = view.findViewById(R.id.saveButton)

        expenseDateEditText.apply {
            isFocusable = false
            setOnClickListener { showDatePickerDialog() }
        }

        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, currencyList)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerCurrency.adapter = adapter

        arguments?.let {
            expenseIndex = it.getInt("expenseIndex", -1)
            expenseNameEditText.setText(it.getString("expenseName", ""))
            expenseAmountEditText.setText(it.getDouble("expenseAmount", 0.0).toString())
            expenseDateEditText.setText(it.getString("expenseDate", ""))
        }

        spinnerCurrency.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?, view: View?, position: Int, id: Long
            ) {
                val amount = expenseAmountEditText.text.toString().toDoubleOrNull() ?: 0.0
                performCurrencyConversion(amount)
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }

        expenseAmountEditText.setOnFocusChangeListener { _, hasFocus ->
            if (!hasFocus) {
                val amount = expenseAmountEditText.text.toString().toDoubleOrNull() ?: 0.0
                performCurrencyConversion(amount)
            }
        }

        saveButton.setOnClickListener {
            val name = expenseNameEditText.text.toString()
            val expenseAmount = expenseAmountEditText.text.toString().toDoubleOrNull() ?: 0.0
            val date = expenseDateEditText.text.toString()
            val selectedCurrency = spinnerCurrency.selectedItem as String
            val convertedCost = tvConvertedCost.text.toString().substringAfter(": ").toDoubleOrNull() ?: 0.0

            val bundle = Bundle().apply {
                putInt("expenseIndex", expenseIndex)
                putString("expenseName", name)
                putDouble("expenseAmount", expenseAmount)
                putString("expenseDate", date)
                putString("expenseCurrency", selectedCurrency)
                putFloat("expenseConvertedCost", convertedCost.toFloat())
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
            val formattedDate = String.format(Locale.getDefault(), "%02d/%02d/%04d", selectedDay, selectedMonth + 1, selectedYear)
            expenseDateEditText.setText(formattedDate)
        }, year, month, day).show()
    }

    private fun performCurrencyConversion(amount: Double) {
        lifecycleScope.launch {
            try {
                val currencyResponse = RetrofitInstance.api.getCurrencyRates()
                val selectedCurrency = spinnerCurrency.selectedItem as String
                val conversionRate = currencyResponse.cad[selectedCurrency.lowercase(Locale.getDefault())]
                    ?: 1.0
                val convertedAmount = amount * conversionRate
                tvConvertedCost.text = String.format(Locale.getDefault(), "Converted: %.2f", convertedAmount)
            } catch (e: Exception) {
                e.printStackTrace()
                Toast.makeText(requireContext(), "Error fetching currency rates: ${e.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }

}