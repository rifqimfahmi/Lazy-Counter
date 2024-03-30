package dev.rifqimfahmi.lazycounter.data

sealed class CounterAction(
    val value: Int
) {
    class Increment(value: Int) : CounterAction(value)
    class Decrement(value: Int) : CounterAction(value)
    class Reset(value: Int) : CounterAction(value)
}