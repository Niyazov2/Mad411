package com.example.expensemad411

import ExpenseClass
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.textfield.TextInputEditText

class MainActivity : AppCompatActivity() {

    private val expenses = ArrayList<ExpenseClass>()
    private lateinit var expenseAdapter: ExpAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)
        val nameInput = findViewById<TextInputEditText>(R.id.ExpenseName)
        val amountInput = findViewById<TextInputEditText>(R.id.ExpenseAmount)
        val addButton = findViewById<Button>(R.id.addExpense)
        val recyclerView = findViewById<RecyclerView>(R.id.exView)

        recyclerView.layoutManager = LinearLayoutManager(this)
        expenseAdapter = ExpAdapter(expenses) { position ->

            expenses.removeAt(position)
            expenseAdapter.notifyItemRemoved(position)
        }
        recyclerView.adapter = expenseAdapter


        addButton.setOnClickListener {
            val name = nameInput.text.toString().trim()
            val amountText = amountInput.text.toString().trim()


            if (name.isEmpty() || amountText.isEmpty()) {
                Toast.makeText(this, "Please fill both fields", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Convert the amount to a number
            val amount = amountText.toDoubleOrNull()
            if (amount == null) {
                Toast.makeText(this, "Enter a valid amount", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }


            val newExpense = ExpenseClass(name, amount)
            expenses.add(newExpense)
            expenseAdapter.notifyItemInserted(expenses.size - 1)

            // Clear input fields
            nameInput.text?.clear()
            amountInput.text?.clear()
        }
    }
}
