package androidx.room;

import androidx.sqlite.db.SupportSQLiteOpenHelper;

/* JADX INFO: loaded from: classes.dex */
interface DelegatingOpenHelper {
    SupportSQLiteOpenHelper getDelegate();
}
