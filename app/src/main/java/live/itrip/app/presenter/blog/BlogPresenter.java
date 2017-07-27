package live.itrip.app.presenter.blog;

import javax.inject.Inject;

import live.itrip.app.data.api.BlogApi;
import live.itrip.app.data.model.BlogModel;
import live.itrip.app.data.observer.ResponseObserver;
import live.itrip.app.presenter.base.RxMvpPresenter;
import live.itrip.common.mvp.view.LceView;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.schedulers.Schedulers;

/**
 * Created by Feng on 2017/7/24.
 */

public class BlogPresenter extends RxMvpPresenter<LceView<BlogModel>> {
    private final BlogApi mBlogApi;

    @Inject
    public BlogPresenter(BlogApi api) {
        this.mBlogApi = api;
    }

    public void loadBlogDetail(Long blogId) {
        mCompositeSubscription.add(mBlogApi.getBlogDetail(blogId)
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

    public void addUserRelation(int id) {

    }

    /**
     * 收藏/取消收藏
     */
    public void favReverse() {


    }

    public void addComment(Long id, int type, String commentText, int i, long mCommentId, long mCommentAuthorId) {

    }
}
