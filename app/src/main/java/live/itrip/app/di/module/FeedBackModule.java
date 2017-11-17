package live.itrip.app.di.module;

import dagger.Module;
import dagger.Provides;
import live.itrip.app.data.api.FeedBackApi;
import live.itrip.app.data.net.FeedBackDataSource;

/**
 * Created by Feng on 2017/9/6.
 */
@Module
public class FeedBackModule {

    @Provides
    FeedBackApi provideFeedBackApi(FeedBackDataSource dataSource) {
        return dataSource;
    }

}
