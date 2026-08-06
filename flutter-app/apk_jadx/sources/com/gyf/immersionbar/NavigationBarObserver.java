package com.gyf.immersionbar;

import android.app.Application;
import android.database.ContentObserver;
import android.os.Handler;
import android.os.Looper;
import com.gyf.immersionbar.GestureUtils;
import java.util.ArrayList;
import java.util.Iterator;

/* JADX INFO: loaded from: classes.dex */
final class NavigationBarObserver extends ContentObserver {
    private Application mApplication;
    private boolean mIsRegister;
    private ArrayList<OnNavigationBarListener> mListeners;

    static NavigationBarObserver getInstance() {
        return NavigationBarObserverInstance.INSTANCE;
    }

    private NavigationBarObserver() {
        super(new Handler(Looper.getMainLooper()));
        this.mIsRegister = false;
    }

    /* JADX WARN: Removed duplicated region for block: B:50:0x00ba  */
    /* JADX WARN: Removed duplicated region for block: B:52:0x00c7  */
    /* JADX WARN: Removed duplicated region for block: B:54:0x00d2  */
    /* JADX WARN: Removed duplicated region for block: B:60:? A[RETURN, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    void register(android.app.Application r6) {
        /*
            Method dump skipped, instruction units count: 220
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.gyf.immersionbar.NavigationBarObserver.register(android.app.Application):void");
    }

    @Override // android.database.ContentObserver
    public void onChange(boolean z) {
        super.onChange(z);
        ArrayList<OnNavigationBarListener> arrayList = this.mListeners;
        if (arrayList == null || arrayList.isEmpty()) {
            return;
        }
        GestureUtils.GestureBean gestureBean = GestureUtils.getGestureBean(this.mApplication);
        boolean z2 = false;
        if (!gestureBean.isGesture || (gestureBean.checkNavigation && BarConfig.getNavigationBarHeightInternal(this.mApplication) > 0)) {
            z2 = true;
        }
        Iterator<OnNavigationBarListener> it = this.mListeners.iterator();
        while (it.hasNext()) {
            it.next().onNavigationBarChange(z2, gestureBean.type);
        }
    }

    void addOnNavigationBarListener(OnNavigationBarListener onNavigationBarListener) {
        if (onNavigationBarListener == null) {
            return;
        }
        if (this.mListeners == null) {
            this.mListeners = new ArrayList<>();
        }
        if (this.mListeners.contains(onNavigationBarListener)) {
            return;
        }
        this.mListeners.add(onNavigationBarListener);
    }

    void removeOnNavigationBarListener(OnNavigationBarListener onNavigationBarListener) {
        ArrayList<OnNavigationBarListener> arrayList;
        if (onNavigationBarListener == null || (arrayList = this.mListeners) == null) {
            return;
        }
        arrayList.remove(onNavigationBarListener);
    }

    private static class NavigationBarObserverInstance {
        private static final NavigationBarObserver INSTANCE = new NavigationBarObserver();

        private NavigationBarObserverInstance() {
        }
    }
}
