package ru.aeyu.uvencotestapp.ui.composables

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.aeyu.uvencotestapp.R
import ru.aeyu.uvencotestapp.ui.theme.UvencoTestAppTheme

@Composable
fun ErrorDialog(
    modifier: Modifier = Modifier,
    text: String,
    onExit: () -> Unit = {}
) {
    AlertDialog(
        modifier = modifier,
        shape = MaterialTheme.shapes.small,
        onDismissRequest = { onExit() },
        confirmButton = {
            Button(
                onClick = { onExit() },
                shape = MaterialTheme.shapes.small,
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer
                ),
                elevation = ButtonDefaults.buttonElevation(
                    defaultElevation = 7.dp
                ),
            ) {
                Text(
                    text = stringResource(id = R.string.bt_ok),
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.error
                )
            }
        },
        containerColor = MaterialTheme.colorScheme.primaryContainer,
        title = {
            Text(
                text = stringResource(id = R.string.error),
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.error
            )
        },
        text = { Text(text = text, style = MaterialTheme.typography.bodyMedium) },
    )
}

@Preview(showBackground = true)
@Composable
fun ErrorDialogPreview() {
    UvencoTestAppTheme {
        ErrorDialog(
            text = "test err"
        ) {}
    }
}