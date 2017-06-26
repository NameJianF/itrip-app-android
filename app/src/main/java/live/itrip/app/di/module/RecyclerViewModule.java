package live.itrip.app.di.module;

import dagger.Module;
import dagger.Provides;
import live.itrip.app.data.api.RecyclerItemListApi;
import live.itrip.app.data.net.RecyclerItemDataSource;

/**
 * Created by Feng on 2017/6/26.
 */
@Module
public class RecyclerViewModule {

    @Provides
    RecyclerItemListApi provideRecyclerItemListApi(RecyclerItemDataSource dataSource) {
        return dataSource;
    }
}
