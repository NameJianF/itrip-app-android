package live.itrip.app.ui.util;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentActivity;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.ViewGroup;
import android.view.WindowManager;

import live.itrip.app.App;
import live.itrip.app.ui.DetailPage;
import live.itrip.app.ui.SimpleBackPage;
import live.itrip.app.ui.activity.DetailActivity;
import live.itrip.app.ui.activity.SimpleBackActivity;
import live.itrip.common.util.AppLog;

/**
 * Created by Feng on 2017/5/11.
 */

public class UIUtils {
    private static int STATUS_BAR_HEIGHT = 0;
    private final static String STATUS_BAR_DEF_PACKAGE = "android";
    private final static String STATUS_BAR_DEF_TYPE = "dimen";
    private final static String STATUS_BAR_NAME = "status_bar_height";
    private final static String STATUS_CLASS_NAME = "com.android.internal.R$dimen";
    private final static String STATUS_CLASS_FIELD = "status_bar_height";
    /**
     * 显示设置界面
     *
     * @param context
     */
    public static void showSetting(Context context) {
        showSimpleBack(context, SimpleBackPage.SETTING);
    }

    public static void showAbout(Context context) {
        showSimpleBack(context, SimpleBackPage.ABOUT);
    }


    public static void showSimpleBack(Context context, SimpleBackPage page) {
        Intent intent = new Intent(context, SimpleBackActivity.class);
        intent.putExtra(SimpleBackActivity.BUNDLE_KEY_PAGE, page.getValue());
        context.startActivity(intent);
    }

    public static void showSimpleBack(Context context, SimpleBackPage page,
                                      Bundle args) {
        Intent intent = new Intent(context, SimpleBackActivity.class);
        intent.putExtra(SimpleBackActivity.BUNDLE_KEY_ARGS, args);
        intent.putExtra(SimpleBackActivity.BUNDLE_KEY_PAGE, page.getValue());
        context.startActivity(intent);
    }

    public static void showDetailActivity(Context context, DetailPage page, Bundle args) {
        Intent intent = new Intent(context, DetailActivity.class);
        intent.putExtra(DetailActivity.BUNDLE_KEY_ARGS, args);
        intent.putExtra(DetailActivity.BUNDLE_KEY_PAGE, page.getValue());
        context.startActivity(intent);
    }


    /**
     * 清除app缓存
     */
    public static void clearAppCache(boolean showToast) {
        final Handler handler = showToast ? new Handler() {
            @Override
            public void handleMessage(Message msg) {
                if (msg.what == 1) {
                    ToastUtils.showToast("缓存清除成功");
                } else {
                    ToastUtils.showToast("缓存清除失败");
                }
            }
        } : null;
        Message msg = new Message();
        msg.what = 1;
        handler.sendMessage(msg);

//        AppOperator.runOnThread(new Runnable() {
//            @Override
//            public void run() {
//                Message msg = new Message();
//                try {
//                    AppContext.getInstance().clearAppCache();
//                    msg.what = 1;
//                } catch (Exception e) {
//                    e.printStackTrace();
//                    msg.what = -1;
//                }
//                if (handler != null)
//                    handler.sendMessage(msg);
//            }
//        });
    }


    /**
     * 打开内置浏览器
     *
     * @param context
     * @param url
     */
    public static void openInternalBrowser(Context context, String url) {
//        try {
//            Bundle bundle = new Bundle();
//            bundle.putString(BrowserFragment.BROWSER_KEY, url);
//            showSimpleBack(context, SimpleBackPage.BROWSER, bundle);
//        } catch (Exception e) {
//            e.printStackTrace();
        openExternalBrowser(context, url);
//        }
    }

    /**
     * 打开外置的浏览器
     *
     * @param context
     * @param url
     */
    public static void openExternalBrowser(Context context, String url) {
        Uri uri = Uri.parse(url);
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        context.startActivity(Intent.createChooser(intent, "选择打开的应用"));
    }

    /**
     * 获得屏幕的宽度
     *
     * @param context context
     * @return width
     */
    public static int getScreenWidth(Context context) {
        WindowManager manager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        manager.getDefaultDisplay().getMetrics(outMetrics);
        return outMetrics.widthPixels;
    }

    /**
     * 获得屏幕的高度
     *
     * @param context context
     * @return height
     */
    public static int getScreenHeight(Context context) {
        WindowManager manager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        manager.getDefaultDisplay().getMetrics(outMetrics);
        return outMetrics.heightPixels;
    }

    public static boolean isFullScreen(final Activity activity) {
        return (activity.getWindow().getAttributes().flags &
                WindowManager.LayoutParams.FLAG_FULLSCREEN) != 0;
    }

    public static boolean isTranslucentStatus(final Activity activity) {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT &&
                (activity.getWindow().getAttributes().flags & WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS) != 0;
    }

    static boolean isFitsSystemWindows(final Activity activity) {
        return ((ViewGroup) activity.findViewById(android.R.id.content)).getChildAt(0).
                getFitsSystemWindows();
    }

    public static synchronized int getStatusBarHeight(final Context context) {
        if (STATUS_BAR_HEIGHT > 0)
            return STATUS_BAR_HEIGHT;

        Resources resources = context.getResources();
        int resourceId = context.getResources().
                getIdentifier(STATUS_BAR_NAME, STATUS_BAR_DEF_TYPE, STATUS_BAR_DEF_PACKAGE);
        if (resourceId > 0) {
            STATUS_BAR_HEIGHT = context.getResources().getDimensionPixelSize(resourceId);
            AppLog.d(String.format("Get status bar height %s", STATUS_BAR_HEIGHT));
        } else {
            try {
                Class<?> clazz = Class.forName(STATUS_CLASS_NAME);
                Object object = clazz.newInstance();
                int height = Integer.parseInt(clazz.getField(STATUS_CLASS_FIELD)
                        .get(object).toString());
                STATUS_BAR_HEIGHT = resources.getDimensionPixelSize(height);
            } catch (Exception e) {
                e.printStackTrace();
                return (int) DeviceUtils.dp2px(25);
            }
        }
        return STATUS_BAR_HEIGHT;
    }

}
