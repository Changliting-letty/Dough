/**
 * @TIME：2022/3/31 18:16
 * @Author:clt
 * @desc: this is LogUtil
 * 日志打印
 */

package com.letty.demo.dough.log;

import android.util.Log;

import java.net.MulticastSocket;

public class LogUtil {
    /*关闭日志*/
    public static final int LEVEL_OFF = 0;
    public static final int LEVEL_VERBOSE = 1;
    public static final int LEVEL_DEBUG = 2;
    public static final int LEVEL_INFO = 3;
    public static final int LEVEL_WARN = 4;
    public static final int LEVEL_ERROR = 5;
    public static final int LEVAL_SYSTEM = 6;
    /*显示所有信息，包括输出的信息*/
    public static final int LEVAL_ALL = 7;

    /*是否运行输出log*/
    private static int isDebuggable = LEVAL_ALL;

    /*计时*/
    private static long timeStamp = 0;

    /**
     * 以级别v形式输出log
     * @param msg
     */
    public static void v(String tag, String msg) {
        if (isDebuggable >= LEVEL_VERBOSE) {
            Log.v(tag, msg);
        }
    }

    public static void d(String tag, String msg) {
        if (isDebuggable >= LEVEL_DEBUG) {
            Log.d(tag, msg);
        }
    }

    public static void i(String tag, String msg) {
        if (isDebuggable >= LEVEL_INFO) {
            Log.i(tag, msg);
        }
    }

    /**
     *
     * @param
     */
    public static void w(String tag, Throwable tr) {
        if (isDebuggable >= LEVEL_WARN) {
            Log.w(tag, "", tr);
        }
    }

    public static void w(String tag, String msg, Throwable tr) {
        if (isDebuggable >= LEVEL_WARN && msg != null) {
            Log.w(tag, msg, tr);
        }
    }

    public static void e(String tag, String msg) {
        if (isDebuggable >= LEVEL_ERROR) {
            Log.w(tag, msg);
        }
    }

    public static void e(String tag, String msg, Throwable tr) {
        if (isDebuggable >= LEVEL_ERROR && msg != null) {
            Log.w(tag, msg, tr);
        }
    }

    public static void e(String tag, Throwable tr) {
        if (isDebuggable >= LEVEL_ERROR) {
            Log.w(tag, "", tr);
        }
    }

    /** 以级别为 s 的形式输出LOG,主要是为了System.out.println */
    public static void s(String msg) {
        if (isDebuggable >= LEVEL_ERROR) {
            System.out.println("-------" + msg + "-------");
        }
    }

    /**
     *   附带时间戳，用于输出一个时间段结束点
     * @param tag
     * @param msg
     */
    public static void recordStartTime(String tag, String msg) {
        timeStamp = System.currentTimeMillis();
        if (msg != null && !msg.equals("")) {
            e(tag, "[started: " + timeStamp + "]" + msg);
        }
    }

    public static void elapsed(String tag, String msg) {
        long currentTime = System.currentTimeMillis();
        long elapsedTime = currentTime - timeStamp;
        timeStamp = currentTime;
        e(tag, "[Elapsed：" + elapsedTime + "]" + msg);
    }


}
