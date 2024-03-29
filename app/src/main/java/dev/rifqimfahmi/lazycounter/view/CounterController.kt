package dev.rifqimfahmi.lazycounter.view

import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.PressInteraction
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dev.rifqimfahmi.lazycounter.viewmodel.CounterViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


@Composable
fun CounterController(
    viewModel: CounterViewModel
) {
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.Center
    ) {
        TriggerButton(
            onClick = { viewModel.decrease() },
            text = "-1"
        )
        TriggerButton(
            onClick = { viewModel.increase() },
            text = "+1"
        )
    }
}

@Composable
private fun TriggerButton(
    onClick: () -> Unit,
    text: String
) {
    val interactionSource = remember { MutableInteractionSource() }
    var longPressJob by remember { mutableStateOf<Job?>(null) }
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(interactionSource) {
        interactionSource.interactions.collectLatest { interaction ->
            when (interaction) {
                is PressInteraction.Press -> {
                    longPressJob = coroutineScope.launch {
                        delay(500)
                        // long click start below
                        while (true) {
                            onClick.invoke()
                            delay(100)
                        }
                    }
                }

                is PressInteraction.Release -> {
                    longPressJob?.cancel()
                }
            }
        }
    }

    Button(
        onClick = onClick,
        modifier = Modifier.padding(horizontal = 8.dp),
        interactionSource = interactionSource
    ) {
        Text(
            modifier = Modifier.padding(8.dp),
            text = text
        )
    }
}

@Preview
@Composable
fun preview() {
    CounterController(
        CounterViewModel()
    )
}