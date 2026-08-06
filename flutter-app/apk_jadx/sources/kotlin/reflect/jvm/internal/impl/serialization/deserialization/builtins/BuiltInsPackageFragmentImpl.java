package kotlin.reflect.jvm.internal.impl.serialization.deserialization.builtins;

import java.io.InputStream;
import kotlin.io.CloseableKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.builtins.BuiltInsPackageFragment;
import kotlin.reflect.jvm.internal.impl.descriptors.ModuleDescriptor;
import kotlin.reflect.jvm.internal.impl.metadata.ProtoBuf;
import kotlin.reflect.jvm.internal.impl.metadata.builtins.BuiltInsBinaryVersion;
import kotlin.reflect.jvm.internal.impl.name.FqName;
import kotlin.reflect.jvm.internal.impl.serialization.deserialization.DeserializedPackageFragmentImpl;
import kotlin.reflect.jvm.internal.impl.storage.StorageManager;

/* JADX INFO: compiled from: BuiltInsPackageFragmentImpl.kt */
/* JADX INFO: loaded from: classes2.dex */
public final class BuiltInsPackageFragmentImpl extends DeserializedPackageFragmentImpl implements BuiltInsPackageFragment {
    public static final Companion Companion = new Companion(null);
    private final boolean isFallback;

    public /* synthetic */ BuiltInsPackageFragmentImpl(FqName fqName, StorageManager storageManager, ModuleDescriptor moduleDescriptor, ProtoBuf.PackageFragment packageFragment, BuiltInsBinaryVersion builtInsBinaryVersion, boolean z, DefaultConstructorMarker defaultConstructorMarker) {
        this(fqName, storageManager, moduleDescriptor, packageFragment, builtInsBinaryVersion, z);
    }

    private BuiltInsPackageFragmentImpl(FqName fqName, StorageManager storageManager, ModuleDescriptor moduleDescriptor, ProtoBuf.PackageFragment packageFragment, BuiltInsBinaryVersion builtInsBinaryVersion, boolean z) {
        super(fqName, storageManager, moduleDescriptor, packageFragment, builtInsBinaryVersion, null);
        this.isFallback = z;
    }

    /* JADX INFO: compiled from: BuiltInsPackageFragmentImpl.kt */
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final BuiltInsPackageFragmentImpl create(FqName fqName, StorageManager storageManager, ModuleDescriptor module, InputStream inputStream, boolean z) {
            Intrinsics.checkNotNullParameter(fqName, "fqName");
            Intrinsics.checkNotNullParameter(storageManager, "storageManager");
            Intrinsics.checkNotNullParameter(module, "module");
            Intrinsics.checkNotNullParameter(inputStream, "inputStream");
            InputStream inputStream2 = inputStream;
            Throwable th = (Throwable) null;
            try {
                InputStream inputStream3 = inputStream2;
                BuiltInsBinaryVersion from = BuiltInsBinaryVersion.Companion.readFrom(inputStream3);
                if (from != null) {
                    if (!from.isCompatible()) {
                        throw new UnsupportedOperationException("Kotlin built-in definition format version is not supported: expected " + BuiltInsBinaryVersion.INSTANCE + ", actual " + from + ". Please update Kotlin");
                    }
                    ProtoBuf.PackageFragment proto = ProtoBuf.PackageFragment.parseFrom(inputStream3, BuiltInSerializerProtocol.INSTANCE.getExtensionRegistry());
                    CloseableKt.closeFinally(inputStream2, th);
                    Intrinsics.checkNotNullExpressionValue(proto, "proto");
                    return new BuiltInsPackageFragmentImpl(fqName, storageManager, module, proto, from, z, null);
                }
                Intrinsics.throwUninitializedPropertyAccessException("version");
                throw null;
            } catch (Throwable th2) {
                try {
                    throw th2;
                } catch (Throwable th3) {
                    CloseableKt.closeFinally(inputStream2, th2);
                    throw th3;
                }
            }
        }
    }
}
