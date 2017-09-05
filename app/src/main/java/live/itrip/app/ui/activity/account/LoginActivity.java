package live.itrip.app.ui.activity.account;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import org.json.JSONException;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import live.itrip.app.App;
import live.itrip.app.R;
import live.itrip.app.cache.SharePreferenceData;
import live.itrip.app.data.model.UserExpandModel;
import live.itrip.app.data.model.UserModel;
import live.itrip.app.di.HasComponent;
import live.itrip.app.di.component.AccountComponent;
import live.itrip.app.di.component.DaggerAccountComponent;
import live.itrip.app.di.module.AccountModule;
import live.itrip.app.di.module.ActivityModule;
import live.itrip.app.presenter.account.LoginPresenter;
import live.itrip.app.ui.view.mvp.LoginView;
import live.itrip.app.ui.base.BaseLoadingActivity;
import live.itrip.common.util.AppLog;
import live.itrip.common.util.InputMethodUtils;

/**
 * Created by Feng on 2017/4/26.
 */

public class LoginActivity extends BaseLoadingActivity implements LoginView, HasComponent<AccountComponent> {

    @BindView(R.id.image_view_icon)
    ImageView mIcon;
    @BindView(R.id.edit_text_user_name)
    EditText mUsername;
    @BindView(R.id.edit_text_password)
    EditText mPassword;
    @BindView(R.id.button_login)
    Button mLoginBtn;

    @Inject
    LoginPresenter mPresenter;

    public static void launch(Context context) {
        context.startActivity(new Intent(context, LoginActivity.class));
    }

    public static void launchForResult(Activity activity) {
        activity.startActivityForResult(new Intent(activity, LoginActivity.class), 0);
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_login;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getComponent().inject(this);

        ButterKnife.bind(this);

        mPresenter.attachView(this);

        this.setActionBarTitle(getString(R.string.sign_in));
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

    @OnClick(R.id.button_login)
    public void onClick() {

        String username = mUsername.getText().toString();
        String password = mPassword.getText().toString();

        if (!TextUtils.isEmpty(username) && !TextUtils.isEmpty(password)) {
            InputMethodUtils.hideSoftInput(this);
            try {
                mPresenter.login(username, password);
            } catch (JSONException e) {
                e.printStackTrace();
            }
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
                .applicationComponent(App.getInstance().getComponent())
                .activityModule(new ActivityModule(this))
                .accountModule(new AccountModule())
                .build();
    }

    @Override
    public void loginSuccess(UserModel user) {
        try {
            mPresenter.getUserExpandInfo(user.getEmail(), user.getToken());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        Snackbar.make(mLoginBtn, "Login Success", Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void getUserExpandInfoSuccess(UserExpandModel userExpandModel) {
        if (userExpandModel == null) {
            AppLog.d("get user expand info failed ...");
        }
        finish();
    }
}
