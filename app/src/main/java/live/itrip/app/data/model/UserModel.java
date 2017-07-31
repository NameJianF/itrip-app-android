package live.itrip.app.data.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Feng on 2017/4/26.
 */
public class UserModel implements Parcelable {
    private Long id;
    private String userName;
    private String email;
    private String img;
    private String mobile;
    private String uidQq;
    private String uidWeibo;
    private String uidWeixin;
    private String uidAli;
    private String source;
    private String subsource;
    private String level;
    private String status;
    private String identity;
    private Long createTime;
    private String token;

    protected UserModel(Parcel in) {
        id = in.readLong();
        userName = in.readString();
        email = in.readString();
        img = in.readString();
        mobile = in.readString();
        uidQq = in.readString();
        uidWeibo = in.readString();
        uidWeixin = in.readString();
        uidAli = in.readString();
        source = in.readString();
        subsource = in.readString();
        level = in.readString();
        status = in.readString();
        identity = in.readString();
        createTime = in.readLong();
        token = in.readString();
    }

    public static final Creator<UserModel> CREATOR = new Creator<UserModel>() {
        @Override
        public UserModel createFromParcel(Parcel in) {
            return new UserModel(in);
        }

        @Override
        public UserModel[] newArray(int size) {
            return new UserModel[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(id);
        dest.writeString(userName);
        dest.writeString(email);
        dest.writeString(img);
        dest.writeString(mobile);
        dest.writeString(uidQq);
        dest.writeString(uidWeibo);
        dest.writeString(uidWeixin);
        dest.writeString(uidAli);
        dest.writeString(source);
        dest.writeString(subsource);
        dest.writeString(level);
        dest.writeString(status);
        dest.writeString(identity);
        dest.writeLong(createTime);
        dest.writeString(token);
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getUidQq() {
        return uidQq;
    }

    public void setUidQq(String uidQq) {
        this.uidQq = uidQq;
    }

    public String getUidWeibo() {
        return uidWeibo;
    }

    public void setUidWeibo(String uidWeibo) {
        this.uidWeibo = uidWeibo;
    }

    public String getUidWeixin() {
        return uidWeixin;
    }

    public void setUidWeixin(String uidWeixin) {
        this.uidWeixin = uidWeixin;
    }

    public String getUidAli() {
        return uidAli;
    }

    public void setUidAli(String uidAli) {
        this.uidAli = uidAli;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getSubsource() {
        return subsource;
    }

    public void setSubsource(String subsource) {
        this.subsource = subsource;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getIdentity() {
        return identity;
    }

    public void setIdentity(String identity) {
        this.identity = identity;
    }

    public Long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}