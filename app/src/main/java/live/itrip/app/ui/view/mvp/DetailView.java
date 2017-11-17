package live.itrip.app.ui.view.mvp;

import android.support.annotation.UiThread;

import java.util.ArrayList;

import live.itrip.app.data.model.ChildMultiItem;
import live.itrip.common.mvp.view.LoadView;
import live.itrip.common.mvp.view.MvpView;

/**
 * Created by Feng on 2017/8/8.
 */

public interface DetailView<M> extends LoadView {
    /**
     * 加载 详细信息
     *
     * @param data
     */
    @UiThread
    void showDetailContent(M data);

    @UiThread
    void showEmpty();

    /**
     * 成功：加载相关推荐
     */
    @UiThread
    void showRecommendSuccess(ArrayList<ChildMultiItem> data);

    /**
     * 成功：收藏
     *
     * @param isFav
     * @param favCount
     * @param strId
     */
    @UiThread
    void showFavReverseSuccess(boolean isFav, int favCount, int strId);

    /**
     * 失败：收藏
     */
    @UiThread
    void showFavError();

    /**
     * 成功：添加评论
     */
    @UiThread
    void showAddCommentSuccess();
}
