package live.itrip.app.data.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by Feng on 2017/7/12.
 */

public class PlanCategoryModel extends MultiItemEntity implements Parcelable {
    public static final int ITEM_BANNER = 0;   // banner
    public static final int ITEM_NAV = 1;      // 内置导航
    public static final int ITEM_NEW_PLAN = 2; // 最新行程
    public static final int ITEM_HOT = 3;      // 热门行程
    public static final int ITEM_LIST= 4;     // 猜你喜欢
    public static final int ITEM_BLOG = 5;     // 热门博客
    public static final int ITEM_AD = 6;       // 广告


    private Long id;
    private String title;
    private String content;
    private Long createTime;
    private String type; // 记录类型
    private String imgUrl; // 图片地址

    @SerializedName("items")
    private ArrayList<ChildMultiItem> items;

    protected PlanCategoryModel(Parcel in) {
        id = in.readLong();
        title = in.readString();
        content = in.readString();
        createTime = in.readLong();
        type = in.readString();
        imgUrl = in.readString();
        itemType = in.readInt();
        items = in.createTypedArrayList(ChildMultiItem.CREATOR);
    }

    public static final Creator<PlanCategoryModel> CREATOR = new Creator<PlanCategoryModel>() {
        @Override
        public PlanCategoryModel createFromParcel(Parcel in) {
            return new PlanCategoryModel(in);
        }

        @Override
        public PlanCategoryModel[] newArray(int size) {
            return new PlanCategoryModel[size];
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
