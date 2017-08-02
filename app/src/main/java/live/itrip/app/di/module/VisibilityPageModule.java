package live.itrip.app.di.module;

import dagger.Module;
import dagger.Provides;
import live.itrip.app.data.api.VisibilityPageApi;
import live.itrip.app.data.net.VisibilityPageDataSource;

/**
 * Created by Feng on 2017/7/12.
 */
@Module
public class VisibilityPageModule {
    @Provides
    VisibilityPageApi provideVisibilityPageApi(VisibilityPageDataSource dataSource) {
        return dataSource;
    }
}
