package live.itrip.app.ui.view.mvp;

import android.support.annotation.UiThread;

import live.itrip.common.mvp.view.LoadView;

/**
 * Created by Feng on 2017/4/26.
 */

public interface AccountView extends LoadView {
    @UiThread
    void onSuccess(int code, String msg);

//    void getUserExpandInfoSuccess(UserExpandModel userExpandModel);

}