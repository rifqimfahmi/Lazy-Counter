package dev.rifqimfahmi.lazycounter.viewmodel

import androidx.compose.runtime.mutableIntStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class CounterViewModel: ViewModel() {

    private val _count = MutableLiveData(0)
    val count get() = _count


    fun increase() {
        _count.value = (_count.value ?: 0) + 1
    }

    fun decrease() {
        val newValue = (_count.value ?: 0) - 1
        if (newValue >= 0) {
            _count.value = newValue
        }
    }
}