package com.lazyee.klib.json;

import androidx.exifinterface.media.ExifInterface;
import kotlin.Metadata;

/* JADX INFO: compiled from: JSON.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata(bv = {1, 0, 3}, d1 = {"\u0000\u001c\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0000\n\u0002\b\u0006\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\bf\u0018\u0000*\u0004\b\u0000\u0010\u00012\u00020\u0002J-\u0010\u0006\u001a\u0004\u0018\u0001H\u0007\"\u0004\b\u0001\u0010\u00072\b\u0010\b\u001a\u0004\u0018\u00010\t2\f\u0010\n\u001a\b\u0012\u0004\u0012\u0002H\u00070\u000bH&¢\u0006\u0002\u0010\fJ\u001f\u0010\r\u001a\u0004\u0018\u00010\t\"\u0004\b\u0001\u0010\u00072\b\u0010\u000e\u001a\u0004\u0018\u0001H\u0007H&¢\u0006\u0002\u0010\u000fR\u0012\u0010\u0003\u001a\u00028\u0000X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0004\u0010\u0005¨\u0006\u0010"}, d2 = {"Lcom/lazyee/klib/json/JSON;", ExifInterface.GPS_DIRECTION_TRUE, "", "instance", "getInstance", "()Ljava/lang/Object;", "fromJson", "R", "json", "", "clazz", "Ljava/lang/Class;", "(Ljava/lang/String;Ljava/lang/Class;)Ljava/lang/Object;", "toJson", "entity", "(Ljava/lang/Object;)Ljava/lang/String;", "library_release"}, k = 1, mv = {1, 4, 2})
public interface JSON<T> {
    <R> R fromJson(String json, Class<R> clazz);

    T getInstance();

    <R> String toJson(R entity);
}
