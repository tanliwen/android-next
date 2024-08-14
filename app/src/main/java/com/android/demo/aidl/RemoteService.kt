package com.android.demo.aidl

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.os.Parcel
import android.os.Process
import android.os.RemoteException
import com.android.demo.log.LogTag
import com.android.demo.log.logD


class RemoteService : Service() {

    companion object {
        const val TAG = "${LogTag.TAG_AIDL}_service"
    }

    private lateinit var mMyData: MyData

    override fun onCreate() {
        super.onCreate()
        logD(TAG, "[${this::class.simpleName}] onCreate")
        initMyData()
    }

    private fun initMyData() {
        mMyData = MyData()
        mMyData.data1 = 10
        mMyData.data2 = 20
    }

    override fun onBind(intent: Intent?): IBinder? {
        logD(TAG, "[${this::class.simpleName}] onBind")
        return mBinder
    }

    override fun onUnbind(intent: Intent?): Boolean {
        logD(TAG, "[${this::class.simpleName}] onUnbind")
        return super.onUnbind(intent)
    }

    override fun onDestroy() {
        logD(TAG, "[${this::class.simpleName}] onDestroy")
        super.onDestroy()
    }

    /** * 实现IRemoteService.aidl中定义的方法  */
    private val mBinder: IRemoteService.Stub = object : IRemoteService.Stub() {
        @Throws(RemoteException::class)
        override fun getPid(): Int {
            logD(TAG, "[${RemoteService::class.simpleName}] getPid()=" + Process.myPid())
            return Process.myPid()
        }

        @Throws(RemoteException::class)
        override fun getMyData(): MyData {
            logD(TAG, "[${RemoteService::class.simpleName}] getMyData() $mMyData")
            return mMyData
        }

        /**此处可用于权限拦截 */
        @Throws(RemoteException::class)
        override fun onTransact(code: Int, data: Parcel, reply: Parcel?, flags: Int): Boolean {
            return super.onTransact(code, data, reply, flags)
        }
    }
}