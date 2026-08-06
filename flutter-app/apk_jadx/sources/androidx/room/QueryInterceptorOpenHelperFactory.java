package androidx.room;

import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteOpenHelper;
import java.util.concurrent.Executor;

/* JADX INFO: loaded from: classes.dex */
final class QueryInterceptorOpenHelperFactory implements SupportSQLiteOpenHelper.Factory {
    private final SupportSQLiteOpenHelper.Factory mDelegate;
    private final RoomDatabase.QueryCallback mQueryCallback;
    private final Executor mQueryCallbackExecutor;

    QueryInterceptorOpenHelperFactory(SupportSQLiteOpenHelper.Factory factory, RoomDatabase.QueryCallback queryCallback, Executor executor) {
        this.mDelegate = factory;
        this.mQueryCallback = queryCallback;
        this.mQueryCallbackExecutor = executor;
    }

    @Override // androidx.sqlite.db.SupportSQLiteOpenHelper.Factory
    public SupportSQLiteOpenHelper create(SupportSQLiteOpenHelper.Configuration configuration) {
        return new QueryInterceptorOpenHelper(this.mDelegate.create(configuration), this.mQueryCallback, this.mQueryCallbackExecutor);
    }
}
