package com.example.expensemad411

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Button
import androidx.recyclerview.widget.RecyclerView

class ExpenAdapter(
    private val expenseList: MutableList<Expense>,
    private val onDeleteClick: (Int) -> Unit,
    private val onShowDetails: (Expense) -> Unit
) : RecyclerView.Adapter<ExpenAdapter.ExpenseViewHolder>() {

    class ExpenseViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val expenseNameText: TextView = itemView.findViewById(R.id.textViewExpenseName)
        val expenseAmountText: TextView = itemView.findViewById(R.id.textViewExpenseAmount)
        val expenseDateText: TextView = itemView.findViewById(R.id.textViewExpenseDate)
        val deleteButton: Button = itemView.findViewById(R.id.buttonDeleteExpense)
        val detailButton: Button = itemView.findViewById(R.id.buttonShowDetails)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExpenseViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.expense, parent, false)
        return ExpenseViewHolder(view)
    }

    override fun onBindViewHolder(holder: ExpenseViewHolder, position: Int) {
        val expense = expenseList[position]
        holder.expenseNameText.text = expense.name
        holder.expenseAmountText.text = "$${expense.amount}"
        holder.expenseDateText.text = expense.date
        holder.deleteButton.setOnClickListener {
            onDeleteClick(position)
        }
        holder.detailButton.setOnClickListener {
            onShowDetails(expense)
        }
    }

    override fun getItemCount(): Int = expenseList.size
}