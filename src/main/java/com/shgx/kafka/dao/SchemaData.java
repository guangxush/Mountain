package com.shgx.kafka.dao;

/**
 * Created by gshan on 2018/8/27
 */
public class SchemaData {

    private Long userId;
    private String title;
    private String message;
    private int likes;
    private String comments;
    private String sendTime;
    private Boolean delete;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public String getSendTime() {
        return sendTime;
    }

    public void setSendTime(String sendTime) {
        this.sendTime = sendTime;
    }

    public Boolean getDelete() {
        return delete;
    }

    public void setDelete(Boolean delete) {
        this.delete = delete;
    }

    @Override
    public String toString() {
        return "SchemaData{" +
                "userId=" + userId +
                ", title='" + title + '\'' +
                ", message='" + message + '\'' +
                ", likes=" + likes +
                ", comments='" + comments + '\'' +
                ", sendTime=" + sendTime +
                ", delete=" + delete +
                '}';
    }
}
