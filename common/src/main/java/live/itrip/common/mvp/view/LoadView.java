package live.itrip.common.mvp.view;

import android.support.annotation.UiThread;

/**
 * Created by Feng on 2017/4/24.
 */

public interface LoadView extends MvpView {

    @UiThread
    void showLoading();

    @UiThread
    void dismissLoading();

    @UiThread
    void error(Throwable e);
}