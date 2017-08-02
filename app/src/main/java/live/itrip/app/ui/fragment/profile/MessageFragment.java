package live.itrip.app.ui.fragment.profile;

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
import live.itrip.app.ui.activity.profile.DialogMessageActivity;
import live.itrip.app.ui.base.BaseFragment;
import live.itrip.common.mvp.view.LceView;
import live.itrip.common.util.AppLog;

/**
 * Created by Feng on 2017/6/27.
 */

public class MessageFragment extends BaseFragment implements LceView<ArrayList<MessageModel>> {
    @BindView(R.id.mesage_list)
    RecyclerView mRecyclerView;
    @BindView(R.id.refresh_layout)
    SwipeRefreshLayout mRefreshLayout;

    public static final Integer MESSAGE_SELF = 0;
    public static final Integer MESSAGE_SYSTEM = 1;

    @Inject
    MessagePresenter mMessagePresenter;


    private MessageRecyclerAdapter mMessageRecyclerAdapter;

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
        mMessagePresenter.attachView(this);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Long uid = 0L;
        int page = 1;
        int pageSize = 30;
        Long lastMsgId = 0L;

        mMessagePresenter.loadMesages(mCurrentMessage, uid, page, pageSize, lastMsgId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mMessagePresenter.detachView();
    }

    private void initViews() {
        mRefreshLayout.setOnRefreshListener(mRefreshListener);

        mMessageRecyclerAdapter = new MessageRecyclerAdapter(null);
        mMessageRecyclerAdapter.setOnRecyclerViewItemClickListener(mItemClickListener);
        mMessageRecyclerAdapter.setEmptyView(LayoutInflater.from(getContext()).inflate(R.layout.empty_view, null));

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this.getActivity()));
        mRecyclerView.addItemDecoration(new HorizontalDividerItemDecoration
                .Builder(this.getActivity())
                .color(Color.TRANSPARENT)
                .size(getResources().getDimensionPixelSize(R.dimen.divider_height))
                .build());

        mRecyclerView.setAdapter(mMessageRecyclerAdapter);

    }

    private BaseQuickAdapter.OnRecyclerViewItemClickListener mItemClickListener = new BaseQuickAdapter.OnRecyclerViewItemClickListener() {
        @Override
        public void onItemClick(View view, int i) {
            MessageModel msg = mMessageRecyclerAdapter.getItem(i);

            msg.setUserFrom(11L);
            msg.setUserTo(1L);

            DialogMessageActivity.launch(getActivity(), msg.getUserFrom(), msg.getUserTo());
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
    public void showContent(ArrayList<MessageModel> data) {
        AppLog.d("data:" + data);
        if (data != null) {
            mMessageRecyclerAdapter.setNewData(data);
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
            AppLog.d("onRefresh, mCurrentMessage:" + mCurrentMessage);
            Long uid = 0L;
            int page = 1;
            int pageSize = 30;
            Long lastMsgId = 0L;

            mMessagePresenter.loadMesages(mCurrentMessage, uid, page, pageSize, lastMsgId);
        }
    };
}
