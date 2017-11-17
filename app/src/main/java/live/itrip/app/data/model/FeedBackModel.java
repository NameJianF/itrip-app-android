package live.itrip.app.data.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by Feng on 2017/9/6.
 */

public class FeedBackModel implements Parcelable {
    private int feedType;
    private String message;
    private List<String> imageUrlList;
    private String userName;


    protected FeedBackModel(Parcel in) {
        feedType = in.readInt();
        message = in.readString();
        imageUrlList = in.createStringArrayList();
        userName = in.readString();
    }

    public static final Creator<FeedBackModel> CREATOR = new Creator<FeedBackModel>() {
        @Override
        public FeedBackModel createFromParcel(Parcel in) {
            return new FeedBackModel(in);
        }

        @Override
        public FeedBackModel[] newArray(int size) {
            return new FeedBackModel[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(feedType);
        dest.writeString(message);
        dest.writeStringList(imageUrlList);
        dest.writeString(userName);
    }

    public int getFeedType() {
        return feedType;
    }

    public void setFeedType(int feedType) {
        this.feedType = feedType;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<String> getImageUrlList() {
        return imageUrlList;
    }

    public void setImageUrlList(List<String> imageUrlList) {
        this.imageUrlList = imageUrlList;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
