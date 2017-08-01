package live.itrip.app.di.module;

import dagger.Module;
import dagger.Provides;
import live.itrip.app.data.api.BlogDetailApi;
import live.itrip.app.data.net.BlogDetailDataSource;

/**
 * Created by Feng on 2017/7/24.
 */
@Module
public class BlogDetailModule {
    @Provides
    BlogDetailApi provideBlogApi(BlogDetailDataSource dataSource) {
        return dataSource;
    }
}
