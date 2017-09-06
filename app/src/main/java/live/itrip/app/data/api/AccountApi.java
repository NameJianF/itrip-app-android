package live.itrip.app.data.api;

import org.json.JSONException;

import live.itrip.app.data.model.UserExpandModel;
import live.itrip.app.data.model.UserModel;
import live.itrip.app.data.net.response.ResultResp;
import rx.Completable;
import rx.Observable;

/**
 * Created by Feng on 2017/4/26.
 */

public interface AccountApi {

    Observable<UserModel> login(String username, String password) throws JSONException;

    Observable<UserExpandModel> getUserExpandInfo(String username, String token) throws JSONException;

    Observable<UserModel> openLogin(int catalog, String openInfo) throws JSONException;

    Observable<ResultResp> validateSmsCode(String phoneNumber, String smsCode) throws JSONException;

    Observable<ResultResp> sendSmsCode(String phoneNumber, int smsType) throws JSONException;

    Observable<ResultResp> resetPwd(String newPwd, String phoneToken);

    Observable<UserModel> register(String username, String pwd, int sex, String phoneToken);
}
