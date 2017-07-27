package live.itrip.app.di.module;

import dagger.Module;
import dagger.Provides;
import live.itrip.app.data.api.BlogApi;
import live.itrip.app.data.net.BlogDataSource;

/**
 * Created by Feng on 2017/7/24.
 */
@Module
public class BlogModule {
    @Provides
    BlogApi provideBlogApi(BlogDataSource dataSource) {
        return dataSource;
    }
}
