package live.itrip.app.presenter;

import org.json.JSONException;

import java.util.List;

import javax.inject.Inject;

import live.itrip.app.data.api.FeedBackApi;
import live.itrip.app.data.net.response.ResultResp;
import live.itrip.app.data.observer.ResponseObserver;
import live.itrip.app.presenter.base.RxMvpPresenter;
import live.itrip.app.ui.view.mvp.FeedBackView;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.schedulers.Schedulers;

/**
 * Created by Feng on 2017/9/6.
 */

public class FeedBackPresenter extends RxMvpPresenter<FeedBackView> {

    private final FeedBackApi mFeedBackApi;

    @Inject
    public FeedBackPresenter(FeedBackApi api) {
        this.mFeedBackApi = api;
    }

    public void submitFeedBackMessages(int feedType, String message, List<String> imageUrlList, String userName) throws JSONException {

        mCompositeSubscription.add(mFeedBackApi.submitFeedBackMessages(feedType, message, imageUrlList, userName)
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
                        // success
                        getMvpView().onSuccess(resp.getCode(),resp.getMsg());
                    }

                    @Override
                    public void onError(Throwable e) {
                        getMvpView().error(e);
                    }
                }));
    }
}
