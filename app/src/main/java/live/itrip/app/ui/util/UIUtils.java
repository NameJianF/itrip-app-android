package live.itrip.app.ui.util;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentActivity;

import live.itrip.app.App;
import live.itrip.app.ui.SimpleBackPage;
import live.itrip.app.ui.activity.SimpleBackActivity;

/**
 * Created by Feng on 2017/5/11.
 */

public class UIUtils {
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

}
