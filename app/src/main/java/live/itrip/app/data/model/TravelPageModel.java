package live.itrip.app.data.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.chad.library.adapter.base.entity.MultiItemEntity;

import java.util.ArrayList;

/**
 * Created by Feng on 2017/7/14.
 */

public class TravelPageModel extends MultiItemEntity implements Parcelable {
    public static final int ITEM_BANNER = 0;
    public static final int ITEM_NAV = 1;
    public static final int ITEM_HOT = 2;
    public static final int ITEM_CATEGORY = 3;
    public static final int ITEM_LIST = 4;
    public static final int ITEM_AD = 5;


    private Long id;
    private String title;
    private String content;
    private Long createTime;
    private String type; // 记录类型
    private String imgUrl; // 图片地址

    private ArrayList itemList;


    public TravelPageModel() {

    }

    protected TravelPageModel(Parcel in) {
        id = in.readLong();
        title = in.readString();
        content = in.readString();
        createTime = in.readLong();
        type = in.readString();
        imgUrl = in.readString();
        itemList = in.readArrayList(ChildMultiItem.class.getClassLoader());
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
        dest.writeList(itemList);
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

    public ArrayList getItemList() {
        return itemList;
    }

    public void setItemList(ArrayList itemList) {
        this.itemList = itemList;
    }
}
