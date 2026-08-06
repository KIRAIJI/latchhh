package com.gyf.immersionbar;

import android.graphics.Rect;
import android.os.Build;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.widget.FrameLayout;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

/* JADX INFO: loaded from: classes.dex */
class FitsKeyboard implements ViewTreeObserver.OnGlobalLayoutListener {
    private View mChildView;
    private View mContentView;
    private View mDecorView;
    private ImmersionBar mImmersionBar;
    private boolean mIsAddListener;
    private int mPaddingBottom;
    private int mPaddingLeft;
    private int mPaddingRight;
    private int mPaddingTop;
    private int mTempKeyboardHeight;
    private Window mWindow;

    FitsKeyboard(ImmersionBar immersionBar) {
        this.mPaddingLeft = 0;
        this.mPaddingTop = 0;
        this.mPaddingRight = 0;
        this.mPaddingBottom = 0;
        this.mImmersionBar = immersionBar;
        Window window = immersionBar.getWindow();
        this.mWindow = window;
        View decorView = window.getDecorView();
        this.mDecorView = decorView;
        FrameLayout frameLayout = (FrameLayout) decorView.findViewById(android.R.id.content);
        if (immersionBar.isDialogFragment()) {
            Fragment supportFragment = immersionBar.getSupportFragment();
            if (supportFragment != null) {
                this.mChildView = supportFragment.getView();
            } else {
                android.app.Fragment fragment = immersionBar.getFragment();
                if (fragment != null) {
                    this.mChildView = fragment.getView();
                }
            }
        } else {
            View childAt = frameLayout.getChildAt(0);
            this.mChildView = childAt;
            if (childAt != null && (childAt instanceof DrawerLayout)) {
                this.mChildView = ((DrawerLayout) childAt).getChildAt(0);
            }
        }
        View view = this.mChildView;
        if (view != null) {
            this.mPaddingLeft = view.getPaddingLeft();
            this.mPaddingTop = this.mChildView.getPaddingTop();
            this.mPaddingRight = this.mChildView.getPaddingRight();
            this.mPaddingBottom = this.mChildView.getPaddingBottom();
        }
        View view2 = this.mChildView;
        this.mContentView = view2 != null ? view2 : frameLayout;
    }

    void enable(int i) {
        if (Build.VERSION.SDK_INT >= 19) {
            this.mWindow.setSoftInputMode(i);
            if (this.mIsAddListener) {
                return;
            }
            this.mDecorView.getViewTreeObserver().addOnGlobalLayoutListener(this);
            this.mIsAddListener = true;
        }
    }

    void disable() {
        if (Build.VERSION.SDK_INT < 19 || !this.mIsAddListener) {
            return;
        }
        if (this.mChildView != null) {
            this.mContentView.setPadding(this.mPaddingLeft, this.mPaddingTop, this.mPaddingRight, this.mPaddingBottom);
        } else {
            this.mContentView.setPadding(this.mImmersionBar.getPaddingLeft(), this.mImmersionBar.getPaddingTop(), this.mImmersionBar.getPaddingRight(), this.mImmersionBar.getPaddingBottom());
        }
    }

    void cancel() {
        if (Build.VERSION.SDK_INT < 19 || !this.mIsAddListener) {
            return;
        }
        this.mDecorView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
        this.mIsAddListener = false;
    }

    void resetKeyboardHeight() {
        this.mTempKeyboardHeight = 0;
    }

    @Override // android.view.ViewTreeObserver.OnGlobalLayoutListener
    public void onGlobalLayout() {
        int i;
        ImmersionBar immersionBar = this.mImmersionBar;
        if (immersionBar == null || immersionBar.getBarParams() == null || !this.mImmersionBar.getBarParams().keyboardEnable) {
            return;
        }
        BarConfig barConfig = this.mImmersionBar.getBarConfig();
        int navigationBarHeight = barConfig.isNavigationAtBottom() ? barConfig.getNavigationBarHeight() : barConfig.getNavigationBarWidth();
        Rect rect = new Rect();
        this.mDecorView.getWindowVisibleDisplayFrame(rect);
        int height = this.mContentView.getHeight() - rect.bottom;
        if (height != this.mTempKeyboardHeight) {
            this.mTempKeyboardHeight = height;
            boolean z = true;
            if (ImmersionBar.checkFitsSystemWindows(this.mWindow.getDecorView().findViewById(android.R.id.content))) {
                height -= navigationBarHeight;
                if (height <= navigationBarHeight) {
                    z = false;
                }
            } else if (this.mChildView != null) {
                if (this.mImmersionBar.getBarParams().isSupportActionBar) {
                    height += this.mImmersionBar.getActionBarHeight() + barConfig.getStatusBarHeight();
                }
                if (this.mImmersionBar.getBarParams().fits) {
                    height += barConfig.getStatusBarHeight();
                }
                if (height > navigationBarHeight) {
                    i = this.mPaddingBottom + height;
                } else {
                    i = 0;
                    z = false;
                }
                this.mContentView.setPadding(this.mPaddingLeft, this.mPaddingTop, this.mPaddingRight, i);
            } else {
                int paddingBottom = this.mImmersionBar.getPaddingBottom();
                height -= navigationBarHeight;
                if (height > navigationBarHeight) {
                    paddingBottom = height + navigationBarHeight;
                } else {
                    z = false;
                }
                this.mContentView.setPadding(this.mImmersionBar.getPaddingLeft(), this.mImmersionBar.getPaddingTop(), this.mImmersionBar.getPaddingRight(), paddingBottom);
            }
            int i2 = height >= 0 ? height : 0;
            if (this.mImmersionBar.getBarParams().onKeyboardListener != null) {
                this.mImmersionBar.getBarParams().onKeyboardListener.onKeyboardChange(z, i2);
            }
            if (!z && this.mImmersionBar.getBarParams().barHide != BarHide.FLAG_SHOW_BAR) {
                this.mImmersionBar.setBar();
            }
            if (z) {
                return;
            }
            this.mImmersionBar.fitsParentBarKeyboard();
        }
    }
}
