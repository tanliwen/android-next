package com.android.demo.lifecycle

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.android.demo.log.LogTag
import com.android.demo.ui.theme.AndroidDemoTheme

class TestLifecycleActivity : ComponentActivity() {

     companion object {
         const val TAG = LogTag.TAG_LIFECYCLE
     }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AndroidDemoTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Greeting(
                        name = "TestLifecycleActivity activity",
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
        Log.d(TAG, "onCreate ${this::class.simpleName}")
    }

    override fun onRestart() {
        super.onRestart()
        Log.d(TAG, "onRestart ${this::class.simpleName}")
    }

    override fun onStart() {
        super.onStart()
        Log.d(TAG, "onStart ${this::class.simpleName}")
    }

    override fun onStop() {
        super.onStop()
        Log.d(TAG, "onStop ${this::class.simpleName}")
    }

    override fun onPause() {
        super.onPause()
        Log.d(TAG, "onPause ${this::class.simpleName}")
    }

    override fun onResume() {
        super.onResume()
        Log.d(TAG, "onResume ${this::class.simpleName}")
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        Log.d(TAG, "onSaveInstanceState ${this::class.simpleName}")
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        Log.d(TAG, "onRestoreInstanceState ${this::class.simpleName}")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "onDestroy ${this::class.simpleName}")
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    AndroidDemoTheme {
        Greeting("Android")
    }
}