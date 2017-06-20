package live.itrip.app.update;

import android.app.ProgressDialog;
import android.content.Context;

import live.itrip.app.bean.Version;
import live.itrip.app.ui.util.DialogUtils;

/**
 * Created by 建锋 on 2017/6/20.
 */

public class CheckUpdateManager {
    private ProgressDialog mWaitDialog;
    private Context mContext;
    private boolean mIsShowDialog;
    private RequestPermissions mCaller;

    public CheckUpdateManager(Context context, boolean showWaitingDialog) {
        this.mContext = context;
        mIsShowDialog = showWaitingDialog;
        if (mIsShowDialog) {
            mWaitDialog = DialogUtils.getProgressDialog(mContext);
            mWaitDialog.setMessage("正在检查中...");
            mWaitDialog.setCancelable(false);
            mWaitDialog.setCanceledOnTouchOutside(false);
        }
    }


    public void checkUpdate() {
        if (mIsShowDialog) {
            mWaitDialog.show();
        }

        try {
            Thread.sleep(3000);
            mWaitDialog.hide();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void setCaller(RequestPermissions caller) {
        this.mCaller = caller;
    }

    public interface RequestPermissions {
        void call(Version version);
    }
}
