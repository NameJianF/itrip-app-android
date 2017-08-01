package live.itrip.app.data.net.response;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import live.itrip.app.data.model.BlogDetailModel;

/**
 * Created by Feng on 2017/7/24.
 */

public class BlogDetailResultResp implements Parcelable {
    private String op;
    private Integer code;
    private String msg;

    @SerializedName("data")
    private BlogDetailModel blogDetailModel;

    protected BlogDetailResultResp(Parcel in) {
        op = in.readString();
        code = in.readInt();
        msg = in.readString();
        blogDetailModel = in.readParcelable(BlogDetailModel.class.getClassLoader());
    }

    public static final Creator<BlogDetailResultResp> CREATOR = new Creator<BlogDetailResultResp>() {
        @Override
        public BlogDetailResultResp createFromParcel(Parcel in) {
            return new BlogDetailResultResp(in);
        }

        @Override
        public BlogDetailResultResp[] newArray(int size) {
            return new BlogDetailResultResp[size];
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
        dest.writeParcelable(blogDetailModel, flags);
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

    public BlogDetailModel getBlogDetailModel() {
        return blogDetailModel;
    }

    public void setBlogDetailModel(BlogDetailModel blogDetailModel) {
        this.blogDetailModel = blogDetailModel;
    }
}
