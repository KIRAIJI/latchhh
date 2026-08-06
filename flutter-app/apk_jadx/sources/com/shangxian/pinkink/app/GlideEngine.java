package com.shangxian.pinkink.app;

import android.content.Context;
import android.widget.ImageView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.luck.picture.lib.engine.ImageEngine;
import com.luck.picture.lib.utils.ActivityCompatHelper;
import com.shangxian.pinkink.R;

/* JADX INFO: loaded from: classes.dex */
public class GlideEngine implements ImageEngine {
    @Override // com.luck.picture.lib.engine.ImageEngine
    public void loadImage(Context context, String url, ImageView imageView) {
        if (ActivityCompatHelper.assertValidRequest(context)) {
            Glide.with(context).load(url).into(imageView);
        }
    }

    @Override // com.luck.picture.lib.engine.ImageEngine
    public void loadImage(Context context, ImageView imageView, String url, int maxWidth, int maxHeight) {
        if (ActivityCompatHelper.assertValidRequest(context)) {
            Glide.with(context).load(url).override(maxWidth, maxHeight).into(imageView);
        }
    }

    @Override // com.luck.picture.lib.engine.ImageEngine
    public void loadAlbumCover(Context context, String url, ImageView imageView) {
        if (ActivityCompatHelper.assertValidRequest(context)) {
            Glide.with(context).asBitmap().load(url).override(180, 180).sizeMultiplier(0.5f).transform(new CenterCrop(), new RoundedCorners(8)).placeholder(R.drawable.ps_image_placeholder).into(imageView);
        }
    }

    @Override // com.luck.picture.lib.engine.ImageEngine
    public void loadGridImage(Context context, String url, ImageView imageView) {
        if (ActivityCompatHelper.assertValidRequest(context)) {
            Glide.with(context).load(url).override(200, 200).centerCrop().placeholder(R.drawable.ps_image_placeholder).into(imageView);
        }
    }

    @Override // com.luck.picture.lib.engine.ImageEngine
    public void pauseRequests(Context context) {
        if (ActivityCompatHelper.assertValidRequest(context)) {
            Glide.with(context).pauseRequests();
        }
    }

    @Override // com.luck.picture.lib.engine.ImageEngine
    public void resumeRequests(Context context) {
        if (ActivityCompatHelper.assertValidRequest(context)) {
            Glide.with(context).resumeRequests();
        }
    }

    private GlideEngine() {
    }

    private static final class InstanceHolder {
        static final GlideEngine instance = new GlideEngine();

        private InstanceHolder() {
        }
    }

    public static GlideEngine createGlideEngine() {
        return InstanceHolder.instance;
    }
}
