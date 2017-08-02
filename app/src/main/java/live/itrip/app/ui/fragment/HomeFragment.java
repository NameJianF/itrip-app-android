package live.itrip.app.ui.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alibaba.fastjson.JSON;
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

import java.util.ArrayList;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import live.itrip.app.R;
import live.itrip.app.adapter.PlanCategoryRecyclerAdapter;
import live.itrip.app.data.model.PlanCategoryModel;
import live.itrip.app.di.component.MainComponent;
import live.itrip.app.presenter.PlanCategoryPresenter;
import live.itrip.app.ui.base.BaseFragment;
import live.itrip.common.mvp.view.LceView;
import live.itrip.common.util.AppLog;


/**
 * Created by Feng on 2017/4/25.
 * 1. banner
 * 2. nav  内置导航
 * 3. new plan  最新行程
 * 4. hot 热门行程
 * 5. list 猜你喜欢
 * 6. bolgs 热门博客
 */

public class HomeFragment extends BaseFragment implements LceView<ArrayList<PlanCategoryModel>> {

    @BindView(R.id.refresh_layout)
    SwipeRefreshLayout mRefreshLayout;

    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;

    @Inject
    PlanCategoryPresenter mPlanCategoryPresenter;
    private PlanCategoryRecyclerAdapter mPlanCategoryRecyclerAdapter;


    public static HomeFragment newInstance() {
        return new HomeFragment();
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        ButterKnife.bind(this, root);
        initViews();
        return root;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getComponent(MainComponent.class).inject(this);

        mPlanCategoryPresenter.attachView(this);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // load datas
        mPlanCategoryPresenter.loadHomePageDatas();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mPlanCategoryPresenter.detachView();
    }

    private void initViews() {
        mRefreshLayout.setOnRefreshListener(mRefreshListener);

        mPlanCategoryRecyclerAdapter = new PlanCategoryRecyclerAdapter(null);
        mPlanCategoryRecyclerAdapter.setOnRecyclerViewItemClickListener(mItemClickListener);
        mPlanCategoryRecyclerAdapter.setEmptyView(LayoutInflater.from(getContext()).inflate(R.layout.empty_view, null));
        mPlanCategoryRecyclerAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                AppLog.d("PlanCategoryRecyclerAdapter onLoadMoreRequested.");
            }
        });

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this.getActivity()));
        mRecyclerView.addItemDecoration(new HorizontalDividerItemDecoration
                .Builder(this.getActivity())
                .color(Color.TRANSPARENT)
                .size(getResources().getDimensionPixelSize(R.dimen.divider_height))
                .build());


        mRecyclerView.setAdapter(mPlanCategoryRecyclerAdapter);

    }

    private BaseMultiItemQuickAdapter.OnRecyclerViewItemClickListener mItemClickListener = new BaseMultiItemQuickAdapter.OnRecyclerViewItemClickListener() {
        @Override
        public void onItemClick(View view, int i) {
            PlanCategoryModel model = (PlanCategoryModel) mPlanCategoryRecyclerAdapter.getItem(i);

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
        AppLog.d("data:" + JSON.toJSONString(data));
        if (data != null) {
            mPlanCategoryRecyclerAdapter.setNewData(data);
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
            AppLog.d("onRefresh, HomeFragement.");
            mPlanCategoryPresenter.loadHomePageDatas();
        }
    };
}
