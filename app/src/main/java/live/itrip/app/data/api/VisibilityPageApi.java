package live.itrip.app.data.api;

import java.util.ArrayList;

import live.itrip.app.data.model.VisibilityPageModel;
import rx.Observable;

/**
 * Created by Feng on 2017/7/12.
 */

public interface VisibilityPageApi {

    Observable<ArrayList<VisibilityPageModel>> loadDatas();

}
