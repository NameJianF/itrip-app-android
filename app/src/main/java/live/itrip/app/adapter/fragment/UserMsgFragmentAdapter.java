package live.itrip.app.adapter.fragment;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import live.itrip.app.data.api.MessageApi;
import live.itrip.app.ui.fragment.profile.MessageFragment;

/**
 * Created by Feng on 2017/7/14.
 */

public class UserMsgFragmentAdapter extends ArrayFragmentPagerAdapter<Integer> {

    public UserMsgFragmentAdapter(FragmentManager fm, Integer[] data) {
        super(fm);
        setList(data);
    }

    @Override
    public Fragment getItem(int position) {
        int type = mList.get(position);
        if (type == MessageFragment.MESSAGE_SELF) {
            return MessageFragment.newInstance(MessageApi.FLAG_SYSTEM);
        } else if (type == MessageFragment.MESSAGE_SYSTEM) {
            return MessageFragment.newInstance(MessageApi.FLAG_USER);
        } else {
            return null;
        }
    }

    @Override
    public CharSequence getPageTitle(int position) {
        int type = mList.get(position);
        if (type == MessageFragment.MESSAGE_SELF) {
            return "私信";
        } else if (type == MessageFragment.MESSAGE_SYSTEM) {
            return "系统消息";
        } else {
            return "";
        }
    }
}
