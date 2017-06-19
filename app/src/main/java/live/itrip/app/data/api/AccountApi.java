package live.itrip.app.data.api;

import live.itrip.app.data.model.User;
import rx.Observable;

/**
 * Created by Feng on 2017/4/26.
 */

public interface AccountApi {

    Observable<User> login(String username, String password);
}
