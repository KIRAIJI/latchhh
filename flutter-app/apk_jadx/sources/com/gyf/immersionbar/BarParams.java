package com.gyf.immersionbar;

import android.view.View;
import java.util.HashMap;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
public class BarParams implements Cloneable {
    public int flymeOSStatusBarFontColor;
    public int flymeOSStatusBarFontTempColor;
    OnBarListener onBarListener;
    OnKeyboardListener onKeyboardListener;
    OnNavigationBarListener onNavigationBarListener;
    public View statusBarView;
    public View titleBarView;
    public int statusBarColor = 0;
    public int navigationBarColor = -16777216;
    public int defaultNavigationBarColor = -16777216;
    public float statusBarAlpha = 0.0f;
    public float statusBarTempAlpha = 0.0f;
    public float navigationBarAlpha = 0.0f;
    public float navigationBarTempAlpha = 0.0f;
    public boolean fullScreen = false;
    public boolean hideNavigationBar = false;
    public BarHide barHide = BarHide.FLAG_SHOW_BAR;
    public boolean statusBarDarkFont = false;
    public boolean navigationBarDarkIcon = false;
    public boolean autoStatusBarDarkModeEnable = false;
    public boolean autoNavigationBarDarkModeEnable = false;
    public float autoStatusBarDarkModeAlpha = 0.0f;
    public float autoNavigationBarDarkModeAlpha = 0.0f;
    public boolean statusBarColorEnabled = true;
    public int statusBarColorTransform = -16777216;
    public int navigationBarColorTransform = -16777216;
    Map<View, Map<Integer, Integer>> viewMap = new HashMap();
    public float viewAlpha = 0.0f;
    public int contentColor = 0;
    public int contentColorTransform = -16777216;
    public float contentAlpha = 0.0f;
    public boolean fits = false;
    public boolean fitsLayoutOverlapEnable = true;
    public boolean isSupportActionBar = false;
    public boolean keyboardEnable = false;
    public int keyboardMode = 18;
    public boolean navigationBarEnable = true;
    public boolean navigationBarWithKitkatEnable = true;
    public boolean navigationBarWithEMUI3Enable = true;
    public boolean barEnable = true;

    /* JADX INFO: Access modifiers changed from: protected */
    /* JADX INFO: renamed from: clone, reason: merged with bridge method [inline-methods] */
    public BarParams m89clone() {
        try {
            return (BarParams) super.clone();
        } catch (CloneNotSupportedException unused) {
            return null;
        }
    }
}
