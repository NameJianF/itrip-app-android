package live.itrip.app.data.api;

import live.itrip.app.data.model.UpdateModel;
import rx.Observable;

/**
 * Created by Feng on 2017/6/28.
 */

public interface SettingApi {

    Observable<UpdateModel> checkAppVersion();
}
