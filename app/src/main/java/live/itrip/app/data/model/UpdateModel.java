package live.itrip.app.data.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Feng on 2017/6/28.
 */

public class UpdateModel implements Parcelable {

    private String versionCode;
    private String versionName;
    private String desc;
    private String downloadUrl;
    private String publishDate;

    public String getVersionCode() {
        return versionCode;
    }

    public void setVersionCode(String versionCode) {
        this.versionCode = versionCode;
    }

    public String getVersionName() {
        return versionName;
    }

    public void setVersionName(String versionName) {
        this.versionName = versionName;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getDownloadUrl() {
        return downloadUrl;
    }

    public void setDownloadUrl(String downloadUrl) {
        this.downloadUrl = downloadUrl;
    }

    public String getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(String publishDate) {
        this.publishDate = publishDate;
    }

    public UpdateModel() {
    }

    protected UpdateModel(Parcel in) {
        this.versionCode = in.readString();
        this.versionName = in.readString();
        this.desc = in.readString();
        this.downloadUrl = in.readString();
        this.publishDate = in.readString();
    }

    public static final Creator<UpdateModel> CREATOR = new Creator<UpdateModel>() {
        @Override
        public UpdateModel createFromParcel(Parcel in) {
            return new UpdateModel(in);
        }

        @Override
        public UpdateModel[] newArray(int size) {
            return new UpdateModel[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.versionCode);
        dest.writeString(this.versionName);
        dest.writeString(this.desc);
        dest.writeString(this.downloadUrl);
        dest.writeString(this.publishDate);
    }
}
