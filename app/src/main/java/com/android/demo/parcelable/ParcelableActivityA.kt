package com.android.demo.parcelable

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

class ParcelableActivityA : ComponentActivity(), View.OnClickListener {

    companion object {
        private const val TAG = "${LogTag.TAG_PARCELABLE}_a"
    }

    private lateinit var mTvTitle: TextView
    var myData: MyData? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.i(TAG, "[${this::class.simpleName}]  onCreate")
        setContentView(R.layout.activity_layout_parcelable_a)

        myData = MyData()
        myData?.data1 = 1
        myData?.data2 = 1

        mTvTitle = findViewById(id.tv_title)
        findViewById<View>(id.btn_jump).setOnClickListener(this)
        findViewById<View>(id.btn_print).setOnClickListener(this)
    }

    override fun onClick(v: View) {
        when (v.id) {
            id.btn_jump -> gotoNewActivityWithParcelable()
            id.btn_print -> print()
            else -> {}
        }
    }

    private fun gotoNewActivityWithParcelable() {
        val intent = Intent()
        intent.setClass(this.applicationContext, ParcelableActivityB::class.java)
        intent.putExtra("data", myData)
        startActivity(intent)
    }

    private fun print() {
        myData?.let {
            mTvTitle.text = it.toString()
        }
    }
}