package com.weisente.ndk.util;

import android.os.Environment;
import android.util.Log;



import java.io.File;

public class AppHelper {
  //
  private static final String DIR_TYPE = "log";

  /**
   * 申请 root 权限
   */
  public static void requestRoot() {
    new Thread() {
      @Override
      public void run() {
        try {
          Runtime.getRuntime().exec("su");
        } catch (Exception e) {
          LogUtil.e("Exception AppHelper.requestRoot():\n" + Log.getStackTraceString(e));
        }
      }
    }.start();
  }

  /**
   * 重启手机
   */
  public static void reboot() {
    new Thread() {
      @Override
      public void run() {
        try {
          Runtime.getRuntime().exec("su -c reboot");
        } catch (Exception e) {
          LogUtil.e("Exception AppHelper.reboot():\n" + Log.getStackTraceString(e));
        }
      }
    }.start();
  }

  /**
   * kill app for restart in new thread
   */
  public static void kill(final String packageName) {
    new Thread() {
      @Override
      public void run() {
        try {
          ShellUtil.execCommand("am force-stop " + packageName, true);
        } catch (Exception e) {
          LogUtil.e("Exception AppHelper.killSelf():\n" + Log.getStackTraceString(e));
        }
      }
    }.start();
  }

  /**
   * kill app for restart in current thread
   */
  public static void stop(String packageName) {
    ShellUtil.execCommand("am force-stop " + packageName, true);
  }

  /**
   * start an app in current thread
   */
  public static void start(String packageName, String activityName) {
    ShellUtil.execCommand(
        "am start -a android.intent.action.MAIN -n " + packageName + "/" + activityName, true);
  }

  /**
   * 获取公共路径
   */
  public static String getPublicDir(String subDir) {
    File file = Environment.getExternalStoragePublicDirectory(DIR_TYPE + File.separator + subDir);
    if (!(file.exists() && file.isDirectory())) {
      file.mkdirs();
    }

    return file.getAbsolutePath();
  }

  /**
   * 获取公共路径
   */
  public static String getPublicDir(String parentDir, String subDir) {
    File file = Environment.getExternalStoragePublicDirectory(parentDir + File.separator + subDir);
    if (!(file.exists() && file.isDirectory())) {
      file.mkdirs();
    }

    return file.getAbsolutePath();
  }

  /**
   * 执行静默安装逻辑，需要手机ROOT。
   *
   * @param apkPath 要安装的 apk 文件的路径
   */
  public static void silentInstall(final String apkPath) {
    new Thread() {
      @Override
      public void run() {
        ShellUtil.execCommand("pm install -r " + apkPath, true);
      }
    }.start();
  }
}
