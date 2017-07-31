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
    private String imageUrl;

    protected ChildMultiItem(Parcel in) {
        id = in.readLong();
        title = in.readString();
        imageUrl = in.readString();
        itemType = in.readInt();
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

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(id);
        dest.writeString(title);
        dest.writeString(imageUrl);
        dest.writeInt(itemType);
    }
}
