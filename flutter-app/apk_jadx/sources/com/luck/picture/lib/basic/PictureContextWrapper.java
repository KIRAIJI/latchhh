package com.luck.picture.lib.basic;

import android.content.Context;
import android.content.ContextWrapper;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.language.PictureLanguageUtils;

/* JADX INFO: loaded from: classes.dex */
public class PictureContextWrapper extends ContextWrapper {
    public PictureContextWrapper(Context context) {
        super(context);
    }

    public static ContextWrapper wrap(Context context, int i, int i2) {
        if (i != -2) {
            PictureLanguageUtils.setAppLanguage(context, i, i2);
        }
        return new PictureContextWrapper(context);
    }

    @Override // android.content.ContextWrapper, android.content.Context
    public Object getSystemService(String str) {
        if (PictureMimeType.MIME_TYPE_PREFIX_AUDIO.equals(str)) {
            return getApplicationContext().getSystemService(str);
        }
        return super.getSystemService(str);
    }
}
