package live.itrip.app.presenter;

import android.app.Application;

import java.util.ArrayList;

import javax.inject.Inject;

import live.itrip.app.data.api.RecyclerItemListApi;
import live.itrip.app.data.model.RecyclerViewItem;
import live.itrip.app.data.observer.ResponseObserver;
import live.itrip.app.presenter.base.RxMvpPresenter;
import live.itrip.common.mvp.view.LceView;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.schedulers.Schedulers;

/**
 * Created by Feng on 2017/6/26.
 */

public class RecyclerViewPresenter extends RxMvpPresenter<LceView<ArrayList<RecyclerViewItem>>> {

    private final RecyclerItemListApi mRecyclerItemListApi;

    @Inject
    public RecyclerViewPresenter(RecyclerItemListApi api) {
        this.mRecyclerItemListApi = api;
    }

    public void loadItemList(@RecyclerItemListApi.RecyclerItemType int type, Integer page) {
        Observable<ArrayList<RecyclerViewItem>> observable = this.mRecyclerItemListApi.getItemList(type, page);

        if (observable == null) {
            return;
        }

        mCompositeSubscription.add(observable
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
                .subscribe(new ResponseObserver<ArrayList<RecyclerViewItem>>() {

                    @Override
                    public void onError(Throwable e) {
                        getMvpView().showError(e);
                    }

                    @Override
                    public void onSuccess(ArrayList<RecyclerViewItem> items) {
                        getMvpView().showContent(items);
                    }
                }));
    }
}
