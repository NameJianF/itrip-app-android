package live.itrip.app.ui.activity.account;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import live.itrip.app.App;
import live.itrip.app.R;
import live.itrip.app.data.PreferenceData;
import live.itrip.app.data.model.User;
import live.itrip.app.di.HasComponent;
import live.itrip.app.di.component.AccountComponent;
import live.itrip.app.di.component.DaggerAccountComponent;
import live.itrip.app.di.module.AccountModule;
import live.itrip.app.di.module.ActivityModule;
import live.itrip.app.presenter.account.LoginPresenter;
import live.itrip.app.ui.activity.account.view.LoginView;
import live.itrip.app.ui.base.BaseLoadingActivity;
import live.itrip.common.util.InputMethodUtils;

/**
 * Created by Feng on 2017/4/26.
 */

public class LoginActivity extends BaseLoadingActivity implements LoginView, HasComponent<AccountComponent> {

    @BindView(R.id.icon)
    ImageView mIcon;
    @BindView(R.id.username)
    EditText mUsername;
    @BindView(R.id.password)
    EditText mPassword;
    @BindView(R.id.login_btn)
    Button mLoginBtn;

    @Inject
    LoginPresenter mPresenter;
    private ActionBar mActionBar;

    public static void launch(Context context) {
        context.startActivity(new Intent(context, LoginActivity.class));
    }

    public static void launchForResult(Activity activity) {
        activity.startActivityForResult(new Intent(activity, LoginActivity.class), 0);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getComponent().inject(this);

        setContentView(R.layout.activity_login);

        ButterKnife.bind(this);

        mPresenter.attachView(this);

        mActionBar = this.getSupportActionBar();
        if (mActionBar != null) {
            mActionBar.setDisplayHomeAsUpEnabled(true);
            mActionBar.setDisplayShowHomeEnabled(true);
            mActionBar.setTitle(R.string.sign_in);
        }
    }

    @Override
    public String getLoadingMessage() {
        return null;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.detachView();
    }

    @OnClick(R.id.login_btn)
    public void onClick() {

        String username = mUsername.getText().toString();
        String password = mPassword.getText().toString();

        if (!TextUtils.isEmpty(username) && !TextUtils.isEmpty(password)) {
            InputMethodUtils.hideSoftInput(this);
            mPresenter.login(username, password);
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }


    @Override
    public AccountComponent getComponent() {
        return DaggerAccountComponent.builder()
                .applicationComponent(App.get(this).getComponent())
                .activityModule(new ActivityModule(this))
                .accountModule(new AccountModule())
                .build();
    }

    @Override
    public void loginSuccess(User user) {
        Snackbar.make(mLoginBtn, "Login Success", Snackbar.LENGTH_LONG).show();
        PreferenceData.Account.saveLogonUser(this, user);
        finish();
    }
}
