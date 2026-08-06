package com.shangxian.pinkink.bean;

import com.shangxian.pinkink.constants.AppConfig;

/* JADX INFO: loaded from: classes.dex */
public class FontBean {
    String fontCover;
    String fontFilePath;
    long columnId = 0;
    String id = "";
    String fontName = "";
    String userId = "";
    boolean isChecked = false;

    public long getColumnId() {
        return this.columnId;
    }

    public void setColumnId(long columnId) {
        this.columnId = columnId;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFontName() {
        return this.fontName;
    }

    public void setFontName(String fontName) {
        this.fontName = fontName;
    }

    public String getFontCover() {
        return this.fontCover;
    }

    public void setFontCover(String fontCover) {
        this.fontCover = fontCover;
    }

    public String getFontFilePath() {
        return this.fontFilePath;
    }

    public void setFontFilePath(String fontFilePath) {
        this.fontFilePath = fontFilePath;
    }

    public String getUserId() {
        return this.userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public boolean isChecked() {
        return this.isChecked;
    }

    public void setChecked(boolean checked) {
        this.isChecked = checked;
    }

    public String getFileSuffix() {
        return getFontFilePath().split("\\.")[r0.length - 1];
    }

    public String getFileName() {
        return this.id + "." + getFileSuffix();
    }

    public String getLocalFilePath() {
        return AppConfig.INSTANCE.getFontSavePath() + getFileName();
    }
}
