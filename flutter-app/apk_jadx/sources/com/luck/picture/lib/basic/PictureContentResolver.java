package com.luck.picture.lib.basic;

import android.content.Context;
import android.net.Uri;
import java.io.InputStream;
import java.io.OutputStream;

/* JADX INFO: loaded from: classes.dex */
public final class PictureContentResolver {
    public static InputStream openInputStream(Context context, Uri uri) {
        try {
            return context.getContentResolver().openInputStream(uri);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static OutputStream openOutputStream(Context context, Uri uri) {
        try {
            return context.getContentResolver().openOutputStream(uri);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
