package com.example.fragmentapp

import android.graphics.Color
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlin.random.Random



class SharedViewModel : ViewModel() {
    private val _fragment2Color = MutableLiveData(generateRandomColor())
    val fragment2Color: LiveData<Int> get() = _fragment2Color

    private val _fragment3Color = MutableLiveData(generateRandomColor())
    val fragment3Color: LiveData<Int> get() = _fragment3Color

    private val _isSwapped = MutableLiveData(false)
    val isSwapped: LiveData<Boolean> get() = _isSwapped

    fun swapFragments() {
        _isSwapped.value = !(_isSwapped.value ?: false)
    }

    fun changeBackgroundColors() {
        _fragment2Color.value = generateRandomColor()
        _fragment3Color.value = generateRandomColor()
    }

    private fun generateRandomColor(): Int {
        return Color.rgb(Random.nextInt(256), Random.nextInt(256), Random.nextInt(256))
    }
}
