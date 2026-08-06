package com.shangxian.pinkink.db;

import androidx.room.Room;
import com.shangxian.pinkink.app.PinkInkApplication;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: PinkInkRoomDatabase.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata(d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0006\u0010\u0005\u001a\u00020\u0006J\u0006\u0010\u0007\u001a\u00020\u0004R\u0010\u0010\u0003\u001a\u0004\u0018\u00010\u0004X\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006\b"}, d2 = {"Lcom/shangxian/pinkink/db/PinkInkDB;", "", "()V", "instance", "Lcom/shangxian/pinkink/db/PinkInkRoomDatabase;", "close", "", "db", "app_release"}, k = 1, mv = {1, 6, 0}, xi = 48)
public final class PinkInkDB {
    public static final PinkInkDB INSTANCE = new PinkInkDB();
    private static volatile PinkInkRoomDatabase instance;

    private PinkInkDB() {
    }

    public final PinkInkRoomDatabase db() {
        if (instance == null) {
            synchronized (this) {
                if (instance == null) {
                    instance = (PinkInkRoomDatabase) Room.databaseBuilder(PinkInkApplication.INSTANCE.getApplicationContext(), PinkInkRoomDatabase.class, "PinkInkDB").build();
                }
                Unit unit = Unit.INSTANCE;
            }
        }
        PinkInkRoomDatabase pinkInkRoomDatabase = instance;
        Intrinsics.checkNotNull(pinkInkRoomDatabase);
        return pinkInkRoomDatabase;
    }

    public final void close() {
        PinkInkRoomDatabase pinkInkRoomDatabase = instance;
        if (pinkInkRoomDatabase != null) {
            pinkInkRoomDatabase.close();
        }
        instance = null;
    }
}
