package live.itrip.app.ui.activity.account;

import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.net.Uri;
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

import java.lang.reflect.Type;

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

public class RetrieveActivity extends AccountBaseActivity implements AccountView, HasComponent<AccountComponent>
        , View.OnFocusChangeListener, ViewTreeObserver.OnGlobalLayoutListener {
    @BindView(R.id.ly_retrieve_bar)
    LinearLayout mLlRetrieveBar;

    @BindView(R.id.ll_retrieve_tel)
    LinearLayout mLlRetrieveTel;
    @BindView(R.id.et_retrieve_tel)
    EditText mEtRetrieveTel;
    @BindView(R.id.iv_retrieve_tel_del)
    ImageView mIvRetrieveTelDel;

    @BindView(R.id.ll_retrieve_code)
    LinearLayout mLlRetrieveCode;
    @BindView(R.id.et_retrieve_code_input)
    EditText mEtRetrieveCodeInput;
    @BindView(R.id.retrieve_sms_call)
    TextView mTvRetrieveSmsCall;

    @BindView(R.id.bt_retrieve_submit)
    Button mBtRetrieveSubmit;
    @BindView(R.id.tv_retrieve_label)
    TextView mTvRetrieveLabel;

    private boolean mMachPhoneNum;
    private int mTopMargin;
    private int mRequestType;
    private CountDownTimer mTimer;


    @Inject
    AccountPresenter mAccountPresenter;

    /**
     * show the retrieve activity
     *
     * @param context context
     */
    public static void launch(Context context) {
        Intent intent = new Intent(context, RetrieveActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_retrieve_pwd;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getComponent().inject(this);
        ButterKnife.bind(this);
        mAccountPresenter.attachView(this);

        initWidget();
    }

    private void initWidget() {
        TextView tvLabel = (TextView) mLlRetrieveBar.findViewById(R.id.tv_navigation_label);
        tvLabel.setText(R.string.retrieve_pwd_label);
        mEtRetrieveTel.setOnFocusChangeListener(this);
        mEtRetrieveTel.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                int length = s.length();
                if (length > 0) {
                    mIvRetrieveTelDel.setVisibility(View.VISIBLE);
                } else {
                    mIvRetrieveTelDel.setVisibility(View.INVISIBLE);
                }

            }

            @Override
            public void afterTextChanged(Editable s) {
                int length = s.length();
                String input = s.toString();
                mMachPhoneNum = ParserUtils.machPhoneNum(input);

                //对提交控件的状态判定
                if (mMachPhoneNum) {
                    String smsCode = mEtRetrieveCodeInput.getText().toString().trim();

                    if (!TextUtils.isEmpty(smsCode)) {
                        mBtRetrieveSubmit.setBackgroundResource(R.drawable.bg_login_submit);
                        mBtRetrieveSubmit.setTextColor(getResources().getColor(R.color.white));
                    } else {
                        mBtRetrieveSubmit.setBackgroundResource(R.drawable.bg_login_submit_lock);
                        mBtRetrieveSubmit.setTextColor(getResources().getColor(R.color.account_lock_font_color));
                    }
                } else {
                    mBtRetrieveSubmit.setBackgroundResource(R.drawable.bg_login_submit_lock);
                    mBtRetrieveSubmit.setTextColor(getResources().getColor(R.color.account_lock_font_color));
                }

                if (length > 0 && length < 11) {
                    mLlRetrieveTel.setBackgroundResource(R.drawable.bg_login_input_error);
                    mTvRetrieveSmsCall.setAlpha(0.4f);
                } else if (length == 11) {
                    if (mMachPhoneNum) {
                        mLlRetrieveTel.setBackgroundResource(R.drawable.bg_login_input_ok);
                        if (mTvRetrieveSmsCall.getTag() == null) {
                            mTvRetrieveSmsCall.setAlpha(1.0f);
                        } else {
                            mTvRetrieveSmsCall.setAlpha(0.4f);
                        }
                    } else {
                        mLlRetrieveTel.setBackgroundResource(R.drawable.bg_login_input_error);
                        showToastForKeyBord(R.string.hint_username_ok);
                        mTvRetrieveSmsCall.setAlpha(0.4f);
                    }
                } else if (length > 11) {
                    mTvRetrieveSmsCall.setAlpha(0.4f);
                    mLlRetrieveTel.setBackgroundResource(R.drawable.bg_login_input_error);
                } else if (length <= 0) {
                    mTvRetrieveSmsCall.setAlpha(0.4f);
                    mLlRetrieveTel.setBackgroundResource(R.drawable.bg_login_input_ok);
                }

            }
        });
        mEtRetrieveCodeInput.setOnFocusChangeListener(this);
        mEtRetrieveCodeInput.addTextChangedListener(new TextWatcher() {
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
                    mBtRetrieveSubmit.setBackgroundResource(R.drawable.bg_login_submit);
                    mBtRetrieveSubmit.setTextColor(getResources().getColor(R.color.white));
                } else {
                    mBtRetrieveSubmit.setBackgroundResource(R.drawable.bg_login_submit_lock);
                    mBtRetrieveSubmit.setTextColor(getResources().getColor(R.color.account_lock_font_color));
                }
                mLlRetrieveCode.setBackgroundResource(R.drawable.bg_login_input_ok);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        mLlRetrieveBar.getViewTreeObserver().addOnGlobalLayoutListener(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        hideKeyBoard(getCurrentFocus().getWindowToken());
        mLlRetrieveBar.getViewTreeObserver().removeOnGlobalLayoutListener(this);
    }

    @OnClick({R.id.ib_navigation_back, R.id.iv_retrieve_tel_del, R.id.retrieve_sms_call,
            R.id.bt_retrieve_submit, R.id.tv_retrieve_label, R.id.lay_retrieve_container})
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.ib_navigation_back:
                finish();
                break;
            case R.id.iv_retrieve_tel_del:
                mEtRetrieveTel.setText(null);
                break;
            case R.id.retrieve_sms_call:
                //获取验证码
                requestSmsCode();
                break;
            case R.id.bt_retrieve_submit:
                //根据验证码获取phoneToken
                requestRetrievePwd();
                break;
            case R.id.tv_retrieve_label:
                //打开web进入邮箱找回密码
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_VIEW);
                // intent.setAction(Intent.CATEGORY_BROWSABLE);
                Uri content_url = Uri.parse(Constants.RETRIEVE_PWD_URL);
                intent.setData(content_url);
                startActivity(intent);
                break;
            case R.id.lay_retrieve_container:
                hideKeyBoard(getCurrentFocus().getWindowToken());
                break;
            default:
                break;
        }

    }

    private void requestRetrievePwd() {

        String smsCode = mEtRetrieveCodeInput.getText().toString().trim();
        if (!mMachPhoneNum || TextUtils.isEmpty(smsCode)) {
            // showToastForKeyBord(R.string.hint_username_ok);
            return;
        }

        if (!DeviceUtils.hasInternet()) {
            showToastForKeyBord(R.string.state_network_error);
            return;
        }
        mRequestType = 2;
        String phoneNumber = mEtRetrieveTel.getText().toString().trim();
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

        if (mTvRetrieveSmsCall.getTag() == null) {
            mRequestType = 1;
            mTvRetrieveSmsCall.setAlpha(0.6f);
            mTvRetrieveSmsCall.setTag(true);
            mTimer = new CountDownTimer(60 * 1000, 1000) {

                @SuppressLint("DefaultLocale")
                @Override
                public void onTick(long millisUntilFinished) {
                    mTvRetrieveSmsCall.setText(String.format("%s%s%d%s", getString(R.string.register_sms_hint), "(", millisUntilFinished / 1000, ")"));
                }

                @Override
                public void onFinish() {
                    mTvRetrieveSmsCall.setTag(null);
                    mTvRetrieveSmsCall.setText(getString(R.string.register_sms_hint));
                    mTvRetrieveSmsCall.setAlpha(1.0f);
                }
            }.start();
            String phoneNumber = mEtRetrieveTel.getText().toString().trim();
            try {
                mAccountPresenter.sendSmsCode(phoneNumber, Constants.SmsType.RESET_PWD_INTENT);
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
            case R.id.et_retrieve_tel:
                if (hasFocus) {
                    mLlRetrieveTel.setActivated(true);
                    mLlRetrieveCode.setActivated(false);
                }
                break;
            case R.id.et_retrieve_code_input:
                if (hasFocus) {
                    mLlRetrieveCode.setActivated(true);
                    mLlRetrieveTel.setActivated(false);
                }
                break;
            default:
                break;
        }
    }

    @Override
    public void onGlobalLayout() {

        final LinearLayout layRetrieveTel = this.mLlRetrieveTel;
        Rect KeypadRect = new Rect();

        mLlRetrieveBar.getWindowVisibleDisplayFrame(KeypadRect);

        int screenHeight = mLlRetrieveBar.getRootView().getHeight();

        int keypadHeight = screenHeight - KeypadRect.bottom;

        if (keypadHeight > 0) {
            updateKeyBoardActiveStatus(true);
        } else {
            updateKeyBoardActiveStatus(false);
        }

        if (keypadHeight > 0 && layRetrieveTel.getTag() == null) {
            final LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) layRetrieveTel.getLayoutParams();
            final int topMargin = layoutParams.topMargin;
            this.mTopMargin = topMargin;
            layRetrieveTel.setTag(true);
            ValueAnimator valueAnimator = ValueAnimator.ofFloat(1, 0);
            valueAnimator.setDuration(400).setInterpolator(new DecelerateInterpolator());
            valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    float animatedValue = (float) animation.getAnimatedValue();
                    layoutParams.topMargin = (int) (topMargin * animatedValue);
                    layRetrieveTel.requestLayout();
                }
            });

            if (valueAnimator.isRunning()) {
                valueAnimator.cancel();
            }
            valueAnimator.start();


        } else if (keypadHeight == 0 && layRetrieveTel.getTag() != null) {
            final int topMargin = mTopMargin;
            final LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) layRetrieveTel.getLayoutParams();
            layRetrieveTel.setTag(null);
            ValueAnimator valueAnimator = ValueAnimator.ofFloat(0, 1);
            valueAnimator.setDuration(400).setInterpolator(new DecelerateInterpolator());
            valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    float animatedValue = (float) animation.getAnimatedValue();
                    layoutParams.topMargin = (int) (topMargin * animatedValue);
                    layRetrieveTel.requestLayout();
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
                            mEtRetrieveCodeInput.setText(null);
                            ToastUtils.showToast(getString(R.string.send_sms_code_success_hint));
                            break;
                        case 218:
                            //手机号已被注册,提示重新输入
                            mLlRetrieveTel.setBackgroundResource(R.drawable.bg_login_input_error);
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
                //第二步请求进行重置密码
                case 2:
                    switch (code) {
                        case 0:
                            if (mTimer != null) {
                                mTimer.onFinish();
                                mTimer.cancel();
                            }
                            ResetPwdActivity.launch(RetrieveActivity.this, msg);
                            break;
                        case 215:
                            mLlRetrieveCode.setBackgroundResource(R.drawable.bg_login_input_error);
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
            AppLog.e(e);
            //请求失败,比如服务器连接超时,回收timer,需重新请求发送验证码
            if (mRequestType == 1) {
                if (mTimer != null) {
                    mTimer.onFinish();
                    mTimer.cancel();
                }
            }
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
