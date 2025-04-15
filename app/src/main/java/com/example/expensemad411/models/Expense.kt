package com.example.expensemad411.models

data class Expense(
    val name: String,
    val amount: Double,
    val date: String,
    val currency: String = "CAD"
    val converted
)