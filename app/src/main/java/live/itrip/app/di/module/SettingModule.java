package live.itrip.app.di.module;

import dagger.Module;
import dagger.Provides;
import live.itrip.app.data.api.SettingApi;
import live.itrip.app.data.net.SettingDataSource;

/**
 * Created by Feng on 2017/6/28.
 */
@Module
public class SettingModule {
    @Provides
    SettingApi provideSettingApi(SettingDataSource dataSource) {
        return dataSource;
    }
}
