package com.ghn.kotlin_compose

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.core.Animatable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ghn.kotlin_compose.ui.navigation.AppNavigation
import com.ghn.kotlin_compose.ui.theme.KotlinComposeTheme
import com.ghn.kotlin_compose.ui.theme.MyAppTheme
import kotlinx.coroutines.NonCancellable.isActive
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyAppTheme {
                Surface(color = MaterialTheme.colorScheme.background) {
                    AppNavigation()
                }
            }
        }
    }
}


@Composable
fun MoviesScreen(snackbarHostState: SnackbarHostState) {

    // Creates a CoroutineScope bound to the MoviesScreen's lifecycle
    val scope = rememberCoroutineScope()

    Scaffold(
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState)
        }
    ) { contentPadding ->
        Column(Modifier.padding(contentPadding)) {
            Button(
                onClick = {
                    // Create a new coroutine in the event handler to show a snackbar
                    scope.launch {
                        snackbarHostState.showSnackbar("Something happened!")
                    }
                }
            ) {
                Text("Press me")
            }
        }
    }
}


@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Composable
fun MyComposable(modifier: Modifier = Modifier) {
    Column {
        Text("JetPack", modifier = modifier)
        Text("Compose", modifier = modifier)
    }
}

@Composable
fun LoginScreen(showError: Boolean) {
    if (showError) {
        LoginError()
        // Allow the pulse rate to be configured, so it can be sped up if the user is running
        // out of time
        val pulseRateMs by remember { mutableStateOf(3000L) }
        val alpha = remember { Animatable(1f) }
        LaunchedEffect(pulseRateMs) { // Restart the effect when the pulse rate changes
            while (isActive) {
                delay(pulseRateMs) // Pulse the alpha every pulseRateMs to alert the user
                alpha.animateTo(0f)
                alpha.animateTo(1f)
            }
        }
    }
    LoginInput() // This call site affects where LoginInput is placed in Composition
}

@Composable
fun LoginInput() {
    Log.i("TAG", "LoginInput: 111") /* ... */
}

@Composable
fun LoginError() {
    Log.i("TAG", "LoginError: 222") /* ... */
}


@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    KotlinComposeTheme {
        Greeting("Android")
    }
}

