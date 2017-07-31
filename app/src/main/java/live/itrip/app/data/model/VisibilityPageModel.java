package live.itrip.app.data.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by Feng on 2017/7/12.
 */

public class VisibilityPageModel extends MultiItemEntity implements Parcelable {
    public static final int ITEM_NAV = 1;
    public static final int ITEM_HOT = 2;
    public static final int ITEM_CATEGORY = 3;
    public static final int ITEM_BLOG = 4;
    public static final int ITEM_AD = 5;


    private Long id;
    private String title;
    private String content;
    private Long createTime;
    private String type; // 记录类型
    private String imgUrl; // 图片地址

    @SerializedName("items")
    private ArrayList<ChildMultiItem> items;

    protected VisibilityPageModel(Parcel in) {
        id = in.readLong();
        title = in.readString();
        content = in.readString();
        createTime = in.readLong();
        type = in.readString();
        imgUrl = in.readString();
        itemType = in.readInt();
        items = in.createTypedArrayList(ChildMultiItem.CREATOR);
    }

    public static final Creator<VisibilityPageModel> CREATOR = new Creator<VisibilityPageModel>() {
        @Override
        public VisibilityPageModel createFromParcel(Parcel in) {
            return new VisibilityPageModel(in);
        }

        @Override
        public VisibilityPageModel[] newArray(int size) {
            return new VisibilityPageModel[size];
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
        dest.writeString(content);
        dest.writeLong(createTime);
        dest.writeString(type);
        dest.writeString(imgUrl);
        dest.writeInt(itemType);
        dest.writeTypedList(items);
    }

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

    public ArrayList<ChildMultiItem> getItems() {
        return items;
    }

    public void setItems(ArrayList<ChildMultiItem> items) {
        this.items = items;
    }
}
