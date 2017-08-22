package live.itrip.app.ui.view.mvp;

import live.itrip.app.data.model.UserModel;
import live.itrip.common.mvp.view.LoadView;

/**
 * Created by Feng on 2017/4/26.
 */

public interface LoginView extends LoadView {

    void loginSuccess(UserModel user);

}