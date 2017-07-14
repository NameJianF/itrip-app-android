package live.itrip.app.di.component;

import dagger.Component;
import live.itrip.app.di.PerActivity;
import live.itrip.app.di.module.ActivityModule;
import live.itrip.app.di.module.HomePageModule;
import live.itrip.app.di.module.MessageModule;
import live.itrip.app.di.module.RecyclerViewModule;
import live.itrip.app.di.module.SettingModule;
import live.itrip.app.di.module.VisibilityPageModule;
import live.itrip.app.ui.fragment.HomeFragment;
import live.itrip.app.ui.fragment.VisibilityFragment;
import live.itrip.app.ui.fragment.app.SettingsFragment;
import live.itrip.app.ui.fragment.common.MessageFragment;

/**
 * Created by Feng on 2017/4/25.
 */

@PerActivity
@Component(
        dependencies = ApplicationComponent.class,
        modules = {ActivityModule.class, HomePageModule.class, VisibilityPageModule.class,
                MessageModule.class, RecyclerViewModule.class, SettingModule.class})
public interface MainComponent extends ActivityComponent {
    void inject(HomeFragment homeFragment);

    void inject(VisibilityFragment visibilityFragment);

    void inject(MessageFragment messageFragment);

    void inject(SettingsFragment settingsFragment);
}