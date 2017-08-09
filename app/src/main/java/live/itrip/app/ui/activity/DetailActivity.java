package live.itrip.app.ui.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.ViewPager;
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

import butterknife.BindView;
import butterknife.ButterKnife;
import live.itrip.app.App;
import live.itrip.app.R;
import live.itrip.app.adapter.fragment.ViewPagerAdapter;
import live.itrip.app.cache.DetailCache;
import live.itrip.app.data.PreferenceData;
import live.itrip.app.data.model.BaseDetailModel;
import live.itrip.app.data.model.BlogDetailModel;
import live.itrip.app.data.model.CommentModel;
import live.itrip.app.di.HasComponent;
import live.itrip.app.di.component.DaggerMainComponent;
import live.itrip.app.di.component.MainComponent;
import live.itrip.app.di.module.ActivityModule;
import live.itrip.app.presenter.interfaces.IDetailPresenter;
import live.itrip.app.ui.DetailPage;
import live.itrip.app.ui.activity.account.LoginActivity;
import live.itrip.app.ui.base.BaseActivity;
import live.itrip.app.ui.base.BaseDetailFragment;
import live.itrip.app.ui.interfaces.DetailContract;
import live.itrip.app.ui.util.ToastUtils;
import live.itrip.app.ui.view.dialog.ShareDialog;
import live.itrip.app.ui.widget.EmptyLayout;
import live.itrip.app.ui.widget.adapter.OnKeyArrivedListenerAdapter;
import live.itrip.app.ui.widget.comment.CommentBar;
import live.itrip.app.ui.widget.comment.OnCommentClickListener;
import live.itrip.common.util.AppLog;

/**
 * Created by Feng on 2017/7/24.
 */
public class DetailActivity extends BaseActivity implements OnCommentClickListener, HasComponent<MainComponent> {
    @BindView(R.id.linear_layout_comment)
    LinearLayout mLinearLayoutComment;
    @BindView(R.id.frame_layout_container)
    FrameLayout mFrameLayout;
    @BindView(R.id.empty_layout_error)
    EmptyLayout mEmptyLayout;
    @BindView(R.id.view_pager)
    ViewPager mViewPager;

    public final static String BUNDLE_KEY_PAGE = "EXTRA_PAGE";
    public final static String BUNDLE_KEY_ARGS = "EXTRA_ARGS";
    protected int mPageValue = -1;

    private BaseDetailFragment mDetailFragment;
    private CommentBar mCommentBar;
    private ShareDialog mAlertDialog;
    private TextView mCommentCountView;

    private Long extraBlogId = 0L;
    private String mCommentHint;
    private BaseDetailModel mDetailModel;
    private long mCommentId;
    private long mCommentAuthorId;
    private boolean mInputEmpty = false;

