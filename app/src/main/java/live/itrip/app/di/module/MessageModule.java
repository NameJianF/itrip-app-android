package live.itrip.app.di.module;

import dagger.Module;
import dagger.Provides;
import live.itrip.app.data.api.MessageApi;
import live.itrip.app.data.net.MessageDataSource;

/**
 * Created by Feng on 2017/6/27.
 */
@Module
public class MessageModule {
    @Provides
    MessageApi provideTrendingApi(MessageDataSource dataSource) {
        return dataSource;
    }
}
