package live.itrip.app.ui.fragment.plan;

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
import live.itrip.app.adapter.fragment.PlanSelGuidedFragmentAdapter;
import live.itrip.app.ui.base.BaseFragment;
import live.itrip.app.ui.fragment.plan.child.SelfGuidedChildFragment;

/**
 * Created by Feng on 2017/6/27.
 * <p>
 * 自由行
 */
public class PlanSelfGuidedFragment extends BaseFragment {
    @BindView(R.id.tabs)
    PagerSlidingTabStrip mTabs;
    @BindView(R.id.viewPager)
    ViewPager mViewPager;

    private static final Integer[] FRAGMENT_CATEGORY = {
            SelfGuidedChildFragment.CITY_HOT,
            SelfGuidedChildFragment.CITY_TOKYO_OSAKA,
            SelfGuidedChildFragment.CITY_HOKKAIDO,
            SelfGuidedChildFragment.CITY_OTHER
    };


    public static PlanSelfGuidedFragment newInstance() {
        return new PlanSelfGuidedFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_plan_self_guided, container, false);
        ButterKnife.bind(this, view);
        initViews();
        return view;
    }

    private void initViews() {
        PlanSelGuidedFragmentAdapter mFragmentAdapter = new PlanSelGuidedFragmentAdapter(getChildFragmentManager(), FRAGMENT_CATEGORY);
        mViewPager.setAdapter(mFragmentAdapter);
        mViewPager.setOffscreenPageLimit(FRAGMENT_CATEGORY.length);

        mTabs.setViewPager(mViewPager);
    }

}
