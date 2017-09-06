package live.itrip.app.di.component;

import dagger.Component;
import live.itrip.app.di.PerActivity;
import live.itrip.app.di.module.AccountModule;
import live.itrip.app.di.module.ActivityModule;
import live.itrip.app.ui.activity.account.LoginActivity;
import live.itrip.app.ui.activity.account.RegisterStepOneActivity;
import live.itrip.app.ui.activity.account.RegisterStepTwoActivity;
import live.itrip.app.ui.activity.account.ResetPwdActivity;
import live.itrip.app.ui.activity.account.RetrieveActivity;

/**
 * Created by Feng on 2017/4/26.
 */
@PerActivity
@Component(
        dependencies = ApplicationComponent.class,
        modules = {ActivityModule.class, AccountModule.class})
public interface AccountComponent extends ActivityComponent {

    void inject(LoginActivity loginActivity);

    void inject(RetrieveActivity retrieveActivity);

    void inject(ResetPwdActivity resetPwdActivity);

    void inject(RegisterStepOneActivity registerStepOneActivity);

    void inject(RegisterStepTwoActivity registerStepTwoActivity);
}
