package live.itrip.app.data.net.response;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

import live.itrip.app.data.model.HomePageModel;
import live.itrip.app.data.model.VisibilityPageModel;

/**
 * Created by Feng on 2017/7/12.
 */

public class VisibilityPageResultResp implements Parcelable {
    private String op;
    private Integer code;
    private String msg;
    @SerializedName("data")
    private ArrayList<VisibilityPageModel> dataList;


    protected VisibilityPageResultResp(Parcel in) {
        op = in.readString();
        code = in.readInt();
        msg = in.readString();
        dataList = in.createTypedArrayList(VisibilityPageModel.CREATOR);
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(op);
        dest.writeInt(code);
        dest.writeString(msg);
        dest.writeTypedList(dataList);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<VisibilityPageResultResp> CREATOR = new Creator<VisibilityPageResultResp>() {
        @Override
        public VisibilityPageResultResp createFromParcel(Parcel in) {
            return new VisibilityPageResultResp(in);
        }

        @Override
        public VisibilityPageResultResp[] newArray(int size) {
            return new VisibilityPageResultResp[size];
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

    public ArrayList<VisibilityPageModel> getDataList() {
        return dataList;
    }

    public void setDataList(ArrayList<VisibilityPageModel> dataList) {
        this.dataList = dataList;
    }
}
