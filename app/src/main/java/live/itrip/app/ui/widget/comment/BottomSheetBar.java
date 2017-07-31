package live.itrip.app.ui.widget.comment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;

import live.itrip.app.R;
import live.itrip.app.ui.util.DeviceUtils;
import live.itrip.app.ui.view.dialog.BottomDialog;
import live.itrip.app.ui.widget.RichEditText;
import live.itrip.app.ui.widget.emoji.EmojiView;

/**
 * Created by Feng on 2017/7/26.
 */

public class BottomSheetBar {
    private View mRootView;
    private RichEditText mEditText;
    private ImageButton mFaceView;
    private Context mContext;
    private Button mBtnCommit;
    private BottomDialog mDialog;
    private FrameLayout mFrameLayout;
    private EmojiView mEmojiView;


    private BottomSheetBar(Context context) {
        this.mContext = context;
    }

    @SuppressLint("InflateParams")
    public static BottomSheetBar delegation(Context context) {
        BottomSheetBar bar = new BottomSheetBar(context);
        bar.mRootView = LayoutInflater.from(context).inflate(R.layout.layout_bottom_sheet_comment_bar, null, false);
        bar.initView();
        return bar;
    }

    private void initView() {
        mFrameLayout = (FrameLayout) mRootView.findViewById(R.id.fl_face);
        mEditText = (RichEditText) mRootView.findViewById(R.id.et_comment);
        mFaceView = (ImageButton) mRootView.findViewById(R.id.ib_face);

        mBtnCommit = (Button) mRootView.findViewById(R.id.btn_comment);
        mBtnCommit.setEnabled(false);

        mDialog = new BottomDialog(mContext, false);
        mDialog.setContentView(mRootView);

        mDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                DeviceUtils.closeKeyboard(mEditText);
                mFrameLayout.setVisibility(View.GONE);
            }
        });

        mFaceView.setVisibility(View.VISIBLE);
        mFaceView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mEmojiView == null) {
                    mEmojiView = new EmojiView(mContext, mEditText);
                    mFrameLayout.addView(mEmojiView);
                }
                mFrameLayout.setVisibility(View.VISIBLE);
                mEmojiView.openPanel();
                DeviceUtils.closeKeyboard(mEditText);
            }
        });

        mEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mFrameLayout.setVisibility(View.GONE);
            }
        });

        mEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                mBtnCommit.setEnabled(s.length() > 0);
            }
        });
    }

    public void show(String hint) {
        mDialog.show();
        if (!"添加评论".equals(hint)) {
            mEditText.setHint(hint + " ");
        }
        mRootView.postDelayed(new Runnable() {
            @Override
            public void run() {
                DeviceUtils.showSoftKeyboard(mEditText);
            }
        }, 50);
    }

    public void dismiss() {
        DeviceUtils.closeKeyboard(mEditText);
        mDialog.dismiss();
    }

//    public void setFaceListener(View.OnClickListener listener) {
//        mFaceView.setOnClickListener(listener);
//    }

    public void setCommitListener(View.OnClickListener listener) {
        mBtnCommit.setOnClickListener(listener);
    }

    public RichEditText getEditText() {
        return mEditText;
    }

    public String getCommentText() {
        return mEditText.getText().toString().trim();
    }

    public Button getBtnCommit() {
        return mBtnCommit;
    }

}
