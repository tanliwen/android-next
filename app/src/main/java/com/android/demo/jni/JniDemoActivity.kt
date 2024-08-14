package com.android.demo.jni

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.activity.ComponentActivity
import com.android.demo.R
import com.android.demo.R.id
import com.android.demo.aidl.MyData
import com.android.demo.lifecycle.TestLifecycleActivity
import com.android.demo.log.LogTag

class JniDemoActivity : ComponentActivity(), View.OnClickListener {

    companion object {
        private const val TAG = LogTag.TAG_JNI
    }

    var jniDemo: JniDemo = JniDemo()

    private lateinit var mTvTitle: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.i(TAG, "[${this::class.simpleName}]  onCreate")
        setContentView(R.layout.activity_layout_jni_demo)
        mTvTitle = findViewById(id.tv_title)
        findViewById<View>(id.btn_print).setOnClickListener(this)
    }

    override fun onClick(v: View) {
        when (v.id) {
            id.btn_print -> print()
            else -> {}
        }
    }

    private fun print() {
        mTvTitle.text = jniDemo.stringFromJNI()
    }
}