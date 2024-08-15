package com.android.demo.epoll

import androidx.annotation.Keep

@Keep
open class EpollDemo {
    companion object {
        init {
            System.loadLibrary("jni-demo")
        }
    }

    external fun createServer(): String
    external fun release(): String
    external fun write(data: Int): String


}