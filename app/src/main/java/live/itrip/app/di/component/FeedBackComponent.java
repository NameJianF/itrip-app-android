package live.itrip.app.di.component;

import dagger.Component;
import live.itrip.app.di.PerActivity;
import live.itrip.app.di.module.ActivityModule;
import live.itrip.app.di.module.FeedBackModule;
import live.itrip.app.ui.activity.profile.FeedBackActivity;

/**
 * Created by Feng on 2017/6/20.
 */
@PerActivity
@Component(
        dependencies = ApplicationComponent.class,
        modules = {ActivityModule.class, FeedBackModule.class})
public interface FeedBackComponent extends ActivityComponent {

    void inject(FeedBackActivity feedBackActivity);
}
