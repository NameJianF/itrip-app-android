package live.itrip.app.ui.interfaces;

import live.itrip.app.data.model.BlogDetailModel;
import live.itrip.app.data.model.CommentModel;

/**
 * Created by Feng on 2017/7/21.
 */

public interface DetailContract {

    interface EmptyView {
        void hideEmptyLayout();

        void showErrorLayout(int errorType);

        void showGetDetailSuccess(BlogDetailModel bean);

        void showFavReverseSuccess(boolean isFav, int favCount, int strId);

        void showCommentSuccess(CommentModel comment);

        void showCommentError(String message);
    }
}
