package live.itrip.app.data.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Feng on 2017/6/27.
 */

public class MessageModel implements Parcelable {
    private Long id;
    private Long userFrom;
    private String userName; // 发送者姓名
    private String img; // 发送者头像地址
    private Long userTo;
    private String type; // 消息类型， 1：系统，2：私人
    private String content;
    private Long createTime;
    private String readme;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserFrom() {
        return userFrom;
    }

    public void setUserFrom(Long userFrom) {
        this.userFrom = userFrom;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public Long getUserTo() {
        return userTo;
    }

    public void setUserTo(Long userTo) {
        this.userTo = userTo;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }

    public String getReadme() {
        return readme;
    }

    public void setReadme(String readme) {
        this.readme = readme;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(this.id);
        dest.writeLong(this.userFrom);
        dest.writeString(this.userName);
        dest.writeString(this.img);
        dest.writeLong(this.userTo);
        dest.writeString(this.type);
        dest.writeString(this.content);
        dest.writeLong(this.createTime);
        dest.writeString(this.readme);
    }

    public MessageModel() {
    }

    protected MessageModel(Parcel in) {
        this.id = in.readLong();
        this.userFrom = in.readLong();
        this.userName = in.readString();
        this.img = in.readString();
        this.userTo = in.readLong();
        this.createTime = in.readLong();
        this.type = in.readString();
        this.content = in.readString();
        this.readme = in.readString();
    }

    public static final Creator<MessageModel> CREATOR = new Creator<MessageModel>() {
        @Override
        public MessageModel createFromParcel(Parcel in) {
            return new MessageModel(in);
        }

        @Override
        public MessageModel[] newArray(int size) {
            return new MessageModel[size];
        }
    };


}
