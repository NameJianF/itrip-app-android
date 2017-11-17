package live.itrip.app.ui.view.mvp;

import android.support.annotation.UiThread;

import live.itrip.common.mvp.view.LoadView;

/**
 * Created by Feng on 2017/9/7.
 */

public interface FeedBackView extends LoadView {
    @UiThread
    void onSuccess(int code, String msg);
}
