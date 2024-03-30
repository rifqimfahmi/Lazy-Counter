package dev.rifqimfahmi.lazycounter

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.*
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import dev.rifqimfahmi.lazycounter.data.CounterAction
import dev.rifqimfahmi.lazycounter.ui.theme.LazyCounterTheme
import dev.rifqimfahmi.lazycounter.view.CounterController
import dev.rifqimfahmi.lazycounter.view.DialogConfirmReset
import dev.rifqimfahmi.lazycounter.viewmodel.CounterViewModel
import kotlin.math.min

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LazyCounterTheme {
                Counter()
            }
        }
    }
}

@Composable
fun Counter() {
    val viewModel: CounterViewModel = viewModel()
    val openAlertDialog = remember { mutableStateOf(false) }
    Scaffold(
        topBar = topBar(),
        floatingActionButton = {
            FloatingActionButton(onClick = {
                openAlertDialog.value = true
            }) {
                Icon(
                    Icons.Default.Refresh,
                    contentDescription = "Add"
                )
            }
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
        ) {
            if (openAlertDialog.value) {
                DialogConfirmReset(
                    onDismissRequest = { openAlertDialog.value = false },
                    onConfirmation = {
                        viewModel.reset()
                        openAlertDialog.value = false
                    }
                )
            }
            CounterNumber(viewModel)
            CounterController(viewModel)
        }
    }
}

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
            val multiplier = min(((char * 0.03) + 1), 3.0)
            Text(
                modifier = Modifier,
                text = char.toString(),
                style = MaterialTheme.typography.titleMedium,
                fontSize = 45.sp * multiplier
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
fun topBar(): @Composable () -> Unit = {
    TopAppBar(
        title = {
            Text("Count It")
        },
        colors = topAppBarColors(
            containerColor = MaterialTheme.colorScheme.primary,
            titleContentColor = MaterialTheme.colorScheme.secondaryContainer
        )
    )
}

@Preview(
    showBackground = true,
    apiLevel = 33
)
@Composable
fun GreetingPreview() {
    LazyCounterTheme {
        Counter()
    }
}