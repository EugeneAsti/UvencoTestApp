package ru.aeyu.uvencotestapp.ui.composables


import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.aeyu.uvencotestapp.ui.theme.UvencoTestAppTheme

@Composable
fun ProgressIndicator() {
    LinearProgressIndicator(
        modifier = Modifier
            .padding(2.dp)
            .fillMaxWidth()
            .height(2.dp),
        trackColor = Color.LightGray,
        color = MaterialTheme.colorScheme.onTertiary
    )
}

@Preview(showBackground = true)
@Composable
fun ProgressIndicatorPreview() {
    UvencoTestAppTheme {
        ProgressIndicator()
    }
}