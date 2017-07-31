package live.itrip.app.ui.fragment.app;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;
import live.itrip.app.R;
import live.itrip.app.data.PreferenceData;
import live.itrip.app.data.model.UserModel;
import live.itrip.app.ui.activity.SimpleBackActivity;
import live.itrip.app.ui.base.BaseFragment;
import live.itrip.common.util.AppLog;

/**
 * Created by Feng on 2017/6/22.
 */

public class UserInforDetailFragment extends BaseFragment {
//    @BindView(R.id.iv_avatar)
//    PortraitView mUserFace;
//
//    @BindView(R.id.tv_name)
//    TextView mName;
//
//    @BindView(R.id.tv_join_time)
//    TextView mJoinTime;
//
//    @BindView(R.id.tv_location)
//    TextView mFrom;
//
//    @BindView(R.id.tv_academic_focus)
//    TextView mFocus;
//
//    @BindView(R.id.tv_desc)
//    TextView mDesc;

    private UserModel userInfo;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle arguments = getArguments();
        if (arguments != null && arguments.getSerializable("user_info") != null) {
            userInfo = (UserModel) arguments.getSerializable("user_info");
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        AppLog.d("UserInforDetailFragment ===> onCreateView");
        View root = inflater.inflate(R.layout.fragment_user_infor_detail, container, false);
        ButterKnife.bind(this, root);

        initData();
        return root;
    }

    public void initData() {
        if (userInfo == null)
            return;

        if (userInfo.getId() != PreferenceData.Account.getUserId()
                && getActivity() instanceof SimpleBackActivity) {
            String title = TextUtils.isEmpty(userInfo.getUserName()) ? "" : userInfo.getUserName();
            ((SimpleBackActivity) getActivity()).setActionBarTitle(title);
        }

        //  sendRequiredData();
        fillUI();
    }

    public void fillUI() {
//        mUserFace.setup(userInfo);
//        mUserFace.setOnClickListener(null);
//        mName.setText(getText(userInfo.getUserName()));
//        mJoinTime.setText(getText(StringUtils.formatYearMonthDayNew(userInfo.getMore().getJoinDate())));
//        mFrom.setText(getText(userInfo.getMore().getCity()));
//        mFocus.setText(getText(userInfo.getMore().getExpertise()));
//        mDesc.setText(getText(userInfo.getDesc()));
    }

    private String getText(String text) {
        if (text == null || text.equalsIgnoreCase("null"))
            return "<无>";
        else return text;
    }

    @Override
    public void onResume() {
        super.onResume();
    }
}
