package live.itrip.app.presenter;

import java.util.ArrayList;

import javax.inject.Inject;

import live.itrip.app.data.api.HomePageApi;
import live.itrip.app.data.model.HomePageModel;
import live.itrip.app.data.model.MessageModel;
import live.itrip.app.data.observer.ResponseObserver;
import live.itrip.app.presenter.base.RxMvpPresenter;
import live.itrip.common.mvp.view.LceView;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.schedulers.Schedulers;

/**
 * Created by Feng on 2017/7/12.
 */

public class HomePagePresenter extends RxMvpPresenter<LceView<ArrayList<HomePageModel>>> {
    private final HomePageApi mHomePageApi;

    @Inject
    public HomePagePresenter(HomePageApi api) {
        this.mHomePageApi = api;
    }


    public void loadDatas() {
        mCompositeSubscription.add(mHomePageApi.loadDatas()
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
                .subscribe(new ResponseObserver<ArrayList<HomePageModel>>() {
                    @Override
                    public void onSuccess(ArrayList<HomePageModel> list) {
                        getMvpView().showContent(list);
                    }

                    @Override
                    public void onError(Throwable e) {
                        getMvpView().showError(e);
                    }
                }));
    }
}
