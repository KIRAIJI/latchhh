package kotlin.reflect.jvm.internal.impl.types.checker;

import java.util.ArrayDeque;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.descriptors.ClassifierDescriptor;
import kotlin.reflect.jvm.internal.impl.renderer.DescriptorRenderer;
import kotlin.reflect.jvm.internal.impl.resolve.calls.inference.CapturedTypeConstructorKt;
import kotlin.reflect.jvm.internal.impl.types.KotlinType;
import kotlin.reflect.jvm.internal.impl.types.TypeConstructor;
import kotlin.reflect.jvm.internal.impl.types.TypeConstructorSubstitution;
import kotlin.reflect.jvm.internal.impl.types.TypeProjection;
import kotlin.reflect.jvm.internal.impl.types.TypeUtils;
import kotlin.reflect.jvm.internal.impl.types.Variance;
import kotlin.reflect.jvm.internal.impl.types.typesApproximation.CapturedTypeApproximationKt;

/* JADX INFO: compiled from: utils.kt */
/* JADX INFO: loaded from: classes2.dex */
public final class UtilsKt {
    public static final KotlinType findCorrespondingSupertype(KotlinType subtype, KotlinType supertype, TypeCheckingProcedureCallbacks typeCheckingProcedureCallbacks) {
        boolean z;
        Intrinsics.checkNotNullParameter(subtype, "subtype");
        Intrinsics.checkNotNullParameter(supertype, "supertype");
        Intrinsics.checkNotNullParameter(typeCheckingProcedureCallbacks, "typeCheckingProcedureCallbacks");
        ArrayDeque arrayDeque = new ArrayDeque();
        arrayDeque.add(new SubtypePathNode(subtype, null));
        TypeConstructor constructor = supertype.getConstructor();
        while (!arrayDeque.isEmpty()) {
            SubtypePathNode subtypePathNode = (SubtypePathNode) arrayDeque.poll();
            KotlinType type = subtypePathNode.getType();
            TypeConstructor constructor2 = type.getConstructor();
            if (typeCheckingProcedureCallbacks.assertEqualTypeConstructors(constructor2, constructor)) {
                boolean zIsMarkedNullable = type.isMarkedNullable();
                for (SubtypePathNode previous = subtypePathNode.getPrevious(); previous != null; previous = previous.getPrevious()) {
                    KotlinType type2 = previous.getType();
                    List<TypeProjection> arguments = type2.getArguments();
                    if ((arguments instanceof Collection) && arguments.isEmpty()) {
                        z = false;
                    } else {
                        Iterator<T> it = arguments.iterator();
                        while (it.hasNext()) {
                            if (((TypeProjection) it.next()).getProjectionKind() != Variance.INVARIANT) {
                                z = true;
                                break;
                            }
                        }
                        z = false;
                    }
                    if (z) {
                        KotlinType kotlinTypeSafeSubstitute = CapturedTypeConstructorKt.wrapWithCapturingSubstitution$default(TypeConstructorSubstitution.Companion.create(type2), false, 1, null).buildSubstitutor().safeSubstitute(type, Variance.INVARIANT);
                        Intrinsics.checkNotNullExpressionValue(kotlinTypeSafeSubstitute, "TypeConstructorSubstitution.create(currentType)\n                            .wrapWithCapturingSubstitution().buildSubstitutor()\n                            .safeSubstitute(substituted, Variance.INVARIANT)");
                        type = approximate(kotlinTypeSafeSubstitute);
                    } else {
                        type = TypeConstructorSubstitution.Companion.create(type2).buildSubstitutor().safeSubstitute(type, Variance.INVARIANT);
                        Intrinsics.checkNotNullExpressionValue(type, "{\n                    TypeConstructorSubstitution.create(currentType)\n                            .buildSubstitutor()\n                            .safeSubstitute(substituted, Variance.INVARIANT)\n                }");
                    }
                    zIsMarkedNullable = zIsMarkedNullable || type2.isMarkedNullable();
                }
                TypeConstructor constructor3 = type.getConstructor();
                if (typeCheckingProcedureCallbacks.assertEqualTypeConstructors(constructor3, constructor)) {
                    return TypeUtils.makeNullableAsSpecified(type, zIsMarkedNullable);
                }
                throw new AssertionError("Type constructors should be equals!\nsubstitutedSuperType: " + debugInfo(constructor3) + ", \n\nsupertype: " + debugInfo(constructor) + " \n" + typeCheckingProcedureCallbacks.assertEqualTypeConstructors(constructor3, constructor));
            }
            for (KotlinType immediateSupertype : constructor2.mo1678getSupertypes()) {
                Intrinsics.checkNotNullExpressionValue(immediateSupertype, "immediateSupertype");
                arrayDeque.add(new SubtypePathNode(immediateSupertype, subtypePathNode));
            }
        }
        return null;
    }

    private static final KotlinType approximate(KotlinType kotlinType) {
        return CapturedTypeApproximationKt.approximateCapturedTypes(kotlinType).getUpper();
    }

    private static final String debugInfo(TypeConstructor typeConstructor) {
        StringBuilder sb = new StringBuilder();
        m1683debugInfo$lambda1$unaryPlus(Intrinsics.stringPlus("type: ", typeConstructor), sb);
        m1683debugInfo$lambda1$unaryPlus(Intrinsics.stringPlus("hashCode: ", Integer.valueOf(typeConstructor.hashCode())), sb);
        m1683debugInfo$lambda1$unaryPlus(Intrinsics.stringPlus("javaClass: ", typeConstructor.getClass().getCanonicalName()), sb);
        for (ClassifierDescriptor classifierDescriptorMo1677getDeclarationDescriptor = typeConstructor.mo1677getDeclarationDescriptor(); classifierDescriptorMo1677getDeclarationDescriptor != null; classifierDescriptorMo1677getDeclarationDescriptor = classifierDescriptorMo1677getDeclarationDescriptor.getContainingDeclaration()) {
            m1683debugInfo$lambda1$unaryPlus(Intrinsics.stringPlus("fqName: ", DescriptorRenderer.FQ_NAMES_IN_TYPES.render(classifierDescriptorMo1677getDeclarationDescriptor)), sb);
            m1683debugInfo$lambda1$unaryPlus(Intrinsics.stringPlus("javaClass: ", classifierDescriptorMo1677getDeclarationDescriptor.getClass().getCanonicalName()), sb);
        }
        String string = sb.toString();
        Intrinsics.checkNotNullExpressionValue(string, "StringBuilder().apply(builderAction).toString()");
        return string;
    }

    /* JADX INFO: renamed from: debugInfo$lambda-1$unaryPlus, reason: not valid java name */
    private static final StringBuilder m1683debugInfo$lambda1$unaryPlus(String str, StringBuilder this_anonymous) {
        Intrinsics.checkNotNullParameter(str, "<this>");
        Intrinsics.checkNotNullParameter(this_anonymous, "$this_anonymous");
        this_anonymous.append(str);
        Intrinsics.checkNotNullExpressionValue(this_anonymous, "append(value)");
        this_anonymous.append('\n');
        Intrinsics.checkNotNullExpressionValue(this_anonymous, "append('\\n')");
        return this_anonymous;
    }
}
