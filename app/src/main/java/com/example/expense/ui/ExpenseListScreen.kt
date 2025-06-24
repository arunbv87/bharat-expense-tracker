package com.example.expense.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.platform.LocalContext
import com.example.expense.data.AppDatabase
import kotlinx.coroutines.flow.collect
import java.util.Date

@Composable
fun ExpenseListScreen() {
    val dao = AppDatabase.getInstance(LocalContext.current).expenseDao()
    val expenses = dao.getAllExpenses().collectAsState(initial = emptyList())

    
    val categoryTotals = expenses.value.groupBy { it.category }
        .mapValues { entry -> entry.value.sumOf { it.amount }.toFloat() }
    val pieColors = listOf(Color.Red, Color.Green, Color.Blue, Color.Yellow, Color.Cyan)

    Column {
        PieChart(data = categoryTotals, colors = pieColors)
    
        Text("Expenses: ${expenses.value.size}")
        Lazy
    val categoryTotals = expenses.value.groupBy { it.category }
        .mapValues { entry -> entry.value.sumOf { it.amount }.toFloat() }
    val pieColors = listOf(Color.Red, Color.Green, Color.Blue, Color.Yellow, Color.Cyan)

    Column {
        PieChart(data = categoryTotals, colors = pieColors)
    
            items(expenses.value) { exp ->
                Text("${exp.category} - ₹${exp.amount} on ${Date(exp.date)}")
            }
        }
    }
}