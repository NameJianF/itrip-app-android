package live.itrip.app.presenter;

import java.util.ArrayList;

import javax.inject.Inject;

import live.itrip.app.data.api.MessageApi;
import live.itrip.app.data.model.MessageModel;
import live.itrip.app.data.observer.ResponseObserver;
import live.itrip.app.presenter.base.RxMvpPresenter;
import live.itrip.common.mvp.view.LceView;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.schedulers.Schedulers;

/**
 * Created by Feng on 2017/6/27.
 */
public class MessagePresenter extends RxMvpPresenter<LceView<ArrayList<MessageModel>>> {

    private final MessageApi mMessageApi;

    @Inject
    public MessagePresenter(MessageApi api) {
        this.mMessageApi = api;
    }

    public void loadMesages(@MessageApi.MessageType int type) {
        mCompositeSubscription.add(mMessageApi.getMessages(type)
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
                .subscribe(new ResponseObserver<ArrayList<MessageModel>>() {
                    @Override
                    public void onSuccess(ArrayList<MessageModel> msgList) {
                        getMvpView().showContent(msgList);
                    }

                    @Override
                    public void onError(Throwable e) {
                        getMvpView().showError(e);
                    }
                }));
    }
}
