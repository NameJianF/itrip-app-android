package live.itrip.app.ui.fragment.profile;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import live.itrip.app.App;
import live.itrip.app.R;
import live.itrip.app.data.PreferenceData;
import live.itrip.app.ui.base.BaseFragment;
import live.itrip.app.ui.util.UIUtils;
import live.itrip.common.util.AppLog;

/**
 * Created by Feng on 2017/6/20.
 */

public class AboutFragment extends BaseFragment {
    @BindView(R.id.tv_version_name)
    TextView mTvVersionName;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        AppLog.d("AboutFragment ===> onCreateView");
        View root = inflater.inflate(R.layout.fragment_about, container, false);
        ButterKnife.bind(this, root);
        initData();
        return root;
    }

    public void initData() {
        mTvVersionName.setText(App.getInstance().getVersion());
    }


    @OnClick({
            R.id.tv_site, R.id.tv_knowmore
    })
    public void OnClick(View v) {
        final int id = v.getId();
        switch (id) {
            case R.id.tv_site:
                UIUtils.openInternalBrowser(getActivity(), "http://www.itrip.live");
                break;
            case R.id.tv_knowmore:
                UIUtils.openInternalBrowser(getActivity(), "http://www.itrip.live/about.html");
                break;
            default:
                break;
        }

    }

    @Override
    public void onResume() {
        super.onResume();
    }
}
