package live.itrip.app.presenter;

import java.util.ArrayList;

import javax.inject.Inject;

import live.itrip.app.data.api.CommonModelApi;
import live.itrip.app.data.model.CommonModel;
import live.itrip.app.data.observer.ResponseObserver;
import live.itrip.app.presenter.base.RxMvpPresenter;
import live.itrip.common.mvp.view.LceView;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.schedulers.Schedulers;

/**
 * Created by Feng on 2017/6/22.
 */

public class CommonModelListPresenter extends RxMvpPresenter<LceView<ArrayList<CommonModel>>> {

    private final CommonModelApi mCommonModelApi;

    @Inject
    public CommonModelListPresenter(CommonModelApi api) {
        this.mCommonModelApi = api;
    }

    public void loadCommonModels(Integer userid, @CommonModelApi.CommonModelType int type) {
        Observable<ArrayList<CommonModel>> observable = null;

        switch (type) {
            case CommonModelApi.DUBBLES:
                observable = mCommonModelApi.getDubbles(userid);
                break;

//            case CommonModelApi.STARRED_REPOS:
//                if (isSelf) {
//                    observable = mCommonModelApi.getMyStarredRepos();
//                } else {
//                    observable = mCommonModelApi.getUserStarredRepos(username);
//                }
//                break;
//
//            case CommonModelApi.ORG_REPOS:
//                // TODO, not support now.
//                break;

            default:
                break;
        }

        if (observable == null) return;

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
                .subscribe(new ResponseObserver<ArrayList<CommonModel>>() {

                    @Override
                    public void onError(Throwable e) {
                        getMvpView().showError(e);
                    }

                    @Override
                    public void onSuccess(ArrayList<CommonModel> repos) {
                        getMvpView().showContent(repos);
                    }
                }));
    }
}
