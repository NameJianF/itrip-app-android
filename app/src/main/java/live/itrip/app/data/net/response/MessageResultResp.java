package live.itrip.app.data.net.response;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

import live.itrip.app.data.model.MessageModel;

/**
 * Created by Feng on 2017/6/27.
 */

public class MessageResultResp implements Parcelable {
    private String op;
    private Integer code;
    private String msg;

    @SerializedName("data")
    private ArrayList<MessageModel> systemMsgs;

    protected MessageResultResp(Parcel in) {
        op = in.readString();
        msg = in.readString();
        systemMsgs = in.createTypedArrayList(MessageModel.CREATOR);
    }

    public static final Creator<MessageResultResp> CREATOR = new Creator<MessageResultResp>() {
        @Override
        public MessageResultResp createFromParcel(Parcel in) {
            return new MessageResultResp(in);
        }

        @Override
        public MessageResultResp[] newArray(int size) {
            return new MessageResultResp[size];
        }
    };

    public String getOp() {
        return op;
    }

    public void setOp(String op) {
        this.op = op;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }


    //    @SerializedName("data")
//    private ArrayList<MessageModel> userMsgs;

    public ArrayList<MessageModel> getSystemMsgs() {
        return systemMsgs;
    }

    public void setSystemMsgs(ArrayList<MessageModel> systemMsgs) {
        this.systemMsgs = systemMsgs;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(op);
        dest.writeString(msg);
        dest.writeTypedList(systemMsgs);
    }

//    public ArrayList<MessageModel> getUserMsgs() {
//        return userMsgs;
//    }
//
//    public void setUserMsgs(ArrayList<MessageModel> userMsgs) {
//        this.userMsgs = userMsgs;
//    }
}
