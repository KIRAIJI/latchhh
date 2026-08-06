package com.lazyee.nfc.bean;

import android.graphics.Bitmap;

/* JADX INFO: loaded from: classes.dex */
public class DeviceInfo {
    private String AppID;
    private String EN_Color;
    private String UID;
    private int black;
    private String color;
    private String color_desc;
    private byte[] compressData;
    private int cosVersion;
    private int height;
    private boolean isBattery;
    private boolean isCompress;
    private boolean isPin;
    private Bitmap mBitmap;
    private String manufacturer;
    private int pictureCapacity;
    private int red;
    private int refreshScan;
    private String screen;
    private int size;
    private int userData;
    private int white;
    private int width;
    private int yellow;
    private int colorCount = 3;
    private String colorType = "";
    private int deviceType = 0;
    private String scanType = "";
    private String pinCode = "1122334455";

    public String getManufacturer() {
        return this.manufacturer;
    }

    public void setManufacturer(String str) {
        this.manufacturer = str;
    }

    public String getColor() {
        return this.color;
    }

    public void setColor(String str) {
        this.color = str;
    }

    public String getScreen() {
        return this.screen;
    }

    public void setScreen(String str) {
        this.screen = str;
    }

    public String getColorType() {
        return this.colorType;
    }

    public void setColorType(String str) {
        this.colorType = str;
    }

    public String getScanType() {
        return this.scanType;
    }

    public void setScanType(String str) {
        this.scanType = str;
    }

    public int getPictureCapacity() {
        return this.pictureCapacity;
    }

    public void setPictureCapacity(int i) {
        this.pictureCapacity = i;
    }

    public boolean isBattery() {
        return this.isBattery;
    }

    public void setBattery(boolean z) {
        this.isBattery = z;
    }

    public String getAppID() {
        return this.AppID;
    }

    public void setAppID(String str) {
        this.AppID = str;
    }

    public String getUID() {
        return this.UID;
    }

    public void setUID(String str) {
        this.UID = str;
    }

    public boolean isCompress() {
        return this.isCompress;
    }

    public void setCompress(boolean z) {
        this.isCompress = z;
    }

    public String getEN_Color() {
        return this.EN_Color;
    }

    public void setEN_Color(String str) {
        this.EN_Color = str;
    }

    public int getWidth() {
        return this.width;
    }

    public void setWidth(int i) {
        this.width = i;
    }

    public int getHeight() {
        return this.height;
    }

    public void setHeight(int i) {
        this.height = i;
    }

    public int getDeviceType() {
        return this.deviceType;
    }

    public void setDeviceType(int i) {
        this.deviceType = i;
    }

    public void setPin(boolean z) {
        this.isPin = z;
    }

    public boolean getPin() {
        return this.isPin;
    }

    public int getSize() {
        return this.size;
    }

    public void setSize(int i) {
        this.size = i;
    }

    public int getBlack() {
        return this.black;
    }

    public void setBlack(int i) {
        this.black = i;
    }

    public int getWhite() {
        return this.white;
    }

    public void setWhite(int i) {
        this.white = i;
    }

    public int getRed() {
        return this.red;
    }

    public void setRed(int i) {
        this.red = i;
    }

    public int getYellow() {
        return this.yellow;
    }

    public void setYellow(int i) {
        this.yellow = i;
    }

    public int getColorCount() {
        return this.colorCount;
    }

    public void setColorCount(int i) {
        this.colorCount = i;
    }

    public int getCosVersion() {
        return this.cosVersion;
    }

    public void setCosVersion(int i) {
        this.cosVersion = i;
    }

    public void setColorDesc(String str) {
        this.color_desc = str;
    }

    public String getColorDesc() {
        return this.color_desc;
    }

    public int getRefreshScan() {
        return this.refreshScan;
    }

    public void setRefreshScan(int i) {
        this.refreshScan = i;
    }

    public int getUserData() {
        return this.userData;
    }

    public void setUserData(int i) {
        this.userData = i;
    }

    public String getPinCode() {
        return this.pinCode;
    }

    public Bitmap getBitmap() {
        return this.mBitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.mBitmap = bitmap;
    }

    public void setPinCode(String str) {
        this.pinCode = str;
    }

    public byte[] getCompressData() {
        return this.compressData;
    }

    public void setCompressData(byte[] bArr) {
        this.compressData = bArr;
    }
}
