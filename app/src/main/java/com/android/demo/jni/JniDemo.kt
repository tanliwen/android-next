package com.android.demo.jni

import androidx.annotation.Keep

@Keep
open class JniDemo {

    companion object {
        init {
            System.loadLibrary("jni-demo")
        }
    }

    external fun stringFromJNI(): String
}