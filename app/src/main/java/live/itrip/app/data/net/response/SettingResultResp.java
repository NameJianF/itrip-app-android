package live.itrip.app.data.net.response;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Feng on 2017/6/28.
 */

public class SettingResultResp implements Parcelable {

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

    public SettingResultResp() {
    }

    protected SettingResultResp(Parcel in) {
        this.versionCode = in.readString();
        this.versionName = in.readString();
        this.desc = in.readString();
        this.downloadUrl = in.readString();
        this.publishDate = in.readString();
    }

    public static final Creator<SettingResultResp> CREATOR = new Creator<SettingResultResp>() {
        @Override
        public SettingResultResp createFromParcel(Parcel in) {
            return new SettingResultResp(in);
        }

        @Override
        public SettingResultResp[] newArray(int size) {
            return new SettingResultResp[size];
        }
    };

}
