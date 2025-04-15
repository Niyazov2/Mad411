package com.example.expensemad411

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import android.app.DatePickerDialog
import java.util.Calendar

class MainActivity : AppCompatActivity() {

    // Global variables
    private lateinit var expenseAdapter: ExpenAdapter
    private lateinit var footerFragment: FooterFragment

    // List for expenses and a reference for FooterFragment
    private val expenseList = mutableListOf<Expense>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("MainActivity", "onCreate called")
        setContentView(R.layout.activity_main)

        // Layout components
        val expenseNameInput = findViewById<EditText>(R.id.editTextExpenseName)
        val amountInput = findViewById<EditText>(R.id.editTextAmount)
        val dateInput = findViewById<EditText>(R.id.editTextDate)
        val addExpenseButton = findViewById<Button>(R.id.buttonAddExpense)
        val openWebButton = findViewById<Button>(R.id.buttonOpenWeb)
        val expensesRecyclerView = findViewById<RecyclerView>(R.id.recyclerViewExpenses)
        val rootLayout = findViewById<ConstraintLayout>(R.id.rootLayout)

        // Initialize adapter with delete and show details callbacks
        expenseAdapter = ExpenAdapter(expenseList,
            onDeleteClick = { position ->
                expenseList.removeAt(position)
                expenseAdapter.notifyItemRemoved(position)
                updateFooterTotal()
                // Show Snackbar for deletion
                Snackbar.make(rootLayout, "Expense deleted", Snackbar.LENGTH_SHORT).show()
            },
            onShowDetails = { expense ->
                // Launch ExpenseDetailsActivity with expense data
                val intent = Intent(this, ExpenseDetailsActivity::class.java).apply {
                    putExtra("expenseName", expense.name)
                    putExtra("expenseAmount", expense.amount)
                    putExtra("expenseDate", expense.date)
                }
                startActivity(intent)
            }
        )
        expensesRecyclerView.layoutManager = LinearLayoutManager(this)
        expensesRecyclerView.adapter = expenseAdapter

        // Set up a DatePicker for the date input
        dateInput.setOnClickListener {
            val calendar = Calendar.getInstance()
            DatePickerDialog(this, { _, year, month, day ->
                // Month is zero-indexed; add 1.
                dateInput.setText("$day/${month + 1}/$year")
            }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show()
        }

        // Add Expense button click listener
        addExpenseButton.setOnClickListener {
            addExpense(expenseNameInput, amountInput, dateInput)
        }

        // Implicit intent: Open web browser with financial tips
        openWebButton.setOnClickListener {
            val url = "https://www.investopedia.com/financial-advice/"
            val webIntent = Intent(Intent.ACTION_VIEW).apply {
                data = android.net.Uri.parse(url)
            }
            startActivity(webIntent)
        }

        val headerFragment = HeaderFragment()
        footerFragment = FooterFragment()
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.headerContainer, headerFragment)
            replace(R.id.footerContainer, footerFragment)
            commit()
        }
    }

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
        updateFooterTotal()

        nameInput.text.clear()
        amountInput.text.clear()
        dateInput.text.clear()
    }

    private fun updateFooterTotal() {
        val total = expenseList.sumOf { it.amount }
        footerFragment.updateTotal(total)
    }

    // Lifecycle methods with logging
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
}}