package live.itrip.app.di.module;

import dagger.Module;
import dagger.Provides;
import live.itrip.app.data.api.PlanDetailApi;
import live.itrip.app.data.net.PlanDetailDataSource;

/**
 * Created by Feng on 2017/7/24.
 */
@Module
public class PlanDetailModule {
    @Provides
    PlanDetailApi providePlanDetailApi(PlanDetailDataSource dataSource) {
        return dataSource;
    }
}
