package live.itrip.app.ui.view.mvp;

import android.support.annotation.UiThread;

import live.itrip.common.mvp.view.LoadView;
import live.itrip.common.mvp.view.MvpView;

/**
 * Created by Feng on 2017/8/22.
 */

public interface SettingsView<M> extends LoadView {

    @UiThread
    void logoutSuccess();

    @UiThread
    void showContent(M data);

}
