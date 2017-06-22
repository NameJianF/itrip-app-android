package live.itrip.app.data.api;

import android.support.annotation.IntDef;

import java.util.ArrayList;

import live.itrip.app.data.model.CommonModel;
import rx.Observable;

/**
 * Created by Feng on 2017/6/22.
 */

public interface CommonModelApi {
    public int DUBBLES = 1;

    @IntDef({
            DUBBLES
    })
    @interface CommonModelType {
    }

    /**
     * Get current user's repositories.
     *
     * @return
     */
    Observable<ArrayList<CommonModel>> getDubbles(Integer userid);
}
