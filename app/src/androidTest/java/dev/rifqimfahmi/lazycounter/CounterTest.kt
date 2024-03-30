package dev.rifqimfahmi.lazycounter

import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import org.junit.Rule
import org.junit.Test


class CounterTest {

    @get:Rule
    val rule = createComposeRule()

    private val buttonIncrease = hasText("+1") and hasClickAction()
    private val buttonDecrease = hasText("-1") and hasClickAction()
    private val resetFab = hasContentDescriptionExactly("Reset") and hasClickAction()

    @Test
    fun increase_increaseCounter() {
        rule.setContent {
            Counter()
        }

        rule.onNode(buttonIncrease).performClick()
        rule.onNode(buttonIncrease).performClick()

        rule.onNodeWithText("2").assertExists()
    }

    @Test
    fun decrease_decreaseCounter() {
        rule.setContent {
            Counter()
        }

        rule.onNode(buttonIncrease).performClick()
        rule.onNode(buttonIncrease).performClick()
        rule.onNode(buttonIncrease).performClick()
        rule.onNode(buttonDecrease).performClick()
        rule.onNode(buttonDecrease).performClick()

        rule.onNodeWithText("1").assertExists()
    }

    @Test
    fun reset_resetCounter() {
        rule.setContent {
            Counter()
        }

        rule.onNode(buttonIncrease).performClick()
        rule.onNode(buttonIncrease).performClick()
        rule.onNode(buttonIncrease).performClick()
        rule.onNode(resetFab).performClick()
        rule.onNode(hasText("Confirm")).performClick()

        rule.onNodeWithText("0").assertExists()
    }
}