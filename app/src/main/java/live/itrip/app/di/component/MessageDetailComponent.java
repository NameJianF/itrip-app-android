package live.itrip.app.di.component;

import dagger.Component;
import live.itrip.app.di.PerActivity;
import live.itrip.app.di.module.ActivityModule;
import live.itrip.app.di.module.MessageModule;
import live.itrip.app.ui.activity.profile.DialogMessageActivity;

/**
 * Created by Feng on 2017/7/6.
 */

@PerActivity
@Component(dependencies = ApplicationComponent.class,
        modules = {ActivityModule.class, MessageModule.class})
public interface MessageDetailComponent {

    void inject(DialogMessageActivity messageDetailActivity);
}
