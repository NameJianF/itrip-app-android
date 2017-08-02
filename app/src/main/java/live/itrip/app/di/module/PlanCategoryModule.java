package live.itrip.app.di.module;

import dagger.Module;
import dagger.Provides;
import live.itrip.app.data.api.PlanCategoryApi;
import live.itrip.app.data.net.PlanCategoryDataSource;

/**
 * Created by Feng on 2017/7/12.
 */
@Module
public class PlanCategoryModule {
    @Provides
    PlanCategoryApi providePlanCategoryApi(PlanCategoryDataSource dataSource) {
        return dataSource;
    }
}
