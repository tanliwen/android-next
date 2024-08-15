#include <jni.h>
#include <string>
#include <sstream>
#include <unistd.h>
#include <sys/epoll.h>
#include <sys/eventfd.h>
#include <fcntl.h>
#include <iostream>
#include "native-log.h"

int mWakeEventFd = -1;
int mEpollFd = -1;
int CLOSE_END_DATA = 5;

void writeCloseData(uint64_t uiWrite);

void releaseEpollEnv() {
    if (mEpollFd > 0) {
        close(mEpollFd);
        mEpollFd = -1;
    }
    if (mWakeEventFd > 0) {
        close(mWakeEventFd);
        mWakeEventFd = -1;
    }
}

int createEpollServer() {
    // 创建 epoll 实例
    LOGI("Enter epoll test function thread id = %ld", pthread_self());
    mEpollFd = epoll_create(10);
    if (mEpollFd == -1) {
        std::cerr << "epoll_create1 failed" << std::endl;
        return 1;
    } else {
        LOGI("epoll create1 succeed epoll_fd = %d", mEpollFd);
    }
    mWakeEventFd = eventfd(0, 0);
    if (mWakeEventFd == -1) {
        LOGE("open failed");
        releaseEpollEnv();
        return 1;
    }
    LOGI("open succeed mWakeEventFd = %d", mWakeEventFd);
    struct epoll_event event;
    memset(&event, 0, sizeof(event));
    event.events = EPOLLIN;
    event.data.fd = mWakeEventFd;
    if (epoll_ctl(mEpollFd, EPOLL_CTL_ADD, mWakeEventFd, &event) == -1) {
        LOGE("epoll_ctl failed");
        releaseEpollEnv();
        return 1;
    }

    struct epoll_event events[10];
    uint64_t uiRead;
    int flag = 1;
    while (flag == 1) {
        // 等待事件发生
        LOGE("epoll wait");
        int num_events = epoll_wait(mEpollFd, events, 10, -1);
        if (num_events == -1) {
            LOGE("epoll_wait failed");
            releaseEpollEnv();
            return 1;
        }
        for (int i = 0; i < num_events; i++) {
            int result = eventfd_read(mWakeEventFd, &uiRead);
            if (result != 0) {
                LOGE("eventfd_read failed");
                releaseEpollEnv();
                return 1;
            } else {
                if (uiRead >= CLOSE_END_DATA) {
                    LOGI("eventfd_read exit %ld", uiRead);
                    flag = 0;
                    break;
                } else {
                    LOGI("eventfd_read succeed %ld", uiRead);
                }
            }
        }
    }
    LOGE("epoll end");
    releaseEpollEnv();
    return 0;
}

extern "C" JNIEXPORT jstring JNICALL
Java_com_android_demo_epoll_EpollDemo_createServer(
        JNIEnv *env,
        jobject /* this */) {
    std::string hello = "create from epoll test C++";
    createEpollServer();
    return env->NewStringUTF(hello.c_str());
}

void writeCloseData(uint64_t uiWrite) {
    if (mWakeEventFd > 0) {
        if (0 != eventfd_write(mWakeEventFd, uiWrite)) {
            LOGE("write eventfd_write failed %ld", mWakeEventFd);
        } else {
            LOGI("write eventfd_write succeed %ld", mWakeEventFd);
        }
    } else {
        LOGW("write eventfd_write ignore %ld", mWakeEventFd);
    }
}

extern "C" JNIEXPORT jstring JNICALL
Java_com_android_demo_epoll_EpollDemo_release(
        JNIEnv *env,
        jobject /* this */) {
    std::string hello = "called epoll release from epoll test C++";
    LOGI("called epoll release");
    if (mWakeEventFd > 0) {
        writeCloseData(CLOSE_END_DATA);
    }
    releaseEpollEnv();
    return env->NewStringUTF(hello.c_str());
}

extern "C" JNIEXPORT jstring JNICALL
Java_com_android_demo_epoll_EpollDemo_write(
        JNIEnv *env,
        jobject /* this */, jint data) {
    std::stringstream ss;
    ss << "write " << data << " from epoll test C++";
    std::string hello = ss.str();
    uint64_t uiWrite = data;
    writeCloseData(uiWrite);
    return env->NewStringUTF(hello.c_str());
}