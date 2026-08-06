package kotlin.reflect.jvm.internal.impl.serialization.deserialization.builtins;

import java.util.List;
import kotlin.Unit;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.metadata.ProtoBuf;
import kotlin.reflect.jvm.internal.impl.metadata.builtins.BuiltInsProtoBuf;
import kotlin.reflect.jvm.internal.impl.name.FqName;
import kotlin.reflect.jvm.internal.impl.protobuf.ExtensionRegistryLite;
import kotlin.reflect.jvm.internal.impl.protobuf.GeneratedMessageLite;
import kotlin.reflect.jvm.internal.impl.serialization.SerializerExtensionProtocol;
import kotlin.text.StringsKt;

/* JADX INFO: compiled from: BuiltInSerializerProtocol.kt */
/* JADX INFO: loaded from: classes2.dex */
public final class BuiltInSerializerProtocol extends SerializerExtensionProtocol {
    public static final BuiltInSerializerProtocol INSTANCE = new BuiltInSerializerProtocol();

    /* JADX WARN: Illegal instructions before constructor call */
    private BuiltInSerializerProtocol() {
        ExtensionRegistryLite extensionRegistryLiteNewInstance = ExtensionRegistryLite.newInstance();
        BuiltInsProtoBuf.registerAllExtensions(extensionRegistryLiteNewInstance);
        Unit unit = Unit.INSTANCE;
        Intrinsics.checkNotNullExpressionValue(extensionRegistryLiteNewInstance, "newInstance().apply(BuiltInsProtoBuf::registerAllExtensions)");
        GeneratedMessageLite.GeneratedExtension<ProtoBuf.Package, Integer> packageFqName = BuiltInsProtoBuf.packageFqName;
        Intrinsics.checkNotNullExpressionValue(packageFqName, "packageFqName");
        GeneratedMessageLite.GeneratedExtension<ProtoBuf.Constructor, List<ProtoBuf.Annotation>> constructorAnnotation = BuiltInsProtoBuf.constructorAnnotation;
        Intrinsics.checkNotNullExpressionValue(constructorAnnotation, "constructorAnnotation");
        GeneratedMessageLite.GeneratedExtension<ProtoBuf.Class, List<ProtoBuf.Annotation>> classAnnotation = BuiltInsProtoBuf.classAnnotation;
        Intrinsics.checkNotNullExpressionValue(classAnnotation, "classAnnotation");
        GeneratedMessageLite.GeneratedExtension<ProtoBuf.Function, List<ProtoBuf.Annotation>> functionAnnotation = BuiltInsProtoBuf.functionAnnotation;
        Intrinsics.checkNotNullExpressionValue(functionAnnotation, "functionAnnotation");
        GeneratedMessageLite.GeneratedExtension<ProtoBuf.Property, List<ProtoBuf.Annotation>> propertyAnnotation = BuiltInsProtoBuf.propertyAnnotation;
        Intrinsics.checkNotNullExpressionValue(propertyAnnotation, "propertyAnnotation");
        GeneratedMessageLite.GeneratedExtension<ProtoBuf.Property, List<ProtoBuf.Annotation>> propertyGetterAnnotation = BuiltInsProtoBuf.propertyGetterAnnotation;
        Intrinsics.checkNotNullExpressionValue(propertyGetterAnnotation, "propertyGetterAnnotation");
        GeneratedMessageLite.GeneratedExtension<ProtoBuf.Property, List<ProtoBuf.Annotation>> propertySetterAnnotation = BuiltInsProtoBuf.propertySetterAnnotation;
        Intrinsics.checkNotNullExpressionValue(propertySetterAnnotation, "propertySetterAnnotation");
        GeneratedMessageLite.GeneratedExtension<ProtoBuf.EnumEntry, List<ProtoBuf.Annotation>> enumEntryAnnotation = BuiltInsProtoBuf.enumEntryAnnotation;
        Intrinsics.checkNotNullExpressionValue(enumEntryAnnotation, "enumEntryAnnotation");
        GeneratedMessageLite.GeneratedExtension<ProtoBuf.Property, ProtoBuf.Annotation.Argument.Value> compileTimeValue = BuiltInsProtoBuf.compileTimeValue;
        Intrinsics.checkNotNullExpressionValue(compileTimeValue, "compileTimeValue");
        GeneratedMessageLite.GeneratedExtension<ProtoBuf.ValueParameter, List<ProtoBuf.Annotation>> parameterAnnotation = BuiltInsProtoBuf.parameterAnnotation;
        Intrinsics.checkNotNullExpressionValue(parameterAnnotation, "parameterAnnotation");
        GeneratedMessageLite.GeneratedExtension<ProtoBuf.Type, List<ProtoBuf.Annotation>> typeAnnotation = BuiltInsProtoBuf.typeAnnotation;
        Intrinsics.checkNotNullExpressionValue(typeAnnotation, "typeAnnotation");
        GeneratedMessageLite.GeneratedExtension<ProtoBuf.TypeParameter, List<ProtoBuf.Annotation>> typeParameterAnnotation = BuiltInsProtoBuf.typeParameterAnnotation;
        Intrinsics.checkNotNullExpressionValue(typeParameterAnnotation, "typeParameterAnnotation");
        super(extensionRegistryLiteNewInstance, packageFqName, constructorAnnotation, classAnnotation, functionAnnotation, propertyAnnotation, propertyGetterAnnotation, propertySetterAnnotation, enumEntryAnnotation, compileTimeValue, parameterAnnotation, typeAnnotation, typeParameterAnnotation);
    }

    public final String getBuiltInsFilePath(FqName fqName) {
        Intrinsics.checkNotNullParameter(fqName, "fqName");
        StringBuilder sb = new StringBuilder();
        String strAsString = fqName.asString();
        Intrinsics.checkNotNullExpressionValue(strAsString, "fqName.asString()");
        sb.append(StringsKt.replace$default(strAsString, '.', '/', false, 4, (Object) null));
        sb.append('/');
        sb.append(getBuiltInsFileName(fqName));
        return sb.toString();
    }

    public final String getBuiltInsFileName(FqName fqName) {
        Intrinsics.checkNotNullParameter(fqName, "fqName");
        return Intrinsics.stringPlus(shortName(fqName), ".kotlin_builtins");
    }

    private final String shortName(FqName fqName) {
        if (fqName.isRoot()) {
            return "default-package";
        }
        String strAsString = fqName.shortName().asString();
        Intrinsics.checkNotNullExpressionValue(strAsString, "fqName.shortName().asString()");
        return strAsString;
    }
}
