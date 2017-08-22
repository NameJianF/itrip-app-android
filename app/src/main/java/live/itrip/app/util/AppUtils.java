package live.itrip.app.util;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Handler;
import android.os.Message;

import java.util.Properties;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import live.itrip.app.App;
import live.itrip.app.cache.DataCacheManager;
import live.itrip.app.config.AppConfig;

/**
 * Created by Feng on 2017/7/21.
 */

public class AppUtils {
    private static ExecutorService EXECUTORS_INSTANCE;

    public static Executor getExecutor() {
        if (EXECUTORS_INSTANCE == null) {
            synchronized (AppUtils.class) {
                if (EXECUTORS_INSTANCE == null) {
                    EXECUTORS_INSTANCE = Executors.newFixedThreadPool(6);
                }
            }
        }
        return EXECUTORS_INSTANCE;
    }

    public static void runOnThread(Runnable runnable) {
        getExecutor().execute(runnable);
    }

    /**
     * 获取版本号
     *
     * @return 当前应用的版本号
     */
    public static String getVersion() {
        try {
            PackageManager manager = App.getInstance().getPackageManager();
            PackageInfo info = manager.getPackageInfo(App.getInstance().getPackageName(), 0);
            return info.versionName;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "NO_VERSION";
    }


    /**
     * 清除app缓存
     */
    public static void clearAppCache(final boolean showToast) {
        final Handler handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                if (msg.what == 1) {
                    ToastUtils.showToast("缓存清除成功");
                } else {
                    ToastUtils.showToast("缓存清除失败");
                }
            }
        };
        AppUtils.runOnThread(new Runnable() {
            @Override
            public void run() {
                Message msg = new Message();
                try {
                    // 清除数据缓存
                    DataCacheManager.clear(App.getContext());
                    msg.what = 1;
                } catch (Exception e) {
                    e.printStackTrace();
                    msg.what = -1;
                }
                if (handler != null && showToast) {
                    handler.sendMessage(msg);
                }
            }
        });
    }


}
