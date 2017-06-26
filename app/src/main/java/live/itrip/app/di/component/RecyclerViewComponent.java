package live.itrip.app.di.component;

/**
 * Created by Feng on 2017/6/26.
 */

import dagger.Component;
import live.itrip.app.di.PerActivity;
import live.itrip.app.di.module.ActivityModule;
import live.itrip.app.di.module.RecyclerViewModule;
import live.itrip.app.ui.activity.common.RecyclerViewActivity;

@PerActivity
@Component(dependencies = ApplicationComponent.class,
        modules = {ActivityModule.class, RecyclerViewModule.class})
public interface RecyclerViewComponent {

    void inject(RecyclerViewActivity recyclerViewActivity);
}
