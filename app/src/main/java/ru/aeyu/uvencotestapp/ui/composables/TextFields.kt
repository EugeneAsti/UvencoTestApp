package ru.aeyu.uvencotestapp.ui.composables

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DockedSearchBar
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.aeyu.uvencotestapp.ui.theme.UvencoTestAppTheme

@Composable
fun OutlinedTextFieldWithoutBorders(
    isLoading: Boolean = false,
    startingValue: String,
    onValueChange: (String) -> Unit,
    labelText: String,
    suffix: @Composable (() -> Unit)? = null,
    isError: Boolean = false,
) {
    var text by remember(startingValue) { mutableStateOf(startingValue) }
    println("!!!###!!! ------------------- OutlinedTextFieldWithoutBorders: text=${text}, startingValue = $startingValue")

    OutlinedTextField(
        modifier = Modifier
            .padding(all = 12.dp)
            .fillMaxWidth(),
        value = text,
        textStyle = MaterialTheme.typography.bodyLarge,
        onValueChange = {
            text = it
            onValueChange(it)
        },
        label = {
            Text(
                text = labelText,
            )
        },
        suffix = suffix,
        isError = isError,
        colors = OutlinedTextFieldDefaults.colors(
            cursorColor = MaterialTheme.colorScheme.onSurface,
            focusedTextColor = MaterialTheme.colorScheme.onSurface,
            unfocusedTextColor = MaterialTheme.colorScheme.onSurface,
            focusedLabelColor = MaterialTheme.colorScheme.onSecondaryContainer,
            unfocusedLabelColor = MaterialTheme.colorScheme.onSecondaryContainer,
            focusedBorderColor = Color.Transparent,
            unfocusedBorderColor = Color.Transparent,
            errorBorderColor = MaterialTheme.colorScheme.error,
            focusedContainerColor = MaterialTheme.colorScheme.secondaryContainer,
            unfocusedContainerColor = MaterialTheme.colorScheme.secondaryContainer,
        ),
        trailingIcon = {
            if(isLoading)
                CircularProgressIndicator(
                    modifier = Modifier.size(24.dp),
                    color = MaterialTheme.colorScheme.onPrimary,
                    trackColor = MaterialTheme.colorScheme.onSurface,
                    strokeWidth = 2.dp
                )
        },
        singleLine = true,
    )
}

@Preview(name = "OutlinedTextFieldWithoutBorders")
@Composable
fun PreviewOutlinedTextFieldWithoutBorders() {
    UvencoTestAppTheme {
        OutlinedTextFieldWithoutBorders(
            isLoading = true,
            startingValue = "Кофе амаретто",
            onValueChange = {},
            labelText = "Наименование",
            isError = true
        )
    }
}
