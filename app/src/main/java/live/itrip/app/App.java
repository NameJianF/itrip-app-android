package live.itrip.app;

import android.app.Application;
import android.content.Context;
import android.content.res.Configuration;

import live.itrip.app.di.component.ApplicationComponent;
import live.itrip.app.di.component.DaggerApplicationComponent;
import live.itrip.app.di.module.ApplicationModule;
import live.itrip.app.handler.fps.Audience;
import live.itrip.app.handler.fps.Takt;
import live.itrip.app.service.InitializeService;
import live.itrip.common.util.AppLog;

/**
 * Created on 2017/4/21.
 *
 * @author JianF
 */

public class App extends Application {
    static App instance;

    @Override
    public void onCreate() {
        super.onCreate();

        // init logger.
        AppLog.init(BuildConfig.DEBUG);

        InitializeService.start(this);

        // 注册fps回调, 5s
        Takt.stock(this)
                .interval(5 * 1000)
                .listener(new Audience() {
                    @Override
                    public void heartbeat(String fps) {
                        AppLog.d(String.format(" >>>>>>>>>> FPS[%s],Pid[%s],Tid[%s] <<<<<<<< "
                                , fps
                                , android.os.Process.myPid()
                                , android.os.Process.myTid()));
                    }
                })
                .play();

        instance = this;
    }

    public static App getInstance() {
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

    @Override
    public void onTerminate() {
        Takt.finish();
        super.onTerminate();
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

    /**
     * Needed to replace the component with a test specific one
     *
     * @param applicationComponent
     */
    public void setComponent(ApplicationComponent applicationComponent) {
        mApplicationComponent = applicationComponent;
    }
}
