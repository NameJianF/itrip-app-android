package live.itrip.app.presenter;

import java.util.ArrayList;

import javax.inject.Inject;

import live.itrip.app.data.api.VisibilityPageApi;
import live.itrip.app.data.model.VisibilityPageModel;
import live.itrip.app.data.observer.ResponseObserver;
import live.itrip.app.presenter.base.RxMvpPresenter;
import live.itrip.common.mvp.view.LceView;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.schedulers.Schedulers;

/**
 * Created by Feng on 2017/7/12.
 */

public class VisibilityPagePresenter extends RxMvpPresenter<LceView<ArrayList<VisibilityPageModel>>> {
    private final VisibilityPageApi mVisibilityPageApi;

    @Inject
    public VisibilityPagePresenter(VisibilityPageApi api) {
        this.mVisibilityPageApi = api;
    }


    public void loadDatas() {
        mCompositeSubscription.add(mVisibilityPageApi.loadDatas()
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
                .subscribe(new ResponseObserver<ArrayList<VisibilityPageModel>>() {
                    @Override
                    public void onSuccess(ArrayList<VisibilityPageModel> list) {
                        getMvpView().showContent(list);
                    }

                    @Override
                    public void onError(Throwable e) {
                        getMvpView().showError(e);
                    }
                }));
    }
}
