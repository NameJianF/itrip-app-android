package live.itrip.app.ui.fragment.app;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import java.io.File;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import live.itrip.app.App;
import live.itrip.app.R;
import live.itrip.app.data.PreferenceData;
import live.itrip.app.data.model.UpdateModel;
import live.itrip.app.di.component.MainComponent;
import live.itrip.app.presenter.SettingPresenter;
import live.itrip.app.ui.activity.FeedBackActivity;
import live.itrip.app.ui.base.BaseFragment;
import live.itrip.app.ui.util.DialogUtils;
import live.itrip.app.ui.util.UIUtils;
import live.itrip.common.mvp.view.LceView;
import live.itrip.common.util.AppLog;
import live.itrip.common.util.FileUtils;

/**
 * 系统设置界面
 *
 * @author
 */
public class SettingsFragment extends BaseFragment implements LceView<UpdateModel> {

    private static final int RC_EXTERNAL_STORAGE = 0x04;//存储权限


    // 缓存
    @BindView(R.id.tv_cache_size)
    TextView mTvCacheSize;
    // 检查更新
    @BindView(R.id.rl_check_version)
    FrameLayout mRlCheck_version;
    @BindView(R.id.setting_line_top)
    View mSettingLineTop;
    @BindView(R.id.setting_line_bottom)
    View mSettingLineBottom;
    // 注销
    @BindView(R.id.rl_cancel)
    FrameLayout mCancel;

//    private Version mVersion;

    @Inject
    SettingPresenter mPresenter;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        AppLog.d("SettingsFragment ===> onCreateView");
        View root = inflater.inflate(R.layout.fragment_settings, container, false);
        ButterKnife.bind(this, root);

        return root;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getComponent(MainComponent.class).inject(this);

        mPresenter.attachView(this);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initData();
    }

    public void initData() {
        calculateCacheSize();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mPresenter.detachView();
    }


    @Override
    public void onResume() {
        super.onResume();
        boolean login = PreferenceData.Account.isLogon(App.getContext());

        if (!login) {
            mCancel.setVisibility(View.INVISIBLE);
            mSettingLineTop.setVisibility(View.INVISIBLE);
            mSettingLineBottom.setVisibility(View.INVISIBLE);
        } else {
            mCancel.setVisibility(View.VISIBLE);
            mSettingLineTop.setVisibility(View.VISIBLE);
            mSettingLineBottom.setVisibility(View.VISIBLE);
        }
    }

    /**
     * 计算缓存的大小
     */
    private void calculateCacheSize() {
        long fileSize = 0;
        String cacheSize = "0KB";
        File filesDir = getActivity().getFilesDir();
        File cacheDir = getActivity().getCacheDir();

        fileSize += FileUtils.getDirSize(filesDir);
        fileSize += FileUtils.getDirSize(cacheDir);
        // 2.2版本才有将应用缓存转移到sd卡的功能
//        if (AppContext.isMethodsCompat(android.os.Build.VERSION_CODES.FROYO)) {
//            File externalCacheDir = MethodsCompat
//                    .getExternalCacheDir(getActivity());
//            fileSize += FileUtils.getDirSize(externalCacheDir);
//        }
        if (fileSize > 0)
            cacheSize = FileUtils.formatFileSize(fileSize);
        mTvCacheSize.setText(cacheSize);
    }


    @OnClick({
            R.id.rl_clean_cache, R.id.rl_feedback, R.id.rl_about, R.id.rl_check_version, R.id.rl_cancel
    })
    public void OnClick(View v) {
        final int id = v.getId();
        switch (id) {
            case R.id.rl_clean_cache:
                onClickCleanCache();
                break;
            case R.id.rl_feedback:
                if (!PreferenceData.Account.checkLogon(this.getContext())) {
                    return;
                }
                FeedBackActivity.launch(getActivity());
                break;
            case R.id.rl_about:
                UIUtils.showAbout(getActivity());
                break;
            case R.id.rl_check_version:
                mPresenter.checkAppVersion();
                break;
            case R.id.rl_cancel:
                // 清理所有缓存
                UIUtils.clearAppCache(false);
                // 注销操作
//                AccountUtils.logout(mCancel, new Runnable() {
//                    @SuppressLint("SetTextI18n")
//                    @Override
//                    public void run() {
//                        //getActivity().finish();
//                        mTvCacheSize.setText("0KB");
//                        AppContext.showToastShort(getString(R.string.logout_success_hint));
//                        mCancel.setVisibility(View.INVISIBLE);
//                        mSettingLineTop.setVisibility(View.INVISIBLE);
//                        mSettingLineBottom.setVisibility(View.INVISIBLE);
//                    }
//                });
                break;
            default:
                break;
        }
    }

    private void onClickCleanCache() {
        DialogUtils.getConfirmDialog(getActivity(), "是否清空缓存?", new DialogInterface.OnClickListener
                () {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                UIUtils.clearAppCache(true);
//                mTvCacheSize.setText("0KB");
            }
        }).show();
    }


    @Override
    public void showLoading() {

    }

    @Override
    public void dismissLoading() {

    }

    @Override
    public void showContent(UpdateModel data) {
        // success
        DialogUtils.getConfirmDialog(getActivity(), "更新提示", data.getDesc(), "现在更新", "稍后更新", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                AppLog.d("现在更新...");
            }
        }).show();
    }

    @Override
    public void showError(Throwable e) {
        AppLog.e(e);
    }

    @Override
    public void showEmpty() {
        // TODO
    }
}
