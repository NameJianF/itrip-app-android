package live.itrip.app.data.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Feng on 2017/7/24.
 */

public class BlogDetailModel extends BaseDetailModel implements Parcelable {
    private int type;       // 博客类型
    private int today;      // 今日发布
    private int originate;  // 原创
    private int reprint;    // 转发
    private int recommend;  // 推荐
    private int relation; // 关注

    private Long authId; // 作者id
    private String authName;// 作者名称
    private String authAvator; // 作者头像地址

    protected BlogDetailModel(Parcel in) {
        super(in);
        type = in.readInt();
        today = in.readInt();
        originate = in.readInt();
        reprint = in.readInt();
        recommend = in.readInt();
        relation = in.readInt();
        authId = in.readLong();
        authName = in.readString();
        authAvator = in.readString();
    }

    public static final Creator<BlogDetailModel> CREATOR = new Creator<BlogDetailModel>() {
        @Override
        public BlogDetailModel createFromParcel(Parcel in) {
            return new BlogDetailModel(in);
        }

        @Override
        public BlogDetailModel[] newArray(int size) {
            return new BlogDetailModel[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(type);
        dest.writeInt(today);
        dest.writeInt(originate);
        dest.writeInt(reprint);
        dest.writeInt(recommend);
        dest.writeInt(relation);
        dest.writeLong(authId);
        dest.writeString(authName);
        dest.writeString(authAvator);
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getToday() {
        return today;
    }

    public void setToday(int today) {
        this.today = today;
    }

    public int getOriginate() {
        return originate;
    }

    public void setOriginate(int originate) {
        this.originate = originate;
    }

    public int getReprint() {
        return reprint;
    }

    public void setReprint(int reprint) {
        this.reprint = reprint;
    }

    public int getRecommend() {
        return recommend;
    }

    public void setRecommend(int recommend) {
        this.recommend = recommend;
    }

    public Long getAuthId() {
        return authId;
    }

    public void setAuthId(Long authId) {
        this.authId = authId;
    }

    public String getAuthName() {
        return authName;
    }

    public void setAuthName(String authName) {
        this.authName = authName;
    }

    public String getAuthAvator() {
        return authAvator;
    }

    public void setAuthAvator(String authAvator) {
        this.authAvator = authAvator;
    }

    public int getRelation() {
        return relation;
    }

    public void setRelation(int relation) {
        this.relation = relation;
    }
}
