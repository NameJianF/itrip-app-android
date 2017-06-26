package live.itrip.app.di.module;

import android.app.Application;
import android.content.Context;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import live.itrip.app.data.net.client.RecyclerItemRetrofit;
import live.itrip.app.di.ApplicationContext;
import live.itrip.app.service.net.RecyclerItemDataService;

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

}
