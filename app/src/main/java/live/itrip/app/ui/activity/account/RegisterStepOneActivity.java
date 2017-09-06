package live.itrip.app.ui.activity.account;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.json.JSONException;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import live.itrip.app.App;
import live.itrip.app.R;
import live.itrip.app.config.Constants;
import live.itrip.app.di.HasComponent;
import live.itrip.app.di.component.AccountComponent;
import live.itrip.app.di.component.DaggerAccountComponent;
import live.itrip.app.di.module.AccountModule;
import live.itrip.app.di.module.ActivityModule;
import live.itrip.app.presenter.account.AccountPresenter;
import live.itrip.app.ui.view.mvp.AccountView;
import live.itrip.app.util.DeviceUtils;
import live.itrip.app.util.ParserUtils;
import live.itrip.app.util.ToastUtils;
import live.itrip.common.util.AppLog;

/**
 * Created by Feng on 2017/9/6.
 */

public class RegisterStepOneActivity extends AccountBaseActivity implements AccountView, HasComponent<AccountComponent>
        , View.OnFocusChangeListener, ViewTreeObserver.OnGlobalLayoutListener {
    @BindView(R.id.ly_retrieve_bar)
    LinearLayout mLayBackBar;

    @BindView(R.id.iv_login_logo)
    ImageView mIvLogo;

    @BindView(R.id.ll_register_phone)
    LinearLayout mLlRegisterPhone;
    @BindView(R.id.et_register_username)
    EditText mEtRegisterUsername;
    @BindView(R.id.iv_register_username_del)
    ImageView mIvRegisterDel;

    @BindView(R.id.ll_register_sms_code)
    LinearLayout mLlRegisterSmsCode;
    @BindView(R.id.et_register_auth_code)
    EditText mEtRegisterAuthCode;
    @BindView(R.id.tv_register_sms_call)
    TextView mTvRegisterSmsCall;
    @BindView(R.id.bt_register_submit)
    Button mBtRegisterSubmit;

    private boolean mMachPhoneNum;
    private int mLogoHeight;
    private int mLogoWidth;
    private CountDownTimer mTimer;
    private int mRequestType = 1;    //1. 请求发送验证码  2.请求phoneToken

    @Inject
    AccountPresenter mAccountPresenter;

    public static void launch(Context context) {
        Intent intent = new Intent(context, RegisterStepOneActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_register_step_one;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getComponent().inject(this);
        ButterKnife.bind(this);
        mAccountPresenter.attachView(this);

        this.setActionBarTitle(getString(R.string.login));

        initWidget();
    }

    protected void initWidget() {
        TextView label = (TextView) mLayBackBar.findViewById(R.id.tv_navigation_label);
        label.setVisibility(View.INVISIBLE);

        mEtRegisterUsername.addTextChangedListener(
                new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        int length = s.length();
                        if (length > 0) {
                            mIvRegisterDel.setVisibility(View.VISIBLE);
                        } else {
                            mIvRegisterDel.setVisibility(View.INVISIBLE);
                        }
                    }

                    @Override
                    public void afterTextChanged(Editable s) {
                        int length = s.length();
                        String input = s.toString();
                        mMachPhoneNum = ParserUtils.machPhoneNum(input);

                        if (mMachPhoneNum) {
                            String smsCode = mEtRegisterAuthCode.getText().toString().trim();

                            if (!TextUtils.isEmpty(smsCode)) {
                                mBtRegisterSubmit.setBackgroundResource(R.drawable.bg_login_submit);
                                mBtRegisterSubmit.setTextColor(getResources().getColor(R.color.white));
                            } else {
                                mBtRegisterSubmit.setBackgroundResource(R.drawable.bg_login_submit_lock);
                                mBtRegisterSubmit.setTextColor(getResources().getColor(R.color.account_lock_font_color));
                            }
                        } else {
                            mBtRegisterSubmit.setBackgroundResource(R.drawable.bg_login_submit_lock);
                            mBtRegisterSubmit.setTextColor(getResources().getColor(R.color.account_lock_font_color));
                        }

                        if (length > 0 && length < 11) {
                            mLlRegisterPhone.setBackgroundResource(R.drawable.bg_login_input_error);
                            mTvRegisterSmsCall.setAlpha(0.4f);
                        } else if (length == 11) {
                            if (mMachPhoneNum) {
                                mLlRegisterPhone.setBackgroundResource(R.drawable.bg_login_input_ok);
                                if (mTvRegisterSmsCall.getTag() == null) {
                                    mTvRegisterSmsCall.setAlpha(1.0f);
                                } else {
                                    mTvRegisterSmsCall.setAlpha(0.4f);
                                }
                            } else {
                                mLlRegisterPhone.setBackgroundResource(R.drawable.bg_login_input_error);
                                showToastForKeyBord(R.string.hint_username_ok);
                                mTvRegisterSmsCall.setAlpha(0.4f);
                            }
                        } else if (length > 11) {
                            mTvRegisterSmsCall.setAlpha(0.4f);
                            mLlRegisterPhone.setBackgroundResource(R.drawable.bg_login_input_error);
                        } else if (length <= 0) {
                            mTvRegisterSmsCall.setAlpha(0.4f);
                            mLlRegisterPhone.setBackgroundResource(R.drawable.bg_login_input_ok);
                        }


                    }
                }

        );
        mEtRegisterUsername.setOnFocusChangeListener(this);
        mEtRegisterAuthCode.setOnFocusChangeListener(this);
        mEtRegisterAuthCode.addTextChangedListener(new TextWatcher() {
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
                if (length > 0 && mMachPhoneNum) {
                    mBtRegisterSubmit.setBackgroundResource(R.drawable.bg_login_submit);
                    mBtRegisterSubmit.setTextColor(getResources().getColor(R.color.white));
                } else {
                    mBtRegisterSubmit.setBackgroundResource(R.drawable.bg_login_submit_lock);
                    mBtRegisterSubmit.setTextColor(getResources().getColor(R.color.account_lock_font_color));
                }
                mLlRegisterSmsCode.setBackgroundResource(R.drawable.bg_login_input_ok);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        mLayBackBar.getViewTreeObserver().addOnGlobalLayoutListener(this);
    }

    @SuppressWarnings("ConstantConditions")
    @Override
    protected void onDestroy() {
        super.onDestroy();
        hideKeyBoard(getCurrentFocus().getWindowToken());
        mLayBackBar.getViewTreeObserver().removeOnGlobalLayoutListener(this);
    }

    @OnClick({R.id.ib_navigation_back, R.id.iv_register_username_del, R.id.tv_register_sms_call,
            R.id.bt_register_submit, R.id.lay_register_one_container})
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.ib_navigation_back:
                finish();
                break;
            case R.id.iv_register_username_del:
                mEtRegisterUsername.setText(null);
                break;
            case R.id.tv_register_sms_call:
                requestSmsCode();
                break;
            case R.id.bt_register_submit:
                requestRegister();
                // RegisterStepTwoActivity.show(this,null);
                break;
            case R.id.lay_register_one_container:
                hideKeyBoard(getCurrentFocus().getWindowToken());
                break;
            default:
                break;
        }

    }

    private void requestRegister() {

        String smsCode = mEtRegisterAuthCode.getText().toString().trim();
        if (!mMachPhoneNum || TextUtils.isEmpty(smsCode)) {
            //showToastForKeyBord(R.string.hint_username_ok);
            return;
        }

        if (!DeviceUtils.hasInternet()) {
            showToastForKeyBord(R.string.state_network_error);
            return;
        }

        mRequestType = 2;
        String phoneNumber = mEtRegisterUsername.getText().toString().trim();
        try {
            mAccountPresenter.validateSmsCode(phoneNumber, smsCode);
        } catch (JSONException e) {
            AppLog.e(e);
        }
    }

    private void requestSmsCode() {
        if (!mMachPhoneNum) {
            //showToastForKeyBord(R.string.hint_username_ok);
            return;
        }
        if (!DeviceUtils.hasInternet()) {
            showToastForKeyBord(R.string.state_network_error);
            return;
        }

        if (mTvRegisterSmsCall.getTag() == null) {
            mRequestType = 1;
            mTvRegisterSmsCall.setAlpha(0.6f);
            mTvRegisterSmsCall.setTag(true);
            mTimer = new CountDownTimer(60 * 1000, 1000) {
                @Override
                public void onTick(long millisUntilFinished) {
                    mTvRegisterSmsCall.setText(String.format("%s%s%d%s",
                            getResources().getString(R.string.register_sms_hint), "(", millisUntilFinished / 1000, ")"));
                }

                @Override
                public void onFinish() {
                    mTvRegisterSmsCall.setTag(null);
                    mTvRegisterSmsCall.setText(getResources().getString(R.string.register_sms_hint));
                    mTvRegisterSmsCall.setAlpha(1.0f);
                }
            }.start();
            String phoneNumber = mEtRegisterUsername.getText().toString().trim();
            try {
                mAccountPresenter.sendSmsCode(phoneNumber, Constants.SmsType.REGISTER_INTENT);
            } catch (JSONException e) {
                AppLog.e(e);
            }
        } else {
            ToastUtils.showToast(getString(R.string.register_sms_wait_hint));
        }
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        int id = v.getId();
        switch (id) {
            case R.id.et_register_username:
                if (hasFocus) {
                    mLlRegisterPhone.setActivated(true);
                    mLlRegisterSmsCode.setActivated(false);
                }
                break;
            case R.id.et_register_auth_code:
                if (hasFocus) {
                    mLlRegisterSmsCode.setActivated(true);
                    mLlRegisterPhone.setActivated(false);
                }
                break;
            default:
                break;
        }
    }

    @Override
    public void onGlobalLayout() {

        final ImageView ivLogo = this.mIvLogo;

        Rect keypadRect = new Rect();

        mLayBackBar.getWindowVisibleDisplayFrame(keypadRect);

        int screenHeight = mLayBackBar.getRootView().getHeight();

        int keypadHeight = screenHeight - keypadRect.bottom;
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
                    LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) ivLogo.getLayoutParams();

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
                    LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) ivLogo.getLayoutParams();
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

    @Override
    public void onSuccess(int code, String msg) {
        try {
            switch (mRequestType) {
                //第一步请求发送验证码
                case 1:
                    switch (code) {
                        case 0:
                            //发送验证码成功,请求进入下一步
                            //意味着我们可以进行第二次请求了,获取phoneToken
                            //mRequestType = 2;
                            ToastUtils.showToast(getString(R.string.send_sms_code_success_hint));
                            mEtRegisterAuthCode.setText(null);
                            break;
                        case 218:
                            //手机号已被注册,提示重新输入
                            mLlRegisterPhone.setBackgroundResource(R.drawable.bg_login_input_error);
                            showToastForKeyBord(msg);
                            break;
                        case -1:
                            //异常错误，发送验证码失败,回收timer,需重新请求发送验证码
                            if (mTimer != null) {
                                mTimer.onFinish();
                                mTimer.cancel();
                            }
                            showToastForKeyBord(msg);
                            break;
                        default:
                            break;
                    }

                    break;
                //第二步请求进行注册
                case 2:
                    switch (code) {
                        case 0:
                            //注册成功,进行用户信息填写
                            if (msg != null) {
                                if (mTimer != null) {
                                    mTimer.onFinish();
                                    mTimer.cancel();
                                }
                                RegisterStepTwoActivity.launch(RegisterStepOneActivity.this, msg);
                            }

                            break;
                        case 215://注册失败,手机验证码错误
                            mLlRegisterSmsCode.setBackgroundResource(R.drawable.bg_login_input_error);
                            showToastForKeyBord(msg);
                            break;
                        case -1:
                            //请求失败,比如服务器连接超时,回收timer,需重新请求发送验证码
                            if (mTimer != null) {
                                mTimer.onFinish();
                                mTimer.cancel();
                            }
                        default:
                            showToastForKeyBord(msg);
                            break;
                    }
                    break;
                default:
                    break;
            }

        } catch (Exception e) {
            if (mRequestType == 1) {
                if (mTimer != null) {
                    mTimer.onFinish();
                    mTimer.cancel();
                }
            }
            requestFailureHint(e);
        }
    }

    @Override
    public AccountComponent getComponent() {
        return DaggerAccountComponent.builder()
                .applicationComponent(App.getInstance().getComponent())
                .activityModule(new ActivityModule(this))
                .accountModule(new AccountModule())
                .build();
    }
}
