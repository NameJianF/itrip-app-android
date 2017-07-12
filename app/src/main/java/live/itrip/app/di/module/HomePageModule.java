package live.itrip.app.di.module;

import dagger.Module;
import dagger.Provides;
import live.itrip.app.data.api.HomePageApi;
import live.itrip.app.data.net.HomePageDataSource;

/**
 * Created by Feng on 2017/7/12.
 */
@Module
public class HomePageModule {
    @Provides
    HomePageApi provideMessageApi(HomePageDataSource dataSource) {
        return dataSource;
    }
}
