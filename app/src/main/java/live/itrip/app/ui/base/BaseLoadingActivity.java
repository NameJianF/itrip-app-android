package live.itrip.app.ui.base;

import android.os.Bundle;
import android.support.design.widget.Snackbar;

import live.itrip.app.ui.view.loading.LoadingView;
import live.itrip.common.mvp.view.LoadView;

/**
 * Created by Feng on 2017/4/26.
 */
public abstract class BaseLoadingActivity extends BaseActivity implements LoadView {

    private LoadingView mLoadingView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mLoadingView = new LoadingView(this, getLoadingMessage());
    }

    @Override
    public void showLoading() {
        mLoadingView.show();
    }

    @Override
    public void dismissLoading() {
        mLoadingView.dismiss();
    }

    public abstract String getLoadingMessage();

    @Override
    public void error(Throwable e) {
        Snackbar.make(getWindow().getDecorView(), e.getMessage(), Snackbar.LENGTH_LONG).show();
    }
}
