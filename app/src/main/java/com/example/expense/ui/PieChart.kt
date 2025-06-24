package com.example.expense.ui

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.drawArc
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlin.math.atan2
import kotlin.math.sqrt

@Composable
fun PieChart(data: Map<String, Float>, colors: List<Color>) {
    val total = data.values.sum()
    var startAngle = 0f
    var selectedCategory by remember { mutableStateOf<String?>(null) }

    Column(modifier = Modifier.fillMaxWidth()) {
        Box(contentAlignment = Alignment.Center, modifier = Modifier.height(250.dp)) {
            Canvas(modifier = Modifier
                .fillMaxSize()
                .pointerInput(Unit) {
                    detectTapGestures { offset ->
                        // Optional: handle tap location to identify segment (not implemented fully here)
                    }
                }) {
                val rect = Rect(Offset.Zero, size)
                var colorIndex = 0

                data.forEach { (category, value) ->
                    val angle = (value / total) * 360f
                    drawArc(
                        color = colors[colorIndex % colors.size],
                        startAngle = startAngle,
                        sweepAngle = angle,
                        useCenter = true,
                        topLeft = rect.topLeft,
                        size = rect.size
                    )
                    startAngle += angle
                    colorIndex++
                }
            }
        }

        selectedCategory?.let { category ->
            Text(
                text = "$category: ₹${data[category] ?: 0f}",
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.padding(8.dp)
            )
        }

        Column(modifier = Modifier.padding(8.dp)) {
            data.entries.forEachIndexed { index, entry ->
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { selectedCategory = entry.key }
                        .padding(vertical = 4.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .size(16.dp)
                            .background(colors[index % colors.size])
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("${entry.key} - ₹${entry.value}", fontSize = 14.sp)
                }
            }
        }
    }
}