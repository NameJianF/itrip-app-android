package live.itrip.app.ui.fragment.plan;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.NestedScrollView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import live.itrip.app.R;
import live.itrip.app.bean.Recommend;
import live.itrip.app.data.model.ChildMultiItem;
import live.itrip.app.data.model.PlanDetailModel;
import live.itrip.app.di.component.MainComponent;
import live.itrip.app.presenter.interfaces.IDetailPresenter;
import live.itrip.app.presenter.plan.PlanDetailPresenter;
import live.itrip.app.ui.activity.DetailActivity;
import live.itrip.app.ui.base.BaseDetailFragment;
import live.itrip.app.util.PicassoImageLoader;
import live.itrip.app.util.ToastUtils;
import live.itrip.app.ui.view.mvp.DetailView;
import live.itrip.app.ui.widget.DetailRecommendView;
import live.itrip.app.ui.widget.EmptyLayout;
import live.itrip.app.ui.widget.ExtendWebView;
import live.itrip.common.util.AppLog;
import live.itrip.common.util.StringUtils;

/**
 * Created by Feng on 2017/7/24.
 */

public class PlanDetailFragment extends BaseDetailFragment implements DetailView<PlanDetailModel> {
    @BindView(R.id.image_view_plan)
    ImageView mImageViewPlan;     // 顶端大图

    @BindView(R.id.image_view_recommend)
    ImageView mImageRecommend;
    @BindView(R.id.tv_title)
    TextView mTextTitle;
    @BindView(R.id.linear_layout_share)
    LinearLayout mLinearLayoutShare;   // 分享

    @BindView(R.id.text_view_price)
    TextView mTextViewPrice;   // 价格
    @BindView(R.id.text_view_participate)
    TextView mTextViewParticipate;   // 销售数量

    @BindView(R.id.lay_nsv)
    NestedScrollView mViewScroller;
    @BindView(R.id.extend_web_view)
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
        mDetailRecommendView.setTitle("相关行程");

        mLinearLayoutShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastUtils.showToast("LinearLayout Share Clicked.");
            }
        });

    }

    private DetailActivity getDetailActivity(){
        return (DetailActivity) getActivity();
    }

    @Override
    public void showLoading() {
        this.getDetailActivity().showErrorLayout(EmptyLayout.NETWORK_LOADING);
    }

    @Override
    public void dismissLoading() {
        this.getDetailActivity().showErrorLayout(EmptyLayout.HIDE_LAYOUT);
    }

    @Override
    public void showRecommendSuccess(ArrayList<ChildMultiItem> data) {
        AppLog.d("data:" + JSON.toJSONString(data));
        if (data != null) {
            List<Recommend> recommendList = new ArrayList<>();
            for (ChildMultiItem item : data) {
                Recommend recommend = new Recommend();
                recommend.setId(item.getId());
                recommend.setTitle(item.getTitle());
                recommend.setSubTitle(item.getSubTitle());
                recommend.setImageUrl(item.getImageUrl());
                recommend.setPrice(item.getPrice());
                recommend.setParticipate(item.getParticipate());

//                recommend.getCommentCount();
                recommendList.add(recommend);
            }
            mDetailRecommendView.setRecommendModels(recommendList, DetailRecommendView.ViewType.PLAN);
        }
    }

    @Override
    public void showFavReverseSuccess(boolean isFav, int favCount, int strId) {
        this.getDetailActivity().showFavReverseSuccess(isFav,favCount,strId);
    }

    @Override
    public void showFavError() {

    }

    @Override
    public void showAddCommentSuccess() {
//        this.getDetailActivity().showCommentSuccess();
    }

    @Override
    public void showDetailContent(PlanDetailModel data) {
        this.mPlanDetail = data;

        if (this.mPlanDetail != null) {
            // set activity
            this.getDetailActivity().showGetDetailSuccess(data);

            PicassoImageLoader.getInstance().showImage(this.getContext(), this.mPlanDetail.getTitleImage(), mImageViewPlan);
            if ("1".equals(this.mPlanDetail.getRecommend())) {
                mImageRecommend.setVisibility(View.VISIBLE);
            } else {
                mImageRecommend.setVisibility(View.GONE);
            }
            mTextTitle.setText(this.mPlanDetail.getTitle());
            mTextViewPrice.setText(StringUtils.trimNewLine(String.format("¥ %s", this.mPlanDetail.getPrice())));
            mTextViewParticipate.setText(StringUtils.trimNewLine(String.format("销售%s笔", this.mPlanDetail.getParticipate())));

            mExtendWebView.getSettings().setJavaScriptEnabled(true);  //设置使用够执行JS脚本
            mExtendWebView.getSettings().setBuiltInZoomControls(true);//设置使支持缩放

//            mExtendWebView.setWebViewClient(new WebViewClient() {
//                @Override
//                public boolean shouldOverrideUrlLoading(WebView view, String url) {
//                    view.loadUrl(url);
//                    return true;
//                }
//            });
//            mExtendWebView.setWebViewClient(new WebViewClient() {
//                public void onPageFinished(WebView view, String url) {
//                    mViewScroller.scrollTo(0, 0);
//                }
//            });
            mExtendWebView.loadUrl(this.mPlanDetail.getHref());

            DetailActivity activity = (DetailActivity) getActivity();
            activity.getEmptyLayout().setErrorType(EmptyLayout.HIDE_LAYOUT);
            activity.getEmptyLayout().setVisibility(View.GONE);

            // 加载相关推荐
            mPlanDetailPresenter.loadRecommendList(mPlanDetail.getType());
        }
    }


    @Override
    public void showError(Throwable e) {
        this.getDetailActivity().showErrorLayout(EmptyLayout.NETWORK_ERROR);
    }

    @Override
    public void showEmpty() {
        this.getDetailActivity().getEmptyLayout().setErrorType(EmptyLayout.NODATA);
    }


    @Override
    public IDetailPresenter getDetailPresenter() {
        return this.mPlanDetailPresenter;
    }

}
