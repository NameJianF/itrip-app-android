package live.itrip.app.presenter.account;

import android.app.Application;

import org.json.JSONException;

import javax.inject.Inject;

import live.itrip.app.data.PreferenceData;
import live.itrip.app.data.api.AccountApi;
import live.itrip.app.data.model.UserModel;
import live.itrip.app.data.observer.ResponseObserver;
import live.itrip.app.presenter.base.RxMvpPresenter;
import live.itrip.app.ui.activity.account.view.LoginView;
import live.itrip.common.util.AppLog;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.schedulers.Schedulers;

/**
 * Created by Feng on 2017/4/26.
 */

public class LoginPresenter extends RxMvpPresenter<LoginView> {

    private final AccountApi mAccountApi;

    @Inject
    public LoginPresenter(AccountApi api) {
        this.mAccountApi = api;
    }

    @Inject
    Application mContext;

    public void login(String username, String password) throws JSONException {
        mCompositeSubscription.add(mAccountApi.login(username, password)
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
                .subscribe(new ResponseObserver<UserModel>() {
                    @Override
                    public void onSuccess(UserModel user) {
                        if (user != null) {
                            // save user
                            PreferenceData.Account.saveLogonUser(mContext, user);

                            // save token
                            PreferenceData.Account.saveLoginToken(mContext, user.getToken());
                            AppLog.d("user:" + user.getToken());

                            getMvpView().loginSuccess(user);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        AppLog.e(e);
                        getMvpView().error(e);
                    }
                }));
    }
}