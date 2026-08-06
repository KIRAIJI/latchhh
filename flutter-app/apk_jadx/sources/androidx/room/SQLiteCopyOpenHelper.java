package androidx.room;

import android.content.Context;
import android.util.Log;
import androidx.room.util.CopyLock;
import androidx.room.util.DBUtil;
import androidx.room.util.FileUtil;
import androidx.sqlite.db.SupportSQLiteDatabase;
import androidx.sqlite.db.SupportSQLiteOpenHelper;
import androidx.sqlite.db.framework.FrameworkSQLiteOpenHelperFactory;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.util.concurrent.Callable;

/* JADX INFO: loaded from: classes.dex */
class SQLiteCopyOpenHelper implements SupportSQLiteOpenHelper, DelegatingOpenHelper {
    private final Context mContext;
    private final String mCopyFromAssetPath;
    private final File mCopyFromFile;
    private final Callable<InputStream> mCopyFromInputStream;
    private DatabaseConfiguration mDatabaseConfiguration;
    private final int mDatabaseVersion;
    private final SupportSQLiteOpenHelper mDelegate;
    private boolean mVerified;

    SQLiteCopyOpenHelper(Context context, String str, File file, Callable<InputStream> callable, int i, SupportSQLiteOpenHelper supportSQLiteOpenHelper) {
        this.mContext = context;
        this.mCopyFromAssetPath = str;
        this.mCopyFromFile = file;
        this.mCopyFromInputStream = callable;
        this.mDatabaseVersion = i;
        this.mDelegate = supportSQLiteOpenHelper;
    }

    @Override // androidx.sqlite.db.SupportSQLiteOpenHelper
    public String getDatabaseName() {
        return this.mDelegate.getDatabaseName();
    }

    @Override // androidx.sqlite.db.SupportSQLiteOpenHelper
    public void setWriteAheadLoggingEnabled(boolean z) {
        this.mDelegate.setWriteAheadLoggingEnabled(z);
    }

    @Override // androidx.sqlite.db.SupportSQLiteOpenHelper
    public synchronized SupportSQLiteDatabase getWritableDatabase() {
        if (!this.mVerified) {
            verifyDatabaseFile(true);
            this.mVerified = true;
        }
        return this.mDelegate.getWritableDatabase();
    }

    @Override // androidx.sqlite.db.SupportSQLiteOpenHelper
    public synchronized SupportSQLiteDatabase getReadableDatabase() {
        if (!this.mVerified) {
            verifyDatabaseFile(false);
            this.mVerified = true;
        }
        return this.mDelegate.getReadableDatabase();
    }

    @Override // androidx.sqlite.db.SupportSQLiteOpenHelper, java.io.Closeable, java.lang.AutoCloseable
    public synchronized void close() {
        this.mDelegate.close();
        this.mVerified = false;
    }

    @Override // androidx.room.DelegatingOpenHelper
    public SupportSQLiteOpenHelper getDelegate() {
        return this.mDelegate;
    }

    void setDatabaseConfiguration(DatabaseConfiguration databaseConfiguration) {
        this.mDatabaseConfiguration = databaseConfiguration;
    }

    private void verifyDatabaseFile(boolean z) {
        String databaseName = getDatabaseName();
        File databasePath = this.mContext.getDatabasePath(databaseName);
        DatabaseConfiguration databaseConfiguration = this.mDatabaseConfiguration;
        CopyLock copyLock = new CopyLock(databaseName, this.mContext.getFilesDir(), databaseConfiguration == null || databaseConfiguration.multiInstanceInvalidation);
        try {
            copyLock.lock();
            if (!databasePath.exists()) {
                try {
                    copyDatabaseFile(databasePath, z);
                    copyLock.unlock();
                    return;
                } catch (IOException e) {
                    throw new RuntimeException("Unable to copy database file.", e);
                }
            }
            if (this.mDatabaseConfiguration == null) {
                copyLock.unlock();
                return;
            }
            try {
                int version = DBUtil.readVersion(databasePath);
                int i = this.mDatabaseVersion;
                if (version == i) {
                    copyLock.unlock();
                    return;
                }
                if (this.mDatabaseConfiguration.isMigrationRequired(version, i)) {
                    copyLock.unlock();
                    return;
                }
                if (this.mContext.deleteDatabase(databaseName)) {
                    try {
                        copyDatabaseFile(databasePath, z);
                    } catch (IOException e2) {
                        Log.w("ROOM", "Unable to copy database file.", e2);
                    }
                } else {
                    Log.w("ROOM", "Failed to delete database file (" + databaseName + ") for a copy destructive migration.");
                }
                copyLock.unlock();
                return;
            } catch (IOException e3) {
                Log.w("ROOM", "Unable to read database version.", e3);
                copyLock.unlock();
                return;
            }
        } catch (Throwable th) {
            copyLock.unlock();
            throw th;
        }
        copyLock.unlock();
        throw th;
    }

