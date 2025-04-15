package com.example.expensemad411

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Button
import androidx.recyclerview.widget.RecyclerView
import com.example.expensemad411.models.Expense

class ExpenAdapter(
    private val expenseList: MutableList<Expense>,
    private val listener: ExpenseItemListener
) : RecyclerView.Adapter<ExpenAdapter.ExpenseViewHolder>() {

    interface ExpenseItemListener{
        fun onEdit(expense: Expense)
        fun onDelete(expense: Expense)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExpenseViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.expense, parent, false)
        return ExpenseViewHolder(view)
    }

    override fun onBindViewHolder(holder: ExpenseViewHolder, position: Int) {
        val expense = expenseList[position]
        holder.bind(expense)
    }

    override fun getItemCount(): Int = expenseList.size

    inner class ExpenseViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val expenseNameText: TextView = itemView.findViewById(R.id.expenseNameTextView)
        private val expenseAmountText: TextView = itemView.findViewById(R.id.expenseAmountTextView)
        private val expenseDateText: TextView = itemView.findViewById(R.id.expenseDateTextView)
        private val editButton: Button = itemView.findViewById(R.id.editButton)
        private val deleteButton: Button = itemView.findViewById(R.id.deleteButton)

        fun bind(expense: Expense) {
            expenseNameText.text = expense.name
            expenseAmountText.text = "$${expense.amount}"
            expenseDateText.text = expense.date
            editButton.setOnClickListener { listener.onEdit(expense) }
            deleteButton.setOnClickListener { listener.onDelete(expense) }
        }
    }
}
