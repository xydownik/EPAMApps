package com.example.fibonaccinumbers

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast
class FibonacciReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        val n = intent.getIntExtra(R.string.fibonacci_number.toString(), -1)
        if (n == -1) {
            Log.e(
                context.getString(R.string.fibonaccireceiver),
                context.getString(R.string.error_no_valid_fibonacci_number_received))
            return
        }

        val result = fibonacci(n)

        Toast.makeText(context, "Fibonacci($n) = $result", Toast.LENGTH_LONG).show()
    }

    private fun fibonacci(n: Int): Long {
        var a = 0L
        var b = 1L
        repeat(n) {
            val next = a + b
            a = b
            b = next
        }
        return a
    }
}
