package com.android.demo.aidl

import android.content.ComponentName
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import android.os.Process
import android.os.RemoteException
import android.util.Log
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.activity.ComponentActivity
import com.android.demo.R
import com.android.demo.R.id
import com.android.demo.log.LogTag

class AidlClientActivity : ComponentActivity(), View.OnClickListener {

    companion object {
        private const val TAG = "${LogTag.TAG_AIDL}_client"
    }

    private var mRemoteService: IRemoteService? = null
    private var mIsBound = false
    private lateinit var mCallBackTv: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.i(TAG, "[${this::class.simpleName}]  onCreate")
        setContentView(R.layout.activity_layout_aidl)
        mCallBackTv = findViewById(id.tv_callback)
        mCallBackTv.setText(R.string.remote_service_unattached)
        findViewById<View>(id.btn_bind).setOnClickListener(this)
        findViewById<View>(id.btn_unbind).setOnClickListener(this)
        findViewById<View>(id.btn_kill).setOnClickListener(this)
    }

    override fun onClick(v: View) {
        when (v.id) {
            id.btn_bind -> bindRemoteService()
            id.btn_unbind -> unbindRemoteService()
            id.btn_kill -> killRemoteService()
            else -> {}
        }
    }

    /**
     * 用于监控远程服务连接的状态
     */
    private val mConnection: ServiceConnection = object : ServiceConnection {
        override fun onServiceConnected(name: ComponentName, service: IBinder) {
            mRemoteService = IRemoteService.Stub.asInterface(service)
            var pidInfo: String? = null
            try {
                val myData = mRemoteService?.getMyData()
                pidInfo = "pid=" + mRemoteService?.getPid() +
                        ", data1 = " + myData?.data1 +
                        ", data2=" + myData?.data2
            } catch (e: RemoteException) {
                e.printStackTrace()
            }
            Log.i(TAG, "[${AidlClientActivity::class.simpleName}] onServiceConnected")
            mCallBackTv.text = pidInfo
            Toast.makeText(
                this@AidlClientActivity,
                R.string.remote_service_connected,
                Toast.LENGTH_SHORT
            ).show()
        }

        override fun onServiceDisconnected(name: ComponentName) {
            Log.i(TAG, "[${AidlClientActivity::class.simpleName}] onServiceDisconnected")
            mCallBackTv.setText(R.string.remote_service_disconnected)
            mRemoteService = null
            Toast.makeText(
                this@AidlClientActivity,
                R.string.remote_service_disconnected,
                Toast.LENGTH_SHORT
            ).show()
        }
    }


    /**
     * 绑定远程服务
     */
    private fun bindRemoteService() {
        Log.i(TAG, "[${AidlClientActivity::class.simpleName}] bindRemoteService")
        val intent = Intent(this@AidlClientActivity, RemoteService::class.java)
//        intent.setAction(IRemoteService::class.java.name)
        bindService(intent, mConnection, BIND_AUTO_CREATE)

        mIsBound = true
        mCallBackTv.setText(R.string.binding)
    }

    /**
     * 解除绑定远程服务
     */
    private fun unbindRemoteService() {
        if (!mIsBound) {
            return
        }
        Log.i(TAG, "[${AidlClientActivity::class.simpleName}] unbindRemoteService ==>")
        unbindService(mConnection)
        mIsBound = false
        mCallBackTv.setText(R.string.unbinding)
    }

    /**
     * 杀死远程服务
     */
    private fun killRemoteService() {
        Log.i(TAG, "[${AidlClientActivity::class.simpleName}] killRemoteService")
        try {
            mRemoteService?.let {
                Process.killProcess(it.pid)
            }
            mCallBackTv.setText(R.string.kill_success)
        } catch (e: RemoteException) {
            e.printStackTrace()
            Toast.makeText(this@AidlClientActivity, R.string.kill_failure, Toast.LENGTH_SHORT).show()
        }
    }
}