package live.itrip.app.di.module;

import dagger.Module;
import dagger.Provides;
import live.itrip.app.data.api.AccountApi;
import live.itrip.app.data.net.AccountDataSource;

/**
 * Created by Feng on 2017/4/26.
 */

@Module
public class AccountModule {

    @Provides
    AccountApi provideAccountApi(AccountDataSource dataSource) {
        return dataSource;
    }
}
