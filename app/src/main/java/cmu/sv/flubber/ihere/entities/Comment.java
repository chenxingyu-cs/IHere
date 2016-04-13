package cmu.sv.flubber.ihere.entities;

import java.util.Date;

/**
 * Created by xingyuchen on 4/12/16.
 */
public class Comment {
    private int commentId;
    private int iTagId;
    private int sourceUserId;
    private int targetUserId;
    private String content;
    private Date date;

    public Comment() {
        super();
    }

    public Comment(int iTagId, int sourceUserId, int targetUserId, String content, Date date) {
        this.iTagId = iTagId;
        this.sourceUserId = sourceUserId;
        this.targetUserId = targetUserId;
        this.content = content;
        this.date = date;
    }

    public int getCommentId() {
        return commentId;
    }

    public void setCommentId(int commentId) {
        this.commentId = commentId;
    }

    public int getiTagId() {
        return iTagId;
    }

    public void setiTagId(int iTagId) {
        this.iTagId = iTagId;
    }

    public int getSourceUserId() {
        return sourceUserId;
    }

    public void setSourceUserId(int sourceUserId) {
        this.sourceUserId = sourceUserId;
    }

    public int getTargetUserId() {
        return targetUserId;
    }

    public void setTargetUserId(int targetUserId) {
        this.targetUserId = targetUserId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
