package com.weisente.ndk.util;


import android.util.Log;



/**
 * 日志打印工具类
 * <p>
 * Created by Anenn on 2/27/18.
 */
public final class LogUtil {
    public static final int BUILD_TYPE_DEBUG = 1;
    public static final int BUILD_TYPE_RELEASE = 2;
    public static final int BUILD_TYPE_INTEST = 3;

    private static String TAG = "scs";
    private static boolean isOutput;

    private LogUtil() {
    }

    public static void init(int buildType) {
        init(buildType, TAG);
    }

    public static void init(int buildType, String tag) {
        isOutput = buildType != BUILD_TYPE_RELEASE;
        TAG = tag;

//        LogRecorder.getInstance().setLogEnabled(buildType != BUILD_TYPE_DEBUG);
    }

    private static String wrapTag(String tag) {
        return tag == null ? TAG : tag;
    }

    private static String wrapContent(Object object) {
        return object == null ? "Log with null object" : object.toString();
    }

    public static void v(Object object) {
        v(TAG, object);
    }

    public static void v(String tag, Object object) {
        if (isOutput)
            Log.v(wrapTag(tag), wrapContent(object));

//        LogRecorder.getInstance().record(object);
    }

    public static void d(Object object) {
        d(TAG, object);
    }

    public static void d(String tag, Object object) {
        Log.d(wrapTag(tag), wrapContent(object));

        // Debug 模式下，不保存日志到文件中
        // LogRecorder.getInstance().record(object);
    }

    public static void i(Object object) {
        i(TAG, object);
    }

    public static void i(String tag, Object object) {
        if (isOutput)
            Log.i(wrapTag(tag), wrapContent(object));

//        LogRecorder.getInstance().record(object);
    }

    public static void w(Object object) {
        w(TAG, object);
    }

    public static void w(String tag, Object object) {
        if (isOutput)
            Log.w(wrapTag(tag), wrapContent(object));

//        LogRecorder.getInstance().record(object);
    }

    public static void e(Object object) {
        e(TAG, object);
    }

    public static void e(String tag, Object object) {
        if (isOutput)
            Log.e(wrapTag(tag), wrapContent(object));

//        LogRecorder.getInstance().record(object);
    }
}
