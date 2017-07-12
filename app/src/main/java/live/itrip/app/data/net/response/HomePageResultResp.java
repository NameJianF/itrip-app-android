package live.itrip.app.data.net.response;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

import live.itrip.app.data.model.HomePageModel;

/**
 * Created by Feng on 2017/7/12.
 */

public class HomePageResultResp implements Parcelable {
    private String op;
    private Integer code;
    private String msg;
    @SerializedName("data")
    private ArrayList<HomePageModel> dataList;


    protected HomePageResultResp(Parcel in) {
        op = in.readString();
        code = in.readInt();
        msg = in.readString();
        dataList = in.createTypedArrayList(HomePageModel.CREATOR);
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

    public static final Creator<HomePageResultResp> CREATOR = new Creator<HomePageResultResp>() {
        @Override
        public HomePageResultResp createFromParcel(Parcel in) {
            return new HomePageResultResp(in);
        }

        @Override
        public HomePageResultResp[] newArray(int size) {
            return new HomePageResultResp[size];
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

    public ArrayList<HomePageModel> getDataList() {
        return dataList;
    }

    public void setDataList(ArrayList<HomePageModel> dataList) {
        this.dataList = dataList;
    }
}
