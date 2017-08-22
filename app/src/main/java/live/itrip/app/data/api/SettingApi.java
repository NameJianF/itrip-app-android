package live.itrip.app.data.api;

import org.json.JSONException;

import live.itrip.app.data.model.UpdateModel;
import live.itrip.app.data.net.response.ResultResp;
import rx.Observable;

/**
 * Created by Feng on 2017/6/28.
 */

public interface SettingApi {

    Observable<UpdateModel> checkAppVersion();

    Observable<ResultResp> logout(String username, String token) throws JSONException;
}
