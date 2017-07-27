package live.itrip.app.di.module;

import android.app.Application;
import android.content.Context;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import live.itrip.app.data.net.client.BlogRetrofit;
import live.itrip.app.data.net.client.HomePageRetrofit;
import live.itrip.app.data.net.client.MessageRetrofit;
import live.itrip.app.data.net.client.RecyclerItemRetrofit;
import live.itrip.app.data.net.client.SettingRetrofit;
import live.itrip.app.data.net.client.VisibilityPageRetrofit;
import live.itrip.app.di.ApplicationContext;
import live.itrip.app.service.net.BlogService;
import live.itrip.app.service.net.HomePageServive;
import live.itrip.app.service.net.MessageService;
import live.itrip.app.service.net.RecyclerItemDataService;
import live.itrip.app.service.net.SettingService;
import live.itrip.app.service.net.VisibilityPageServive;

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

    @Provides
    @Singleton
    HomePageServive provideHomePageServive(HomePageRetrofit retrofit) {
        return retrofit.get().create(HomePageServive.class);
    }

    @Provides
    @Singleton
    VisibilityPageServive provideVisibilityPageServive(VisibilityPageRetrofit retrofit) {
        return retrofit.get().create(VisibilityPageServive.class);
    }


    @Provides
    @Singleton
    BlogService provideBlogService(BlogRetrofit retrofit) {
        return retrofit.get().create(BlogService.class);
    }
}
