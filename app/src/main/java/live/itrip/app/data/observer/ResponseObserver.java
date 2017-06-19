package live.itrip.app.data.observer;

import live.itrip.common.util.AppLog;
import rx.Subscriber;

/**
 * Created by Feng on 2017/4/26.
 */

public abstract class ResponseObserver<T> extends Subscriber<T> {
    @Override
    public void onCompleted() {
        AppLog.d("onCompleted");
    }

    @Override
    public void onNext(T t) {
        AppLog.d("onNext");
        onSuccess(t);
    }

    public abstract void onSuccess(T t);
}