    @Override
    protected int getContentView() {
        return R.layout.activity_detail;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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

        if (TextUtils.isEmpty(mCommentHint))
            mCommentHint = getString(R.string.pub_comment_hint);

        mEmptyLayout.setOnLayoutClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mEmptyLayout.getErrorState() != EmptyLayout.NETWORK_LOADING) {
                    mEmptyLayout.setErrorType(EmptyLayout.NETWORK_LOADING);
                    getDetailPresenter().loadDetail(extraBlogId);
                }
            }
        });

        mCommentBar = CommentBar.delegation(this, mLinearLayoutComment);
        mCommentBar.setCommentHint(mCommentHint);
        mCommentBar.getBottomSheet().getEditText().setHint(mCommentHint);
        if (mDetailModel != null) {
            mCommentBar.setFavDrawable(mDetailModel.getFavorite() == 1 ? R.drawable.ic_faved : R.drawable.ic_fav);
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
                getDetailPresenter().favReverse();
            }
        });

        mCommentBar.setShareListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // test
                toShare("测试数据title", "测试数据title", "http://www.tourin.cn/index.html");
                if (mDetailModel != null) {
//                    toShare(mDetailModel.getTitle(), mDetailModel.getBody(), mDetailModel.getHref());
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
                if (mCommentBar == null) {
                    return;
                }

                mCommentBar.getBottomSheet().dismiss();
                mCommentBar.setCommitButtonEnable(false);
                if (mDetailModel != null) {
//                    getDetailPresenter().addComment(mDetailModel.getId(),
//                            mDetailModel.getType(),
//                            mCommentBar.getBottomSheet().getCommentText(),
//                            0,
//                            mCommentId,
//                            mCommentAuthorId);
                }
            }
        });

        mCommentBar.getBottomSheet().getEditText().setOnKeyArrivedListener(new OnKeyArrivedListenerAdapter(this));

        try {
            mDetailFragment = (BaseDetailFragment) page.getClz().newInstance();

            ViewPagerAdapter mViewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
            mViewPagerAdapter.addFragment(mDetailFragment);
            mViewPager.setAdapter(mViewPagerAdapter);

            Bundle args = data.getBundleExtra(BUNDLE_KEY_ARGS);
            if (args != null) {
                mDetailFragment.setArguments(args);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new IllegalArgumentException(
                    "generate fragment error. by value:" + pageValue);
        }
    }

    private IDetailPresenter getDetailPresenter() {
        return mDetailFragment.getDetailPresenter();
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
//                    CommentsActivity.show(DetailActivity.this, mDetailModel.getId(), mDetailModel.getType(), OSChinaApi.COMMENT_NEW_ORDER, mDetailModel.getTitle());
                    ToastUtils.showToast("CommentsActivity show.");
                }
            });
        }
    }

    public void showErrorLayout(int errorType) {
        mEmptyLayout.setErrorType(errorType);
    }

    public void showGetDetailSuccess(BaseDetailModel model) {
        this.mDetailModel = model;
        if (mCommentBar != null) {
            mCommentBar.setFavDrawable(mDetailModel.getFavorite() == 1 ? R.drawable.ic_faved : R.drawable.ic_fav);
        }
        if (mCommentCountView != null) {
            mCommentCountView.setText(String.valueOf(mDetailModel.getCommentCount()));
        }
    }

    public void showFavReverseSuccess(boolean isFav, int favCount, int strId) {
        if (mCommentBar != null) {
            mCommentBar.setFavDrawable(isFav ? R.drawable.ic_faved : R.drawable.ic_fav);
        }
    }

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
    protected boolean toShare(String title, String content, String url) {

        // test data
        // 分享
        if (mAlertDialog == null) {
            mAlertDialog = new ShareDialog(this, 0L)
                    .type(1)
                    .title(title)
                    .content(content)
                    .imageUrl("http://img.blog.csdn.net/20151123180434692")
                    .url(url).with();
        }
        mAlertDialog.show();

        /*
        if (TextUtils.isEmpty(title) || TextUtils.isEmpty(url) || mDetailModel == null)
            return false;

        String imageUrl = null;
//        List<BlogDetailModel.Image> images = mDetailModel.getImages();
        switch (mDetailModel.getType()) {
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
                    ShareDialog(this, mDetailModel.getId())
                    .type(mDetailModel.getType())
                    .title(title)
                    .content(content)
                    .imageUrl(imageUrl)//如果没有图片，即url为null，直接加入app默认分享icon
                    .url(url).with();
        }
        mAlertDialog.show();
*/
        return true;
    }

    /**
     * backspage 退格
     */
    protected void handleKeyDel() {
//        if (mCommentId != mDetailModel.getId()) {
//            if (TextUtils.isEmpty(mCommentBar.getBottomSheet().getCommentText())) {
//                if (mInputEmpty) {
//                    mCommentId = mDetailModel.getId();
//                    mCommentAuthorId = 0;
//                    mCommentBar.setCommentHint(mCommentHint);
//                    mCommentBar.getBottomSheet().getEditText().setHint(mCommentHint);
//                } else {
//                    mInputEmpty = true;
//                }
//            } else {
//                mInputEmpty = false;
//            }
//        }
    }


    public void setToolBarTitle(String title) {
        this.setActionBarTitle(title);
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
                    if (mDetailModel != null)
                        mCommentCountView.setText(mDetailModel.getCommentCount() + "");
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
            DetailCache.addCache(mDetailModel);
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

    @Override
    public MainComponent getComponent() {
        return DaggerMainComponent.builder()
                .applicationComponent(App.getInstance().getComponent())
                .activityModule(new ActivityModule(this))
                .build();
    }

}