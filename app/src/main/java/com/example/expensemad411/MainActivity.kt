package com.example.expensemad411

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import android.app.DatePickerDialog
import java.util.Calendar

class MainActivity : AppCompatActivity() {

    // Global variables
    private lateinit var expenseAdapter: ExpenAdapter
    private val expenseList = mutableListOf<Expense>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("MainActivity", "onCreate called")
        setContentView(R.layout.activity_main)

        val expenseNameInput = findViewById<EditText>(R.id.editTextExpenseName)
        val amountInput = findViewById<EditText>(R.id.editTextAmount)
        val dateInput = findViewById<EditText>(R.id.editTextDate)
        val addExpenseButton = findViewById<Button>(R.id.buttonAddExpense)
        val expensesRecyclerView = findViewById<RecyclerView>(R.id.recyclerViewExpenses)

        // Initialize adapter
        expenseAdapter = ExpenAdapter(expenseList,
            onDeleteClick = { position ->
                expenseList.removeAt(position)
                expenseAdapter.notifyItemRemoved(position)
            }
        )
        expensesRecyclerView.layoutManager = LinearLayoutManager(this)
        expensesRecyclerView.adapter = expenseAdapter

        //  DatePickerDialog
        dateInput.setOnClickListener {
            val calendar = Calendar.getInstance()
            DatePickerDialog(this, { _, year, month, day ->
                dateInput.setText("$day/${month + 1}/$year")
            }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show()
        }

        // Add Expense
        addExpenseButton.setOnClickListener {
            addExpense(expenseNameInput, amountInput, dateInput)
        }
    }

    // function to add expense
    private fun addExpense(nameInput: EditText, amountInput: EditText, dateInput: EditText) {
        val name = nameInput.text.toString().trim()
        val amountText = amountInput.text.toString().trim()
        val date = dateInput.text.toString().trim()

        if (name.isEmpty()) {
            Toast.makeText(this, "Expense name cannot be empty", Toast.LENGTH_SHORT).show()
            return
        }
        if (amountText.isEmpty()) {
            Toast.makeText(this, "Amount cannot be empty", Toast.LENGTH_SHORT).show()
            return
        }
        val amount = amountText.toDoubleOrNull() ?: run {
            Toast.makeText(this, "Invalid amount", Toast.LENGTH_SHORT).show()
            return
        }
        val expenseDate = if (date.isEmpty()) "N/A" else date

        expenseList.add(Expense(name, amount, expenseDate))
        expenseAdapter.notifyItemInserted(expenseList.size - 1)

        nameInput.text.clear()
        amountInput.text.clear()
        dateInput.text.clear()
    }

    // Lifecycle  methods
    override fun onStart() {
        super.onStart()
        Log.d("MainActivity", "onStart called")
    }
    override fun onResume() {
        super.onResume()
        Log.d("MainActivity", "onResume called")
    }
    override fun onPause() {
        super.onPause()
        Log.d("MainActivity", "onPause called")
    }
    override fun onStop() {
        super.onStop()
        Log.d("MainActivity", "onStop called")
    }
    override fun onDestroy() {
        super.onDestroy()
        Log.d("MainActivity", "onDestroy called")
    }
}