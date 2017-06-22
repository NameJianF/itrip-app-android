package live.itrip.app.di.component;

import dagger.Component;
import live.itrip.app.di.PerActivity;
import live.itrip.app.di.module.ActivityModule;
import live.itrip.app.di.module.CommonModelModule;
import live.itrip.app.ui.activity.CommonModelListActivity;

/**
 * Created by Feng on 2017/6/22.
 */
@PerActivity
@Component(
        dependencies = ApplicationComponent.class,
        modules = {ActivityModule.class, CommonModelModule.class})
public interface CommonModelComponent extends ActivityComponent {

    void inject(CommonModelListActivity activity);
}
