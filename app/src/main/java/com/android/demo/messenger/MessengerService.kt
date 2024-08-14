package com.android.demo.messenger

import android.app.Service
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.IBinder
import android.os.Looper
import android.os.Message
import android.os.Messenger
import android.util.Log
import com.android.demo.log.LogTag

class MessengerService : Service() {

    companion object {
        const val TAG: String = "${LogTag.TAG_MESSENGER}_service"
    }

    // 创建一个用于接收客户端消息的 Handler
    internal class IncomingHandler : Handler(Looper.getMainLooper()) {
        override fun handleMessage(msg: Message) {
            Log.d(TAG, "${MessengerService::class.simpleName}]  Received message: " + msg.what)
            // 回复客户端消息
            val clientMessenger = msg.replyTo
            val replyMessage = Message.obtain(null, 200)
            val bundle = Bundle()
            bundle.putString("reply", "Hello from Service!")
            replyMessage.data = bundle
            try {
                clientMessenger.send(replyMessage)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    // 暴露给客户端用于通信的 Messenger 对象
    private val messenger: Messenger = Messenger(IncomingHandler())

    override fun onBind(intent: Intent): IBinder? {
        Log.d(TAG, "${this::class.simpleName}] onBind")
        return messenger.binder
    }

    override fun onUnbind(intent: Intent?): Boolean {
        Log.d(TAG, "${this::class.simpleName}] onUnbind")
        return super.onUnbind(intent)
    }

    override fun onDestroy() {
        Log.d(TAG, "${this::class.simpleName}] onDestroy")
        super.onDestroy()
    }
}