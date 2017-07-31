package live.itrip.app.data.api;

import org.json.JSONException;

import live.itrip.app.data.model.UserModel;
import rx.Observable;

/**
 * Created by Feng on 2017/4/26.
 */

public interface AccountApi {

    Observable<UserModel> login(String username, String password) throws JSONException;
}
