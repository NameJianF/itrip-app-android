package live.itrip.app.ui.fragment.plan;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.NestedScrollView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import live.itrip.app.R;
import live.itrip.app.data.model.PlanDetailModel;
import live.itrip.app.di.component.MainComponent;
import live.itrip.app.presenter.interfaces.IDetailPresenter;
import live.itrip.app.presenter.plan.PlanDetailPresenter;
import live.itrip.app.ui.activity.DetailActivity;
import live.itrip.app.ui.base.BaseDetailFragment;
import live.itrip.app.ui.util.ToastUtils;
import live.itrip.app.ui.widget.DetailRecommendView;
import live.itrip.app.ui.widget.EmptyLayout;
import live.itrip.app.ui.widget.ExtendWebView;
import live.itrip.app.ui.widget.IdentityView;
import live.itrip.common.mvp.view.LceView;

/**
 * Created by Feng on 2017/7/24.
 */

public class PlanDetailFragment extends BaseDetailFragment implements LceView<PlanDetailModel> {
    @BindView(R.id.iv_label_today)
    ImageView mImageToday;
    @BindView(R.id.iv_label_recommend)
    ImageView mImageRecommend;
    @BindView(R.id.iv_label_originate)
    ImageView mImageOriginate;
    @BindView(R.id.iv_label_reprint)
    ImageView mImageReprint;
    @BindView(R.id.iv_avatar)
    ImageView mImageAvatar;
    @BindView(R.id.identityView)
    IdentityView mIdentityView;
    @BindView(R.id.tv_name)
    TextView mTextName;
    @BindView(R.id.tv_pub_date)
    TextView mTextPubDate;
    @BindView(R.id.tv_title)
    TextView mTextTitle;
    @BindView(R.id.tv_detail_abstract)
    TextView mTextAbstract;
    @BindView(R.id.btn_relation)
    Button mBtnRelation;
    @BindView(R.id.lay_nsv)
    NestedScrollView mViewScroller;
    @BindView(R.id.webView)
    ExtendWebView mExtendWebView;
    @BindView(R.id.lay_detail_recommend)
    DetailRecommendView mDetailRecommendView;

    public static final String EXTRA_PLAN_ID = "extra_plan_id";
    private PlanDetailModel mPlanDetail;
    private Long planId;

    @Inject
    PlanDetailPresenter mPlanDetailPresenter;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_plan_detail, container, false);
        ButterKnife.bind(this, root);
        initViews();
        return root;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getComponent(MainComponent.class).inject(this);

        // get plan id
        planId = getArguments().getLong(EXTRA_PLAN_ID, 0L);
        mPlanDetailPresenter.attachView(this);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mPlanDetailPresenter.loadDetail(planId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mPlanDetailPresenter.detachView();
    }

    private void initViews() {
        // set title
        ((DetailActivity) getActivity()).setToolBarTitle("行程详情");

        mBtnRelation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mPlanDetail.getAuthor() != null) {
                    mPlanDetailPresenter.addUserRelation(mPlanDetail.getAuthor().getId());
                }
            }
        });
        mDetailRecommendView.setTitle("相关文章");
        mImageAvatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mPlanDetail != null && mPlanDetail.getAuthor() != null) {
//                    OtherUserHomeActivity.show(mContext, mPlanDetail.getAuthor());
                    ToastUtils.showToast("Image Avatar Clicked.");
                }
            }
        });
    }

    @Override
    public void showLoading() {
        DetailActivity activity = (DetailActivity) getActivity();
        activity.getEmptyLayout().setErrorType(EmptyLayout.NETWORK_LOADING);
    }

    @Override
    public void dismissLoading() {
        DetailActivity activity = (DetailActivity) getActivity();
        activity.getEmptyLayout().setErrorType(EmptyLayout.HIDE_LAYOUT);
    }

    @Override
    public void showContent(PlanDetailModel data) {
        // test data
        mImageToday.setVisibility(View.VISIBLE);
        mImageRecommend.setVisibility(View.VISIBLE);
        mImageOriginate.setVisibility(View.VISIBLE);
        mImageReprint.setVisibility(View.VISIBLE);

        mImageAvatar.setImageResource(R.mipmap.logo);
        mIdentityView.setText("iTrip.live");
        mTextName.setText("iTrip 管理员");
        mTextPubDate.setText("发表于1天前 (2017-07-24 20:12)");
        mTextTitle.setText("Webpack从零开始");
        mTextAbstract.setText("在Google IO大会中不仅仅带来了Android Studio 2.2预览版，同时带给我们一个依赖约束的库。 简单来说，她是相对布局的升级版本，但是区别与相对布局更加强调约束。何为约束，即控件之间的关系。");

        mExtendWebView.getSettings().setJavaScriptEnabled(true);//设置使用够执行JS脚本
        mExtendWebView.getSettings().setBuiltInZoomControls(true);//设置使支持缩放
        mExtendWebView.loadUrl("http://tourin.cn/view/product/21.html");
        mExtendWebView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });

        DetailActivity activity = (DetailActivity) getActivity();
        activity.getEmptyLayout().setErrorType(EmptyLayout.HIDE_LAYOUT);
        activity.getEmptyLayout().setVisibility(View.GONE);
    }

    @Override
    public void showError(Throwable e) {
        DetailActivity activity = (DetailActivity) getActivity();
        activity.getEmptyLayout().setErrorType(EmptyLayout.NETWORK_ERROR);
    }

    @Override
    public void showEmpty() {
        DetailActivity activity = (DetailActivity) getActivity();
        activity.getEmptyLayout().setErrorType(EmptyLayout.NODATA);
    }

    @Override
    public IDetailPresenter getDetailPresenter() {
        return this.mPlanDetailPresenter;
    }
}