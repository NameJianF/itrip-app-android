package live.itrip.app.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import live.itrip.app.data.api.MessageApi;
import live.itrip.app.ui.fragment.common.MessageFragment;
import live.itrip.app.ui.fragment.trvel.TravelChildFragment;

/**
 * Created by Feng on 2017/7/14.
 */

public class TravelFragmentAdapter extends ArrayFragmentPagerAdapter<Integer> {

    public TravelFragmentAdapter(FragmentManager fm, Integer[] data) {
        super(fm);
        setList(data);
    }

    @Override
    public Fragment getItem(int position) {
        int type = mList.get(position);
        if (type == TravelChildFragment.FRAGMENT_JINGXUAN) {
            return MessageFragment.newInstance(MessageApi.FLAG_SYSTEM);
        } else if (type == TravelChildFragment.FRAGMENT_MY) {
            return MessageFragment.newInstance(MessageApi.FLAG_USER);
        } else {
            return null;
        }
    }

    @Override
    public CharSequence getPageTitle(int position) {
        int type = mList.get(position);
        if (type == TravelChildFragment.FRAGMENT_JINGXUAN) {
            return "精选";
        } else if (type == TravelChildFragment.FRAGMENT_MY) {
            return "我的行程";
        } else {
            return "";
        }
    }
}
