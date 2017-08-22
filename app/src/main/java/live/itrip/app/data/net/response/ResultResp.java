package live.itrip.app.data.net.response;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Feng on 2017/8/21.
 * <p>
 * 请求结果： op 、code and message
 */

public class ResultResp implements Parcelable {
    private String op;
    private int code;
    private String msg;


    protected ResultResp(Parcel in) {
        op = in.readString();
        code = in.readInt();
        msg = in.readString();
    }

    public static final Creator<ResultResp> CREATOR = new Creator<ResultResp>() {
        @Override
        public ResultResp createFromParcel(Parcel in) {
            return new ResultResp(in);
        }

        @Override
        public ResultResp[] newArray(int size) {
            return new ResultResp[size];
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
    }

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
}
