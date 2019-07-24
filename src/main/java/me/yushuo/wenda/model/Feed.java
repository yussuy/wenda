package me.yushuo.wenda.model;

import java.util.Date;

public class Feed {
    private int id;
    private int userId;
    private int type;
    private Date createdId;
    //JSON
    private String data;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public Date getCreatedId() {
        return createdId;
    }

    public void setCreatedId(Date createdId) {
        this.createdId = createdId;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
