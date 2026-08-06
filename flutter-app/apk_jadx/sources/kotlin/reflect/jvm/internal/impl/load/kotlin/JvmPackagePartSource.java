package kotlin.reflect.jvm.internal.impl.load.kotlin;

import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.descriptors.SourceFile;
import kotlin.reflect.jvm.internal.impl.metadata.ProtoBuf;
import kotlin.reflect.jvm.internal.impl.metadata.deserialization.NameResolver;
import kotlin.reflect.jvm.internal.impl.metadata.deserialization.ProtoBufUtilKt;
import kotlin.reflect.jvm.internal.impl.metadata.jvm.JvmProtoBuf;
import kotlin.reflect.jvm.internal.impl.metadata.jvm.deserialization.JvmMetadataVersion;
import kotlin.reflect.jvm.internal.impl.name.ClassId;
import kotlin.reflect.jvm.internal.impl.name.Name;
import kotlin.reflect.jvm.internal.impl.protobuf.GeneratedMessageLite;
import kotlin.reflect.jvm.internal.impl.resolve.jvm.JvmClassName;
import kotlin.reflect.jvm.internal.impl.serialization.deserialization.IncompatibleVersionErrorData;
import kotlin.reflect.jvm.internal.impl.serialization.deserialization.descriptors.DeserializedContainerAbiStability;
import kotlin.reflect.jvm.internal.impl.serialization.deserialization.descriptors.DeserializedContainerSource;
import kotlin.text.StringsKt;

/* JADX INFO: compiled from: JvmPackagePartSource.kt */
/* JADX INFO: loaded from: classes2.dex */
public final class JvmPackagePartSource implements DeserializedContainerSource {
    private final DeserializedContainerAbiStability abiStability;
    private final JvmClassName className;
    private final JvmClassName facadeClassName;
    private final IncompatibleVersionErrorData<JvmMetadataVersion> incompatibility;
    private final boolean isPreReleaseInvisible;
    private final KotlinJvmBinaryClass knownJvmBinaryClass;
    private final String moduleName;

    public JvmPackagePartSource(JvmClassName className, JvmClassName jvmClassName, ProtoBuf.Package packageProto, NameResolver nameResolver, IncompatibleVersionErrorData<JvmMetadataVersion> incompatibleVersionErrorData, boolean z, DeserializedContainerAbiStability abiStability, KotlinJvmBinaryClass kotlinJvmBinaryClass) {
        String string;
        Intrinsics.checkNotNullParameter(className, "className");
        Intrinsics.checkNotNullParameter(packageProto, "packageProto");
        Intrinsics.checkNotNullParameter(nameResolver, "nameResolver");
        Intrinsics.checkNotNullParameter(abiStability, "abiStability");
        this.className = className;
        this.facadeClassName = jvmClassName;
        this.incompatibility = incompatibleVersionErrorData;
        this.isPreReleaseInvisible = z;
        this.abiStability = abiStability;
        this.knownJvmBinaryClass = kotlinJvmBinaryClass;
        GeneratedMessageLite.GeneratedExtension<ProtoBuf.Package, Integer> packageModuleName = JvmProtoBuf.packageModuleName;
        Intrinsics.checkNotNullExpressionValue(packageModuleName, "packageModuleName");
        Integer num = (Integer) ProtoBufUtilKt.getExtensionOrNull(packageProto, packageModuleName);
        String str = "main";
        if (num != null && (string = nameResolver.getString(num.intValue())) != null) {
            str = string;
        }
        this.moduleName = str;
    }

    public final JvmClassName getFacadeClassName() {
        return this.facadeClassName;
    }

    public final KotlinJvmBinaryClass getKnownJvmBinaryClass() {
        return this.knownJvmBinaryClass;
    }

    /* JADX WARN: Illegal instructions before constructor call */
    public JvmPackagePartSource(KotlinJvmBinaryClass kotlinClass, ProtoBuf.Package packageProto, NameResolver nameResolver, IncompatibleVersionErrorData<JvmMetadataVersion> incompatibleVersionErrorData, boolean z, DeserializedContainerAbiStability abiStability) {
        Intrinsics.checkNotNullParameter(kotlinClass, "kotlinClass");
        Intrinsics.checkNotNullParameter(packageProto, "packageProto");
        Intrinsics.checkNotNullParameter(nameResolver, "nameResolver");
        Intrinsics.checkNotNullParameter(abiStability, "abiStability");
        JvmClassName jvmClassNameByClassId = JvmClassName.byClassId(kotlinClass.getClassId());
        Intrinsics.checkNotNullExpressionValue(jvmClassNameByClassId, "byClassId(kotlinClass.classId)");
        String multifileClassName = kotlinClass.getClassHeader().getMultifileClassName();
        JvmClassName jvmClassNameByInternalName = null;
        if (multifileClassName != null) {
            if (multifileClassName.length() > 0) {
                jvmClassNameByInternalName = JvmClassName.byInternalName(multifileClassName);
            }
        }
        this(jvmClassNameByClassId, jvmClassNameByInternalName, packageProto, nameResolver, incompatibleVersionErrorData, z, abiStability, kotlinClass);
    }

    @Override // kotlin.reflect.jvm.internal.impl.serialization.deserialization.descriptors.DeserializedContainerSource
    public String getPresentableString() {
        return "Class '" + getClassId().asSingleFqName().asString() + '\'';
    }

    public final Name getSimpleName() {
        String internalName = this.className.getInternalName();
        Intrinsics.checkNotNullExpressionValue(internalName, "className.internalName");
        Name nameIdentifier = Name.identifier(StringsKt.substringAfterLast$default(internalName, '/', (String) null, 2, (Object) null));
        Intrinsics.checkNotNullExpressionValue(nameIdentifier, "identifier(className.internalName.substringAfterLast('/'))");
        return nameIdentifier;
    }

    public final ClassId getClassId() {
        return new ClassId(this.className.getPackageFqName(), getSimpleName());
    }

    public String toString() {
        return ((Object) getClass().getSimpleName()) + ": " + this.className;
    }

    @Override // kotlin.reflect.jvm.internal.impl.descriptors.SourceElement
    public SourceFile getContainingFile() {
        SourceFile NO_SOURCE_FILE = SourceFile.NO_SOURCE_FILE;
        Intrinsics.checkNotNullExpressionValue(NO_SOURCE_FILE, "NO_SOURCE_FILE");
        return NO_SOURCE_FILE;
    }
}
