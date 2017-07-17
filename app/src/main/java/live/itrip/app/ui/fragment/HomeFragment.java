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

import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

import java.util.ArrayList;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import live.itrip.app.R;
import live.itrip.app.adapter.HomePageRecyclerAdapter;
import live.itrip.app.data.model.HomePageModel;
import live.itrip.app.di.component.MainComponent;
import live.itrip.app.presenter.HomePagePresenter;
import live.itrip.app.ui.base.BaseFragment;
import live.itrip.app.ui.util.DatasConvertUtils;
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

public class HomeFragment extends BaseFragment implements LceView<ArrayList<HomePageModel>> {

    @BindView(R.id.refresh_layout)
    SwipeRefreshLayout mRefreshLayout;

    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;

    @Inject
    HomePagePresenter mHomePagePresenter;
    private HomePageRecyclerAdapter mHomePageRecyclerAdapter;


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

        mHomePagePresenter.attachView(this);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

//        mHomePagePresenter.loadDatas();

        this.showContent(DatasConvertUtils.converHomePageDatas());
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mHomePagePresenter.detachView();
    }

    private void initViews() {
        mRefreshLayout.setOnRefreshListener(mRefreshListener);

        mHomePageRecyclerAdapter = new HomePageRecyclerAdapter(null);
//        mHomePageRecyclerAdapter.setOnRecyclerViewItemClickListener(mItemClickListener);
        mHomePageRecyclerAdapter.setEmptyView(LayoutInflater.from(getContext()).inflate(R.layout.empty_view, null));


        mRecyclerView.setLayoutManager(new LinearLayoutManager(this.getActivity()));
        mRecyclerView.addItemDecoration(new HorizontalDividerItemDecoration
                .Builder(this.getActivity())
                .color(Color.TRANSPARENT)
                .size(getResources().getDimensionPixelSize(R.dimen.divider_height))
                .build());

        mRecyclerView.setAdapter(mHomePageRecyclerAdapter);

    }

//    private BaseMultiItemQuickAdapter.OnRecyclerViewItemClickListener mItemClickListener = new BaseMultiItemQuickAdapter.OnRecyclerViewItemClickListener() {
//        @Override
//        public void onItemClick(View view, int i) {
//            HomePageModel model = (HomePageModel) mHomePageRecyclerAdapter.getItem(i);
//
////            DialogMessageActivity.launch(getActivity(), msg.getUserFrom(), msg.getUserTo());
//            AppLog.d("Recycler View Item Clicked.");
//        }
//    };

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
    public void showContent(ArrayList<HomePageModel> data) {
        AppLog.d("data:" + data);
        if (data != null) {
            //
            mHomePageRecyclerAdapter.setNewData(data);
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
//            mHomePagePresenter.loadDatas();
        }
    };
}
