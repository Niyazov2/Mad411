package com.example.expensemad411

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.expensemad411.R

class ExpenseActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.expense)

        val tvExpenseName = findViewById<TextView>(R.id.ExpenseName)
        val tvExpenseAmount = findViewById<TextView>(R.id.ExpenseAmount)

        val name = intent.getStringExtra("expense_name")
        val amount = intent.getDoubleExtra("expense_amount", 0.0)



        tvExpenseName.text = "Expense: $name"
        tvExpenseAmount.text = "Amount: $$amount"

    }
}
