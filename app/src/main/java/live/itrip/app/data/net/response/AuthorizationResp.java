package live.itrip.app.data.net.response;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import live.itrip.app.data.model.UserModel;

/**
 * Created by Feng on 2017/4/26.
 */

public class AuthorizationResp implements Parcelable {
    private String op;
    private Integer code;
    private String msg;
    @SerializedName("data")
    private UserModel author;


    protected AuthorizationResp(Parcel in) {
        op = in.readString();
        code = in.readInt();
        msg = in.readString();
        author = in.readParcelable(UserModel.class.getClassLoader());
    }

    public static final Creator<AuthorizationResp> CREATOR = new Creator<AuthorizationResp>() {
        @Override
        public AuthorizationResp createFromParcel(Parcel in) {
            return new AuthorizationResp(in);
        }

        @Override
        public AuthorizationResp[] newArray(int size) {
            return new AuthorizationResp[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(op);
        dest.writeInt(code);
        dest.writeString(msg);
        dest.writeParcelable(author, flags);
    }

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

    public UserModel getAuthor() {
        return author;
    }

    public void setAuthor(UserModel author) {
        this.author = author;
    }
}
