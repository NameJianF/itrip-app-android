package live.itrip.app;

import android.app.Application;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Configuration;

import live.itrip.app.config.AppConfig;
import live.itrip.app.di.component.ApplicationComponent;
import live.itrip.app.di.component.DaggerApplicationComponent;
import live.itrip.app.di.module.ApplicationModule;
import live.itrip.app.service.InitializeService;
import live.itrip.common.util.AppLog;

/**
 * Created by Feng on 2017/4/21.
 */

public class App extends Application {

    //    static Context context;
    static App instance;

    @Override
    public void onCreate() {
        super.onCreate();

        // init logger.
        AppLog.init(BuildConfig.DEBUG);

        InitializeService.start(this);

        AppConfig.CLIENT_VERSION = getVersion();

//        context = getApplicationContext();
        instance = this;
    }

    public static App getInstance() {
//        return (App) context.getApplicationContext();
        return instance;
    }

    public static Context getContext() {
        return instance.getApplicationContext();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
    }

    @Override
    public void onTrimMemory(int level) {
        super.onTrimMemory(level);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    ApplicationComponent mApplicationComponent;

    public ApplicationComponent getComponent() {
        if (mApplicationComponent == null) {
            mApplicationComponent = DaggerApplicationComponent.builder()
                    .applicationModule(new ApplicationModule(this))
                    .build();
        }
        return mApplicationComponent;
    }

    // Needed to replace the component with a test specific one
    public void setComponent(ApplicationComponent applicationComponent) {
        mApplicationComponent = applicationComponent;
    }

    /**
     * 2  * 获取版本号
     * 3  * @return 当前应用的版本号
     * 4
     */
    public String getVersion() {
        try {
            PackageManager manager = this.getPackageManager();
            PackageInfo info = manager.getPackageInfo(this.getPackageName(), 0);
            return info.versionName;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "NO_VERSION";
    }


}
