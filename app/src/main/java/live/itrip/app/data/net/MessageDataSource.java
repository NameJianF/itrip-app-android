package live.itrip.app.data.net;

import java.util.ArrayList;

import javax.inject.Inject;

import live.itrip.app.data.api.MessageApi;
import live.itrip.app.data.model.MessageModel;
import live.itrip.app.data.net.response.MessageResultResp;
import live.itrip.app.service.net.MessageService;
import live.itrip.common.util.AppLog;
import rx.Observable;
import rx.functions.Func1;

/**
 * Created by Feng on 2017/6/27.
 */

public class MessageDataSource implements MessageApi {

    MessageService mMessageService;

    @Inject
    public MessageDataSource(MessageService service) {
        this.mMessageService = service;
    }

    @Override
    public Observable<ArrayList<MessageModel>> getMessages(@MessageType final int type) {
        return mMessageService.getMessages()
                .map(new Func1<MessageResultResp, ArrayList<MessageModel>>() {
                    @Override
                    public ArrayList<MessageModel> call(MessageResultResp resp) {

                        switch (type) {
                            case FLAG_SYSTEM:
                                return resp.getSystemMsgs();
                            case FLAG_USER:
                                return resp.getUserMsgs();
                            default:
                                AppLog.w("unknown language");
                                break;
                        }

                        return null;
                    }
                });
    }
}
