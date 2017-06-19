package live.itrip.common.mvp.view;

import android.support.annotation.UiThread;

/**
 * Created by Feng on 2017/4/24.
 */

public interface LoadView extends MvpView {

    @UiThread
    public void showLoading();

    @UiThread
    public void dismissLoading();

    @UiThread
    public void error(Throwable e);
}