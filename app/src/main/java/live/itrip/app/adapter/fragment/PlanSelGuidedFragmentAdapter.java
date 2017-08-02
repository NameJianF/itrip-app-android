package live.itrip.app.adapter.fragment;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import live.itrip.app.ui.fragment.plan.child.SelfGuidedChildFragment;

/**
 * Created by Feng on 2017/7/14.
 */

public class PlanSelGuidedFragmentAdapter extends ArrayFragmentPagerAdapter<Integer> {

    public PlanSelGuidedFragmentAdapter(FragmentManager fm, Integer[] data) {
        super(fm);
        setList(data);
    }

    @Override
    public Fragment getItem(int position) {
        int type = mList.get(position);
        if (type == SelfGuidedChildFragment.CITY_HOT
                || type == SelfGuidedChildFragment.CITY_TOKYO_OSAKA
                || type == SelfGuidedChildFragment.CITY_HOKKAIDO
                || type == SelfGuidedChildFragment.CITY_OTHER) {
            return SelfGuidedChildFragment.newInstance(type);
        } else {
            return null;
        }
    }

    @Override
    public CharSequence getPageTitle(int position) {
        int type = mList.get(position);
        if (type == SelfGuidedChildFragment.CITY_HOT) {
            return "最热门";
        } else if (type == SelfGuidedChildFragment.CITY_TOKYO_OSAKA) {
            return "东京/大阪";
        } else if (type == SelfGuidedChildFragment.CITY_HOKKAIDO) {
            return "北海道";
        } else if (type == SelfGuidedChildFragment.CITY_OTHER) {
            return "其他";
        } else {
            return "";
        }
    }
}
