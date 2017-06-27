package live.itrip.app.data.net.response;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

import live.itrip.app.data.model.MessageModel;

/**
 * Created by Feng on 2017/6/27.
 */

public class MessageResultResp {

    @SerializedName("systemMsg")
    private ArrayList<MessageModel> systemMsgs;

    @SerializedName("userMsg")
    private ArrayList<MessageModel> userMsgs;

    public ArrayList<MessageModel> getSystemMsgs() {
        return systemMsgs;
    }

    public void setSystemMsgs(ArrayList<MessageModel> systemMsgs) {
        this.systemMsgs = systemMsgs;
    }

    public ArrayList<MessageModel> getUserMsgs() {
        return userMsgs;
    }

    public void setUserMsgs(ArrayList<MessageModel> userMsgs) {
        this.userMsgs = userMsgs;
    }
}
