package live.itrip.app.data.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Feng on 2017/8/9.
 */

public class BaseDetailModel implements Parcelable{
    private long id;
    private String title;      // 标题
    private String subTitle;   // 副标/简介
    private String titleImage; // 图片
    private String content;    // 文本内容
    private String href = "";  // 正文链接

    private int favorite = 1; // 1：收藏，0：未收藏
    private int commentCount; // 评论总数


    protected BaseDetailModel(Parcel in) {
        id = in.readLong();
        title = in.readString();
        subTitle = in.readString();
        titleImage = in.readString();
        content = in.readString();
        href = in.readString();
        favorite = in.readInt();
        commentCount = in.readInt();
    }

    public static final Creator<BaseDetailModel> CREATOR = new Creator<BaseDetailModel>() {
        @Override
        public BaseDetailModel createFromParcel(Parcel in) {
            return new BaseDetailModel(in);
        }

        @Override
        public BaseDetailModel[] newArray(int size) {
            return new BaseDetailModel[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(id);
        dest.writeString(title);
        dest.writeString(subTitle);
        dest.writeString(titleImage);
        dest.writeString(content);
        dest.writeString(href);
        dest.writeInt(favorite);
        dest.writeInt(commentCount);
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSubTitle() {
        return subTitle;
    }

    public void setSubTitle(String subTitle) {
        this.subTitle = subTitle;
    }

    public String getTitleImage() {
        return titleImage;
    }

    public void setTitleImage(String titleImage) {
        this.titleImage = titleImage;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }

    public int getFavorite() {
        return favorite;
    }

    public void setFavorite(int favorite) {
        this.favorite = favorite;
    }

    public int getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(int commentCount) {
        this.commentCount = commentCount;
    }
}
