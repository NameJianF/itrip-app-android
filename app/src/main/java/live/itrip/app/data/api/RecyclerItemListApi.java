package live.itrip.app.data.api;


import android.support.annotation.IntDef;

import java.util.ArrayList;

import live.itrip.app.data.model.RecyclerViewItem;
import rx.Observable;

/**
 * Created by Feng on 2017/6/26.
 */

public interface RecyclerItemListApi {

    /**
     * 冒泡
     */
    public int FLAG_BUBBLES = 1;
    /**
     * 收藏
     */
    public int FLAG_FAVORITE = 2;

    /**
     * 关注
     */
    public int FLAG_FOLLOWING = 3;
    /**
     * 粉丝
     */
    public int FLAG_FOLLOWER = 4;


    @IntDef({
            FLAG_BUBBLES,
            FLAG_FAVORITE,
            FLAG_FOLLOWING,
            FLAG_FOLLOWER
    })
    @interface RecyclerItemType {
    }

    Observable<ArrayList<RecyclerViewItem>> getItemList(@RecyclerItemListApi.RecyclerItemType int type, Integer page);

}
