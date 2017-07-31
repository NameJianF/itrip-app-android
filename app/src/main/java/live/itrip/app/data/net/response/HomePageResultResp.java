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
    @SerializedName("banner")
    private HomePageModel banner;
    @SerializedName("nav")
    private HomePageModel nav;
    @SerializedName("newPlan")
    private HomePageModel newPlan;
    @SerializedName("hot")
    private HomePageModel hot;
    @SerializedName("love")
    private HomePageModel love;
    @SerializedName("blog")
    private HomePageModel blog;
    @SerializedName("ad")
    private HomePageModel ad;


    protected HomePageResultResp(Parcel in) {
        op = in.readString();
        code = in.readInt();
        msg = in.readString();
        banner = in.readParcelable(HomePageModel.class.getClassLoader());
        nav = in.readParcelable(HomePageModel.class.getClassLoader());
        newPlan = in.readParcelable(HomePageModel.class.getClassLoader());
        hot = in.readParcelable(HomePageModel.class.getClassLoader());
        love = in.readParcelable(HomePageModel.class.getClassLoader());
        blog = in.readParcelable(HomePageModel.class.getClassLoader());
        ad = in.readParcelable(HomePageModel.class.getClassLoader());
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

    public HomePageModel getBanner() {
        return banner;
    }

    public void setBanner(HomePageModel banner) {
        this.banner = banner;
    }

    public HomePageModel getNav() {
        return nav;
    }

    public void setNav(HomePageModel nav) {
        this.nav = nav;
    }

    public HomePageModel getNewPlan() {
        return newPlan;
    }

    public void setNewPlan(HomePageModel newPlan) {
        this.newPlan = newPlan;
    }

    public HomePageModel getHot() {
        return hot;
    }

    public void setHot(HomePageModel hot) {
        this.hot = hot;
    }

    public HomePageModel getLove() {
        return love;
    }

    public void setLove(HomePageModel love) {
        this.love = love;
    }

    public HomePageModel getBlog() {
        return blog;
    }

    public void setBlog(HomePageModel blog) {
        this.blog = blog;
    }

    public HomePageModel getAd() {
        return ad;
    }

    public void setAd(HomePageModel ad) {
        this.ad = ad;
    }
}
