package live.itrip.app.bean;

/**
 * Created by Feng on 2017/7/24.
 */

public class Recommend {
    private Long id;
    private String title;
    private String subTitle; // 简介
    private String imageUrl;

    private Integer favorite; // 收藏
    private Integer commentCount; // 评论数

    // 行程采用
    private Integer price; // 价格
    private Integer participate; // 参与人数

    // 博客采用
    private String author; // 作者
    private Integer toView; // 查看


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

    public Integer getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(Integer commentCount) {
        this.commentCount = commentCount;
    }
}
