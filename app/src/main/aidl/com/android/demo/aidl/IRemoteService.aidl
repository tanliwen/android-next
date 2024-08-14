package com.android.demo.aidl;

import com.android.demo.aidl.MyData;

interface IRemoteService {
    int getPid();
    MyData getMyData();
}