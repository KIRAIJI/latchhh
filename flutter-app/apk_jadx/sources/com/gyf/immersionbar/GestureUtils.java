package com.gyf.immersionbar;

/* JADX INFO: loaded from: classes.dex */
class GestureUtils {
    GestureUtils() {
    }

    /* JADX WARN: Removed duplicated region for block: B:40:0x007f A[PHI: r1 r2
  0x007f: PHI (r1v16 com.gyf.immersionbar.NavigationBarType) = (r1v14 com.gyf.immersionbar.NavigationBarType), (r1v27 com.gyf.immersionbar.NavigationBarType) binds: [B:67:0x00d9, B:39:0x007d] A[DONT_GENERATE, DONT_INLINE]
  0x007f: PHI (r2v16 int) = (r2v15 int), (r2v29 int) binds: [B:67:0x00d9, B:39:0x007d] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Removed duplicated region for block: B:80:0x00fd A[PHI: r1 r2
  0x00fd: PHI (r1v5 com.gyf.immersionbar.NavigationBarType) = 
  (r1v3 com.gyf.immersionbar.NavigationBarType)
  (r1v14 com.gyf.immersionbar.NavigationBarType)
  (r1v17 com.gyf.immersionbar.NavigationBarType)
  (r1v18 com.gyf.immersionbar.NavigationBarType)
  (r1v20 com.gyf.immersionbar.NavigationBarType)
  (r1v22 com.gyf.immersionbar.NavigationBarType)
  (r1v27 com.gyf.immersionbar.NavigationBarType)
 binds: [B:79:0x00fb, B:67:0x00d9, B:61:0x00c1, B:59:0x00bc, B:54:0x00ac, B:45:0x0090, B:39:0x007d] A[DONT_GENERATE, DONT_INLINE]
  0x00fd: PHI (r2v7 int) = (r2v4 int), (r2v15 int), (r2v20 int), (r2v20 int), (r2v24 int), (r2v31 int), (r2v29 int) binds: [B:79:0x00fb, B:67:0x00d9, B:61:0x00c1, B:59:0x00bc, B:54:0x00ac, B:45:0x0090, B:39:0x007d] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Removed duplicated region for block: B:84:0x0104  */
    /* JADX WARN: Removed duplicated region for block: B:92:0x011b  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public static com.gyf.immersionbar.GestureUtils.GestureBean getGestureBean(android.content.Context r9) {
        /*
            Method dump skipped, instruction units count: 292
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.gyf.immersionbar.GestureUtils.getGestureBean(android.content.Context):com.gyf.immersionbar.GestureUtils$GestureBean");
    }

    static class GestureBean {
        public NavigationBarType type;
        public boolean isGesture = false;
        public boolean checkNavigation = false;

        GestureBean() {
        }

        public String toString() {
            return "GestureBean{isGesture=" + this.isGesture + ", checkNavigation=" + this.checkNavigation + ", type=" + this.type + '}';
        }
    }
}
