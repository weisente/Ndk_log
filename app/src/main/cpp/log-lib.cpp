//
// Created by san on 2019/2/19.
//

#include <jni.h>
#include <string>

//对文件操作的库
#include <fcntl.h>
#include "includes/LogBuffer.h"


static const char* const kClassDocScanner = "com/weisente/ndk/MmapLogRecorder";

static AsyncFileFlush *fileFlush = nullptr;


static jlong initMmap(JNIEnv *env, jclass type, jstring buffer_path_,
                        jint capacity, jstring log_path_, jboolean compress_) {
    const char *buffer_path = env->GetStringUTFChars(buffer_path_, 0);
    const char *log_path = env->GetStringUTFChars(log_path_, 0);
    size_t buffer_size = static_cast<size_t>(capacity);
    int buffer_fd = open(buffer_path, O_RDWR|O_CREAT, S_IRUSR|S_IWUSR|S_IRGRP|S_IROTH);
    // buffer 的第一个字节会用于存储日志路径名称长度，后面紧跟日志路径，之后才是日志信息
    if (fileFlush == nullptr) {
        fileFlush = new AsyncFileFlush();
    }
    


}

static void release(JNIEnv *env, jobject instance, jlong ptr){

}

static void write(JNIEnv *env, jobject instance, jlong ptr,
                        jstring log_){

}

static void  flushAsync(JNIEnv *env, jobject instance, jlong ptr) {

}

static void changeLogPath(JNIEnv *env, jobject instance, jlong ptr,
                                jstring logFilePath) {
 ;
}


static JNINativeMethod gMethods[] = {

        {
                "initMmap",
                "(Ljava/lang/String;ILjava/lang/String;Z)J",
                (void *) initMmap
        },

        {
                "write",
                "(JLjava/lang/String;)V",
                (void *) write
        },

        {
                "flushAsync",
                "(J)V",
                (void *) flushAsync
        },

        {
                "changeLogPathNative",
                "(JLjava/lang/String;)V",
                (void *) changeLogPath
        },

        {
                "release",
                "(J)V",
                (void *) release
        }
};

extern "C"
JNIEXPORT jint JNICALL
JNI_OnLoad(JavaVM* vm, void* reserved) {
    JNIEnv *env = NULL;
    if (vm->GetEnv((void **) &env, JNI_VERSION_1_4) != JNI_OK) {
        return JNI_FALSE;
    }
    jclass classDocScanner = env->FindClass(kClassDocScanner);
    if(env -> RegisterNatives(classDocScanner, gMethods, sizeof(gMethods)/ sizeof(gMethods[0])) < 0) {
        return JNI_FALSE;
    }
    return JNI_VERSION_1_4;
}
