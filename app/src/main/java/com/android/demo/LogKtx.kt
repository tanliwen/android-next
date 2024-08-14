package com.android.demo

import android.util.Log

class LogKtx {
}

fun logD(tag: String, message: String) {
    Log.d(tag, message)
}

fun logI(tag: String, message: String) {
    Log.i(tag, message)
}