package live.itrip.app.di.module;

import android.app.Application;
import android.content.Context;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import live.itrip.app.data.net.client.BlogDetailRetrofit;
import live.itrip.app.data.net.client.FeedBackRetrofit;
import live.itrip.app.data.net.client.PlanCategoryRetrofit;
import live.itrip.app.data.net.client.MessageRetrofit;
import live.itrip.app.data.net.client.PlanDetailRetrofit;
import live.itrip.app.data.net.client.RecyclerItemRetrofit;
import live.itrip.app.data.net.client.SettingRetrofit;
import live.itrip.app.data.net.client.VisibilityPageRetrofit;
import live.itrip.app.di.ApplicationContext;
import live.itrip.app.service.net.BlogDetailService;
import live.itrip.app.service.net.FeedBackService;
import live.itrip.app.service.net.PlanCategoryServive;
import live.itrip.app.service.net.MessageService;
import live.itrip.app.service.net.PlanDetailService;
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
    PlanCategoryServive providePlanCategoryServive(PlanCategoryRetrofit retrofit) {
        return retrofit.get().create(PlanCategoryServive.class);
    }

    @Provides
    @Singleton
    VisibilityPageServive provideVisibilityPageServive(VisibilityPageRetrofit retrofit) {
        return retrofit.get().create(VisibilityPageServive.class);
    }


    @Provides
    @Singleton
    BlogDetailService provideBlogDetailService(BlogDetailRetrofit retrofit) {
        return retrofit.get().create(BlogDetailService.class);
    }

    @Provides
    @Singleton
    PlanDetailService providePlanDetailService(PlanDetailRetrofit retrofit) {
        return retrofit.get().create(PlanDetailService.class);
    }

    @Provides
    @Singleton
    FeedBackService provideFeedBackService(FeedBackRetrofit retrofit) {
        return retrofit.get().create(FeedBackService.class);
    }
}
