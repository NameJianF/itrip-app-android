package live.itrip.app.ui.fragment.profile;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.FileProvider;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;

import java.io.File;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import live.itrip.app.App;
import live.itrip.app.R;
import live.itrip.app.cache.DataCacheManager;
import live.itrip.app.cache.SharePreferenceData;
import live.itrip.app.data.model.UpdateModel;
import live.itrip.app.di.component.MainComponent;
import live.itrip.app.presenter.SettingPresenter;
import live.itrip.app.ui.activity.profile.FeedBackActivity;
import live.itrip.app.ui.base.BaseFragment;
import live.itrip.app.util.AppUtils;
import live.itrip.app.util.DialogUtils;
import live.itrip.app.util.ToastUtils;
import live.itrip.app.util.UIUtils;
import live.itrip.app.ui.view.mvp.SettingsView;
import live.itrip.common.files.download.DownLoadHelper;
import live.itrip.common.files.download.interfaces.IdownLoadProgress;
import live.itrip.common.util.AppLog;

/**
 * 系统设置界面
 *
 * @author
 */
public class SettingsFragment extends BaseFragment implements SettingsView<UpdateModel> {

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
        boolean login = SharePreferenceData.Account.isLogon(App.getContext());

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
        String cacheSize = "0KB";
        cacheSize = DataCacheManager.getCacheSizeString(getContext());
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
//                if (!SharePreferenceData.Account.checkLogon(this.getContext())) {
//                    return;
//                }
                FeedBackActivity.launch(getActivity());
                break;
            case R.id.rl_about:
                UIUtils.showAbout(getActivity());
                break;
            case R.id.rl_check_version:
                // 版本更新检测
                mPresenter.checkAppVersion();
                break;
            case R.id.rl_cancel:
                // 注销登陆
                doLogout();
                break;
            default:
                break;
        }
    }

    private void doLogout() {
        // 清理所有缓存
        AppUtils.clearAppCache(false);
        // 注销操作
        try {
            mPresenter.logout(SharePreferenceData.Account.getLogonUser(getContext()).getEmail()
                    , SharePreferenceData.Account.getLogonToken(getContext()));
        } catch (JSONException e) {
            AppLog.e(e);
        }
    }

    /**
     * 清理所有缓存
     */
    private void onClickCleanCache() {
        DialogUtils.getConfirmDialog(getActivity(), getString(R.string.is_clear_cache), new DialogInterface.OnClickListener
                () {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                AppUtils.clearAppCache(true);
                mTvCacheSize.setText("0KB");
            }
        }).show();
    }


    @Override
    public void logoutSuccess() {
        // 注销成功
        // remove user
        SharePreferenceData.Account.removeLogonUser(getContext());

        //getActivity().finish();
        mTvCacheSize.setText(getString(R.string.cache_size_zero));
        mCancel.setVisibility(View.INVISIBLE);
        mSettingLineTop.setVisibility(View.INVISIBLE);
        mSettingLineBottom.setVisibility(View.INVISIBLE);
        ToastUtils.showToast(getString(R.string.logout_success));
    }

    @Override
    public void logoutFailed() {
        // 注销失败
        ToastUtils.showToast(getString(R.string.logout_success));
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void dismissLoading() {

    }

    @Override
    public void showContent(final UpdateModel model) {
        DialogUtils.getConfirmDialog(getActivity(), "更新提示", model.getDesc(), "现在更新", "稍后更新", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                AppLog.d("现在更新...");
                downloadApk(model);
            }
        }).show();
    }

    private void downloadApk(UpdateModel model) {
        final ProgressDialog progressDialog = DialogUtils.getProgressDialog(SettingsFragment.this.getActivity(), "下载文件", "Loading...", false);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);// 设置水平进度条
        progressDialog.show();

        DownLoadHelper downLoadHelper = new DownLoadHelper();
//        String url = "http://cdn.other.file.testin.cn/95efac1d70bf4edea5c67b85e8a5167b.apk";
        final String savePath = App.getContext().getApplicationInfo().dataDir + File.separator;
        final String fileName = "itrip-app.apk";
        downLoadHelper.downLoad(model.getDownloadUrl(), savePath, fileName, new IdownLoadProgress() {
            @Override
            public void onProgress(long progress, long total, boolean done) {
                progressDialog.setProgress((int) (progress * 100 / total));
            }

            @Override
            public void onSucess(String result) {
                Toast.makeText(getActivity(), "download success !", Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();

                // install apk
                installApk(savePath, fileName);
            }

            @Override
            public void onFailed(Throwable e, String reason) {
                Toast.makeText(getActivity(), "download failed !", Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        });
    }

    private void installApk(String savePath, String fileName) {
        File file = new File(savePath + File.separator + fileName);
        if (!file.exists()) {
            Log.e("==", "apk file no exists.");
            return;
        }
        Intent intent = new Intent(Intent.ACTION_VIEW);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            Uri contentUri = FileProvider.getUriForFile(App.getContext(), "live.itrip.app.provider", file);
            intent.setDataAndType(contentUri, "application/vnd.android.package-archive");
        } else {
            intent.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        }
        startActivity(intent);
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
