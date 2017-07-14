package live.itrip.app.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.astuetz.PagerSlidingTabStrip;

import butterknife.BindView;
import butterknife.ButterKnife;
import live.itrip.app.R;
import live.itrip.app.adapter.TravelFragmentAdapter;
import live.itrip.app.ui.base.BaseFragment;
import live.itrip.app.ui.fragment.trvel.TravelChildFragment;

/**
 * Created by Feng on 2017/4/25.
 */

public class TravelFragment extends BaseFragment {
    @BindView(R.id.tabs)
    PagerSlidingTabStrip mTabs;
    @BindView(R.id.view_pager)
    ViewPager mViewPager;
    private TravelFragmentAdapter mAdapter;


    private static final Integer[] FRAGMENT_CATEGORY = {
            TravelChildFragment.FRAGMENT_JINGXUAN,
            TravelChildFragment.FRAGMENT_MY
    };

    public static TravelFragment newInstance() {
//        Bundle args = new Bundle();
//        args.putString("msg", text);
        TravelFragment fragment = new TravelFragment();
//        fragment.setArguments(args);
        return fragment;
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_travel, container, false);
        ButterKnife.bind(this, root);
        initViews();

        return root;
    }

    private void initViews() {
        mAdapter = new TravelFragmentAdapter(getChildFragmentManager(), FRAGMENT_CATEGORY);
        mViewPager.setAdapter(mAdapter);
        mViewPager.setOffscreenPageLimit(FRAGMENT_CATEGORY.length);

        mTabs.setViewPager(mViewPager);
    }
}
