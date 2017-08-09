package live.itrip.app.data.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * Created by Feng on 2017/7/24.
 */

public class PlanDetailModel extends BaseDetailModel implements Parcelable {

    private int price;  // 价格
    //    private Integer score; // 评分
    private String recommend; // 推荐
    private int participate; // 销售数量
    private String type;  // 行程类型


    protected PlanDetailModel(Parcel in) {
        super(in);
        price = in.readInt();
        recommend = in.readString();
        participate = in.readInt();
        type = in.readString();
    }

    public static final Creator<PlanDetailModel> CREATOR = new Creator<PlanDetailModel>() {
        @Override
        public PlanDetailModel createFromParcel(Parcel in) {
            return new PlanDetailModel(in);
        }

        @Override
        public PlanDetailModel[] newArray(int size) {
            return new PlanDetailModel[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeInt(price);
        dest.writeString(recommend);
        dest.writeInt(participate);
        dest.writeString(type);
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getRecommend() {
        return recommend;
    }

    public void setRecommend(String recommend) {
        this.recommend = recommend;
    }


    public int getParticipate() {
        return participate;
    }

    public void setParticipate(int participate) {
        this.participate = participate;
    }


    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
