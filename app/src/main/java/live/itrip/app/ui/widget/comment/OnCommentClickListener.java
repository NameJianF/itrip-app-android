package live.itrip.app.ui.widget.comment;

import android.view.View;

import live.itrip.app.data.model.CommentModel;

/**
 * Created by Feng on 2017/7/21.
 */

public interface OnCommentClickListener {
    void onClick(View view, CommentModel comment);
}
