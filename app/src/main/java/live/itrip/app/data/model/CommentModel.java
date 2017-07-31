package live.itrip.app.data.model;

import com.alibaba.fastjson.JSON;

/**
 * Created by Feng on 2017/7/21.
 */

public class CommentModel {
    public static final int VOTE_STATE_DEFAULT = 0;
    public static final int VOTE_STATE_UP = 1;
    public static final int VOTE_STATE_DOWN = 2;

    private long id;
    private UserModel author;
    private String content;
    private String pubDate;
    private int appClient;
    private long vote;
    private int voteState;
    private boolean best;
//    private Refer[] refer;
//    private Reply[] reply;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public UserModel getAuthor() {
        return author;
    }

    public void setAuthor(UserModel author) {
        this.author = author;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getPubDate() {
        return pubDate;
    }

    public void setPubDate(String pubDate) {
        this.pubDate = pubDate;
    }

    public int getAppClient() {
        return appClient;
    }

    public void setAppClient(int appClient) {
        this.appClient = appClient;
    }

    public long getVote() {
        return vote;
    }

    public void setVote(long vote) {
        this.vote = vote;
    }

    public int getVoteState() {
        return voteState;
    }

    public void setVoteState(int voteState) {
        this.voteState = voteState;
    }

    public boolean isBest() {
        return best;
    }

    public void setBest(boolean best) {
        this.best = best;
    }

//    public Refer[] getRefer() {
//        return refer;
//    }
//
//    public void setRefer(Refer[] refer) {
//        this.refer = refer;
//    }
//
//    public Reply[] getReply() {
//        return reply;
//    }
//
//    public void setReply(Reply[] reply) {
//        this.reply = reply;
//    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}
