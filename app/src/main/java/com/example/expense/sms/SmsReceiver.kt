package com.example.expense.sms

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.provider.Telephony
import com.example.expense.data.AppDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SmsReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        val db = AppDatabase.getInstance(context)
        for (sms in Telephony.Sms.Intents.getMessagesFromIntent(intent)) {
            val parser = SmsParser()
            parser.parse(sms.messageBody)?.let { expense ->
                CoroutineScope(Dispatchers.IO).launch {
                    db.expenseDao().insert(expense.toEntity())
                }
            }
        }
    }
}