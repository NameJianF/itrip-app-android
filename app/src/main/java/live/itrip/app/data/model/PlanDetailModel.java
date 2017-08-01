package live.itrip.app.data.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Feng on 2017/7/24.
 */

public class PlanDetailModel implements Parcelable {
    private Long id;
    /**
     * 1：收藏，0：未收藏
     */
    private int favorite = 1;
    private String title = "";
    private String body = "";
    private String href = "";
    private int type;

    private List<Image> images;
    private Statistics statistics;

    private UserModel author;

    protected PlanDetailModel(Parcel in) {
        id = in.readLong();
        author = in.readParcelable(UserModel.class.getClassLoader());
    }

    public static final Creator<PlanDetailModel> CREATOR = new Creator<PlanDetailModel>() {
        @Override
        public PlanDetailModel createFromParcel(Parcel in) {
            return new PlanDetailModel(in);
        }

        @Override
        public PlanDetailModel[] newArray(int size) {
            return new PlanDetailModel[size];
        }
    };

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getFavorite() {
        return favorite;
    }

    public void setFavorite(int favorite) {
        this.favorite = favorite;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public List<Image> getImages() {
        return images;
    }

    public void setImages(List<Image> images) {
        this.images = images;
    }

    public Statistics getStatistics() {
        return statistics;
    }

    public void setStatistics(Statistics statistics) {
        this.statistics = statistics;
    }

    public UserModel getAuthor() {
        return author;
    }

    public void setAuthor(UserModel author) {
        this.author = author;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(id);
        dest.writeParcelable(author, flags);
    }


    public static class Image implements Serializable {
        private String href;
        private String thumb;
        private int w;
        private int h;
        private String type;
        private String name;

        public String getHref() {
            return href;
        }

        public void setHref(String href) {
            this.href = href;
        }

        public String getThumb() {
            return thumb;
        }

        public void setThumb(String thumb) {
            this.thumb = thumb;
        }

        public int getW() {
            return w;
        }

        public void setW(int w) {
            this.w = w;
        }

        public int getH() {
            return h;
        }

        public void setH(int h) {
            this.h = h;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

    public static class Statistics implements Serializable {
        private int comment;
        private int view;
        private int like;
        private int transmit;
        private int favCount;

        public int getComment() {
            return comment;
        }

        public void setComment(int comment) {
            this.comment = comment;
        }

        public int getView() {
            return view;
        }

        public void setView(int view) {
            this.view = view;
        }

        public int getLike() {
            return like;
        }

        public void setLike(int like) {
            this.like = like;
        }

        public int getTransmit() {
            return transmit;
        }

        public void setTransmit(int transmit) {
            this.transmit = transmit;
        }

        public int getFavCount() {
            return favCount;
        }

        public void setFavCount(int favCount) {
            this.favCount = favCount;
        }
    }
}
