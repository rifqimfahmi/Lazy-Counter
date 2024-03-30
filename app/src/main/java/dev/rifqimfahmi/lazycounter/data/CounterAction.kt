package dev.rifqimfahmi.lazycounter.data

sealed class CounterAction(
    val value: Int
) {
    class Increment(value: Int) : CounterAction(value)
    class Decrement(value: Int) : CounterAction(value)
    class Neutral(value: Int) : CounterAction(value)
}