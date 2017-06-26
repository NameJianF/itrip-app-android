package live.itrip.app.data.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Feng on 2017/6/26.
 */

public class RecyclerViewItem implements Parcelable {

    private int id;
    private String title;
    private String desc;
    private String iamgePath;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getIamgePath() {
        return iamgePath;
    }

    public void setIamgePath(String iamgePath) {
        this.iamgePath = iamgePath;
    }

    public RecyclerViewItem() {
    }

    protected RecyclerViewItem(Parcel in) {
        this.id = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<RecyclerViewItem> CREATOR = new Creator<RecyclerViewItem>() {
        @Override
        public RecyclerViewItem createFromParcel(Parcel in) {
            return new RecyclerViewItem(in);
        }

        @Override
        public RecyclerViewItem[] newArray(int size) {
            return new RecyclerViewItem[size];
        }
    };
}
