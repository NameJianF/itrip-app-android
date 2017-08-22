package live.itrip.app.presenter.plan;

import java.util.ArrayList;

import javax.inject.Inject;

import live.itrip.app.data.api.PlanDetailApi;
import live.itrip.app.data.model.ChildMultiItem;
import live.itrip.app.data.model.PlanDetailModel;
import live.itrip.app.data.observer.ResponseObserver;
import live.itrip.app.presenter.base.RxMvpPresenter;
import live.itrip.app.presenter.interfaces.IDetailPresenter;
import live.itrip.app.ui.view.mvp.DetailView;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.schedulers.Schedulers;

/**
 * Created by Feng on 2017/7/24.
 */

public class PlanDetailPresenter extends RxMvpPresenter<DetailView<PlanDetailModel>> implements IDetailPresenter {
    private final PlanDetailApi mPlanDetailApi;

    @Inject
    public PlanDetailPresenter(PlanDetailApi api) {
        this.mPlanDetailApi = api;
    }


    @Override
    public void loadDetail(Long itemId) {
        mCompositeSubscription.add(mPlanDetailApi.getPlanDetail(itemId)
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
                .subscribe(new ResponseObserver<PlanDetailModel>() {
                    @Override
                    public void onSuccess(PlanDetailModel planDetail) {
                        getMvpView().showDetailContent(planDetail);
                    }

                    @Override
                    public void onError(Throwable e) {
                        getMvpView().showError(e);
                    }
                }));
    }

    /**
     * 加载相关推荐
     *
     * @param category
     */
    public void loadRecommendList(String category) {
        mCompositeSubscription.add(mPlanDetailApi.loadRecommendList(category)
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
                .subscribe(new ResponseObserver<ArrayList<ChildMultiItem>>() {
                    @Override
                    public void onSuccess(ArrayList<ChildMultiItem> list) {
                        getMvpView().showRecommendSuccess(list);
                    }

                    @Override
                    public void onError(Throwable e) {
                        getMvpView().showError(e);
                    }
                }));

    }

    /**
     * 收藏/取消收藏
     */
    public void favReverse() {

    }

    /**
     * 添加评论
     *
     * @param id
     * @param type
     * @param commentText
     * @param i
     * @param mCommentId
     * @param mCommentAuthorId
     */
    public void addComment(Long id, int type, String commentText, int i, long mCommentId, long mCommentAuthorId) {

    }


}
