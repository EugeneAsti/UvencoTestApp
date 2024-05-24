package ru.aeyu.uvencotestapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import dagger.hilt.EntryPoint
import dagger.hilt.android.AndroidEntryPoint
import ru.aeyu.uvencotestapp.ui.navigation.AppNavigationHost
import ru.aeyu.uvencotestapp.ui.theme.UvencoTestAppTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            UvencoTestAppTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.surface
                ) {
                    val navHostController = rememberNavController()
                    AppNavigationHost(navHostController = navHostController) {
                        if(it)
                            finish()
                    }
                }
            }
        }
    }
}

@Preview(name = "AppSurface")
@Composable
fun PreviewSurface(){
    UvencoTestAppTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.surface
        ) {

        }
    }
}