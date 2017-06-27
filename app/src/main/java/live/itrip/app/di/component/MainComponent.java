package live.itrip.app.di.component;

import dagger.Component;
import live.itrip.app.data.model.MessageModel;
import live.itrip.app.di.PerActivity;
import live.itrip.app.di.module.ActivityModule;
import live.itrip.app.di.module.MessageModule;
import live.itrip.app.di.module.RecyclerViewModule;
import live.itrip.app.ui.fragment.common.MessageFragment;

/**
 * Created by Feng on 2017/4/25.
 */

@PerActivity
@Component(
        dependencies = ApplicationComponent.class,
        modules = {ActivityModule.class, MessageModule.class, RecyclerViewModule.class})
public interface MainComponent extends ActivityComponent {

    void inject(MessageFragment messageFragment);

}