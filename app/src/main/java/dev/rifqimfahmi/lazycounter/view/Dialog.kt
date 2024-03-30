package dev.rifqimfahmi.lazycounter.view

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun GeneralDialog(
    onDismissRequest: () -> Unit,
    onConfirmation: () -> Unit,
    dialogTitle: String,
    dialogText: String,
    icon: ImageVector,
) {
    AlertDialog(
        icon = {
            Icon(icon, contentDescription = "Example Icon")
        },
        title = {
            Text(text = dialogTitle)
        },
        text = {
            Text(text = dialogText)
        },
        onDismissRequest = {
            onDismissRequest()
        },
        confirmButton = {
            TextButton(
                onClick = {
                    onConfirmation()
                }
            ) {
                Text("Confirm")
            }
        },
        dismissButton = {
            TextButton(
                onClick = {
                    onDismissRequest()
                }
            ) {
                Text("Dismiss")
            }
        }
    )
}

@Composable
fun DialogConfirmReset(
    onDismissRequest: () -> Unit,
    onConfirmation: () -> Unit
) {
    GeneralDialog(
        onDismissRequest = onDismissRequest,
        onConfirmation = onConfirmation,
        dialogTitle = "Confirm Reset",
        dialogText = "Are you sure to reset counter state?",
        icon = Icons.Default.Info
    )
}

@Preview(showBackground = true, apiLevel = 33)
@Composable
fun PreviewDialog() {
    GeneralDialog(
        onDismissRequest = {  },
        onConfirmation = {
            println("Confirmation registered") // Add logic here to handle confirmation.
        },
        dialogTitle = "Confirm Reset",
        dialogText = "Are you sure to reset counter state?",
        icon = Icons.Default.Info
    )
}