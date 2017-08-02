package live.itrip.app.ui.fragment.plan.child;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

import java.util.ArrayList;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import live.itrip.app.R;
import live.itrip.app.adapter.PlanCategoryRecyclerAdapter;
import live.itrip.app.data.api.MessageApi;
import live.itrip.app.data.model.PlanCategoryModel;
import live.itrip.app.di.component.MainComponent;
import live.itrip.app.presenter.PlanCategoryPresenter;
import live.itrip.app.ui.base.BaseFragment;
import live.itrip.common.mvp.view.LceView;
import live.itrip.common.util.AppLog;

/**
 * Created by Feng on 2017/6/27.
 * <p>
 * 自由行
 */
public class SelfGuidedChildFragment extends BaseFragment implements LceView<ArrayList<PlanCategoryModel>> {
    @BindView(R.id.refreshLayout)
    SwipeRefreshLayout mRefreshLayout;
    @BindView(R.id.planList)
    RecyclerView mRecyclerView;

    public static final int CITY_HOT = 0;    // 最热门
    public static final int CITY_TOKYO_OSAKA = 1;    // 东京/大阪
    public static final int CITY_HOKKAIDO = 2;    // 北海道
    public static final int CITY_OTHER = 3; // 其他

    @Inject
    PlanCategoryPresenter mPlanCategoryPresenter;
    private PlanCategoryRecyclerAdapter mRecyclerAdapter;
    private int mCurrentCity;
    private static final String EXTRA_CITY = "extra_city";

    public static SelfGuidedChildFragment newInstance(int cityFlag) {
        Bundle args = new Bundle();
        args.putInt(EXTRA_CITY, cityFlag);

        SelfGuidedChildFragment fragment = new SelfGuidedChildFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_plan_self_guided_child, container, false);
        ButterKnife.bind(this, view);
        initViews();
        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getComponent(MainComponent.class).inject(this);

        mCurrentCity = getArguments().getInt(EXTRA_CITY, MessageApi.FLAG_SYSTEM);
        mPlanCategoryPresenter.attachView(this);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mPlanCategoryPresenter.loadPlanSelfGuided(mCurrentCity);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mPlanCategoryPresenter.detachView();
    }

    private void initViews() {
        mRefreshLayout.setOnRefreshListener(mRefreshListener);

        mRecyclerAdapter = new PlanCategoryRecyclerAdapter(null);
        mRecyclerAdapter.setOnRecyclerViewItemClickListener(mItemClickListener);
        mRecyclerAdapter.setEmptyView(LayoutInflater.from(getContext()).inflate(R.layout.empty_view, null));

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this.getActivity()));
        mRecyclerView.addItemDecoration(new HorizontalDividerItemDecoration
                .Builder(this.getActivity())
                .color(Color.TRANSPARENT)
                .size(getResources().getDimensionPixelSize(R.dimen.divider_height))
                .build());

        mRecyclerView.setAdapter(mRecyclerAdapter);

    }

    private BaseQuickAdapter.OnRecyclerViewItemClickListener mItemClickListener = new BaseQuickAdapter.OnRecyclerViewItemClickListener() {
        @Override
        public void onItemClick(View view, int i) {
//            PlanCategoryModel model = mRecyclerAdapter.getItem(i);
//            DialogMessageActivity.launch(getActivity(), msg.getUserFrom(), msg.getUserTo());

            AppLog.d("Recycler View Item Clicked.");
        }
    };

    @Override
    public void showLoading() {
        mRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                mRefreshLayout.setRefreshing(true);
            }
        });
    }

    @Override
    public void dismissLoading() {
        mRefreshLayout.setRefreshing(false);
    }

    @Override
    public void showContent(ArrayList<PlanCategoryModel> data) {
        AppLog.d("data:" + data);
        if (data != null) {
            mRecyclerAdapter.setNewData(data);
        }
    }

    @Override
    public void showError(Throwable e) {
        AppLog.e(e);
    }

    @Override
    public void showEmpty() {
        // TODO
    }

    private SwipeRefreshLayout.OnRefreshListener mRefreshListener = new SwipeRefreshLayout.OnRefreshListener() {
        @Override
        public void onRefresh() {
            AppLog.d("onRefresh, mCurrentCity:" + mCurrentCity);
            mPlanCategoryPresenter.loadPlanSelfGuided(mCurrentCity);
        }
    };
}
