package live.itrip.app.data.net.request;

import java.util.List;

/**
 * Created by Feng on 2017/9/6.
 */

public class FeedBackParams extends BaseParams {
    private int feedType;
    private String message;
    private List<String> imageUrlList;
    private String userName;

    public int getFeedType() {
        return feedType;
    }

    public void setFeedType(int feedType) {
        this.feedType = feedType;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<String> getImageUrlList() {
        return imageUrlList;
    }

    public void setImageUrlList(List<String> imageUrlList) {
        this.imageUrlList = imageUrlList;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
