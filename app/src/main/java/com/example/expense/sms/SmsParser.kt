package com.example.expense.sms

import com.example.expense.data.ExpenseEntity

data class Expense(val amount: Double, val date: Long, val mode: String, val category: String) {
    fun toEntity() = ExpenseEntity(amount = amount, date = date, mode = mode, category = category)
}

class SmsParser {
    private val amtRegex = Regex("(?i)(?:INR|Rs\.?)[ ]?([0-9,]+\.?[0-9]*)")
    private val modeRegex = Regex("via (UPI|Card|Wallet|Netbanking|ATM)", RegexOption.IGNORE_CASE)

    fun parse(msg: String): Expense? {
        val amt = amtRegex.find(msg)?.groups?.get(1)?.value?.replace(",", "")?.toDoubleOrNull() ?: return null
        val mode = modeRegex.find(msg)?.groups?.get(1)?.value ?: "Unknown"
        val category = when {
            "swiggy" in msg.lowercase() -> "Food"
            "ola" in msg.lowercase() -> "Transport"
            else -> "General"
        }
        return Expense(amount = amt, date = System.currentTimeMillis(), mode = mode, category = category)
    }
}