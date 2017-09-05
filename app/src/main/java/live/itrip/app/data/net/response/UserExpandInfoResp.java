package live.itrip.app.data.net.response;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import live.itrip.app.data.model.UserExpandModel;
import live.itrip.app.data.model.UserModel;

/**
 * Created by Feng on 2017/4/26.
 */

public class UserExpandInfoResp implements Parcelable {
    private String op;
    private int code = -1;
    private String msg;
    @SerializedName("data")
    private UserExpandModel userExpandModel;

    protected UserExpandInfoResp(Parcel in) {
        op = in.readString();
        code = in.readInt();
        msg = in.readString();
        userExpandModel = in.readParcelable(UserExpandModel.class.getClassLoader());
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(op);
        dest.writeInt(code);
        dest.writeString(msg);
        dest.writeParcelable(userExpandModel, flags);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<UserExpandInfoResp> CREATOR = new Creator<UserExpandInfoResp>() {
        @Override
        public UserExpandInfoResp createFromParcel(Parcel in) {
            return new UserExpandInfoResp(in);
        }

        @Override
        public UserExpandInfoResp[] newArray(int size) {
            return new UserExpandInfoResp[size];
        }
    };

    public String getOp() {
        return op;
    }

    public void setOp(String op) {
        this.op = op;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public UserExpandModel getUserExpandModel() {
        return userExpandModel;
    }

    public void setUserExpandModel(UserExpandModel userExpandModel) {
        this.userExpandModel = userExpandModel;
    }
}
