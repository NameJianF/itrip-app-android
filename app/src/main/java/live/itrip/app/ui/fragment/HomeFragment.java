package live.itrip.app.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import live.itrip.app.R;
import live.itrip.app.ui.base.BaseFragment;

/**
 * Created by Feng on 2017/4/25.
 */

public class HomeFragment extends BaseFragment {

    public static HomeFragment newInstance(){
//        Bundle args = new Bundle();
//        args.putString("msg", text);
        HomeFragment fragment = new HomeFragment();
//        fragment.setArguments(args);
        return fragment;
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_home, container, false);

        return root;
    }
}
