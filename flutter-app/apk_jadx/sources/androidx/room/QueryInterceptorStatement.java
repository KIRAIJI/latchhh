package androidx.room;

import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteStatement;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;

/* JADX INFO: loaded from: classes.dex */
final class QueryInterceptorStatement implements SupportSQLiteStatement {
    private final List<Object> mBindArgsCache = new ArrayList();
    private final SupportSQLiteStatement mDelegate;
    private final RoomDatabase.QueryCallback mQueryCallback;
    private final Executor mQueryCallbackExecutor;
    private final String mSqlStatement;

    QueryInterceptorStatement(SupportSQLiteStatement supportSQLiteStatement, RoomDatabase.QueryCallback queryCallback, String str, Executor executor) {
        this.mDelegate = supportSQLiteStatement;
        this.mQueryCallback = queryCallback;
        this.mSqlStatement = str;
        this.mQueryCallbackExecutor = executor;
    }

    @Override // androidx.sqlite.db.SupportSQLiteStatement
    public void execute() {
        this.mQueryCallbackExecutor.execute(new Runnable() { // from class: androidx.room.QueryInterceptorStatement$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.m49lambda$execute$0$androidxroomQueryInterceptorStatement();
            }
        });
        this.mDelegate.execute();
    }

    /* JADX INFO: renamed from: lambda$execute$0$androidx-room-QueryInterceptorStatement, reason: not valid java name */
    public /* synthetic */ void m49lambda$execute$0$androidxroomQueryInterceptorStatement() {
        this.mQueryCallback.onQuery(this.mSqlStatement, this.mBindArgsCache);
    }

    @Override // androidx.sqlite.db.SupportSQLiteStatement
    public int executeUpdateDelete() {
        this.mQueryCallbackExecutor.execute(new Runnable() { // from class: androidx.room.QueryInterceptorStatement$$ExternalSyntheticLambda2
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.m51x7bfa4cf9();
            }
        });
        return this.mDelegate.executeUpdateDelete();
    }

    /* JADX INFO: renamed from: lambda$executeUpdateDelete$1$androidx-room-QueryInterceptorStatement, reason: not valid java name */
    public /* synthetic */ void m51x7bfa4cf9() {
        this.mQueryCallback.onQuery(this.mSqlStatement, this.mBindArgsCache);
    }

    @Override // androidx.sqlite.db.SupportSQLiteStatement
    public long executeInsert() {
        this.mQueryCallbackExecutor.execute(new Runnable() { // from class: androidx.room.QueryInterceptorStatement$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.m50lambda$executeInsert$2$androidxroomQueryInterceptorStatement();
            }
        });
        return this.mDelegate.executeInsert();
    }

    /* JADX INFO: renamed from: lambda$executeInsert$2$androidx-room-QueryInterceptorStatement, reason: not valid java name */
    public /* synthetic */ void m50lambda$executeInsert$2$androidxroomQueryInterceptorStatement() {
        this.mQueryCallback.onQuery(this.mSqlStatement, this.mBindArgsCache);
    }

    /* JADX INFO: renamed from: lambda$simpleQueryForLong$3$androidx-room-QueryInterceptorStatement, reason: not valid java name */
    public /* synthetic */ void m52xa983133b() {
        this.mQueryCallback.onQuery(this.mSqlStatement, this.mBindArgsCache);
    }

    @Override // androidx.sqlite.db.SupportSQLiteStatement
    public long simpleQueryForLong() {
        this.mQueryCallbackExecutor.execute(new Runnable() { // from class: androidx.room.QueryInterceptorStatement$$ExternalSyntheticLambda3
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.m52xa983133b();
            }
        });
        return this.mDelegate.simpleQueryForLong();
    }

    /* JADX INFO: renamed from: lambda$simpleQueryForString$4$androidx-room-QueryInterceptorStatement, reason: not valid java name */
    public /* synthetic */ void m53x12aaf991() {
        this.mQueryCallback.onQuery(this.mSqlStatement, this.mBindArgsCache);
    }

    @Override // androidx.sqlite.db.SupportSQLiteStatement
    public String simpleQueryForString() {
        this.mQueryCallbackExecutor.execute(new Runnable() { // from class: androidx.room.QueryInterceptorStatement$$ExternalSyntheticLambda4
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.m53x12aaf991();
            }
        });
        return this.mDelegate.simpleQueryForString();
    }

    @Override // androidx.sqlite.db.SupportSQLiteProgram
    public void bindNull(int i) {
        saveArgsToCache(i, this.mBindArgsCache.toArray());
        this.mDelegate.bindNull(i);
    }

    @Override // androidx.sqlite.db.SupportSQLiteProgram
    public void bindLong(int i, long j) {
        saveArgsToCache(i, Long.valueOf(j));
        this.mDelegate.bindLong(i, j);
    }

    @Override // androidx.sqlite.db.SupportSQLiteProgram
    public void bindDouble(int i, double d) {
        saveArgsToCache(i, Double.valueOf(d));
        this.mDelegate.bindDouble(i, d);
    }

    @Override // androidx.sqlite.db.SupportSQLiteProgram
    public void bindString(int i, String str) {
        saveArgsToCache(i, str);
        this.mDelegate.bindString(i, str);
    }

    @Override // androidx.sqlite.db.SupportSQLiteProgram
    public void bindBlob(int i, byte[] bArr) {
        saveArgsToCache(i, bArr);
        this.mDelegate.bindBlob(i, bArr);
    }

    @Override // androidx.sqlite.db.SupportSQLiteProgram
    public void clearBindings() {
        this.mBindArgsCache.clear();
        this.mDelegate.clearBindings();
    }

    @Override // java.io.Closeable, java.lang.AutoCloseable
    public void close() throws IOException {
        this.mDelegate.close();
    }

    private void saveArgsToCache(int i, Object obj) {
        int i2 = i - 1;
        if (i2 >= this.mBindArgsCache.size()) {
            for (int size = this.mBindArgsCache.size(); size <= i2; size++) {
                this.mBindArgsCache.add(null);
            }
        }
        this.mBindArgsCache.set(i2, obj);
    }
}
