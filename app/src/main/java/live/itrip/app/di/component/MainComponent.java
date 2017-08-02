package live.itrip.app.di.component;

import dagger.Component;
import live.itrip.app.di.PerActivity;
import live.itrip.app.di.module.ActivityModule;
import live.itrip.app.di.module.BlogDetailModule;
import live.itrip.app.di.module.PlanCategoryModule;
import live.itrip.app.di.module.MessageModule;
import live.itrip.app.di.module.PlanDetailModule;
import live.itrip.app.di.module.RecyclerViewModule;
import live.itrip.app.di.module.SettingModule;
import live.itrip.app.di.module.VisibilityPageModule;
import live.itrip.app.ui.fragment.HomeFragment;
import live.itrip.app.ui.fragment.VisibilityFragment;
import live.itrip.app.ui.fragment.plan.PlanSelfGuidedFragment;
import live.itrip.app.ui.fragment.plan.child.SelfGuidedChildFragment;
import live.itrip.app.ui.fragment.profile.SettingsFragment;
import live.itrip.app.ui.fragment.blog.BlogDetailFragment;
import live.itrip.app.ui.fragment.profile.MessageFragment;
import live.itrip.app.ui.fragment.plan.PlanDetailFragment;

/**
 * Created by Feng on 2017/4/25.
 */

@PerActivity
@Component(
        dependencies = ApplicationComponent.class,
        modules = {ActivityModule.class, PlanCategoryModule.class, VisibilityPageModule.class,
                MessageModule.class, RecyclerViewModule.class, SettingModule.class,
                BlogDetailModule.class, PlanDetailModule.class, PlanCategoryModule.class})
public interface MainComponent extends ActivityComponent {

    void inject(HomeFragment homeFragment);

    void inject(VisibilityFragment visibilityFragment);

    void inject(MessageFragment messageFragment);

    void inject(SettingsFragment settingsFragment);

    void inject(BlogDetailFragment blogDetailFragment);

    void inject(PlanDetailFragment planDetailFragment);

    void inject(SelfGuidedChildFragment selfGuidedChildFragment);
}