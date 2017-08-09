package live.itrip.app.data.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.chad.library.adapter.base.entity.MultiItemEntity;

/**
 * Created by Feng on 2017/7/14.
 */

public class ChildMultiItem extends MultiItemEntity implements Parcelable {
    public static final int ITEM_PLAN = 1;
    public static final int ITEM_BOLG = 2;
//    public static final int IMG_TEXT = 3;

    private Long id;
    private String title;
    private String subTitle;
    private String imageUrl;

    // 行程采用
    private Integer price; // 价格
    private Integer participate; // 参与人数

    // 博客采用
    private String author; // 作者
    private Integer toView; // 查看
    private Integer favorite; // 收藏

    protected ChildMultiItem(Parcel in) {
        id = in.readLong();
        title = in.readString();
        subTitle = in.readString();
        imageUrl = in.readString();
        itemType = in.readInt();
        price = in.readInt();
        participate = in.readInt();
        author = in.readString();
        toView = in.readInt();
        favorite = in.readInt();
    }

    public static final Creator<ChildMultiItem> CREATOR = new Creator<ChildMultiItem>() {
        @Override
        public ChildMultiItem createFromParcel(Parcel in) {
            return new ChildMultiItem(in);
        }

        @Override
        public ChildMultiItem[] newArray(int size) {
            return new ChildMultiItem[size];
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

    public String getSubTitle() {
        return subTitle;
    }

    public void setSubTitle(String subTitle) {
        this.subTitle = subTitle;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Integer getParticipate() {
        return participate;
    }

    public void setParticipate(Integer participate) {
        this.participate = participate;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Integer getToView() {
        return toView;
    }

    public void setToView(Integer toView) {
        this.toView = toView;
    }

    public Integer getFavorite() {
        return favorite;
    }

    public void setFavorite(Integer favorite) {
        this.favorite = favorite;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(id);
        dest.writeString(title);
        dest.writeString(subTitle);
        dest.writeString(imageUrl);
        dest.writeInt(itemType);
        dest.writeInt(price);
        dest.writeInt(participate);
        dest.writeString(author);
        dest.writeInt(toView);
        dest.writeInt(favorite);
    }
}
