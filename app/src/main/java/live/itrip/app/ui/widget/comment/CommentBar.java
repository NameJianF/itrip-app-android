package live.itrip.app.ui.widget.comment;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import live.itrip.app.R;
import live.itrip.app.data.PreferenceData;
import live.itrip.app.ui.activity.account.LoginActivity;

/**
 * Created by Feng on 2017/7/26.
 */

public class CommentBar {
    private Context mContext;
    private View mRootView;
    private FrameLayout mFrameLayout;
    private ViewGroup mParent;
    private ImageButton mFavView;
    private ImageButton mShareView;
    private TextView mCommentText;
    private BottomSheetBar mDelegation;
    private LinearLayout mCommentLayout;


    private CommentBar(Context context) {
        this.mContext = context;
    }

    public static CommentBar delegation(Context context, ViewGroup parent) {
        CommentBar bar = new CommentBar(context);
        bar.mRootView = LayoutInflater.from(context).inflate(R.layout.layout_comment_bar, parent, false);
        bar.mParent = parent;
        bar.mDelegation = BottomSheetBar.delegation(context);
        bar.mParent.addView(bar.mRootView);
        bar.initView();
        return bar;
    }

    private void initView() {
        //((CoordinatorLayout.LayoutParams) mRootView.getLayoutParams()).setBehavior(new FloatingAutoHideDownBehavior());
        mFavView = (ImageButton) mRootView.findViewById(R.id.ib_fav);
        mShareView = (ImageButton) mRootView.findViewById(R.id.ib_share);
        mCommentText = (TextView) mRootView.findViewById(R.id.tv_comment);
        mCommentLayout = (LinearLayout) mRootView.findViewById(R.id.ll_comment);
        mCommentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (PreferenceData.Account.isLogon(mContext)) {
                    mDelegation.show(mCommentText.getHint().toString());
                } else {
                    LoginActivity.launch(mContext);
                }
            }
        });
    }

    /**
     * share 2 three sdk
     *
     * @param listener
     */
    public void setShareListener(View.OnClickListener listener) {
        mShareView.setOnClickListener(listener);
    }

    /**
     * favorite the detail
     *
     * @param listener
     */
    public void setFavListener(View.OnClickListener listener) {
        mFavView.setOnClickListener(listener);
    }

    public void setCommentListener(View.OnClickListener listener) {
        mCommentText.setOnClickListener(listener);
    }

    public void setCommentHint(String text) {
        mCommentText.setHint(text);
    }

    public void setFavDrawable(int drawable) {
        mFavView.setImageResource(drawable);
    }

    public BottomSheetBar getBottomSheet() {
        return mDelegation;
    }

    public void setCommitButtonEnable(boolean enable) {
        mDelegation.getBtnCommit().setEnabled(enable);
    }

    public void hideShare() {
        mShareView.setVisibility(View.GONE);
    }

    public void hideFav() {
        mFavView.setVisibility(View.GONE);
    }

    public TextView getCommentText() {
        return mCommentText;
    }


    public void performClick() {
        mCommentLayout.performClick();
    }
}
