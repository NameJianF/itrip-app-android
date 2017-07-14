package live.itrip.app.adapter;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.Arrays;
import java.util.List;

/**
 * Created by Feng on 2017/7/14.
 */

public abstract class ArrayFragmentPagerAdapter<T> extends FragmentPagerAdapter {

    protected List<T> mList;

    public ArrayFragmentPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public int getCount() {
        return mList != null ? mList.size() : 0;
    }

    public void setList(List<T> list){
        this.mList = list;
        notifyDataSetChanged();
    }

    public List<T> getList(){
        return mList;
    }

    public void setList(T[] list){
        setList(Arrays.asList(list));
    }
}