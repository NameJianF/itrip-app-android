package live.itrip.app.data.net.response;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import live.itrip.app.data.model.BlogModel;

/**
 * Created by Feng on 2017/7/24.
 */

public class BlogResultResp implements Parcelable {
    private String op;
    private Integer code;
    private String msg;

    @SerializedName("data")
    private BlogModel blogModel;

    protected BlogResultResp(Parcel in) {
        op = in.readString();
        code = in.readInt();
        msg = in.readString();
        blogModel = in.readParcelable(BlogModel.class.getClassLoader());
    }

    public static final Creator<BlogResultResp> CREATOR = new Creator<BlogResultResp>() {
        @Override
        public BlogResultResp createFromParcel(Parcel in) {
            return new BlogResultResp(in);
        }

        @Override
        public BlogResultResp[] newArray(int size) {
            return new BlogResultResp[size];
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
        dest.writeParcelable(blogModel, flags);
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

    public BlogModel getBlogModel() {
        return blogModel;
    }

    public void setBlogModel(BlogModel blogModel) {
        this.blogModel = blogModel;
    }
}
