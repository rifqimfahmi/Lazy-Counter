import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.common.truth.Truth.assertThat
import dev.rifqimfahmi.lazycounter.viewmodel.CounterViewModel
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule

class CounterViewModelTest {

    private lateinit var counterViewModel: CounterViewModel

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        counterViewModel = CounterViewModel()
    }

    @Test
    fun `increase increments count`() {
        counterViewModel.increase()
        assertThat(counterViewModel.count.value?.value).isEqualTo(1)
        counterViewModel.increase()
        counterViewModel.increase()
        assertThat(counterViewModel.count.value?.value).isEqualTo(3)
    }

    @Test
    fun `decrease decrements count`() {
        counterViewModel.increase()
        counterViewModel.increase()
        counterViewModel.decrease()
        assertThat(counterViewModel.count.value?.value).isEqualTo(1)
        counterViewModel.decrease()
        counterViewModel.decrease()
        assertThat(counterViewModel.count.value?.value).isEqualTo(0)
    }

    @Test
    fun `reset sets count to 0`() {
        counterViewModel.increase()
        counterViewModel.increase()
        counterViewModel.increase()
        counterViewModel.reset()
        assertThat(counterViewModel.count.value?.value).isEqualTo(0)
    }

    @Test
    fun `showResetDialogConfirmation sets showResetDialog to true`() {
        counterViewModel.showResetDialogConfirmation()
        assertThat(counterViewModel.showResetDialog.value).isEqualTo(true)
    }

    @Test
    fun `hideResetDialogConfirmation sets showResetDialog to false`() {
        counterViewModel.hideResetDialogConfirmation()
        assertThat(counterViewModel.showResetDialog.value).isEqualTo(false)
    }
}
