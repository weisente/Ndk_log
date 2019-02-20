package com.weisente.ndk.log;

import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;

import com.weisente.ndk.util.AppHelper;

import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * Copyright (C)
 * <p>
 * FileName: MmapLogRecorder
 * <p>
 * Author: san
 * <p>
 * Date: 2019/2/20 11:22 AM
 * <p>
 * Description: 使用mmap进行log日志 内存变成-》文件  序列化
 */
public class MmapLogRecorder {

    public static final int BUFFER_SIZE = 1024 * 400; //400k

    private static final String LOG_DIRECTORY = AppHelper.getPublicDir("log");

    private static final String LOG_NAME_SUFFIX = "txt";

    private boolean isLogEnabled;

    private Handler mLogHandler;

    private String mLogFilePrefix;
    // curLogDate 记录的时间是当天的23:59:59
    private long curLogDate = 0;


    /**
     * 采用静态内部类的方式 对单列进行优化
     */
    public static class SingletonHolder{
        private static MmapLogRecorder instance = new MmapLogRecorder();
    }

    public static MmapLogRecorder getInstance() {
        return SingletonHolder.instance;
    }

    private MmapLogRecorder(){
        HandlerThread handlerThread = new HandlerThread("looper");
        handlerThread.start();

        mLogHandler = new Handler(handlerThread.getLooper()) {
            @Override public void handleMessage(Message msg) {
                handleLog((LogMsg) msg.obj);
            }
        };

        Calendar cal = new GregorianCalendar();
        cal.set(Calendar.HOUR_OF_DAY, 23);
        cal.set(Calendar.MINUTE, 59);
        cal.set(Calendar.SECOND, 59);
        cal.set(Calendar.MILLISECOND, 999);

        SingletonHolder.instance.curLogDate = cal.getTimeInMillis();
    }

    /**
     * 设置日志开关
     *
     * @param logEnabled true 表示开启日志记录，反之亦然
     */
    public void setLogEnabled(boolean logEnabled){
        isLogEnabled = logEnabled;
    }

    /**
     * 设置日志文件名前缀
     *
     * @param logFilePrefix 日志文件名前缀
     */
    public void setLogFilePrefix(String logFilePrefix) {
        mLogFilePrefix = logFilePrefix;
    }

    /**
     * @param log
     */
    private void handleLog(LogMsg log) {
        if(log.date > curLogDate){

        }
    }

    /**
     * 每一次log信息的结构体
     */
    private static class LogMsg {
        long date;
        String log;
    }

    static {
        System.loadLibrary("log-lib");
    }

    /**
     * @param bufferPath Mmap 缓存文件的地址
     * @param mMapSize Mmap 缓存文件的大小
     * @param logPath 日志的路径
     * @param compress 是否需要压缩（现在先不做）
     * @return
     */
    private native static long initMmap(String bufferPath, int mMapSize, String logPath, boolean compress);

    /**
     * @param ptr 从哪里开始写的指针
     * @param log 日志信息
     */
    private native void write(long ptr, String log);

    /**
     * @param ptr 把内容写到文件
     */
    private native void flushAsync(long ptr);

    /**
     * @param ptr 释放资源
     */
    private native void release(long ptr);

    /**
     * @param ptr 写文件的指针
     * @param logPath 新的日志的地址
     */
    private native void changeLogPath(long ptr, String logPath);

}
