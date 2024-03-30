package dev.rifqimfahmi.lazycounter.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dev.rifqimfahmi.lazycounter.data.CounterAction

class CounterViewModel: ViewModel() {

    private val _count = MutableLiveData<CounterAction>()
    val count get() = _count


    fun increase() {
        val nextValue = (_count.value?.value ?: 0) + 1
        _count.value = CounterAction.Increment(nextValue)
    }

    fun decrease() {
        val nextValue = (_count.value?.value ?: 0) - 1
        if (nextValue >= 0) {
            _count.value = CounterAction.Decrement(nextValue)
        }
    }

    fun reset() {
        _count.value = CounterAction.Decrement(0)
    }
}