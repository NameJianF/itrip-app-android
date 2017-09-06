package live.itrip.app.ui.activity.account;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
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

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import live.itrip.app.App;
import live.itrip.app.R;
import live.itrip.app.di.HasComponent;
import live.itrip.app.di.component.AccountComponent;
import live.itrip.app.di.component.DaggerAccountComponent;
import live.itrip.app.di.module.AccountModule;
import live.itrip.app.di.module.ActivityModule;
import live.itrip.app.presenter.account.AccountPresenter;
import live.itrip.app.ui.view.mvp.AccountView;
import live.itrip.app.util.DeviceUtils;
import live.itrip.app.util.ToastUtils;
import live.itrip.common.util.Md5Utils;

/**
 * Created by Feng on 2017/9/6.
 */

public class RegisterStepTwoActivity extends AccountBaseActivity implements AccountView, HasComponent<AccountComponent>
        , View.OnFocusChangeListener, ViewTreeObserver.OnGlobalLayoutListener {

    public static final String PHONE_TOKEN_KEY = "phoneToken";

    @BindView(R.id.ly_register_bar)
    LinearLayout mLlRegisterBar;
    @BindView(R.id.ll_register_two_username)
    LinearLayout mLlRegisterTwoUsername;
    @BindView(R.id.et_register_username)
    EditText mEtRegisterUsername;
    @BindView(R.id.iv_register_username_del)
    ImageView mIvRegisterUsernameDel;
    @BindView(R.id.ll_register_two_pwd)
    LinearLayout mLlRegisterTwoPwd;
    @BindView(R.id.et_register_pwd_input)
    EditText mEtRegisterPwd;
    @BindView(R.id.tv_register_man)
    TextView mTvRegisterMan;
    @BindView(R.id.tv_register_female)
    TextView mTvRegisterFemale;
    @BindView(R.id.bt_register_submit)
    Button mBtRegisterSubmit;

    private String mPhoneToken;
    private int mTopMargin;

    @Inject
    AccountPresenter mAccountPresenter;

    public static void launch(Context context, String phoneToken) {
        Intent intent = new Intent(context, RegisterStepTwoActivity.class);
        intent.putExtra(PHONE_TOKEN_KEY, phoneToken);
        context.startActivity(intent);
    }


    @Override
    protected int getContentView() {
        return R.layout.activity_register_step_two;
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

    protected void initWidget() {
        TextView tvLabel = (TextView) mLlRegisterBar.findViewById(R.id.tv_navigation_label);
        tvLabel.setText(R.string.login_register_hint);

        mEtRegisterUsername.addTextChangedListener(new TextWatcher() {
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

                String smsCode = mEtRegisterPwd.getText().toString().trim();

                if (!TextUtils.isEmpty(smsCode)) {
                    mBtRegisterSubmit.setBackgroundResource(R.drawable.bg_login_submit);
                    mBtRegisterSubmit.setTextColor(getResources().getColor(R.color.white));
                } else {
                    mBtRegisterSubmit.setBackgroundResource(R.drawable.bg_login_submit_lock);
                    mBtRegisterSubmit.setTextColor(getResources().getColor(R.color.account_lock_font_color));
                }

                if (length > 0) {
                    mIvRegisterUsernameDel.setVisibility(View.VISIBLE);
                } else {
                    mIvRegisterUsernameDel.setVisibility(View.INVISIBLE);
                }

                if (length > 12) {
                    showToastForKeyBord(R.string.register_username_error);
                    mLlRegisterTwoUsername.setBackgroundResource(R.drawable.bg_login_input_error);
                } else {
                    mLlRegisterTwoUsername.setBackgroundResource(R.drawable.bg_login_input_ok);
                }
            }
        });
        mEtRegisterUsername.setOnFocusChangeListener(this);
        mEtRegisterPwd.setOnFocusChangeListener(this);
        mEtRegisterPwd.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                int length = s.length();
                if (length < 6) {
                    mLlRegisterTwoPwd.setBackgroundResource(R.drawable.bg_login_input_error);
                } else {
                    mLlRegisterTwoPwd.setBackgroundResource(R.drawable.bg_login_input_ok);
                }
                String username = mEtRegisterUsername.getText().toString().trim();
                if (!TextUtils.isEmpty(username)) {
                    mBtRegisterSubmit.setBackgroundResource(R.drawable.bg_login_submit);
                    mBtRegisterSubmit.setTextColor(getResources().getColor(R.color.white));
                } else {
                    mBtRegisterSubmit.setBackgroundResource(R.drawable.bg_login_submit_lock);
                    mBtRegisterSubmit.setTextColor(getResources().getColor(R.color.account_lock_font_color));
                }
            }
        });
    }

    protected void initData() {
        Intent intent = getIntent();
        mPhoneToken = (String) intent.getSerializableExtra(PHONE_TOKEN_KEY);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mLlRegisterBar.getViewTreeObserver().addOnGlobalLayoutListener(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        hideKeyBoard(getCurrentFocus().getWindowToken());
        mLlRegisterBar.getViewTreeObserver().removeOnGlobalLayoutListener(this);
    }

    @OnClick({R.id.ib_navigation_back, R.id.iv_register_username_del, R.id.tv_register_man,
            R.id.tv_register_female, R.id.bt_register_submit, R.id.lay_register_two_container})
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.ib_navigation_back:
                finish();
                break;
            case R.id.iv_register_username_del:
                mEtRegisterUsername.setText(null);
                break;
            case R.id.tv_register_man:
                if (mTvRegisterMan.getTag() != null) {
                    Drawable left = getResources().getDrawable(R.mipmap.btn_gender_male_normal);
                    mTvRegisterMan.setCompoundDrawablesWithIntrinsicBounds(left, null, null, null);
                    mTvRegisterMan.setTag(null);
                } else {
                    Drawable left = getResources().getDrawable(R.mipmap.btn_gender_male_actived);
                    mTvRegisterMan.setCompoundDrawablesWithIntrinsicBounds(left, null, null, null);
                    mTvRegisterMan.setTag(false);
                    Drawable female = getResources().getDrawable(R.mipmap.btn_gender_female_normal);
                    mTvRegisterFemale.setCompoundDrawablesWithIntrinsicBounds(female, null, null, null);
                    mTvRegisterFemale.setTag(null);
                }

                break;
            case R.id.tv_register_female:
                if (mTvRegisterFemale.getTag() != null) {
                    Drawable left = getResources().getDrawable(R.mipmap.btn_gender_female_normal);
                    mTvRegisterFemale.setCompoundDrawablesWithIntrinsicBounds(left, null, null, null);
                    mTvRegisterFemale.setTag(null);
                } else {
                    Drawable left = getResources().getDrawable(R.mipmap.btn_gender_female_actived);
                    mTvRegisterFemale.setCompoundDrawablesWithIntrinsicBounds(left, null, null, null);
                    mTvRegisterFemale.setTag(true);

                    Drawable men = getResources().getDrawable(R.mipmap.btn_gender_male_normal);
                    mTvRegisterMan.setCompoundDrawablesWithIntrinsicBounds(men, null, null, null);
                    mTvRegisterMan.setTag(null);
                }
                break;
            case R.id.bt_register_submit:
                requestRegisterUserInfo();
                break;
            case R.id.lay_register_two_container:
                hideKeyBoard(getCurrentFocus().getWindowToken());
                break;
            default:
                break;
        }

    }

    private void requestRegisterUserInfo() {

        String username = mEtRegisterUsername.getText().toString().trim();
        String pwd = mEtRegisterPwd.getText().toString().trim();

        if (TextUtils.isEmpty(username) || TextUtils.isEmpty(pwd)) {
            return;
        }

        if (!DeviceUtils.hasInternet()) {
            showToastForKeyBord(R.string.state_network_error);
            return;
        }

        int sex = 0;

        Object isMan = mTvRegisterMan.getTag();
        if (isMan != null) {
            sex = 1;
        }

        Object isFemale = mTvRegisterFemale.getTag();
        if (isFemale != null) {
            sex = 2;
        }

        mAccountPresenter.register(username, Md5Utils.getStringMD5(pwd), sex, mPhoneToken);
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {

        int id = v.getId();
        switch (id) {
            case R.id.et_register_username:
                if (hasFocus) {
                    mLlRegisterTwoUsername.setActivated(true);
                    mLlRegisterTwoPwd.setActivated(false);
                }
                break;
            case R.id.et_register_pwd_input:
                if (hasFocus) {
                    mLlRegisterTwoPwd.setActivated(true);
                    mLlRegisterTwoUsername.setActivated(false);
                }
                break;
            default:
                break;
        }

    }

    @Override
    public void onGlobalLayout() {

        final LinearLayout layRegisterTwoUsername = this.mLlRegisterTwoUsername;
        Rect keypadRect = new Rect();

        mLlRegisterBar.getWindowVisibleDisplayFrame(keypadRect);

        int screenHeight = mLlRegisterBar.getRootView().getHeight();
        int keypadHeight = screenHeight - keypadRect.bottom;

        if (keypadHeight > 0) {
            updateKeyBoardActiveStatus(true);
        } else {
            updateKeyBoardActiveStatus(false);
        }

        if (keypadHeight > 0 && layRegisterTwoUsername.getTag() == null) {
            final LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) layRegisterTwoUsername.getLayoutParams();
            final int topMargin = layoutParams.topMargin;
            this.mTopMargin = topMargin;
            layRegisterTwoUsername.setTag(true);
            ValueAnimator valueAnimator = ValueAnimator.ofFloat(1, 0);
            valueAnimator.setDuration(400).setInterpolator(new DecelerateInterpolator());
            valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    float animatedValue = (float) animation.getAnimatedValue();
                    layoutParams.topMargin = (int) (topMargin * animatedValue);
                    layRegisterTwoUsername.requestLayout();
                }
            });

            if (valueAnimator.isRunning()) {
                valueAnimator.cancel();
            }
            valueAnimator.start();

        } else if (keypadHeight == 0 && layRegisterTwoUsername.getTag() != null) {
            final int topMargin = mTopMargin;
            final LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) layRegisterTwoUsername.getLayoutParams();
            layRegisterTwoUsername.setTag(null);
            ValueAnimator valueAnimator = ValueAnimator.ofFloat(0, 1);
            valueAnimator.setDuration(400).setInterpolator(new DecelerateInterpolator());
            valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    float animatedValue = (float) animation.getAnimatedValue();
                    layoutParams.topMargin = (int) (topMargin * animatedValue);
                    layRegisterTwoUsername.requestLayout();
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
        switch (code) {
            case 0:
                ToastUtils.showToast(getString(R.string.register_success_hint));
                sendLocalReceiver();
                finish();
                break;
            case 216: //phoneToken 已经失效
                finish();
                break;
            case 217:
                mLlRegisterTwoUsername.setBackgroundResource(R.drawable.bg_login_input_error);
                break;
            case 218:
                finish();
                break;
            case 219:
                mLlRegisterTwoPwd.setBackgroundResource(R.drawable.bg_login_input_error);
                break;
            case -1:
                showToastForKeyBord("注册异常");
                break;
            default:
                break;
        }
        if (code != 0) {
            showToastForKeyBord(msg);
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
