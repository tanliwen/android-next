package com.android.demo

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.ComponentActivity
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.android.demo.aidl.AidlClientActivity
import com.android.demo.lifecycle.TestLifecycleActivity
import com.android.demo.ui.theme.AndroidDemoTheme

class MainActivity : ComponentActivity(), View.OnClickListener {

     companion object {
         const val TAG = "tag_lifecycle_main"
     }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
       /* setContent {
            AndroidDemoTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Greeting(
                        name = "MainActivity",
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }*/
        setContentView(R.layout.layout)
        findViewById<View>(R.id.btn_aidl)?.setOnClickListener(this)
        findViewById<View>(R.id.btn_lifecycle)?.setOnClickListener(this)
        Log.d(TAG, "onCreate ${this::class.simpleName}")
    }


    override fun onClick(v: View?) {
        v?.let {
            when(v.id) {
                R.id.btn_lifecycle -> {
                    val intent = Intent()
                    intent.setClass(this.applicationContext, TestLifecycleActivity::class.java)
                    startActivity(intent)
                }
                R.id.btn_aidl -> {
                    val intent = Intent()
                    intent.setClass(this.applicationContext, AidlClientActivity::class.java)
                    startActivity(intent)
                }
            }
        }
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