package live.itrip.app.data.net.response;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

import live.itrip.app.data.model.ChildMultiItem;
import live.itrip.app.data.model.MessageModel;

/**
 * Created by Feng on 2017/6/27.
 */

public class RecommendResultResp implements Parcelable {
    private String op;
    private Integer code;
    private String msg;

    @SerializedName("data")
    private ArrayList<ChildMultiItem> itemList;

    protected RecommendResultResp(Parcel in) {
        op = in.readString();
        code = in.readInt();
        msg = in.readString();
        itemList = in.createTypedArrayList(ChildMultiItem.CREATOR);
    }

    public static final Creator<RecommendResultResp> CREATOR = new Creator<RecommendResultResp>() {
        @Override
        public RecommendResultResp createFromParcel(Parcel in) {
            return new RecommendResultResp(in);
        }

        @Override
        public RecommendResultResp[] newArray(int size) {
            return new RecommendResultResp[size];
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


    public ArrayList<ChildMultiItem> getItemList() {
        return itemList;
    }

    public void setItemList(ArrayList<ChildMultiItem> list) {
        this.itemList = list;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(op);
        dest.writeInt(code);
        dest.writeString(msg);
        dest.writeTypedList(itemList);
    }

}