    private void copyDatabaseFile(File file, boolean z) throws IOException {
        ReadableByteChannel readableByteChannelNewChannel;
        if (this.mCopyFromAssetPath != null) {
            readableByteChannelNewChannel = Channels.newChannel(this.mContext.getAssets().open(this.mCopyFromAssetPath));
        } else if (this.mCopyFromFile != null) {
            readableByteChannelNewChannel = new FileInputStream(this.mCopyFromFile).getChannel();
        } else {
            Callable<InputStream> callable = this.mCopyFromInputStream;
            if (callable != null) {
                try {
                    readableByteChannelNewChannel = Channels.newChannel(callable.call());
                } catch (Exception e) {
                    throw new IOException("inputStreamCallable exception on call", e);
                }
            } else {
                throw new IllegalStateException("copyFromAssetPath, copyFromFile and copyFromInputStream are all null!");
            }
        }
        File fileCreateTempFile = File.createTempFile("room-copy-helper", ".tmp", this.mContext.getCacheDir());
        fileCreateTempFile.deleteOnExit();
        FileUtil.copy(readableByteChannelNewChannel, new FileOutputStream(fileCreateTempFile).getChannel());
        File parentFile = file.getParentFile();
        if (parentFile != null && !parentFile.exists() && !parentFile.mkdirs()) {
            throw new IOException("Failed to create directories for " + file.getAbsolutePath());
        }
        dispatchOnOpenPrepackagedDatabase(fileCreateTempFile, z);
        if (fileCreateTempFile.renameTo(file)) {
            return;
        }
        throw new IOException("Failed to move intermediate file (" + fileCreateTempFile.getAbsolutePath() + ") to destination (" + file.getAbsolutePath() + ").");
    }

    private void dispatchOnOpenPrepackagedDatabase(File file, boolean z) {
        SupportSQLiteDatabase readableDatabase;
        DatabaseConfiguration databaseConfiguration = this.mDatabaseConfiguration;
        if (databaseConfiguration == null || databaseConfiguration.prepackagedDatabaseCallback == null) {
            return;
        }
        SupportSQLiteOpenHelper supportSQLiteOpenHelperCreateFrameworkOpenHelper = createFrameworkOpenHelper(file);
        try {
            if (z) {
                readableDatabase = supportSQLiteOpenHelperCreateFrameworkOpenHelper.getWritableDatabase();
            } else {
                readableDatabase = supportSQLiteOpenHelperCreateFrameworkOpenHelper.getReadableDatabase();
            }
            this.mDatabaseConfiguration.prepackagedDatabaseCallback.onOpenPrepackagedDatabase(readableDatabase);
        } finally {
            supportSQLiteOpenHelperCreateFrameworkOpenHelper.close();
        }
    }

    private SupportSQLiteOpenHelper createFrameworkOpenHelper(File file) {
        try {
            return new FrameworkSQLiteOpenHelperFactory().create(SupportSQLiteOpenHelper.Configuration.builder(this.mContext).name(file.getAbsolutePath()).callback(new SupportSQLiteOpenHelper.Callback(Math.max(DBUtil.readVersion(file), 1)) { // from class: androidx.room.SQLiteCopyOpenHelper.1
                @Override // androidx.sqlite.db.SupportSQLiteOpenHelper.Callback
                public void onCreate(SupportSQLiteDatabase supportSQLiteDatabase) {
                }

                @Override // androidx.sqlite.db.SupportSQLiteOpenHelper.Callback
                public void onUpgrade(SupportSQLiteDatabase supportSQLiteDatabase, int i, int i2) {
                }

                @Override // androidx.sqlite.db.SupportSQLiteOpenHelper.Callback
                public void onOpen(SupportSQLiteDatabase supportSQLiteDatabase) {
                    if (this.version < 1) {
                        supportSQLiteDatabase.setVersion(this.version);
                    }
                }
            }).build());
        } catch (IOException e) {
            throw new RuntimeException("Malformed database file, unable to read version.", e);
        }
    }
}
