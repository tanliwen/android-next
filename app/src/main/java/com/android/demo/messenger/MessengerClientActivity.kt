package com.android.demo.messenger

import android.content.ComponentName
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.Handler
import android.os.IBinder
import android.os.Looper
import android.os.Message
import android.os.Messenger
import android.os.RemoteException
import android.util.Log
import android.widget.Button
import androidx.activity.ComponentActivity
import com.android.demo.R
import com.android.demo.log.LogTag

class MessengerClientActivity : ComponentActivity() {

    companion object {
        const val TAG: String = "${ LogTag.TAG_MESSENGER}_client"
    }

    private var serviceMessenger: Messenger? = null
    private var isBound = false

    private val connection: ServiceConnection = object : ServiceConnection {
        override fun onServiceConnected(name: ComponentName, service: IBinder) {
            Log.d(TAG, "[${MessengerClientActivity::class.simpleName}] Connected to service")
            serviceMessenger = Messenger(service)
            isBound = true

            // 发送消息给服务端
            val message = Message.obtain(null, 100)
            val clientMessenger = Messenger(ClientHandler())
            message.replyTo = clientMessenger
            try {
                serviceMessenger?.send(message)
            } catch (e: RemoteException) {
                e.printStackTrace()
            }
        }

        override fun onServiceDisconnected(name: ComponentName) {
            Log.d(TAG, "[${MessengerClientActivity::class.simpleName}] Disconnected from service")
            serviceMessenger = null
            isBound = false
        }
    }

    internal class ClientHandler : Handler(Looper.getMainLooper()) {
        override fun handleMessage(msg: Message) {
            Log.d(TAG, "[${MessengerClientActivity::class.simpleName}] Received reply from service: " + msg.what)
            val bundle = msg.data
            val reply = bundle.getString("reply")
            Log.d(TAG, "[${MessengerClientActivity::class.simpleName}] Received reply content: $reply")
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_layout_messenger)
        val bindButton = findViewById<Button>(R.id.btn_bind)
        bindButton.setOnClickListener {
            val intent = Intent(this@MessengerClientActivity, MessengerService::class.java)
            bindService(intent, connection, BIND_AUTO_CREATE)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        if (isBound) {
            unbindService(connection)
        }
    }
}