package live.itrip.app.presenter.account;

import android.app.Application;

import org.json.JSONException;

import javax.inject.Inject;

import live.itrip.app.cache.SharePreferenceData;
import live.itrip.app.data.api.AccountApi;
import live.itrip.app.data.model.UserExpandModel;
import live.itrip.app.data.model.UserModel;
import live.itrip.app.data.net.response.ResultResp;
import live.itrip.app.data.observer.ResponseObserver;
import live.itrip.app.presenter.base.RxMvpPresenter;
import live.itrip.app.ui.view.mvp.AccountView;
import live.itrip.common.util.AppLog;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.schedulers.Schedulers;

/**
 * Created by Feng on 2017/4/26.
 */

public class AccountPresenter extends RxMvpPresenter<AccountView> {

    private final AccountApi mAccountApi;

    @Inject
    public AccountPresenter(AccountApi api) {
        this.mAccountApi = api;
    }

    @Inject
    Application mContext;

    /**
     * 普通登录
     *
     * @param username
     * @param password
     * @throws JSONException
     */
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
                        onLoginSuccess(user, true);
                    }

                    @Override
                    public void onError(Throwable e) {
                        AppLog.e(e);
                        getMvpView().error(e);
                    }
                }));
    }

    /**
     * 获取用户详细信息
     *
     * @param username
     * @param token
     * @throws JSONException
     */
    public void getUserExpandInfo(String username, String token) throws JSONException {
        mCompositeSubscription.add(mAccountApi.getUserExpandInfo(username, token)
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
                .subscribe(new ResponseObserver<UserExpandModel>() {
                    @Override
                    public void onSuccess(UserExpandModel userExpandModel) {
                        if (userExpandModel != null) {
                            // save user expand info
                            SharePreferenceData.Account.saveLogonUserExpandInfo(mContext, userExpandModel);
//                            getMvpView().getUserExpandInfoSuccess(userExpandModel);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        AppLog.e(e);
//                        getMvpView().getUserExpandInfoSuccess(null);
                    }
                }));
    }

    /**
     * 第三方登录
     *
     * @param catalog
     * @param openInfo
     * @throws JSONException
     */
    public void openLogin(int catalog, String openInfo) throws JSONException {
        mCompositeSubscription.add(mAccountApi.openLogin(catalog, openInfo)
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
                        onLoginSuccess(user, true);
                    }

                    @Override
                    public void onError(Throwable e) {
                        AppLog.e(e);
                    }
                }));
    }

    private void onLoginSuccess(final UserModel user, boolean loadExpands) {
        if (user != null) {
            // save user
            SharePreferenceData.Account.saveLogonUser(mContext, user);

            // save token
            SharePreferenceData.Account.saveLoginToken(mContext, user.getToken());
            AppLog.d("user:" + user.getToken());

            getMvpView().onSuccess(0, "");

            if (loadExpands) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            // 登录成功，获取用户数据
                            getUserExpandInfo(user.getEmail(), user.getToken());
                        } catch (JSONException e) {
                            AppLog.e(e.getMessage(), e);
                        }
                    }
                }).start();
            }
        }
    }


    /**
     * 验证发送的手机验证码
     *
     * @param phoneNumber
     * @param smsCode
     */
    public void validateSmsCode(String phoneNumber, String smsCode) throws JSONException {
        mCompositeSubscription.add(mAccountApi.validateSmsCode(phoneNumber, smsCode)
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
                        // test
                        getMvpView().onSuccess(0, "Success");
//                        getMvpView().onSuccess(resp.getCode(), resp.getMsg());
                    }

                    @Override
                    public void onError(Throwable e) {
                        AppLog.e(e);
                    }
                }));
    }

    /**
     * 获取手机验证码
     *
     * @param phoneNumber
     * @param resetPwdIntent
     */
    public void sendSmsCode(String phoneNumber, int resetPwdIntent) throws JSONException {
        mCompositeSubscription.add(mAccountApi.sendSmsCode(phoneNumber, resetPwdIntent)
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
                        // test
                        getMvpView().onSuccess(0, "Success");

//                        getMvpView().onSuccess(resp.getCode(), resp.getMsg());
                    }

                    @Override
                    public void onError(Throwable e) {
                        AppLog.e(e);
                    }
                }));
    }

    public void resetPwd(String newPwd, String phoneToken) {
        mCompositeSubscription.add(mAccountApi.resetPwd(newPwd, phoneToken)
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
                        // test
                        getMvpView().onSuccess(0, "Success");

//                        getMvpView().onSuccess(resp.getCode(), resp.getMsg());
                    }

                    @Override
                    public void onError(Throwable e) {
                        AppLog.e(e);
                    }
                }));
    }

    public void register(String username, String pwd, int sex, String phoneToken) {
        mCompositeSubscription.add(mAccountApi.register(username, pwd, sex, phoneToken)
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
                    public void onSuccess(UserModel userModel) {
                        // test
                        getMvpView().onSuccess(0, "Success");

//                        onLoginSuccess(userModel, false);
                    }

                    @Override
                    public void onError(Throwable e) {
                        AppLog.e(e);
                    }
                }));
    }
}