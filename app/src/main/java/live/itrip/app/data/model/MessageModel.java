package live.itrip.app.data.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Feng on 2017/6/27.
 */

public class MessageModel implements Parcelable {
    private Long msgId;
    private String userImage;
    private String title;
    private String content;
    private String sendTime;
    private String readme;

    public Long getMsgId() {
        return msgId;
    }

    public void setMsgId(Long msgId) {
        this.msgId = msgId;
    }

    public String getUserImage() {
        return userImage;
    }

    public void setUserImage(String userImage) {
        this.userImage = userImage;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getSendTime() {
        return sendTime;
    }

    public void setSendTime(String sendTime) {
        this.sendTime = sendTime;
    }

    public String isReadme() {
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
        dest.writeLong(this.msgId);
        dest.writeString(this.userImage);
        dest.writeString(this.title);
        dest.writeString(this.content);
        dest.writeString(this.sendTime);
        dest.writeString(this.readme);
    }

    public MessageModel() {
    }

    protected MessageModel(Parcel in) {
        this.msgId = in.readLong();
        this.userImage = in.readString();
        this.title = in.readString();
        this.sendTime = in.readString();
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
