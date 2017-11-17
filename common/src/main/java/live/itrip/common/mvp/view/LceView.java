package live.itrip.common.mvp.view;

import android.support.annotation.UiThread;

/**
 * Created by Feng on 2017/4/24.
 */

public interface LceView<M> extends MvpView {

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
