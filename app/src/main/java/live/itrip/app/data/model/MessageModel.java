package live.itrip.app.data.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Feng on 2017/6/27.
 */

public class MessageModel implements Parcelable {
    private Long id;
    private Long userFrom;
    private Long userTo;
    private String type;
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
