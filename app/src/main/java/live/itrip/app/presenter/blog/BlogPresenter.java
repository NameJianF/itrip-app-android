package live.itrip.app.presenter.blog;

import javax.inject.Inject;

import live.itrip.app.data.api.BlogApi;
import live.itrip.app.data.model.BlogModel;
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

public class BlogPresenter extends RxMvpPresenter<LceView<BlogModel>> implements IDetailPresenter {
    private final BlogApi mBlogApi;

    @Inject
    public BlogPresenter(BlogApi api) {
        this.mBlogApi = api;
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
        mCompositeSubscription.add(mBlogApi.getBlogDetail(itemId)
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
                .subscribe(new ResponseObserver<BlogModel>() {
                    @Override
                    public void onSuccess(BlogModel blog) {
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
