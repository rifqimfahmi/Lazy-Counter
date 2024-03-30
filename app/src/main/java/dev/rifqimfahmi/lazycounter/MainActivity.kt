package dev.rifqimfahmi.lazycounter

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.*
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import dev.rifqimfahmi.lazycounter.ui.theme.LazyCounterTheme
import dev.rifqimfahmi.lazycounter.view.CounterController
import dev.rifqimfahmi.lazycounter.view.CounterNumber
import dev.rifqimfahmi.lazycounter.view.DialogConfirmReset
import dev.rifqimfahmi.lazycounter.viewmodel.CounterViewModel

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
        topBar = { TopBar() },
        floatingActionButton = { FABReset(viewModel) }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
        ) {
            DialogComposable(viewModel)
            CounterNumber(viewModel)
            CounterController(viewModel)
        }
    }
}

@Composable
fun DialogComposable(viewModel: CounterViewModel) {
    val openAlertDialog = viewModel.showResetDialog.observeAsState()
    if (openAlertDialog.value == true) {
        DialogConfirmReset(
            onDismissRequest = { viewModel.hideResetDialogConfirmation() },
            onConfirmation = {
                viewModel.reset()
                viewModel.hideResetDialogConfirmation()
            }
        )
    }
}

@Composable
fun FABReset(
    viewModel: CounterViewModel
) {
    FloatingActionButton(onClick = {
        viewModel.showResetDialogConfirmation()
    }) {
        Icon(
            Icons.Default.Refresh,
            contentDescription = "Reset"
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar() {
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