package dev.rifqimfahmi.lazycounter.view

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import dev.rifqimfahmi.lazycounter.data.CounterAction
import dev.rifqimfahmi.lazycounter.viewmodel.CounterViewModel
import kotlin.math.min


@Composable
fun CounterNumber(
    viewModel: CounterViewModel
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(.6f),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val charState by viewModel.count.observeAsState(CounterAction.Reset(0))
        AnimatedContent(
            targetState = charState.value,
            transitionSpec = {
                slideInVertically {
                    when (charState) {
                        is CounterAction.Increment -> it
                        is CounterAction.Decrement -> -it
                        else -> it
                    }
                } togetherWith slideOutVertically {
                    when (charState) {
                        is CounterAction.Increment -> -it
                        is CounterAction.Decrement -> it
                        else -> -it
                    }
                }
            },
            label = "anim"
        ) { char ->
            val multiplier = min(
                ((char * 0.03) + 1),
                3.0
            )
            Text(
                modifier = Modifier,
                text = char.toString(),
                style = MaterialTheme.typography.titleMedium,
                fontSize = 45.sp * multiplier
            )
        }
    }
}

@Preview(showBackground = true, apiLevel = 33)
@Composable
fun PreviewCounterNumber() {
    CounterNumber(
        viewModel = CounterViewModel().apply {
            increase()
            increase()
            increase()
        }
    )
}