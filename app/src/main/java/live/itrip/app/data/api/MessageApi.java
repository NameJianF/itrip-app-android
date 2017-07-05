package live.itrip.app.data.api;

import android.support.annotation.IntDef;

import java.util.ArrayList;

import live.itrip.app.data.model.MessageModel;
import rx.Observable;

/**
 * Created by Feng on 2017/6/27.
 */

public interface MessageApi {
    public int FLAG_SYSTEM = 1;
    public int FLAG_USER = 2;

    @IntDef({
            FLAG_SYSTEM,
            FLAG_USER
    })
    @interface MessageType {

    }

    /**
     * Get messages base on type.
     *
     * @param type
     * @return
     */
    Observable<ArrayList<MessageModel>> getMessages(@MessageType int type
            , Long uid, int page, int pageSize, Long lastMsgId);
}
