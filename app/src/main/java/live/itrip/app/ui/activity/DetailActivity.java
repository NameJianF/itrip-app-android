package live.itrip.app.ui.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import live.itrip.app.App;
import live.itrip.app.R;
import live.itrip.app.adapter.ViewPagerAdapter;
import live.itrip.app.cache.DetailCache;
import live.itrip.app.data.PreferenceData;
import live.itrip.app.data.model.BlogModel;
import live.itrip.app.data.model.CommentModel;
import live.itrip.app.di.HasComponent;
import live.itrip.app.di.component.DaggerMainComponent;
import live.itrip.app.di.component.MainComponent;
import live.itrip.app.di.module.ActivityModule;
import live.itrip.app.presenter.blog.BlogPresenter;
import live.itrip.app.ui.DetailPage;
import live.itrip.app.ui.activity.account.LoginActivity;
import live.itrip.app.ui.base.BaseActivity;
import live.itrip.app.ui.base.BaseFragment;
import live.itrip.app.ui.interfaces.DetailContract;
import live.itrip.app.ui.util.HTMLUtils;
import live.itrip.app.ui.util.ToastUtils;
import live.itrip.app.ui.view.dialog.ShareDialog;
import live.itrip.app.ui.widget.EmptyLayout;
import live.itrip.app.ui.widget.adapter.OnKeyArrivedListenerAdapter;
import live.itrip.app.ui.widget.comment.CommentBar;
import live.itrip.app.ui.widget.comment.OnCommentClickListener;
import live.itrip.common.util.AppLog;
import live.itrip.common.util.StringUtils;

/**
 * Created by Feng on 2017/7/24.
 */
public class DetailActivity extends BaseActivity implements DetailContract.EmptyView, OnCommentClickListener, HasComponent<MainComponent> {
    @BindView(R.id.ll_comment)
    LinearLayout mLinearLayoutComment;
    @BindView(R.id.lay_container)
    FrameLayout mFrameLayout;
    @BindView(R.id.lay_error)
    EmptyLayout mEmptyLayout;
    @BindView(R.id.view_pager)
    ViewPager mViewPager;

    public final static String BUNDLE_KEY_PAGE = "BUNDLE_KEY_PAGE";
    public final static String BUNDLE_KEY_ARGS = "BUNDLE_KEY_ARGS";
    protected int mPageValue = -1;

    private ActionBar mActionBar;
    private CommentBar mCommentBar;
    private ShareDialog mAlertDialog;
    private TextView mCommentCountView;

    private Long extraBlogId = 0L;
    private String mCommentHint;
    private BlogModel mBlogModel;
    private long mCommentId;
    private long mCommentAuthorId;
    private boolean mInputDoubleEmpty = false;

    @Inject
    BlogPresenter mBlogPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        ButterKnife.bind(this);

        Intent intent = getIntent();
        if (mPageValue == -1) {
            mPageValue = intent.getIntExtra(BUNDLE_KEY_PAGE, 0);
        }

        initViews(mPageValue, getIntent());

