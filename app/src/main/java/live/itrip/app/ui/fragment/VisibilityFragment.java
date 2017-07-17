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
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

import java.util.ArrayList;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import live.itrip.app.R;
import live.itrip.app.adapter.VisibilityPageRecyclerAdapter;
import live.itrip.app.config.Constants;
import live.itrip.app.data.model.ChildMultiItem;
import live.itrip.app.data.model.VisibilityPageModel;
import live.itrip.app.data.model.VisibilityPageModel;
import live.itrip.app.di.component.MainComponent;
import live.itrip.app.presenter.VisibilityPagePresenter;
import live.itrip.app.ui.base.BaseFragment;
import live.itrip.common.mvp.view.LceView;
import live.itrip.common.util.AppLog;

/**
 * Created by Feng on 2017/4/25.
 */

public class VisibilityFragment extends BaseFragment implements LceView<ArrayList<VisibilityPageModel>> {
    @BindView(R.id.refresh_layout)
    SwipeRefreshLayout mRefreshLayout;

    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;

    @Inject
    VisibilityPagePresenter mVisibilityPagePresenter;
    private VisibilityPageRecyclerAdapter mVisibilityPageRecyclerAdapter;


    public static VisibilityFragment newInstance() {
//        Bundle args = new Bundle();
//        args.putString("msg", text);
        VisibilityFragment fragment = new VisibilityFragment();
//        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_visibility, container, false);
        ButterKnife.bind(this, root);
        initViews();
        return root;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getComponent(MainComponent.class).inject(this);

        mVisibilityPagePresenter.attachView(this);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

//        mVisibilityPagePresenter.loadDatas();

        // test data
        ArrayList<VisibilityPageModel> list = new ArrayList<>();
        for (int i = 0; i < 6; i++) {
            VisibilityPageModel model = new VisibilityPageModel();

            if (i == 0) {
                model.setItemType(VisibilityPageModel.ITEM_NAV);
            } else if (i == 1) {
                model.setItemType(VisibilityPageModel.ITEM_HOT);
            } else if (i == 2) {
                model.setItemType(VisibilityPageModel.ITEM_CATEGORY);

                ArrayList<ChildMultiItem> items = new ArrayList<>();
                for (int j = 0; j < 4; j++) {
                    ChildMultiItem bean = new ChildMultiItem();
                    bean.setId(j + 1L);
                    bean.setImageUrl(Constants.mBannerUrls[j]);
                    bean.setTitle(Constants.mBannerNames[j]);
                    bean.setItemType(ChildMultiItem.ITEM_PLAN);
                    items.add(bean);
                }
                model.setItemList(items);
            } else {
                model.setItemType(VisibilityPageModel.ITEM_AD);
            }

            model.setImgUrl(Constants.mUrls[i]);
            model.setTitle(Constants.mBannerNames[i]);
            model.setContent(Constants.mSubTitles[i]);
            list.add(model);
        }

        System.err.println(JSON.toJSONString(list));
        this.showContent(list);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mVisibilityPagePresenter.detachView();
    }

    private void initViews() {
        mRefreshLayout.setOnRefreshListener(mRefreshListener);

        mVisibilityPageRecyclerAdapter = new VisibilityPageRecyclerAdapter(null);
        mVisibilityPageRecyclerAdapter.setEmptyView(LayoutInflater.from(getContext()).inflate(R.layout.empty_view, null));


        mRecyclerView.setLayoutManager(new LinearLayoutManager(this.getActivity()));
        mRecyclerView.addItemDecoration(new HorizontalDividerItemDecoration
                .Builder(this.getActivity())
                .color(Color.TRANSPARENT)
                .size(getResources().getDimensionPixelSize(R.dimen.divider_height))
                .build());

        mRecyclerView.setAdapter(mVisibilityPageRecyclerAdapter);

    }

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
    public void showContent(ArrayList<VisibilityPageModel> data) {
        AppLog.d("data:" + data);
        if (data != null) {
            //
            mVisibilityPageRecyclerAdapter.setNewData(data);
            mRecyclerView.scrollToPosition(0);
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
            AppLog.d("onRefresh, VisibilityFragement.");
            mVisibilityPagePresenter.loadDatas();
        }
    };
}
