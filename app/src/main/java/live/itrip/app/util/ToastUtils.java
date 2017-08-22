package live.itrip.app.util;

import android.content.Context;
import android.view.Gravity;
import android.widget.Toast;

import live.itrip.app.App;
import live.itrip.app.ui.widget.SimplexToast;

/**
 * Created by Feng on 2017/6/20.
 */

public class ToastUtils {


    /**
     * Toast.LENGTH_SHORT and Gravity.BOTTOM
     *
     * @param message
     */
    public static void showToast(String message) {
        show(null, message, Toast.LENGTH_SHORT, Gravity.BOTTOM);
    }

    /**
     * Toast.LENGTH_SHORT and Gravity.BOTTOM
     *
     * @param message
     */
    public static void showToast(Context context, String message) {
        show(context, message, Toast.LENGTH_SHORT, Gravity.BOTTOM);
    }

    public static void showToast(String message, int duration, int gravity) {
        show(null, message, duration, gravity);
    }

    public static void showToast(Context context, String message, int duration, int gravity) {
        show(context, message, duration, gravity);
    }

    private static void show(Context context, String message, int duration, int gravity) {
        if (context == null) {
            context = App.getContext();
        }
        SimplexToast.show(context, message, gravity, duration);
    }

}
