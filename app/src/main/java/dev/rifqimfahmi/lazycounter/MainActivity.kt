package dev.rifqimfahmi.lazycounter

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.PressInteraction
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import dev.rifqimfahmi.lazycounter.ui.theme.LazyCounterTheme
import dev.rifqimfahmi.lazycounter.view.CounterController
import dev.rifqimfahmi.lazycounter.viewmodel.CounterViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

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
    Scaffold(
        topBar = topBar(),
        floatingActionButton = {
            FloatingActionButton(onClick = { }) {
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
        val charState by viewModel.count.observeAsState(0)
        AnimatedContent(
            targetState = charState,
            transitionSpec = {
                slideInVertically { it } togetherWith slideOutVertically { -it }
            },
            label = "anim"
        ) { char ->
            Text(
                modifier = Modifier,
                text = char.toString(),
                fontSize = 20.sp
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
            containerColor = MaterialTheme.colorScheme.secondaryContainer,
            titleContentColor = MaterialTheme.colorScheme.primary
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