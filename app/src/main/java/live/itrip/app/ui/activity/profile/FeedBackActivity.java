package live.itrip.app.ui.activity.profile;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import org.json.JSONException;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import live.itrip.app.App;
import live.itrip.app.R;
import live.itrip.app.di.HasComponent;
import live.itrip.app.di.component.DaggerFeedBackComponent;
import live.itrip.app.di.component.FeedBackComponent;
import live.itrip.app.di.module.ActivityModule;
import live.itrip.app.di.module.FeedBackModule;
import live.itrip.app.presenter.FeedBackPresenter;
import live.itrip.app.ui.base.BaseActivity;
import live.itrip.app.ui.view.mvp.FeedBackView;
import live.itrip.app.util.ToastUtils;
import live.itrip.common.util.AppLog;

/**
 * Created on 2017/6/20.
 *
 * @author JianF
 */

public class FeedBackActivity extends BaseActivity implements FeedBackView, HasComponent<FeedBackComponent> {

    @BindView(R.id.button_commit)
    Button mButtonSubmit;

    @Inject
    FeedBackPresenter mFeedBackPresenter;

    public static void launch(Context context) {
        context.startActivity(new Intent(context, FeedBackActivity.class));
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_feed_back;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getComponent().inject(this);
        ButterKnife.bind(this);
        mFeedBackPresenter.attachView(this);
        initViews();
        AppLog.d("trace===FeedBackActivity onCreate, end");
    }

    @Override
    protected void onResume() {
        super.onResume();
        AppLog.d("trace===FeedBackActivity onResume");
    }

    private void initViews() {
        this.setActionBarTitle(getString(R.string.feedback));
    }


    @OnClick({R.id.button_commit})
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.button_commit:
                submitFeedBackMessages();
                break;
            default:
                break;
        }

    }

    private void submitFeedBackMessages() {
        try {
            mFeedBackPresenter.submitFeedBackMessages(1, "aaaaaa", null, "fjf789");
        } catch (JSONException e) {
            AppLog.e(e);
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }


    @Override
    public void showLoading() {

    }

    @Override
    public void dismissLoading() {

    }

    @Override
    public void error(Throwable e) {
        AppLog.e(e);
    }

    @Override
    public void onSuccess(int code, String msg) {
        if (code == 0) {
            ToastUtils.showToast("提交成功");
            finish();
        }
    }


    @Override
    public FeedBackComponent getComponent() {
        return DaggerFeedBackComponent.builder()
                .applicationComponent(App.getInstance().getComponent())
                .activityModule(new ActivityModule(this))
                .feedBackModule(new FeedBackModule())
                .build();
    }
}