        AppLog.d("trace===SimpleBackActivity onCreate, end");
    }

    protected void initViews(int pageValue, Intent data) {
        if (data == null) {
            throw new RuntimeException("you must provide a page info to display");
        }
        DetailPage page = DetailPage.getPageByValue(pageValue);
        if (page == null) {
            throw new IllegalArgumentException("can not find page by value:" + pageValue);
        }

        mActionBar = this.getSupportActionBar();
        if (mActionBar != null) {
            mActionBar.setDisplayHomeAsUpEnabled(true);
            mActionBar.setDisplayShowHomeEnabled(true);
            mActionBar.setTitle("博客详情");
        }

        mEmptyLayout.setOnLayoutClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mEmptyLayout.getErrorState() != EmptyLayout.NETWORK_LOADING) {
                    mEmptyLayout.setErrorType(EmptyLayout.NETWORK_LOADING);
                    mBlogPresenter.loadBlogDetail(extraBlogId);
                }
            }
        });

        mCommentBar = CommentBar.delegation(this, mLinearLayoutComment);
        mCommentBar.setCommentHint(mCommentHint);
        mCommentBar.getBottomSheet().getEditText().setHint(mCommentHint);
        if (mBlogModel != null) {
            mCommentBar.setFavDrawable(mBlogModel.getFavorite() == 1 ? R.drawable.ic_faved : R.drawable.ic_fav);
        } else {
            mCommentBar.setFavDrawable(R.drawable.ic_fav);

        }
        mCommentBar.setFavListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!PreferenceData.Account.isLogon(DetailActivity.this)) {
                    LoginActivity.launch(DetailActivity.this);
                    return;
                }
                mBlogPresenter.favReverse();
            }
        });

        mCommentBar.getBottomSheet().setMentionListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ((PreferenceData.Account.isLogon(DetailActivity.this))) {
//                    UserSelectFriendsActivity.show(DetailActivity.this, mCommentBar.getBottomSheet().getEditText());
                    ToastUtils.showToast("UserSelectFriendsActivity show.");
                } else {
                    LoginActivity.launch(DetailActivity.this);
                }
            }
        });

        mCommentBar.setShareListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // test
                toShare("测试数据title", "测试数据title", "http://www.tourin.cn/index.html");
                if (mBlogModel != null) {
                    toShare(mBlogModel.getTitle(), mBlogModel.getBody(), mBlogModel.getHref());
                }
            }
        });

        mCommentBar.getBottomSheet().getEditText().setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_DEL) {
                    handleKeyDel();
                }
                return false;
            }
        });
        mCommentBar.getBottomSheet().setCommitListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mCommentBar == null) return;
                mCommentBar.getBottomSheet().dismiss();
                mCommentBar.setCommitButtonEnable(false);
                mBlogPresenter.addComment(mBlogModel.getId(),
                        mBlogModel.getType(),
                        mCommentBar.getBottomSheet().getCommentText(),
                        0,
                        mCommentId,
                        mCommentAuthorId);
            }
        });

        mCommentBar.getBottomSheet().getEditText().setOnKeyArrivedListener(new OnKeyArrivedListenerAdapter(this));

        try {
            BaseFragment fragment = (BaseFragment) page.getClz().newInstance();

            ViewPagerAdapter mViewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
            mViewPagerAdapter.addFragment(fragment);
            mViewPager.setAdapter(mViewPagerAdapter);

            Bundle args = data.getBundleExtra(BUNDLE_KEY_ARGS);
            if (args != null) {
                fragment.setArguments(args);
            }

        } catch (Exception e) {
            e.printStackTrace();
            throw new IllegalArgumentException(
                    "generate fragment error. by value:" + pageValue);
        }
    }


    public EmptyLayout getEmptyLayout() {
        return mEmptyLayout;
    }

    public void hideEmptyLayout() {
        mEmptyLayout.setErrorType(EmptyLayout.HIDE_LAYOUT);
        if (mCommentCountView != null) {
            mCommentCountView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    CommentsActivity.show(DetailActivity.this, mBlogModel.getId(), mBlogModel.getType(), OSChinaApi.COMMENT_NEW_ORDER, mBlogModel.getTitle());
                    ToastUtils.showToast("CommentsActivity show.");
                }
            });
        }
    }

    @Override
    public void showErrorLayout(int errorType) {
        mEmptyLayout.setErrorType(errorType);
    }

    @Override
    public void showGetDetailSuccess(BlogModel model) {
        this.mBlogModel = model;
        if (mCommentBar != null) {
            mCommentBar.setFavDrawable(mBlogModel.getFavorite() == 1 ? R.drawable.ic_faved : R.drawable.ic_fav);
        }
        if (mCommentCountView != null && mBlogModel.getStatistics() != null) {
            mCommentCountView.setText(String.valueOf(mBlogModel.getStatistics().getComment()));
        }
    }

    @Override
    public void showFavReverseSuccess(boolean isFav, int favCount, int strId) {
        if (mCommentBar != null) {
            mCommentBar.setFavDrawable(isFav ? R.drawable.ic_faved : R.drawable.ic_fav);
        }
    }

    @Override
    public void showCommentSuccess(CommentModel comment) {
        if (mCommentBar == null)
            return;
//        if (mCommentBar.getBottomSheet().isSyncToTweet()) {
//            TweetPublishService.startActionPublish(this,
//                    mCommentBar.getBottomSheet().getCommentText(), null,
//                    About.buildShare(mBean.getId(), mBean.getType()));
//        }
        mCommentBar.getBottomSheet().dismiss();
        mCommentBar.setCommitButtonEnable(true);
        ToastUtils.showToast(getBaseContext().getResources().getString(R.string.pub_comment_success));
//        AppContext.showToastShort(R.string.pub_comment_success);
        mCommentBar.getCommentText().setHint(mCommentHint);
        mCommentBar.getBottomSheet().getEditText().setText("");
        mCommentBar.getBottomSheet().getEditText().setHint(mCommentHint);
        mCommentBar.getBottomSheet().dismiss();
    }

    @Override
    public void showCommentError(String message) {
//        AppContext.showToastShort(R.string.pub_comment_failed);
        ToastUtils.showToast(getBaseContext().getResources().getString(R.string.pub_comment_failed));
        mCommentBar.setCommitButtonEnable(true);
    }

    /**
     * 分享
     *
     * @param title
     * @param content
     * @param url
     * @return
     */
    @SuppressWarnings({"LoopStatementThatDoesntLoop", "SuspiciousMethodCalls"})
    protected boolean toShare(String title, String content, String url) {
        if (TextUtils.isEmpty(title) || TextUtils.isEmpty(url) || mBlogModel == null)
            return false;

        String imageUrl = null;
//        List<BlogModel.Image> images = mBlogModel.getImages();
        switch (mBlogModel.getType()) {
//            case News.TYPE_EVENT:
//                if (images != null && images.size() > 0) {
//                    imageUrl = images.get(0).getHref();
//                }
//                break;
//            case News.TYPE_SOFTWARE:
//                if (images != null && images.size() > 0) {
//                    imageUrl = images.get(0).getThumb();
//                    if (imageUrl != null && imageUrl.contains("logo/default.png")) {
//                        imageUrl = null;
//                    }
//                    break;
//                }
            default:
                String regex = "<img src=\"([^\"]+)\"";
                Pattern pattern = Pattern.compile(regex);
                Matcher matcher = pattern.matcher(content);
                while (matcher.find()) {
                    imageUrl = matcher.group(1);
                    break;
                }
                break;
        }
        content = content.trim();
        if (content.length() > 55) {
            content = HTMLUtils.delHTMLTag(content);
            if (content.length() > 55)
                content = StringUtils.getSubString(0, 55, content);
        } else {
            content = HTMLUtils.delHTMLTag(content);
        }
        if (TextUtils.isEmpty(content))
            content = "";

        // 分享
        if (mAlertDialog == null) {
            mAlertDialog = new
                    ShareDialog(this, mBlogModel.getId())
                    .type(mBlogModel.getType())
                    .title(title)
                    .content(content)
                    .imageUrl(imageUrl)//如果没有图片，即url为null，直接加入app默认分享icon
                    .url(url).with();
        }
        mAlertDialog.show();

        return true;
    }

    /**
     * backspage 退格
     */
    protected void handleKeyDel() {
        if (mCommentId != mBlogModel.getId()) {
            if (TextUtils.isEmpty(mCommentBar.getBottomSheet().getCommentText())) {
                if (mInputDoubleEmpty) {
                    mCommentId = mBlogModel.getId();
                    mCommentAuthorId = 0;
                    mCommentBar.setCommentHint(mCommentHint);
                    mCommentBar.getBottomSheet().getEditText().setHint(mCommentHint);
                } else {
                    mInputDoubleEmpty = true;
                }
            } else {
                mInputDoubleEmpty = false;
            }
        }
    }


    @SuppressLint("SetTextI18n")
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_detail, menu);
        MenuItem item = menu.findItem(R.id.menu_scroll_comment);
        if (item != null) {
            View action = item.getActionView();
            if (action != null) {
                View tv = action.findViewById(R.id.tv_comment_count);
                if (tv != null) {
                    mCommentCountView = (TextView) tv;
                    if (mBlogModel != null && mBlogModel.getStatistics() != null)
                        mCommentCountView.setText(mBlogModel.getStatistics().getComment() + "");
                }
            }
        }
        return true;
    }

    @Override
    public void onClick(View view, CommentModel comment) {
        mCommentId = comment.getId();
        mCommentAuthorId = comment.getAuthor().getId();
        mCommentBar.getCommentText().setHint(String.format("%s %s", getResources().getString(R.string.reply_hint), comment.getAuthor().getUserName()));
        mCommentBar.getBottomSheet().show(String.format("%s %s", getResources().getString(R.string.reply_hint), comment.getAuthor().getUserName()));
    }

    @Override
    public void finish() {
        if (mEmptyLayout.getErrorState() == EmptyLayout.HIDE_LAYOUT) {
            DetailCache.addCache(mBlogModel);
        }
        super.finish();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mAlertDialog == null)
            return;
        mAlertDialog.hideProgressDialog();
        AppLog.d("trace===DetailActivity onResume");
    }

    private abstract class OnDoubleTouchListener implements View.OnTouchListener {
        private long lastTouchTime = 0;
        private AtomicInteger touchCount = new AtomicInteger(0);
        private Runnable mRun = null;
        private Handler mHandler;

        OnDoubleTouchListener() {
            mHandler = new Handler(getMainLooper());
        }

        void removeCallback() {
            if (mRun != null) {
                mHandler.removeCallbacks(mRun);
                mRun = null;
            }
        }

        @Override
        public boolean onTouch(final View v, final MotionEvent event) {
            if (event.getAction() == MotionEvent.ACTION_UP) {
                final long now = System.currentTimeMillis();
                lastTouchTime = now;

                touchCount.incrementAndGet();
                removeCallback();

                mRun = new Runnable() {
                    @Override
                    public void run() {
                        if (now == lastTouchTime) {
                            onMultiTouch(v, event, touchCount.get());
                            touchCount.set(0);
                        }
                    }
                };

                mHandler.postDelayed(mRun, getMultiTouchInterval());
            }
            return true;
        }


        int getMultiTouchInterval() {
            return 400;
        }

        abstract void onMultiTouch(View v, MotionEvent event, int touchCount);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    public void setActionBarTitle(String actionBarTitle) {
        this.mActionBar.setTitle(actionBarTitle);
    }

    @Override
    public MainComponent getComponent() {
        return DaggerMainComponent.builder()
                .applicationComponent(App.getInstance().getComponent())
                .activityModule(new ActivityModule(this))
                .build();
    }

}