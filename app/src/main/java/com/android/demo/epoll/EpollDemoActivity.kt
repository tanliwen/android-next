package com.android.demo.epoll

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.activity.ComponentActivity
import com.android.demo.R
import com.android.demo.log.LogTag
import com.android.demo.log.logI
import java.io.File

class EpollDemoActivity : ComponentActivity(), View.OnClickListener {

    companion object {
        private const val TAG = LogTag.TAG_NATIVE_EPOLL
    }

    private var jniDemo: EpollDemo = EpollDemo()

    private lateinit var mTvTitle: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.i(TAG, "[${this::class.simpleName}]  onCreate")
        setContentView(R.layout.activity_layout_epoll_demo)
        mTvTitle = findViewById(R.id.tv_title)
        findViewById<View>(R.id.btn_create).setOnClickListener(this)
        findViewById<View>(R.id.btn_write).setOnClickListener(this)
        findViewById<View>(R.id.btn_release).setOnClickListener(this)
        val file = File(baseContext.externalCacheDir, "epoll_test.txt")
        if (!file.exists()) {
            file.createNewFile()
        }
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.btn_create -> create()
            R.id.btn_write -> write()
            R.id.btn_release -> release()
            else -> {}
        }
    }

    private fun release (){
        jniDemo.release()
    }

    var count = 0

    override fun onDestroy() {
        super.onDestroy()
        release()
    }

    private fun write() {
        logI(TAG, "java write thread id = ${Thread.currentThread().id}")
        val result = jniDemo.write(++count)
        logI(TAG, "write result = $result")
    }

    private fun create() {
        Thread {
            logI(TAG, "java create thread id = ${Thread.currentThread().id}")
            val result = jniDemo.createServer()
            logI(TAG, "java thread result = $result")
        }.start()
    }
}