package live.itrip.app.ui.activity.account;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import android.support.v4.content.LocalBroadcastManager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;
import android.widget.Toast;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import live.itrip.app.R;
import live.itrip.app.ui.base.BaseActivity;
import live.itrip.app.util.DialogUtils;
import live.itrip.common.mvp.view.LoadView;


/**
 * Created by Feng on 2017/9/5.
 */

public abstract class AccountBaseActivity extends BaseActivity implements LoadView {
    private ProgressDialog mDialog;
    public static final String ACTION_ACCOUNT_FINISH_ALL = "app.oschina.net.action.finish.all";
    protected LocalBroadcastManager mManager;
    private BroadcastReceiver mReceiver;
    protected InputMethodManager mInputMethodManager;
    protected Toast mToast;
    private boolean mKeyBoardIsActive;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        registerLocalReceiver();
        mInputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
    }


    /**
     * showToast
     *
     * @param text text
     */
    @SuppressLint("InflateParams")
    private void showToast(String text) {
        Toast toast = this.mToast;
        if (toast == null) {
            toast = initToast();
        }
        View rootView = LayoutInflater.from(this).inflate(R.layout.view_toast, null, false);
        TextView textView = (TextView) rootView.findViewById(R.id.title_tv);
        textView.setText(text);
        toast.setView(rootView);
        initToastGravity(toast);
        toast.show();
    }

    /**
     * showToast
     *
     * @param id id
     */
    @SuppressLint("InflateParams")
    private void showToast(@StringRes int id) {
        Toast toast = this.mToast;
        if (toast == null) {
            toast = initToast();
        }
        View rootView = LayoutInflater.from(this).inflate(R.layout.view_toast, null, false);
        TextView textView = (TextView) rootView.findViewById(R.id.title_tv);
        textView.setText(id);
        toast.setView(rootView);
        initToastGravity(toast);
        toast.show();
    }

    @NonNull
    private Toast initToast() {
        Toast toast;
        toast = new Toast(this);
        toast.setDuration(Toast.LENGTH_SHORT);
        this.mToast = toast;
        return toast;
    }

    private void initToastGravity(Toast toast) {
        boolean isCenter = this.mKeyBoardIsActive;
        if (isCenter) {
            toast.setGravity(Gravity.CENTER_HORIZONTAL, 0, 0);
        } else {
            toast.setGravity(Gravity.BOTTOM, 0, getResources().getDimensionPixelSize(R.dimen.toast_y_offset));
        }
    }

    /**
     * update keyBord active status
     *
     * @param isActive isActive
     */
    protected void updateKeyBoardActiveStatus(boolean isActive) {
        this.mKeyBoardIsActive = isActive;
    }

    /**
     * cancelToast
     */
    protected void cancelToast() {
        if (mToast != null) {
            mToast.cancel();
        }
    }

    protected boolean sendLocalReceiver() {
        if (mManager != null) {
            Intent intent = new Intent();
            intent.setAction(ACTION_ACCOUNT_FINISH_ALL);
            return mManager.sendBroadcast(intent);
        }

        return false;
    }

    /**
     * register localReceiver
     */
    private void registerLocalReceiver() {
        if (mManager == null)
            mManager = LocalBroadcastManager.getInstance(this);
        IntentFilter filter = new IntentFilter();
        filter.addAction(ACTION_ACCOUNT_FINISH_ALL);
        if (mReceiver == null)
            mReceiver = new BroadcastReceiver() {
                @Override
                public void onReceive(Context context, Intent intent) {
                    String action = intent.getAction();
                    if (ACTION_ACCOUNT_FINISH_ALL.equals(action)) {
                        finish();
                    }
                }
            };
        mManager.registerReceiver(mReceiver, filter);
    }

    /**
     * show WaitDialog
     *
     * @return progressDialog
     */
    protected ProgressDialog showWaitDialog(@StringRes int messageId) {
        if (mDialog == null) {
            if (messageId <= 0) {
                mDialog = DialogUtils.getProgressDialog(this, true);
            } else {
                String message = getResources().getString(messageId);
                mDialog = DialogUtils.getProgressDialog(this, message, true);
            }
        }
        mDialog.show();

        return mDialog;
    }

    /**
     * show FocusWaitDialog
     *
     * @return progressDialog
     */
    protected ProgressDialog showFocusWaitDialog() {

        String message = getResources().getString(R.string.progress_submit);
        if (mDialog == null) {
            mDialog = DialogUtils.getProgressDialog(this, message, false);//DialogHelp.getWaitDialog(this, message);
        }
        mDialog.show();

        return mDialog;
    }

    /**
     * hide waitDialog
     */
    protected void hideWaitDialog() {
        ProgressDialog dialog = mDialog;
        if (dialog != null) {
            mDialog = null;
            try {
                dialog.cancel();
                // dialog.dismiss();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    protected void showToastForKeyBord(@StringRes int id) {
        showToast(id);
    }

    protected void showToastForKeyBord(String message) {
        showToast(message);
    }

    protected void hideKeyBoard(IBinder windowToken) {
        InputMethodManager inputMethodManager = this.mInputMethodManager;
        if (inputMethodManager == null) return;
        boolean active = inputMethodManager.isActive();
        if (active) {
            inputMethodManager.hideSoftInputFromWindow(windowToken, 0);
        }
    }

    /**
     * request network error
     *
     * @param throwable throwable
     */
    protected void requestFailureHint(Throwable throwable) {
        if (throwable != null) {
            throwable.printStackTrace();
        }
        showToastForKeyBord(R.string.request_error_hint);
    }

    @Override
    public void showLoading() {
        showFocusWaitDialog();
    }

    @Override
    public void dismissLoading() {
        hideWaitDialog();
    }

    @Override
    public void error(Throwable e) {
        requestFailureHint(e);
//        Snackbar.make(getWindow().getDecorView(), e.getMessage(), Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @Override
    protected void onStop() {
        super.onStop();
        hideWaitDialog();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        hideKeyBoard(getCurrentFocus().getWindowToken());
        if (mManager != null) {
            if (mReceiver != null)
                mManager.unregisterReceiver(mReceiver);
        }
    }
}
