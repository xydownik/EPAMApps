package com.example.fibonaccinumbers

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class FibonacciViewModel : ViewModel() {

    private val _fibonacciResult = MutableLiveData<Long>()
    val fibonacciResult: LiveData<Long> get() = _fibonacciResult

    private val _isCalculating = MutableLiveData<Boolean>()
    val isCalculating: LiveData<Boolean> get() = _isCalculating

    private var job: Job? = null

    fun calculateFibonacci(n: Int) {
        _isCalculating.value = true
        job = viewModelScope.launch(Dispatchers.Default) {
            var a = 0L
            var b = 1L
            repeat(n) {
                val next = a + b
                a = b
                b = next
                delay(1000)
                _fibonacciResult.postValue(a)
            }
            _isCalculating.postValue(false)
        }
    }

    fun cancelCalculation() {
        job?.cancel()
        _isCalculating.value = false
    }
}
