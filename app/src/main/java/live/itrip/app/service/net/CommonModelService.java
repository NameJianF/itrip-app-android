package live.itrip.app.service.net;

import java.util.ArrayList;

import live.itrip.app.data.model.CommonModel;
import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by Feng on 2017/6/22.
 */

public interface CommonModelService {

    @POST("/user.action")
    Observable<ArrayList<CommonModel>> getDubbles(@Query("userid") Integer userid);
}
