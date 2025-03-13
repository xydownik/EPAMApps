package com.example.fibonaccinumbers

import android.annotation.SuppressLint
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.fibonaccinumbers.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: FibonacciViewModel

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this)[FibonacciViewModel::class.java]

        viewModel.fibonacciResult.observe(this) { result ->
            binding.resultText.text = getString(R.string.result) + result
        }

        viewModel.isCalculating.observe(this) { isCalculating ->
            if (isCalculating) {
                binding.startButton.text = getString(R.string.cancel)
                binding.editText.isEnabled = false
            } else {
                binding.startButton.text = getString(R.string.start)
                binding.editText.isEnabled = true
            }
        }

        binding.startButton.setOnClickListener {
            val n = binding.editText.text.toString().toIntOrNull() ?: return@setOnClickListener

            if (viewModel.isCalculating.value == true) {
                viewModel.cancelCalculation()
            } else {
                viewModel.calculateFibonacci(n)
            }
        }

        binding.scheduleButton.setOnClickListener {
            val n = binding.editText.text.toString().toIntOrNull() ?: return@setOnClickListener
            scheduleCalculation(this, 5000L, n)
        }
    }
    @SuppressLint("ScheduleExactAlarm")
    fun scheduleCalculation(context: Context, delayMillis: Long, n: Int) {
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(context, FibonacciReceiver::class.java).apply {
            putExtra(R.string.fibonacci_number.toString(), n)
        }

        val pendingIntent = PendingIntent.getBroadcast(
            context, n, intent, PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        alarmManager.setExact(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + delayMillis, pendingIntent)
    }


}

