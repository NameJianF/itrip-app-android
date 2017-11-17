package live.itrip.app.di.component;

import android.app.Application;
import android.content.Context;

import javax.inject.Singleton;

import dagger.Component;
import live.itrip.app.di.ApplicationContext;
import live.itrip.app.di.module.ApplicationModule;
import live.itrip.app.service.net.BlogDetailService;
import live.itrip.app.service.net.FeedBackService;
import live.itrip.app.service.net.PlanCategoryServive;
import live.itrip.app.service.net.MessageService;
import live.itrip.app.service.net.PlanDetailService;
import live.itrip.app.service.net.RecyclerItemDataService;
import live.itrip.app.service.net.SettingService;
import live.itrip.app.service.net.VisibilityPageServive;

/**
 * Created by Feng on 2017/4/25.
 */

@Singleton
@Component(modules = ApplicationModule.class)
public interface ApplicationComponent {

    @ApplicationContext
    Context context();

    Application application();

    RecyclerItemDataService recyclerItemDataService();

    MessageService messageService();

    SettingService settingService();

    PlanCategoryServive planCategoryServive();

    VisibilityPageServive visibilityPageService();

    BlogDetailService blogDetailService();

    PlanDetailService planDetailService();

    FeedBackService feedBackService();
}
