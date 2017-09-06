package live.itrip.app.ui.view.mvp;

import live.itrip.common.mvp.view.LoadView;

/**
 * Created by Feng on 2017/4/26.
 */

public interface AccountView extends LoadView {

    void onSuccess(int code, String msg);

//    void getUserExpandInfoSuccess(UserExpandModel userExpandModel);

}