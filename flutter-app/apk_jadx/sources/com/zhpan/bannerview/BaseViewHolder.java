package com.zhpan.bannerview;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.util.SparseArray;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

/* JADX INFO: loaded from: classes.dex */
public class BaseViewHolder<T> extends RecyclerView.ViewHolder {
    private final SparseArray<View> mViews;

    @Deprecated
    public void bindData(T t, int i, int i2) {
    }

    public BaseViewHolder(View view) {
        super(view);
        this.mViews = new SparseArray<>();
    }

    public <V extends View> V findViewById(int i) {
        V v = (V) this.mViews.get(i);
        if (v != null) {
            return v;
        }
        V v2 = (V) this.itemView.findViewById(i);
        this.mViews.put(i, v2);
        return v2;
    }

    public void setText(int i, CharSequence charSequence) {
        View viewFindViewById = findViewById(i);
        if (viewFindViewById instanceof TextView) {
            ((TextView) viewFindViewById).setText(charSequence);
        }
    }

    public void setText(int i, int i2) {
        View viewFindViewById = findViewById(i);
        if (viewFindViewById instanceof TextView) {
            ((TextView) viewFindViewById).setText(i2);
        }
    }

    public void setTextColor(int i, int i2) {
        View viewFindViewById = findViewById(i);
        if (viewFindViewById instanceof TextView) {
            ((TextView) viewFindViewById).setTextColor(i2);
        }
    }

    public void setTextColorRes(int i, int i2) {
        View viewFindViewById = findViewById(i);
        if (viewFindViewById instanceof TextView) {
            ((TextView) viewFindViewById).setTextColor(ContextCompat.getColor(this.itemView.getContext(), i2));
        }
    }

    public void setOnClickListener(int i, View.OnClickListener onClickListener) {
        findViewById(i).setOnClickListener(onClickListener);
    }

    public void setBackgroundResource(int i, int i2) {
        findViewById(i).setBackgroundResource(i2);
    }

    public void setBackgroundColor(int i, int i2) {
        findViewById(i).setBackgroundColor(i2);
    }

    public void setImageResource(int i, int i2) {
        View viewFindViewById = findViewById(i);
        if (viewFindViewById instanceof ImageView) {
            ((ImageView) viewFindViewById).setImageResource(i2);
        }
    }

    public void setImageDrawable(int i, Drawable drawable) {
        View viewFindViewById = findViewById(i);
        if (viewFindViewById instanceof ImageView) {
            ((ImageView) viewFindViewById).setImageDrawable(drawable);
        }
    }

    public void setImageBitmap(int i, Bitmap bitmap) {
        ((ImageView) findViewById(i)).setImageBitmap(bitmap);
    }

    public void setVisibility(int i, int i2) {
        findViewById(i).setVisibility(i2);
    }
}
