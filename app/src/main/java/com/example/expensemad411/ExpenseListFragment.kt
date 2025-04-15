package com.example.expensemad411

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.File
import java.io.FileNotFoundException
import java.io.IOException

private const val FILE_NAME = "expenses.json"

class ExpenseListFragment : Fragment(), ExpenAdapter.ExpenseItemListener {

    private lateinit var recyclerView: RecyclerView
    private lateinit var expenseAdapter: ExpenAdapter
    private val expenseList = mutableListOf<Expense>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_expense_list, container, false)
        recyclerView = view.findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        expenseList.clear()
        expenseList.addAll(loadExpensesFromFile(requireContext()))

        expenseAdapter = ExpenAdapter(expenseList, this)
        recyclerView.adapter = expenseAdapter

        val addExpenseFab: FloatingActionButton = view.findViewById(R.id.addExpenseFab)
        addExpenseFab.setOnClickListener {
            findNavController().navigate(R.id.addExpense)
        }

        return view
    }

    override fun onEditClick(expense: Expense) {
        val bundle = Bundle().apply {
            putInt("expenseId", expense.id)
            putString("expenseName", expense.name)
            putDouble("expenseAmount", expense.amount)
            putString("expenseDate", expense.date)
        }
        findNavController().navigate(R.id.addExpense, bundle)
    }

    override fun onDeleteClick(expense: Expense) {
        expenseList.remove(expense)
        expenseAdapter.notifyDataSetChanged()
        saveExpensesToFile(requireContext(), expenseList)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        findNavController().currentBackStackEntry?.savedStateHandle?.getLiveData<Bundle>("newExpense")
            ?.observe(viewLifecycleOwner) { bundle ->
                val updatedExpense = Expense(
                    bundle.getInt("expenseId"),
                    bundle.getString("expenseName", ""),
                    bundle.getDouble("expenseAmount", 0.0),
                    bundle.getString("expenseDate", "")
                )
                val index = expenseList.indexOfFirst { it.id == updatedExpense.id }
                if (index != -1) {
                    expenseList[index] = updatedExpense
                    expenseAdapter.notifyItemChanged(index)
                } else {
                    expenseList.add(updatedExpense)
                    expenseAdapter.notifyItemInserted(expenseList.size - 1)
                }
                saveExpensesToFile(requireContext(), expenseList)
            }
    }

    private fun saveExpensesToFile(context: Context, expenseList: List<Expense>) {
        try {
            val json = Gson().toJson(expenseList)
            context.openFileOutput(FILE_NAME, Context.MODE_PRIVATE).use { output ->
                output.write(json.toByteArray())
            }
            Log.d("FileStorage", "Expenses saved successfully")
        } catch (e: IOException) {
            Log.e("FileStorage", "Error saving expenses: ${e.message}")
        }
    }

    private fun loadExpensesFromFile(context: Context): MutableList<Expense> {
        val list = mutableListOf<Expense>()
        try {
            val file = File(context.filesDir, FILE_NAME)
            if (!file.exists()) return list
            val json = file.readText()
            val type = object : TypeToken<List<Expense>>() {}.type
            val loadedExpenses: List<Expense> = Gson().fromJson(json, type)
            list.addAll(loadedExpenses)
            Log.d("FileStorage", "Expenses loaded successfully")
        } catch (e: FileNotFoundException) {
            Log.e("FileStorage", "File not found: ${e.message}")
        } catch (e: IOException) {
            Log.e("FileStorage", "Error reading file: ${e.message}")
        }
        return list
    }
}