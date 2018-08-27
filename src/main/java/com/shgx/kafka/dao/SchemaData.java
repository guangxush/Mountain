package com.shgx.kafka.dao;

import java.util.Date;

/**
 * Created by gshan on 2018/8/27
 */
public class SchemaData {

    private Long userId; //用户id
    private String title; //文章主题
    private String message; //文章内容
    private int likes; //文章点赞数
    private String comments; //文章评论
    //private Date sendTime;  //发送时间
    private String sendTime;  //发送时间
    private Boolean delete;  //是否删除

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
