package live.itrip.app.ui.view.mvp;

import android.support.annotation.UiThread;

import live.itrip.common.mvp.view.MvpView;

/**
 * Created by Feng on 2017/8/22.
 */

public interface SettingsView<M> extends MvpView {


    @UiThread
    void logoutSuccess();

    @UiThread
    void logoutFailed();

    @UiThread
    void showLoading();

    @UiThread
    void dismissLoading();

    @UiThread
    void showContent(M data);

    @UiThread
    void showError(Throwable e);

    @UiThread
    void showEmpty();
}
