package cmu.sv.flubber.ihere.entities;

import java.util.Date;

/**
 * Created by xingyuchen on 4/12/16.
 */
public class Comment {
    private int commentId;
    private int iTagId;
    private int userId;
    private String content;
    private Date date;
    private String userName;

    public Comment() {
        super();
    }

    public Comment(int iTagId, int userId, String content, Date date, String name) {
        super();
        this.iTagId = iTagId;
        this.userId = userId;
        this.content = content;
        this.date = date;
        this.userName= name;
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

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
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