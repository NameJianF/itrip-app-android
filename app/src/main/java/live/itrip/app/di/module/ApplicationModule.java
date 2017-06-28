package live.itrip.app.di.module;

import android.app.Application;
import android.content.Context;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import live.itrip.app.data.net.client.MessageRetrofit;
import live.itrip.app.data.net.client.RecyclerItemRetrofit;
import live.itrip.app.data.net.client.SettingRetrofit;
import live.itrip.app.di.ApplicationContext;
import live.itrip.app.service.net.MessageService;
import live.itrip.app.service.net.RecyclerItemDataService;
import live.itrip.app.service.net.SettingService;

/**
 * Created by Feng on 2017/4/25.
 */

@Module
public class ApplicationModule {
    protected final Application mApplication;

    public ApplicationModule(Application application) {
        mApplication = application;
    }

    @Provides
    Application provideApplication() {
        return mApplication;
    }

    @Provides
    @ApplicationContext
    Context provideContext() {
        return mApplication;
    }

    @Provides
    @Singleton
    RecyclerItemDataService provideRecyclerItemDataService(RecyclerItemRetrofit retrofit) {
        return retrofit.get().create(RecyclerItemDataService.class);
    }

    @Provides
    @Singleton
    MessageService provideMessageService(MessageRetrofit retrofit) {
        return retrofit.get().create(MessageService.class);
    }

    @Provides
    @Singleton
    SettingService provideSettingService(SettingRetrofit retrofit) {
        return retrofit.get().create(SettingService.class);
    }
}
