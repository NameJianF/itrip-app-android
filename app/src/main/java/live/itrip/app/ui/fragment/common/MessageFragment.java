package live.itrip.app.ui.fragment.common;

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
import live.itrip.app.adapter.MessageRecyclerAdapter;
import live.itrip.app.data.api.MessageApi;
import live.itrip.app.data.model.MessageModel;
import live.itrip.app.di.component.MainComponent;
import live.itrip.app.presenter.MessagePresenter;
import live.itrip.app.ui.activity.MessageDetailActivity;
import live.itrip.app.ui.base.BaseFragment;
import live.itrip.common.mvp.view.LceView;
import live.itrip.common.util.AppLog;
import live.itrip.common.util.StringUtils;

/**
 * Created by Feng on 2017/6/27.
 */

public class MessageFragment extends BaseFragment implements LceView<ArrayList<MessageModel>> {
    @BindView(R.id.mesage_list)
    RecyclerView mRecyclerView;

    @Inject
    MessagePresenter mPresenter;

    private MessageRecyclerAdapter mAdapter;

    private int mCurrentMessage;

    private static final String EXTRA_MSG = "extra_msg";

    public static MessageFragment newInstance(int msgFlag) {

        Bundle args = new Bundle();
        args.putInt(EXTRA_MSG, msgFlag);

        MessageFragment fragment = new MessageFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_messages, null);
        ButterKnife.bind(this, view);
        initViews();
        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getComponent(MainComponent.class).inject(this);

        mCurrentMessage = getArguments().getInt(EXTRA_MSG, MessageApi.FLAG_SYSTEM);
        mPresenter.attachView(this);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Long uid = 0L;
        int page = 1;
        int pageSize = 30;
        Long lastMsgId = 0L;

        mPresenter.loadMesages(mCurrentMessage, uid, page, pageSize, lastMsgId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mPresenter.detachView();
    }

    private void initViews() {
        mAdapter = new MessageRecyclerAdapter(null);
        mAdapter.setOnRecyclerViewItemClickListener(mItemClickListener);
        mAdapter.setEmptyView(LayoutInflater.from(getContext()).inflate(R.layout.empty_view, null));

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this.getActivity()));
        mRecyclerView.addItemDecoration(new HorizontalDividerItemDecoration
                .Builder(this.getActivity())
                .color(Color.TRANSPARENT)
                .size(getResources().getDimensionPixelSize(R.dimen.divider_height))
                .build());

        mRecyclerView.setAdapter(mAdapter);

    }

    private BaseQuickAdapter.OnRecyclerViewItemClickListener mItemClickListener = new BaseQuickAdapter.OnRecyclerViewItemClickListener() {
        @Override
        public void onItemClick(View view, int i) {
            MessageModel msg = mAdapter.getItem(i);

//            MessageDetailActivity.launch(getActivity(), msg.getId());
            AppLog.d("Recycler View Item Clicked.");
        }
    };

    @Override
    public void showLoading() {
    }

    @Override
    public void dismissLoading() {
    }

    @Override
    public void showContent(ArrayList<MessageModel> data) {
        AppLog.d("data:" + data);
        if (data != null) {
            mAdapter.setNewData(data);
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
}
