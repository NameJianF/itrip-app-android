package live.itrip.app.presenter.blog;

import javax.inject.Inject;

import live.itrip.app.data.api.BlogDetailApi;
import live.itrip.app.data.model.BlogDetailModel;
import live.itrip.app.data.observer.ResponseObserver;
import live.itrip.app.presenter.base.RxMvpPresenter;
import live.itrip.app.presenter.interfaces.IDetailPresenter;
import live.itrip.common.mvp.view.LceView;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.schedulers.Schedulers;

/**
 * Created by Feng on 2017/7/24.
 */

public class BlogDetailPresenter extends RxMvpPresenter<LceView<BlogDetailModel>> implements IDetailPresenter {
    private final BlogDetailApi mBlogDetailApi;

    @Inject
    public BlogDetailPresenter(BlogDetailApi api) {
        this.mBlogDetailApi = api;
    }


    /**
     * 关注
     *
     * @param userId
     */
    public void addUserRelation(Long userId) {

    }

    @Override
    public void loadDetail(Long itemId) {
        mCompositeSubscription.add(mBlogDetailApi.getBlogDetail(itemId)
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
                .subscribe(new ResponseObserver<BlogDetailModel>() {
                    @Override
                    public void onSuccess(BlogDetailModel blog) {
                        getMvpView().showContent(blog);
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
