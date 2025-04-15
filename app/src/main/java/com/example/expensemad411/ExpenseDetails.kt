package com.example.expensemad411

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView

class ExpenseDetailsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)

        // Retrieve data from intent extras.
        val expenseName = intent.getStringExtra("expenseName") ?: "N/A"
        val expenseAmount = intent.getDoubleExtra("expenseAmount", 0.0)
        val expenseDate = intent.getStringExtra("expenseDate") ?: "N/A"

        val nameTextView = findViewById<TextView>(R.id.textViewDetailName)
        val amountTextView = findViewById<TextView>(R.id.textViewDetailAmount)
        val dateTextView = findViewById<TextView>(R.id.textViewDetailDate)
        val backButton = findViewById<Button>(R.id.buttonHome)

        nameTextView.text = "Name: $expenseName"
        amountTextView.text = "Amount: $$expenseAmount"
        dateTextView.text = "Date: $expenseDate"

        // Home button
        backButton.setOnClickListener {
            finish()
        }
    }
}