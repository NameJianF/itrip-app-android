package live.itrip.app.di.component;

import android.app.Application;
import android.content.Context;

import javax.inject.Singleton;

import dagger.Component;
import live.itrip.app.di.ApplicationContext;
import live.itrip.app.di.module.ApplicationModule;
import live.itrip.app.service.net.HomePageServive;
import live.itrip.app.service.net.MessageService;
import live.itrip.app.service.net.RecyclerItemDataService;
import live.itrip.app.service.net.SettingService;

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

    HomePageServive homePageServive();
}
