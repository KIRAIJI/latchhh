package com.shangxian.pinkink.bean;

import com.squareup.moshi.Json;

/* JADX INFO: loaded from: classes.dex */
public class UserInfoBean {

    @Json(name = "headurl")
    private String avatar;
    private long columnId = 0;
    private String id = "";

    @Json(name = "nickname")
    private String nikeName = "";
    private String mobile = "";
    private int state = 0;
    private String token = "";

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAvatar() {
        return this.avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getNikeName() {
        return this.nikeName;
    }

    public void setNikeName(String nikeName) {
        this.nikeName = nikeName;
    }

    public String getMobile() {
        return this.mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public int getState() {
        return this.state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getToken() {
        return this.token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public long getColumnId() {
        return this.columnId;
    }

    public void setColumnId(long columnId) {
        this.columnId = columnId;
    }
}
