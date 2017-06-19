package live.itrip.common.mvp.presenter;

import android.support.annotation.UiThread;

import live.itrip.common.mvp.view.MvpView;

/**
 * Created by Feng on 2017/4/24.
 */

public interface MvpPresenter<V extends MvpView> {

    /**
     * Set or attach the view to this presenter
     */
    @UiThread
    void attachView(V view);

    /**
     * Will be called if the view has been destroyed. Typically this method will be invoked from
     * <code>Activity.detachView()</code> or <code>Fragment.onDestroyView()</code>
     */
    @UiThread
    void detachView();

}

