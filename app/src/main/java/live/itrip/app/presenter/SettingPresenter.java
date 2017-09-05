package live.itrip.app.presenter;

import org.json.JSONException;

import javax.inject.Inject;

import live.itrip.app.cache.SharePreferenceData;
import live.itrip.app.data.api.SettingApi;
import live.itrip.app.data.model.UpdateModel;
import live.itrip.app.data.net.response.ResultResp;
import live.itrip.app.data.observer.ResponseObserver;
import live.itrip.app.presenter.base.RxMvpPresenter;
import live.itrip.app.ui.view.mvp.LogoutView;
import live.itrip.common.util.AppLog;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.schedulers.Schedulers;

/**
 * Created by Feng on 2017/6/28.
 */

public class SettingPresenter extends RxMvpPresenter<LogoutView<UpdateModel>> {
    private final SettingApi mSettingApi;

    @Inject
    public SettingPresenter(SettingApi api) {
        this.mSettingApi = api;
    }


    public void checkAppVersion() {
        mCompositeSubscription.add(mSettingApi.checkAppVersion()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        getMvpView().showLoading();
                    }
                })
                .doOnTerminate(new Action0() {
                    @Override
                    public void call() {
                        getMvpView().dismissLoading();
                    }
                })
                .subscribe(new ResponseObserver<UpdateModel>() {
                    @Override
                    public void onSuccess(UpdateModel model) {
                        getMvpView().showContent(model);
                    }

                    @Override
                    public void onError(Throwable e) {
                        AppLog.e(e);
                        getMvpView().showError(e);
                    }
                }));
    }


    public void logout(final String username, String token) throws JSONException {
        mCompositeSubscription.add(mSettingApi.logout(username, token)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        getMvpView().showLoading();
                    }
                })
                .doOnTerminate(new Action0() {
                    @Override
                    public void call() {
                        getMvpView().dismissLoading();
                    }
                })
                .subscribe(new ResponseObserver<ResultResp>() {
                    @Override
                    public void onSuccess(ResultResp resp) {
                        if (resp != null) {
                            // 成功
                            getMvpView().logoutSuccess();
//                            if (resp.getCode() == 0) {
//                                // 成功
//                                getMvpView().logoutSuccess();
//                            } else {
//                                // 失败
//                                getMvpView().logoutFailed();
//                            }
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        AppLog.e(e);
                        getMvpView().showError(e);
                    }
                }));
    }
}
