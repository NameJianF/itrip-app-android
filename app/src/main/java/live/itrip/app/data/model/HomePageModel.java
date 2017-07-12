package live.itrip.app.data.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Feng on 2017/7/12.
 */

public class HomePageModel implements Parcelable {
    private Long id;
    private String title;
    private String content;
    private Long createTime;
    private String type; // 记录类型
    private String imgUrl; // 图片地址

    public HomePageModel() {

    }

    protected HomePageModel(Parcel in) {
        this.id = in.readLong();
        title = in.readString();
        content = in.readString();
        type = in.readString();
        imgUrl = in.readString();
        this.createTime = in.readLong();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(id);
        dest.writeString(title);
        dest.writeString(content);
        dest.writeString(type);
        dest.writeString(imgUrl);
        dest.writeLong(createTime);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<HomePageModel> CREATOR = new Creator<HomePageModel>() {
        @Override
        public HomePageModel createFromParcel(Parcel in) {
            return new HomePageModel(in);
        }

        @Override
        public HomePageModel[] newArray(int size) {
            return new HomePageModel[size];
        }
    };

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }
}
