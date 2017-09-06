package live.itrip.app.ui.activity.account;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
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

public class ResetPwdActivity extends AccountBaseActivity implements AccountView, HasComponent<AccountComponent>
        , View.OnFocusChangeListener, ViewTreeObserver.OnGlobalLayoutListener {
    @BindView(R.id.ly_reset_bar)
    LinearLayout mLlResetBar;

    @BindView(R.id.ll_reset_pwd)
    LinearLayout mLlResetPwd;
    @BindView(R.id.et_reset_pwd)
    EditText mEtResetPwd;
    @BindView(R.id.iv_reset_pwd_del)
    ImageView mIvResetPwdDel;
    @BindView(R.id.bt_reset_submit)
    Button mBtResetSubmit;

    private String mPhoneToken;
    private int mTopMargin;

    @Inject
    AccountPresenter mAccountPresenter;


    public static void launch(Context context, String phoneToken) {
        Intent intent = new Intent(context, ResetPwdActivity.class);
        intent.putExtra(RegisterStepTwoActivity.PHONE_TOKEN_KEY, phoneToken);
        context.startActivity(intent);
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_reset_pwd;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getComponent().inject(this);
        ButterKnife.bind(this);
        mAccountPresenter.attachView(this);

        initWidget();

        initData();
    }

    protected void initWidget() {
        TextView tvLabel = (TextView) mLlResetBar.findViewById(R.id.tv_navigation_label);
        tvLabel.setText(R.string.reset_pwd_label);
        mEtResetPwd.setOnFocusChangeListener(this);
        mEtResetPwd.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                int length = s.length();
                if (length >= 6) {
                    mIvResetPwdDel.setVisibility(View.VISIBLE);
                    mLlResetPwd.setBackgroundResource(R.drawable.bg_login_input_ok);
                    mBtResetSubmit.setBackgroundResource(R.drawable.bg_login_submit);
                    mBtResetSubmit.setTextColor(getResources().getColor(R.color.white));
                } else {
                    if (length <= 0) {
                        mIvResetPwdDel.setVisibility(View.GONE);
                        mLlResetPwd.setBackgroundResource(R.drawable.bg_login_input_ok);
                    } else {
                        mIvResetPwdDel.setVisibility(View.VISIBLE);
                        mLlResetPwd.setBackgroundResource(R.drawable.bg_login_input_error);
                    }
                    mBtResetSubmit.setBackgroundResource(R.drawable.bg_login_submit_lock);
                    mBtResetSubmit.setTextColor(getResources().getColor(R.color.account_lock_font_color));
                }

            }
        });
    }

    protected void initData() {
        Intent intent = getIntent();
        mPhoneToken = (String) intent.getSerializableExtra(RegisterStepTwoActivity.PHONE_TOKEN_KEY);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mLlResetBar.getViewTreeObserver().addOnGlobalLayoutListener(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        hideKeyBoard(getCurrentFocus().getWindowToken());
        mLlResetBar.getViewTreeObserver().removeOnGlobalLayoutListener(this);
    }

    @OnClick({R.id.ib_navigation_back, R.id.iv_reset_pwd_del, R.id.bt_reset_submit, R.id.lay_reset_container})
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.ib_navigation_back:
                finish();
                break;
            case R.id.iv_reset_pwd_del:
                mEtResetPwd.setText(null);
                break;
            case R.id.bt_reset_submit:
                requestResetPwd();
                break;
            case R.id.lay_reset_container:
                hideKeyBoard(getCurrentFocus().getWindowToken());
                break;
            default:
                break;
        }

    }

    private void requestResetPwd() {
        String tempPwd = mEtResetPwd.getText().toString().trim();
        if (TextUtils.isEmpty(tempPwd) || tempPwd.length() < 6) {
            return;
        }
        if (!DeviceUtils.hasInternet()) {
            showToastForKeyBord(R.string.state_network_error);
            return;
        }

        mAccountPresenter.resetPwd(Md5Utils.getStringMD5(tempPwd), mPhoneToken);
    }


    @Override
    public void onSuccess(int code, String msg) {
        switch (code) {
            case 0:
                ToastUtils.showToast(getString(R.string.reset_success_hint));
                LoginActivity.launch(ResetPwdActivity.this);
                finish();
                break;
            case 216:
                showToastForKeyBord(msg);
                finish();
                break;
            case 219:
                mLlResetPwd.setBackgroundResource(R.drawable.bg_login_input_error);
                showToastForKeyBord(msg);
                break;
            default:
                break;
        }
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        if (hasFocus) {
            mLlResetPwd.setActivated(true);
        }
    }

    @Override
    public void onGlobalLayout() {

        final LinearLayout kayResetPwd = this.mLlResetPwd;
        Rect keypadRect = new Rect();

        mLlResetBar.getWindowVisibleDisplayFrame(keypadRect);

        int screenHeight = mLlResetBar.getRootView().getHeight();

        int keypadHeight = screenHeight - keypadRect.bottom;

        if (keypadHeight > 0) {
            updateKeyBoardActiveStatus(true);
        } else {
            updateKeyBoardActiveStatus(false);
        }

        if (keypadHeight > 0 && kayResetPwd.getTag() == null) {
            final LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) kayResetPwd.getLayoutParams();
            final int topMargin = layoutParams.topMargin;
            this.mTopMargin = topMargin;
            kayResetPwd.setTag(true);
            ValueAnimator valueAnimator = ValueAnimator.ofFloat(1, 0);
            valueAnimator.setDuration(400).setInterpolator(new DecelerateInterpolator());
            valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    float animatedValue = (float) animation.getAnimatedValue();
                    layoutParams.topMargin = (int) (topMargin * animatedValue);
                    kayResetPwd.requestLayout();
                }
            });

            if (valueAnimator.isRunning()) {
                valueAnimator.cancel();
            }
            valueAnimator.start();


        } else if (keypadHeight == 0 && kayResetPwd.getTag() != null) {
            final int topMargin = mTopMargin;
            final LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) kayResetPwd.getLayoutParams();
            kayResetPwd.setTag(null);
            ValueAnimator valueAnimator = ValueAnimator.ofFloat(0, 1);
            valueAnimator.setDuration(400).setInterpolator(new DecelerateInterpolator());
            valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    float animatedValue = (float) animation.getAnimatedValue();
                    layoutParams.topMargin = (int) (topMargin * animatedValue);
                    kayResetPwd.requestLayout();
                }
            });
            if (valueAnimator.isRunning()) {
                valueAnimator.cancel();
            }
            valueAnimator.start();

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
