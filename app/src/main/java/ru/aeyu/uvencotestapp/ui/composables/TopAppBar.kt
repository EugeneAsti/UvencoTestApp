package ru.aeyu.uvencotestapp.ui.composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.aeyu.uvencotestapp.R
import ru.aeyu.uvencotestapp.ui.theme.UvencoTestAppTheme


@Composable
fun TopAppBar(
    modifier: Modifier = Modifier,
    time: String = "--:--",
    temperature: String = "999.0",
    onUSignClick: () -> Unit = {},
) {

    Column(
        modifier = modifier
            .fillMaxWidth()
            .height(height = 54.dp),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = modifier
                .background(color = MaterialTheme.colorScheme.secondary)
                .fillMaxWidth()
                .padding(top = 16.dp, start = 8.dp, end = 2.dp, bottom = 13.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row {
                Image(
                    painter = painterResource(id = R.drawable.u_sign),
                    contentDescription = "u_sing",
                    modifier = modifier.clickable(onClick = onUSignClick)
                )
                Text(
                    modifier = modifier.padding(start = 12.dp),
                    text = stringResource(R.string.header_name),
                    fontWeight = FontWeight.Medium,
                    color = MaterialTheme.colorScheme.onPrimary
                )
            }
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    modifier = modifier.padding(end = 8.dp, start = 8.dp),
                    text = time,
                    fontWeight = FontWeight.Medium,
                    color = MaterialTheme.colorScheme.onSecondary
                )
                Text(
                    modifier = modifier.padding(start = 8.dp),
                    fontWeight = FontWeight.Medium,
                    text = stringResource(id = R.string.temperature, temperature),
                    color = MaterialTheme.colorScheme.onSecondary
                )
                Image(
                    modifier = modifier.padding(end = 8.dp),
                    painter = painterResource(id = R.drawable.drop),
                    contentDescription = "flag",
                    contentScale = ContentScale.FillWidth,

                    )
                Image(
                    modifier = modifier
                        .padding(start = 8.dp)
                        .clip(RoundedCornerShape(2.dp))
                        .size(width = 12.dp, height = 12.dp),
                    painter = painterResource(id = R.drawable.russian_flag),
                    contentDescription = "flag",
                    contentScale = ContentScale.FillWidth,

                    )
                Text(
                    modifier = modifier.padding(end = 8.dp, start = 2.dp),
                    text = stringResource(R.string.lang_ru),
                    fontWeight = FontWeight.Medium,
                    color = MaterialTheme.colorScheme.onSecondary
                )
            }
        }
        HorizontalDivider(
            thickness = 1.dp,
            color = MaterialTheme.colorScheme.onPrimary
        )
    }
}

@Preview(name = "TopAppBar")
@Composable
fun PreviewTopAppBar() {
    UvencoTestAppTheme {
        TopAppBar()
    }
}