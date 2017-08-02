package live.itrip.app.data.net.response;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import live.itrip.app.data.model.PlanCategoryModel;

/**
 * Created by Feng on 2017/7/12.
 */

public class PlanCategoryResultResp implements Parcelable {
    private String op;
    private Integer code;
    private String msg;
    @SerializedName("banner")
    private PlanCategoryModel banner;
    @SerializedName("nav")
    private PlanCategoryModel nav;
    @SerializedName("newPlan")
    private PlanCategoryModel newPlan;
    @SerializedName("hot")
    private PlanCategoryModel hot;
    @SerializedName("love")
    private PlanCategoryModel love;
    @SerializedName("blog")
    private PlanCategoryModel blog;
    @SerializedName("ad")
    private PlanCategoryModel ad;


    protected PlanCategoryResultResp(Parcel in) {
        op = in.readString();
        code = in.readInt();
        msg = in.readString();
        banner = in.readParcelable(PlanCategoryModel.class.getClassLoader());
        nav = in.readParcelable(PlanCategoryModel.class.getClassLoader());
        newPlan = in.readParcelable(PlanCategoryModel.class.getClassLoader());
        hot = in.readParcelable(PlanCategoryModel.class.getClassLoader());
        love = in.readParcelable(PlanCategoryModel.class.getClassLoader());
        blog = in.readParcelable(PlanCategoryModel.class.getClassLoader());
        ad = in.readParcelable(PlanCategoryModel.class.getClassLoader());
    }

    public static final Creator<PlanCategoryResultResp> CREATOR = new Creator<PlanCategoryResultResp>() {
        @Override
        public PlanCategoryResultResp createFromParcel(Parcel in) {
            return new PlanCategoryResultResp(in);
        }

        @Override
        public PlanCategoryResultResp[] newArray(int size) {
            return new PlanCategoryResultResp[size];
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
        dest.writeParcelable(banner, flags);
        dest.writeParcelable(nav, flags);
        dest.writeParcelable(newPlan, flags);
        dest.writeParcelable(hot, flags);
        dest.writeParcelable(love, flags);
        dest.writeParcelable(blog, flags);
        dest.writeParcelable(ad, flags);
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

    public PlanCategoryModel getBanner() {
        return banner;
    }

    public void setBanner(PlanCategoryModel banner) {
        this.banner = banner;
    }

    public PlanCategoryModel getNav() {
        return nav;
    }

    public void setNav(PlanCategoryModel nav) {
        this.nav = nav;
    }

    public PlanCategoryModel getNewPlan() {
        return newPlan;
    }

    public void setNewPlan(PlanCategoryModel newPlan) {
        this.newPlan = newPlan;
    }

    public PlanCategoryModel getHot() {
        return hot;
    }

    public void setHot(PlanCategoryModel hot) {
        this.hot = hot;
    }

    public PlanCategoryModel getLove() {
        return love;
    }

    public void setLove(PlanCategoryModel love) {
        this.love = love;
    }

    public PlanCategoryModel getBlog() {
        return blog;
    }

    public void setBlog(PlanCategoryModel blog) {
        this.blog = blog;
    }

    public PlanCategoryModel getAd() {
        return ad;
    }

    public void setAd(PlanCategoryModel ad) {
        this.ad = ad;
    }
}
