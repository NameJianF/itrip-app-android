package live.itrip.app.data.net;

import java.util.ArrayList;

import javax.inject.Inject;

import live.itrip.app.data.api.MessageApi;
import live.itrip.app.data.model.MessageModel;
import live.itrip.app.data.net.response.MessageResultResp;
import live.itrip.app.service.net.MessageService;
import live.itrip.common.util.AppLog;
import retrofit2.Call;
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
    public Observable<ArrayList<MessageModel>> getMessages(@MessageType final int type
            , Long uid, int page, int pageSize, Long lastMsgId) {


        Observable<MessageResultResp> list = mMessageService.getMessages(type, uid, page, pageSize, lastMsgId);


        return list.map(new Func1<MessageResultResp, ArrayList<MessageModel>>() {
            @Override
            public ArrayList<MessageModel> call(MessageResultResp resp) {

                // test datas
                ArrayList<MessageModel> models = new ArrayList<MessageModel>();

                for (Integer i = 1; i <= 20; i++) {
                    MessageModel model = new MessageModel();
                    model.setId(i.longValue());
                    model.setUserFrom(i.longValue());
                    model.setUserName("Name:" + i);
//                    model.setImg("");
                    model.setType(String.valueOf(type));
                    model.setContent("message content ====== ");
                    model.setCreateTime(System.currentTimeMillis());
                    model.setReadme("0");
                    models.add(model);
                }

                switch (type) {
                    case FLAG_SYSTEM:
//                        return resp.getMessageList();
                        return models;
                    case FLAG_USER:
//                        return resp.getUserMsgs();
                        return models;
                    default:
                        AppLog.w("unknown language");
                        break;
                }

                return null;
            }
        });
    }
}
