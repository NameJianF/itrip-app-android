package live.itrip.app.data.net.response;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import live.itrip.app.data.model.BlogDetailModel;
import live.itrip.app.data.model.PlanDetailModel;

/**
 * Created by Feng on 2017/7/24.
 */

public class PlanDetailResultResp implements Parcelable {
    private String op;
    private Integer code;
    private String msg;

    @SerializedName("data")
    private PlanDetailModel planDetailModel;

    protected PlanDetailResultResp(Parcel in) {
        op = in.readString();
        code = in.readInt();
        msg = in.readString();
        planDetailModel = in.readParcelable(BlogDetailModel.class.getClassLoader());
    }

    public static final Creator<PlanDetailResultResp> CREATOR = new Creator<PlanDetailResultResp>() {
        @Override
        public PlanDetailResultResp createFromParcel(Parcel in) {
            return new PlanDetailResultResp(in);
        }

        @Override
        public PlanDetailResultResp[] newArray(int size) {
            return new PlanDetailResultResp[size];
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
        dest.writeParcelable(planDetailModel, flags);
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

    public PlanDetailModel getPlanDetailModel() {
        return planDetailModel;
    }

    public void setPlanDetailModel(PlanDetailModel planDetailModel) {
        this.planDetailModel = planDetailModel;
    }
}
