package live.itrip.app.ui.base;

import android.app.AlertDialog;
import android.os.Bundle;
import android.support.design.widget.Snackbar;

import dmax.dialog.SpotsDialog;
import live.itrip.common.mvp.view.LoadView;

/**
 * Created by Feng on 2017/4/26.
 */
public abstract class BaseLoadingActivity extends BaseActivity implements LoadView {

    private AlertDialog mLoadingDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mLoadingDialog = new SpotsDialog(this, getLoadingMessage());
    }

    @Override
    public void showLoading() {
        mLoadingDialog.show();
    }

    @Override
    public void dismissLoading() {
        mLoadingDialog.dismiss();
    }

    public abstract String getLoadingMessage();

    @Override
    public void error(Throwable e) {
        Snackbar.make(getWindow().getDecorView(), e.getMessage(), Snackbar.LENGTH_LONG).show();
    }
}
