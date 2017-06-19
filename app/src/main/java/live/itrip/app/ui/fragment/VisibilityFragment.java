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

public class VisibilityFragment extends BaseFragment {

    public static VisibilityFragment newInstance(){
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

        return root;
    }
}
