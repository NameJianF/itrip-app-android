package live.itrip.app.presenter.base;

import live.itrip.common.mvp.presenter.BaseMvpPresenter;
import live.itrip.common.mvp.view.MvpView;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by Feng on 2017/4/26.
 */

public class RxMvpPresenter<V extends MvpView> extends BaseMvpPresenter<V> {

    protected CompositeSubscription mCompositeSubscription;

    @Override
    public void attachView(V mvpView) {
        super.attachView(mvpView);

        mCompositeSubscription = new CompositeSubscription();
    }

    @Override
    public void detachView() {
        super.detachView();

        mCompositeSubscription.clear();
        mCompositeSubscription = null;
    }
}