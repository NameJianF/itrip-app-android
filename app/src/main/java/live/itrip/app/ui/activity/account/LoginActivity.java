package live.itrip.app.ui.activity.account;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.sina.weibo.sdk.auth.Oauth2AccessToken;
import com.sina.weibo.sdk.auth.WeiboAuthListener;
import com.sina.weibo.sdk.auth.sso.SsoHandler;
import com.sina.weibo.sdk.exception.WeiboException;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;

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
import live.itrip.app.presenter.account.AccountPresenter;
import live.itrip.app.ui.view.mvp.AccountView;
import live.itrip.app.util.DeviceUtils;
import live.itrip.app.util.ToastUtils;
import live.itrip.common.open.constants.OpenConstant;
import live.itrip.common.open.factory.OpenBuilder;
import live.itrip.common.util.AppLog;
import live.itrip.common.util.InputMethodUtils;

/**
 * Created by Feng on 2017/4/26.
 */

public class LoginActivity extends AccountBaseActivity implements AccountView, HasComponent<AccountComponent>
        , View.OnFocusChangeListener, ViewTreeObserver.OnGlobalLayoutListener, IUiListener {

    @BindView(R.id.ly_retrieve_bar)
    LinearLayout mLayBackBar;

    @BindView(R.id.iv_login_logo)
    ImageView mIvLoginLogo;

    @BindView(R.id.ll_login_username)
    LinearLayout mLlLoginUsername;
    @BindView(R.id.et_login_username)
    EditText mEtLoginUsername;
    @BindView(R.id.iv_login_username_del)
    ImageView mIvLoginUsernameDel;

    @BindView(R.id.ll_login_pwd)
    LinearLayout mLlLoginPwd;
    @BindView(R.id.et_login_pwd)
    EditText mEtLoginPwd;
    @BindView(R.id.iv_login_pwd_del)
    ImageView mIvLoginPwdDel;

    @BindView(R.id.iv_login_hold_pwd)
    ImageView mIvHoldPwd;
    @BindView(R.id.tv_login_forget_pwd)
    TextView mTvLoginForgetPwd;

    @BindView(R.id.bt_login_submit)
    Button mBtLoginSubmit;
    @BindView(R.id.bt_login_register)
    Button mBtLoginRegister;

    @BindView(R.id.ll_login_layer)
    View mLlLoginLayer;
    @BindView(R.id.ll_login_pull)
    LinearLayout mLlLoginPull;

    @BindView(R.id.ll_login_options)
    LinearLayout mLlLoginOptions;

    @BindView(R.id.ib_login_weibo)
    ImageView mIbLoginWeiBo;
    @BindView(R.id.ib_login_wx)
    ImageView mIbLoginWx;
    @BindView(R.id.ib_login_qq)
    ImageView mImLoginQq;

    private int mLogoHeight;
    private int mLogoWidth;
    private int openType;
    private SsoHandler mSsoHandler;
    private Tencent mTencent;

    @Inject
    AccountPresenter mAccountPresenter;


    /**
     * show the login activity
     *
     * @param context context
     */
    public static void launch(Context context) {
        Intent intent = new Intent(context, LoginActivity.class);
        context.startActivity(intent);
    }

    /**
     * show the login activity
     *
     * @param context context
     */
    public static void launch(Activity context, int requestCode) {
        Intent intent = new Intent(context, LoginActivity.class);
        context.startActivityForResult(intent, requestCode);
    }

    /**
     * show the login activity
     *
     * @param fragment fragment
     */
    public static void launch(Fragment fragment, int requestCode) {
        Intent intent = new Intent(fragment.getActivity(), LoginActivity.class);
        fragment.startActivityForResult(intent, requestCode);
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
        mAccountPresenter.attachView(this);

        this.setActionBarTitle(getString(R.string.login));

        initWidget();

        initData();
    }

    private void initWidget() {
        mLlLoginLayer.setVisibility(View.GONE);
        mEtLoginUsername.setOnFocusChangeListener(this);
        mEtLoginUsername.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @SuppressWarnings("deprecation")
            @Override
            public void afterTextChanged(Editable s) {
                String username = s.toString().trim();
                if (username.length() > 0) {
                    mLlLoginUsername.setBackgroundResource(R.drawable.bg_login_input_ok);
                    mIvLoginUsernameDel.setVisibility(View.VISIBLE);
                } else {
                    mLlLoginUsername.setBackgroundResource(R.drawable.bg_login_input_ok);
                    mIvLoginUsernameDel.setVisibility(View.INVISIBLE);
                }

                String pwd = mEtLoginPwd.getText().toString().trim();
                if (!TextUtils.isEmpty(pwd)) {
                    mBtLoginSubmit.setBackgroundResource(R.drawable.bg_login_submit);
                    mBtLoginSubmit.setTextColor(getResources().getColor(R.color.white));
                } else {
                    mBtLoginSubmit.setBackgroundResource(R.drawable.bg_login_submit_lock);
                    mBtLoginSubmit.setTextColor(getResources().getColor(R.color.account_lock_font_color));
                }

            }
        });

        mEtLoginPwd.setOnFocusChangeListener(this);
        mEtLoginPwd.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @SuppressWarnings("deprecation")
            @Override
            public void afterTextChanged(Editable s) {
                int length = s.length();
                if (length > 0) {
                    mLlLoginPwd.setBackgroundResource(R.drawable.bg_login_input_ok);
                    mIvLoginPwdDel.setVisibility(View.VISIBLE);
                } else {
                    mIvLoginPwdDel.setVisibility(View.INVISIBLE);
                }

                String username = mEtLoginUsername.getText().toString().trim();
                if (TextUtils.isEmpty(username)) {
                    ToastUtils.showToast(getString(R.string.message_username_null));
                }
                String pwd = mEtLoginPwd.getText().toString().trim();
                if (!TextUtils.isEmpty(pwd)) {
                    mBtLoginSubmit.setBackgroundResource(R.drawable.bg_login_submit);
                    mBtLoginSubmit.setTextColor(getResources().getColor(R.color.white));
                } else {
                    mBtLoginSubmit.setBackgroundResource(R.drawable.bg_login_submit_lock);
                    mBtLoginSubmit.setTextColor(getResources().getColor(R.color.account_lock_font_color));
                }
            }
        });

        TextView label = (TextView) mLayBackBar.findViewById(R.id.tv_navigation_label);
        label.setVisibility(View.INVISIBLE);
    }


    private void initData() {
        //初始化控件状态数据
        UserModel userModel = SharePreferenceData.Account.getLogonUser(this);
        if (userModel != null) {
            mEtLoginUsername.setText(userModel.getEmail());
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        mLayBackBar.getViewTreeObserver().addOnGlobalLayoutListener(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mAccountPresenter.detachView();
        hideKeyBoard(getCurrentFocus().getWindowToken());
        mLayBackBar.getViewTreeObserver().removeOnGlobalLayoutListener(this);
    }

    @OnClick({R.id.ib_navigation_back, R.id.et_login_username, R.id.et_login_pwd, R.id.tv_login_forget_pwd,
            R.id.iv_login_hold_pwd, R.id.bt_login_submit, R.id.bt_login_register, R.id.ll_login_pull,
            R.id.ib_login_weibo, R.id.ib_login_wx, R.id.ib_login_qq, R.id.ll_login_layer,
            R.id.iv_login_username_del, R.id.iv_login_pwd_del, R.id.lay_login_container})
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.ib_navigation_back:
                finish();
                break;
            case R.id.et_login_username:
                mEtLoginPwd.clearFocus();
                mEtLoginUsername.setFocusableInTouchMode(true);
                mEtLoginUsername.requestFocus();
                break;
            case R.id.et_login_pwd:
                mEtLoginUsername.clearFocus();
                mEtLoginPwd.setFocusableInTouchMode(true);
                mEtLoginPwd.requestFocus();
                break;
            case R.id.tv_login_forget_pwd:
                //忘记密码
                RetrieveActivity.launch(LoginActivity.this);
                break;
            case R.id.bt_login_submit:
                //用户登录
                loginRequest();
                break;
            case R.id.iv_login_hold_pwd:
                //记住密码
            case R.id.bt_login_register:
                RegisterStepOneActivity.launch(LoginActivity.this);
                break;
            case R.id.ll_login_layer:
            case R.id.ll_login_pull:

                mLlLoginPull.animate().cancel();
                mLlLoginLayer.animate().cancel();

                int height = mLlLoginOptions.getHeight();
                float progress = (mLlLoginLayer.getTag() != null && mLlLoginLayer.getTag() instanceof Float) ?
                        (float) mLlLoginLayer.getTag() : 1;
                int time = (int) (360 * progress);

                if (mLlLoginPull.getTag() != null) {
                    mLlLoginPull.setTag(null);
                    glide(height, progress, time);
                } else {
                    mLlLoginPull.setTag(true);
                    upGlide(height, progress, time);
                }
                break;
            case R.id.ib_login_weibo:
                weiBoLogin();
                break;
            case R.id.ib_login_wx:
                //微信登录
                wechatLogin();
                break;
            case R.id.ib_login_qq:
                //QQ登录
                tencentLogin();
                break;
            case R.id.iv_login_username_del:
                mEtLoginUsername.setText(null);
                break;
            case R.id.iv_login_pwd_del:
                mEtLoginPwd.setText(null);
                break;
            case R.id.lay_login_container:
                hideKeyBoard(getCurrentFocus().getWindowToken());
                break;
            default:
                break;
        }

    }

    /**
     * login tencent
     */
    private void tencentLogin() {
        showWaitDialog(R.string.login_tencent_hint);
        openType = OpenConstant.TENCENT;
        mTencent = OpenBuilder.with(this)
                .useTencent(OpenConstant.QQ_APP_ID)
                .login(this, new OpenBuilder.Callback() {
                    @Override
                    public void onFailed() {
                        hideWaitDialog();
                    }

                    @Override
                    public void onSuccess() {
                        //hideWaitDialog();
                    }
                });
    }

    /**
     * login wechat
     */
    private void wechatLogin() {
        showWaitDialog(R.string.login_wechat_hint);
        openType = OpenConstant.WECHAT;
        OpenBuilder.with(this)
                .useWechat(OpenConstant.WECHAT_APP_ID)
                .login(new OpenBuilder.Callback() {
                    @Override
                    public void onFailed() {
                        hideWaitDialog();
                        ToastUtils.showToast(getString(R.string.login_hint));
                    }

                    @Override
                    public void onSuccess() {
                        //hideWaitDialog();
                    }
                });
    }

    /**
     * login weiBo
     */
    private void weiBoLogin() {
        showWaitDialog(R.string.login_weibo_hint);
        openType = OpenConstant.SINA;
        mSsoHandler = OpenBuilder.with(this)
                .useWeibo(OpenConstant.WB_APP_KEY)
                .login(new WeiboAuthListener() {
                    @Override
                    public void onComplete(Bundle bundle) {
                        hideWaitDialog();
                        Oauth2AccessToken oauth2AccessToken = Oauth2AccessToken.parseAccessToken(bundle);

                        if (oauth2AccessToken.isSessionValid()) {
                            JSONObject jsonObject = new JSONObject();
                            jsonObject.put("openid", oauth2AccessToken.getUid());
                            jsonObject.put("expires_in", oauth2AccessToken.getExpiresTime());
                            jsonObject.put("refresh_token", oauth2AccessToken.getRefreshToken());
                            jsonObject.put("access_token", oauth2AccessToken.getToken());

                            try {
                                mAccountPresenter.openLogin(OpenConstant.SINA, jsonObject.toString());
                            } catch (JSONException e) {
                                AppLog.e(e);
                            }
                        }
                    }

                    @Override
                    public void onWeiboException(WeiboException e) {
                        e.printStackTrace();
                        hideWaitDialog();
                    }

                    @Override
                    public void onCancel() {
                        hideWaitDialog();
                    }
                });
    }

    /**
     * menu up glide
     *
     * @param height   height
     * @param progress progress
     * @param time     time
     */
    private void upGlide(int height, float progress, int time) {
        mLlLoginPull.animate()
                .translationYBy(height * progress)
                .translationY(0)
                .setDuration(time)
                .start();
        mLlLoginLayer.animate()
                .alphaBy(1 - progress)
                .alpha(1)
                .setDuration(time)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationStart(Animator animation) {
                        mLlLoginLayer.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void onAnimationCancel(Animator animation) {
                        if (animation instanceof ValueAnimator) {
                            mLlLoginLayer.setTag(((ValueAnimator) animation).getAnimatedValue());
                        }
                    }

                    @Override
                    public void onAnimationEnd(Animator animation) {
                        if (animation instanceof ValueAnimator) {
                            mLlLoginLayer.setTag(((ValueAnimator) animation).getAnimatedValue());
                        }
                    }
                })
                .start();
    }

    /**
     * menu glide
     *
     * @param height   height
     * @param progress progress
     * @param time     time
     */
    private void glide(int height, float progress, int time) {
        mLlLoginPull.animate()
                .translationYBy(height - height * progress)
                .translationY(height)
                .setDuration(time)
                .start();

        mLlLoginLayer.animate()
                .alphaBy(1 * progress)
                .alpha(0)
                .setDuration(time)
                .setListener(new AnimatorListenerAdapter() {

                    @Override
                    public void onAnimationCancel(Animator animation) {
                        if (animation instanceof ValueAnimator) {
                            mLlLoginLayer.setTag(((ValueAnimator) animation).getAnimatedValue());
                        }
                    }

                    @Override
                    public void onAnimationEnd(Animator animation) {
                        if (animation instanceof ValueAnimator) {
                            mLlLoginLayer.setTag(((ValueAnimator) animation).getAnimatedValue());
                        }
                        mLlLoginLayer.setVisibility(View.GONE);
                    }
                })
                .start();
    }

    public void loginRequest() {

        String username = mEtLoginUsername.getText().toString().trim();
        String password = mEtLoginPwd.getText().toString().trim();

        if (!TextUtils.isEmpty(username) && !TextUtils.isEmpty(password)) {
            InputMethodUtils.hideSoftInput(this);
            if (DeviceUtils.hasInternet()) {
                try {
                    mAccountPresenter.login(username, password);
                } catch (JSONException e) {
                    AppLog.e(e);
                }
            } else {
                showToastForKeyBord(R.string.footer_type_net_error);
            }
        } else {
            showToastForKeyBord(R.string.login_input_username_hint_error);
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
    public void onSuccess(int code, String msg) {
        View view;
        if ((view = getCurrentFocus()) != null) {
            hideKeyBoard(view.getWindowToken());
        }
        ToastUtils.showToast(getString(R.string.login_success_hint));
        setResult(RESULT_OK);
        sendLocalReceiver();

        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        tencentOnActivityResult(data);
        weiBoOnActivityResult(requestCode, resultCode, data);

        super.onActivityResult(requestCode, resultCode, data);
    }

    /**
     * weiBo Activity Result
     *
     * @param requestCode requestCode
     * @param resultCode  resultCode
     * @param data        data
     */

    private void weiBoOnActivityResult(int requestCode, int resultCode, Intent data) {
        if (openType == OpenConstant.SINA) {
            // SSO 授权回调
            // 重要：发起 SSO 登陆的 Activity 必须重写 onActivityResults
            if (mSsoHandler != null)
                mSsoHandler.authorizeCallBack(requestCode, resultCode, data);
        }
    }

    /**
     * tencent Activity Result
     *
     * @param data data
     */
    @SuppressWarnings("deprecation")
    private void tencentOnActivityResult(Intent data) {
        if (openType == OpenConstant.TENCENT) {
            // 对于tencent
            // 注：在某些低端机上调用登录后，由于内存紧张导致APP被系统回收，登录成功后无法成功回传数据。
            if (mTencent != null) {
                mTencent.handleLoginData(data, this);
            }
        }
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {

        int id = v.getId();

        if (id == R.id.et_login_username) {
            if (hasFocus) {
                mLlLoginUsername.setActivated(true);
                mLlLoginPwd.setActivated(false);
            }
        } else {
            if (hasFocus) {
                mLlLoginPwd.setActivated(true);
                mLlLoginUsername.setActivated(false);
            }
        }
    }


    @Override
    public void onGlobalLayout() {

        final ImageView ivLogo = this.mIvLoginLogo;
        Rect KeypadRect = new Rect();

        mLayBackBar.getWindowVisibleDisplayFrame(KeypadRect);

        int screenHeight = mLayBackBar.getRootView().getHeight();

        int keypadHeight = screenHeight - KeypadRect.bottom;

        if (keypadHeight > 0) {
            updateKeyBoardActiveStatus(true);
        } else {
            updateKeyBoardActiveStatus(false);
        }
        if (keypadHeight > 0 && ivLogo.getTag() == null) {
            final int height = ivLogo.getHeight();
            final int width = ivLogo.getWidth();
            this.mLogoHeight = height;
            this.mLogoWidth = width;
            ivLogo.setTag(true);
            ValueAnimator valueAnimator = ValueAnimator.ofFloat(1, 0);
            valueAnimator.setDuration(400).setInterpolator(new DecelerateInterpolator());
            valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    float animatedValue = (float) animation.getAnimatedValue();
                    ViewGroup.LayoutParams layoutParams = ivLogo.getLayoutParams();
                    layoutParams.height = (int) (height * animatedValue);
                    layoutParams.width = (int) (width * animatedValue);
                    ivLogo.requestLayout();
                    ivLogo.setAlpha(animatedValue);
                }
            });

            if (valueAnimator.isRunning()) {
                valueAnimator.cancel();
            }
            valueAnimator.start();


        } else if (keypadHeight == 0 && ivLogo.getTag() != null) {
            final int height = mLogoHeight;
            final int width = mLogoWidth;
            ivLogo.setTag(null);
            ValueAnimator valueAnimator = ValueAnimator.ofFloat(0, 1);
            valueAnimator.setDuration(400).setInterpolator(new DecelerateInterpolator());
            valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    float animatedValue = (float) animation.getAnimatedValue();
                    ViewGroup.LayoutParams layoutParams = ivLogo.getLayoutParams();
                    layoutParams.height = (int) (height * animatedValue);
                    layoutParams.width = (int) (width * animatedValue);
                    ivLogo.requestLayout();
                    ivLogo.setAlpha(animatedValue);
                }
            });

            if (valueAnimator.isRunning()) {
                valueAnimator.cancel();
            }
            valueAnimator.start();

        }

    }

    /**
     * tencent callback
     *
     * @param o json
     */
    @Override
    public void onComplete(Object o) {
        JSONObject jsonObject = (JSONObject) o;
        try {
            mAccountPresenter.openLogin(OpenConstant.TENCENT, jsonObject.toString());
        } catch (JSONException e) {
            AppLog.e(e);
        }
        hideWaitDialog();
    }

    /**
     * tencent callback
     *
     * @param uiError uiError
     */
    @Override
    public void onError(UiError uiError) {
        hideWaitDialog();
    }

    /**
     * tencent callback
     */
    @Override
    public void onCancel() {
        hideWaitDialog();
    }


}
