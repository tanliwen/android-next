package com.android.demo.parcelable

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.activity.ComponentActivity
import com.android.demo.R
import com.android.demo.aidl.MyData
import com.android.demo.log.LogTag

class ParcelableActivityB : ComponentActivity(), View.OnClickListener {

    companion object {
        private const val TAG = "${LogTag.TAG_PARCELABLE}_b"
    }

    private lateinit var mTvTitle: TextView

    private var myData: MyData? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.i(TAG, "[${this::class.simpleName}]  onCreate")
        setContentView(R.layout.activity_layout_parcelable_b)
        mTvTitle = findViewById(R.id.tv_title)

        myData = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent?.getParcelableExtra("data", MyData::class.java)
        } else {
            intent?.getParcelableExtra("data")
        }

        print()
        findViewById<View>(R.id.btn_print).setOnClickListener(this)
        findViewById<View>(R.id.btn_add).setOnClickListener(this)
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.btn_print -> print()
            R.id.btn_add -> add()
            else -> {}
        }
    }

    private fun add() {
        myData?.let {
            ++it.data1
        }
        print()
    }

    private fun print() {
        myData?.let {
            mTvTitle.text = it.toString()
        }
    }

}