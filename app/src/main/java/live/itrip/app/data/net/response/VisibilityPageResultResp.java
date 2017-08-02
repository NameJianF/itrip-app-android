package live.itrip.app.data.net.response;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import live.itrip.app.data.model.VisibilityPageModel;

/**
 * Created by Feng on 2017/7/12.
 */

public class VisibilityPageResultResp implements Parcelable {
    private String op;
    private Integer code;
    private String msg;
    @SerializedName("nav")
    private VisibilityPageModel nav;
    @SerializedName("hot")
    private VisibilityPageModel hot;
    @SerializedName("blog")
    private VisibilityPageModel blog;
    @SerializedName("category")
    private VisibilityPageModel category;
    @SerializedName("ad")
    private VisibilityPageModel ad;


    protected VisibilityPageResultResp(Parcel in) {
        op = in.readString();
        code = in.readInt();
        msg = in.readString();
        nav = in.readParcelable(VisibilityPageModel.class.getClassLoader());
        hot = in.readParcelable(VisibilityPageModel.class.getClassLoader());
        blog = in.readParcelable(VisibilityPageModel.class.getClassLoader());
        category = in.readParcelable(VisibilityPageModel.class.getClassLoader());
        ad = in.readParcelable(VisibilityPageModel.class.getClassLoader());
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(op);
        dest.writeInt(code);
        dest.writeString(msg);
        dest.writeParcelable(nav, flags);
        dest.writeParcelable(hot, flags);
        dest.writeParcelable(blog, flags);
        dest.writeParcelable(category, flags);
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

    public VisibilityPageModel getNav() {
        return nav;
    }

    public void setNav(VisibilityPageModel nav) {
        this.nav = nav;
    }

    public VisibilityPageModel getHot() {
        return hot;
    }

    public void setHot(VisibilityPageModel hot) {
        this.hot = hot;
    }

    public VisibilityPageModel getBlog() {
        return blog;
    }

    public void setBlog(VisibilityPageModel blog) {
        this.blog = blog;
    }

    public VisibilityPageModel getCategory() {
        return category;
    }

    public void setCategory(VisibilityPageModel category) {
        this.category = category;
    }

    public VisibilityPageModel getAd() {
        return ad;
    }

    public void setAd(VisibilityPageModel ad) {
        this.ad = ad;
    }
}
