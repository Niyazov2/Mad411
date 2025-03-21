package com.example.expensemad411

import ExpenseClass
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ExpAdapter(private val expenses: MutableList<ExpenseClass>, private val onDelete: (Int) -> Unit) :
    RecyclerView.Adapter<ExpAdapter.ExpenseViewHolder>() {

    inner class ExpenseViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvName: TextView = itemView.findViewById(R.id.ExpenseName)
        val tvAmount: TextView = itemView.findViewById(R.id.ExpenseAmount)
        val btnDelete: Button = itemView.findViewById(R.id.Delete)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExpenseViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.activity_expense, parent, false)
        return ExpenseViewHolder(view)
    }

    override fun onBindViewHolder(holder: ExpenseViewHolder, position: Int) {
        val expense = expenses[position]
        holder.tvName.text = expense.name
        holder.tvAmount.text = "$${expense.amount}"
        holder.btnDelete.setOnClickListener {
            onDelete(position)
        }
    }

    override fun getItemCount() = expenses.size
}
