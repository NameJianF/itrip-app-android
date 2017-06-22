package live.itrip.common.util;

import android.util.Log;

import com.orhanobut.logger.Logger;

/**
 * Created by Feng on 2017/4/24.
 */

public class AppLog {

    private static final String TAG = "ITRIP-LIVE";
    private static Boolean DEBUG = true;

    /**
     * initialize the logger.
     */
    public static void init(Boolean isDebug) {
        DEBUG = isDebug;
        Logger.init(TAG);
    }

    /**
     * log.i
     *
     * @param msg
     */
    public static void i(String msg) {
        if (DEBUG) {
            Logger.i(msg);
        }
    }

    /**
     * log.i
     *
     * @param msg
     */
    public static void i(String tag, String msg) {
        if (DEBUG) {
            Log.i(tag, msg);
        }
    }


    /**
     * log.d
     *
     * @param msg
     */
    public static void d(String msg) {
        if (DEBUG) {
            Logger.d(msg);
        }
    }

    /**
     * log.d
     *
     * @param msg
     */
    public static void d(String tag, String msg) {
        if (DEBUG) {
            Log.d(tag, msg);
        }
    }

    /**
     * log.w
     *
     * @param msg
     */
    public static void w(String msg) {
        if (DEBUG) {
            Logger.w(msg);
        }
    }

    /**
     * log.w
     *
     * @param msg
     */
    public static void w(String tag, String msg) {
        if (DEBUG) {
            Log.w(tag, msg);
        }
    }

    /**
     * log.e
     *
     * @param msg
     */
    public static void e(String msg) {
        Logger.e(msg);
    }

    /**
     * log.e
     *
     * @param msg
     */
    public static void e(String tag, String msg) {
        Log.e(tag, msg);
    }

    public static void e(String msg, Throwable e) {
        Logger.e(e, msg);
    }

    public static void e(Throwable e) {
        Logger.e(e, "");
    }
}
