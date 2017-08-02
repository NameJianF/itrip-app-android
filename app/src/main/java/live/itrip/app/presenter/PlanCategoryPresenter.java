package live.itrip.app.presenter;

import java.util.ArrayList;

import javax.inject.Inject;

import live.itrip.app.data.api.PlanCategoryApi;
import live.itrip.app.data.model.PlanCategoryModel;
import live.itrip.app.data.observer.ResponseObserver;
import live.itrip.app.presenter.base.RxMvpPresenter;
import live.itrip.common.mvp.view.LceView;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.schedulers.Schedulers;

/**
 * Created by Feng on 2017/7/12.
 */

public class PlanCategoryPresenter extends RxMvpPresenter<LceView<ArrayList<PlanCategoryModel>>> {
    private final PlanCategoryApi mPlanCategoryApi;

    @Inject
    public PlanCategoryPresenter(PlanCategoryApi api) {
        this.mPlanCategoryApi = api;
    }


    /**
     *
     */
    public void loadHomePageDatas() {
        mCompositeSubscription.add(mPlanCategoryApi.loadHomePageDatas()
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
                .subscribe(new ResponseObserver<ArrayList<PlanCategoryModel>>() {
                    @Override
                    public void onSuccess(ArrayList<PlanCategoryModel> list) {
                        getMvpView().showContent(list);
                    }

                    @Override
                    public void onError(Throwable e) {
                        getMvpView().showError(e);
                    }
                }));
    }


    /**
     * 加载自由行页面信息
     *
     * @param mCurrentCity
     */
    public void loadPlanSelfGuided(int mCurrentCity) {
        mCompositeSubscription.add(mPlanCategoryApi.loadCategoryPlans(PlanCategoryApi.FLAG_GROUP_TRAVEL, mCurrentCity)
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
                .subscribe(new ResponseObserver<ArrayList<PlanCategoryModel>>() {
                    @Override
                    public void onSuccess(ArrayList<PlanCategoryModel> list) {
                        getMvpView().showContent(list);
                    }

                    @Override
                    public void onError(Throwable e) {
                        getMvpView().showError(e);
                    }
                }));
    }
}
