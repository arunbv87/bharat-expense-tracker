package com.example.expense.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.expense.data.AppDatabase
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun ReportsScreen() {
    val context = LocalContext.current
    val dao = AppDatabase.getInstance(context).expenseDao()
    val expenses = dao.getAllExpenses().collectAsState(initial = emptyList())

    val monthFormatter = SimpleDateFormat("MMM yyyy", Locale.getDefault())

    val monthlyData = expenses.value.groupBy {
        monthFormatter.format(Date(it.date))
    }.mapValues { entry -> entry.value.sumOf { it.amount }.toFloat() }
     .toSortedMap(compareByDescending { SimpleDateFormat("MMM yyyy").parse(it) })

    Column(modifier = Modifier.padding(16.dp)) {
        Text("Monthly Expense Report", fontSize = 18.sp, modifier = Modifier.padding(bottom = 8.dp))
        monthlyData.forEach { (month, total) ->
            Row(modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp)) {
                Text(month, modifier = Modifier.width(100.dp))
                Box(
                    modifier = Modifier
                        .height(20.dp)
                        .width((total / 10).dp)
                        .background(Color.Blue)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text("₹${"%.0f".format(total)}")
            }
        }
    }