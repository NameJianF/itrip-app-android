package live.itrip.app.data.api;

import java.util.ArrayList;

import live.itrip.app.data.model.HomePageModel;
import rx.Observable;

/**
 * Created by Feng on 2017/7/12.
 */

public interface HomePageApi {

    Observable<ArrayList<HomePageModel>> loadDatas();

}
