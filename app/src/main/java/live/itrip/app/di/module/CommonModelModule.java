package live.itrip.app.di.module;

import dagger.Module;
import dagger.Provides;
import live.itrip.app.data.api.CommonModelApi;
import live.itrip.app.data.net.CommonModelDataSource;

/**
 * Created by Feng on 2017/6/22.
 */
@Module
public class CommonModelModule {


    @Provides
    CommonModelApi provideCommonModelApi(CommonModelDataSource dataSource) {
        return dataSource;
    }
}
