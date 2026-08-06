package kotlin.reflect.jvm.internal.impl.types.checker;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Reflection;
import kotlin.reflect.jvm.internal.impl.builtins.KotlinBuiltIns;
import kotlin.reflect.jvm.internal.impl.builtins.PrimitiveType;
import kotlin.reflect.jvm.internal.impl.builtins.StandardNames;
import kotlin.reflect.jvm.internal.impl.descriptors.ClassDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.ClassKind;
import kotlin.reflect.jvm.internal.impl.descriptors.ClassifierDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.ModalityUtilsKt;
import kotlin.reflect.jvm.internal.impl.descriptors.TypeAliasDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.TypeParameterDescriptor;
import kotlin.reflect.jvm.internal.impl.name.FqName;
import kotlin.reflect.jvm.internal.impl.name.FqNameUnsafe;
import kotlin.reflect.jvm.internal.impl.resolve.InlineClassesUtilsKt;
import kotlin.reflect.jvm.internal.impl.resolve.calls.inference.CapturedType;
import kotlin.reflect.jvm.internal.impl.resolve.constants.IntegerLiteralTypeConstructor;
import kotlin.reflect.jvm.internal.impl.resolve.descriptorUtil.DescriptorUtilsKt;
import kotlin.reflect.jvm.internal.impl.types.AbstractStubType;
import kotlin.reflect.jvm.internal.impl.types.AbstractTypeCheckerContext;
import kotlin.reflect.jvm.internal.impl.types.DefinitelyNotNullType;
import kotlin.reflect.jvm.internal.impl.types.DynamicType;
import kotlin.reflect.jvm.internal.impl.types.FlexibleType;
import kotlin.reflect.jvm.internal.impl.types.IntersectionTypeConstructor;
import kotlin.reflect.jvm.internal.impl.types.KotlinType;
import kotlin.reflect.jvm.internal.impl.types.KotlinTypeFactory;
import kotlin.reflect.jvm.internal.impl.types.KotlinTypeKt;
import kotlin.reflect.jvm.internal.impl.types.SimpleType;
import kotlin.reflect.jvm.internal.impl.types.TypeConstructor;
import kotlin.reflect.jvm.internal.impl.types.TypeProjection;
import kotlin.reflect.jvm.internal.impl.types.TypeSystemCommonBackendContext;
import kotlin.reflect.jvm.internal.impl.types.TypeUtils;
import kotlin.reflect.jvm.internal.impl.types.UnwrappedType;
import kotlin.reflect.jvm.internal.impl.types.Variance;
import kotlin.reflect.jvm.internal.impl.types.model.CaptureStatus;
import kotlin.reflect.jvm.internal.impl.types.model.CapturedTypeMarker;
import kotlin.reflect.jvm.internal.impl.types.model.DefinitelyNotNullTypeMarker;
import kotlin.reflect.jvm.internal.impl.types.model.DynamicTypeMarker;
import kotlin.reflect.jvm.internal.impl.types.model.FlexibleTypeMarker;
import kotlin.reflect.jvm.internal.impl.types.model.KotlinTypeMarker;
import kotlin.reflect.jvm.internal.impl.types.model.SimpleTypeMarker;
import kotlin.reflect.jvm.internal.impl.types.model.TypeArgumentListMarker;
import kotlin.reflect.jvm.internal.impl.types.model.TypeArgumentMarker;
import kotlin.reflect.jvm.internal.impl.types.model.TypeConstructorMarker;
import kotlin.reflect.jvm.internal.impl.types.model.TypeParameterMarker;
import kotlin.reflect.jvm.internal.impl.types.model.TypeSystemContextKt;
import kotlin.reflect.jvm.internal.impl.types.model.TypeSystemInferenceExtensionContext;
import kotlin.reflect.jvm.internal.impl.types.model.TypeVariance;
import kotlin.reflect.jvm.internal.impl.types.typeUtil.TypeUtilsKt;

/* JADX INFO: compiled from: ClassicTypeSystemContext.kt */
/* JADX INFO: loaded from: classes2.dex */
public interface ClassicTypeSystemContext extends TypeSystemCommonBackendContext, TypeSystemInferenceExtensionContext {
    SimpleTypeMarker asSimpleType(KotlinTypeMarker kotlinTypeMarker);

    KotlinTypeMarker createFlexibleType(SimpleTypeMarker simpleTypeMarker, SimpleTypeMarker simpleTypeMarker2);

    SimpleTypeMarker lowerBound(FlexibleTypeMarker flexibleTypeMarker);

    TypeConstructorMarker typeConstructor(SimpleTypeMarker simpleTypeMarker);

    SimpleTypeMarker upperBound(FlexibleTypeMarker flexibleTypeMarker);

    SimpleTypeMarker withNullability(SimpleTypeMarker simpleTypeMarker, boolean z);

    /* JADX INFO: compiled from: ClassicTypeSystemContext.kt */
    public static final class DefaultImpls {
        public static TypeArgumentMarker get(ClassicTypeSystemContext classicTypeSystemContext, TypeArgumentListMarker typeArgumentListMarker, int i) {
            return TypeSystemInferenceExtensionContext.DefaultImpls.get(classicTypeSystemContext, typeArgumentListMarker, i);
        }

        public static boolean isMarkedNullable(ClassicTypeSystemContext classicTypeSystemContext, KotlinTypeMarker kotlinTypeMarker) {
            return TypeSystemInferenceExtensionContext.DefaultImpls.isMarkedNullable(classicTypeSystemContext, kotlinTypeMarker);
        }

        public static SimpleTypeMarker lowerBoundIfFlexible(ClassicTypeSystemContext classicTypeSystemContext, KotlinTypeMarker kotlinTypeMarker) {
            return TypeSystemInferenceExtensionContext.DefaultImpls.lowerBoundIfFlexible(classicTypeSystemContext, kotlinTypeMarker);
        }

        public static KotlinTypeMarker makeNullable(ClassicTypeSystemContext classicTypeSystemContext, KotlinTypeMarker kotlinTypeMarker) {
            return TypeSystemCommonBackendContext.DefaultImpls.makeNullable(classicTypeSystemContext, kotlinTypeMarker);
        }

        public static int size(ClassicTypeSystemContext classicTypeSystemContext, TypeArgumentListMarker typeArgumentListMarker) {
            return TypeSystemInferenceExtensionContext.DefaultImpls.size(classicTypeSystemContext, typeArgumentListMarker);
        }

        public static TypeConstructorMarker typeConstructor(ClassicTypeSystemContext classicTypeSystemContext, KotlinTypeMarker kotlinTypeMarker) {
            return TypeSystemInferenceExtensionContext.DefaultImpls.typeConstructor(classicTypeSystemContext, kotlinTypeMarker);
        }

        public static SimpleTypeMarker upperBoundIfFlexible(ClassicTypeSystemContext classicTypeSystemContext, KotlinTypeMarker kotlinTypeMarker) {
            return TypeSystemInferenceExtensionContext.DefaultImpls.upperBoundIfFlexible(classicTypeSystemContext, kotlinTypeMarker);
        }

        public static boolean isDenotable(ClassicTypeSystemContext classicTypeSystemContext, TypeConstructorMarker receiver) {
            Intrinsics.checkNotNullParameter(classicTypeSystemContext, "this");
            Intrinsics.checkNotNullParameter(receiver, "receiver");
            if (receiver instanceof TypeConstructor) {
                return ((TypeConstructor) receiver).isDenotable();
            }
            throw new IllegalArgumentException(("ClassicTypeSystemContext couldn't handle: " + receiver + ", " + Reflection.getOrCreateKotlinClass(receiver.getClass())).toString());
        }

        public static boolean isIntegerLiteralTypeConstructor(ClassicTypeSystemContext classicTypeSystemContext, TypeConstructorMarker receiver) {
            Intrinsics.checkNotNullParameter(classicTypeSystemContext, "this");
            Intrinsics.checkNotNullParameter(receiver, "receiver");
            if (receiver instanceof TypeConstructor) {
                return receiver instanceof IntegerLiteralTypeConstructor;
            }
            throw new IllegalArgumentException(("ClassicTypeSystemContext couldn't handle: " + receiver + ", " + Reflection.getOrCreateKotlinClass(receiver.getClass())).toString());
        }

        public static Collection<KotlinTypeMarker> possibleIntegerTypes(ClassicTypeSystemContext classicTypeSystemContext, SimpleTypeMarker receiver) {
            Intrinsics.checkNotNullParameter(classicTypeSystemContext, "this");
            Intrinsics.checkNotNullParameter(receiver, "receiver");
            TypeConstructorMarker typeConstructorMarkerTypeConstructor = classicTypeSystemContext.typeConstructor(receiver);
            if (typeConstructorMarkerTypeConstructor instanceof IntegerLiteralTypeConstructor) {
                return ((IntegerLiteralTypeConstructor) typeConstructorMarkerTypeConstructor).getPossibleTypes();
            }
            throw new IllegalArgumentException(("ClassicTypeSystemContext couldn't handle: " + receiver + ", " + Reflection.getOrCreateKotlinClass(receiver.getClass())).toString());
        }

        public static SimpleTypeMarker withNullability(ClassicTypeSystemContext classicTypeSystemContext, SimpleTypeMarker receiver, boolean z) {
            Intrinsics.checkNotNullParameter(classicTypeSystemContext, "this");
            Intrinsics.checkNotNullParameter(receiver, "receiver");
            if (receiver instanceof SimpleType) {
                return ((SimpleType) receiver).makeNullableAsSpecified(z);
            }
            throw new IllegalArgumentException(("ClassicTypeSystemContext couldn't handle: " + receiver + ", " + Reflection.getOrCreateKotlinClass(receiver.getClass())).toString());
        }

        public static boolean isError(ClassicTypeSystemContext classicTypeSystemContext, KotlinTypeMarker receiver) {
            Intrinsics.checkNotNullParameter(classicTypeSystemContext, "this");
            Intrinsics.checkNotNullParameter(receiver, "receiver");
            if (receiver instanceof KotlinType) {
                return KotlinTypeKt.isError((KotlinType) receiver);
            }
            throw new IllegalArgumentException(("ClassicTypeSystemContext couldn't handle: " + receiver + ", " + Reflection.getOrCreateKotlinClass(receiver.getClass())).toString());
        }

        public static boolean isStubType(ClassicTypeSystemContext classicTypeSystemContext, SimpleTypeMarker receiver) {
            Intrinsics.checkNotNullParameter(classicTypeSystemContext, "this");
            Intrinsics.checkNotNullParameter(receiver, "receiver");
            if (receiver instanceof SimpleType) {
                return receiver instanceof AbstractStubType;
            }
            throw new IllegalArgumentException(("ClassicTypeSystemContext couldn't handle: " + receiver + ", " + Reflection.getOrCreateKotlinClass(receiver.getClass())).toString());
        }

        public static KotlinTypeMarker lowerType(ClassicTypeSystemContext classicTypeSystemContext, CapturedTypeMarker receiver) {
            Intrinsics.checkNotNullParameter(classicTypeSystemContext, "this");
            Intrinsics.checkNotNullParameter(receiver, "receiver");
            if (receiver instanceof NewCapturedType) {
                return ((NewCapturedType) receiver).getLowerType();
            }
            throw new IllegalArgumentException(("ClassicTypeSystemContext couldn't handle: " + receiver + ", " + Reflection.getOrCreateKotlinClass(receiver.getClass())).toString());
        }

        public static boolean isIntersection(ClassicTypeSystemContext classicTypeSystemContext, TypeConstructorMarker receiver) {
            Intrinsics.checkNotNullParameter(classicTypeSystemContext, "this");
            Intrinsics.checkNotNullParameter(receiver, "receiver");
            if (receiver instanceof TypeConstructor) {
                return receiver instanceof IntersectionTypeConstructor;
            }
            throw new IllegalArgumentException(("ClassicTypeSystemContext couldn't handle: " + receiver + ", " + Reflection.getOrCreateKotlinClass(receiver.getClass())).toString());
        }

        public static boolean identicalArguments(ClassicTypeSystemContext classicTypeSystemContext, SimpleTypeMarker a, SimpleTypeMarker b) {
            Intrinsics.checkNotNullParameter(classicTypeSystemContext, "this");
            Intrinsics.checkNotNullParameter(a, "a");
            Intrinsics.checkNotNullParameter(b, "b");
            if (a instanceof SimpleType) {
                if (b instanceof SimpleType) {
                    return ((SimpleType) a).getArguments() == ((SimpleType) b).getArguments();
                }
                throw new IllegalArgumentException(("ClassicTypeSystemContext couldn't handle: " + b + ", " + Reflection.getOrCreateKotlinClass(b.getClass())).toString());
            }
            throw new IllegalArgumentException(("ClassicTypeSystemContext couldn't handle: " + a + ", " + Reflection.getOrCreateKotlinClass(a.getClass())).toString());
        }

        public static SimpleTypeMarker asSimpleType(ClassicTypeSystemContext classicTypeSystemContext, KotlinTypeMarker receiver) {
            Intrinsics.checkNotNullParameter(classicTypeSystemContext, "this");
            Intrinsics.checkNotNullParameter(receiver, "receiver");
            if (receiver instanceof KotlinType) {
                UnwrappedType unwrappedTypeUnwrap = ((KotlinType) receiver).unwrap();
                return unwrappedTypeUnwrap instanceof SimpleType ? (SimpleType) unwrappedTypeUnwrap : null;
            }
            throw new IllegalArgumentException(("ClassicTypeSystemContext couldn't handle: " + receiver + ", " + Reflection.getOrCreateKotlinClass(receiver.getClass())).toString());
        }

        public static FlexibleTypeMarker asFlexibleType(ClassicTypeSystemContext classicTypeSystemContext, KotlinTypeMarker receiver) {
            Intrinsics.checkNotNullParameter(classicTypeSystemContext, "this");
            Intrinsics.checkNotNullParameter(receiver, "receiver");
            if (receiver instanceof KotlinType) {
                UnwrappedType unwrappedTypeUnwrap = ((KotlinType) receiver).unwrap();
                return unwrappedTypeUnwrap instanceof FlexibleType ? (FlexibleType) unwrappedTypeUnwrap : null;
            }
            throw new IllegalArgumentException(("ClassicTypeSystemContext couldn't handle: " + receiver + ", " + Reflection.getOrCreateKotlinClass(receiver.getClass())).toString());
        }

        public static DynamicTypeMarker asDynamicType(ClassicTypeSystemContext classicTypeSystemContext, FlexibleTypeMarker receiver) {
            Intrinsics.checkNotNullParameter(classicTypeSystemContext, "this");
            Intrinsics.checkNotNullParameter(receiver, "receiver");
            if (receiver instanceof FlexibleType) {
                return receiver instanceof DynamicType ? (DynamicType) receiver : null;
            }
            throw new IllegalArgumentException(("ClassicTypeSystemContext couldn't handle: " + receiver + ", " + Reflection.getOrCreateKotlinClass(receiver.getClass())).toString());
        }

        public static SimpleTypeMarker upperBound(ClassicTypeSystemContext classicTypeSystemContext, FlexibleTypeMarker receiver) {
            Intrinsics.checkNotNullParameter(classicTypeSystemContext, "this");
            Intrinsics.checkNotNullParameter(receiver, "receiver");
            if (receiver instanceof FlexibleType) {
                return ((FlexibleType) receiver).getUpperBound();
            }
            throw new IllegalArgumentException(("ClassicTypeSystemContext couldn't handle: " + receiver + ", " + Reflection.getOrCreateKotlinClass(receiver.getClass())).toString());
        }

        public static SimpleTypeMarker lowerBound(ClassicTypeSystemContext classicTypeSystemContext, FlexibleTypeMarker receiver) {
            Intrinsics.checkNotNullParameter(classicTypeSystemContext, "this");
            Intrinsics.checkNotNullParameter(receiver, "receiver");
            if (receiver instanceof FlexibleType) {
                return ((FlexibleType) receiver).getLowerBound();
            }
            throw new IllegalArgumentException(("ClassicTypeSystemContext couldn't handle: " + receiver + ", " + Reflection.getOrCreateKotlinClass(receiver.getClass())).toString());
        }

        public static CapturedTypeMarker asCapturedType(ClassicTypeSystemContext classicTypeSystemContext, SimpleTypeMarker receiver) {
            Intrinsics.checkNotNullParameter(classicTypeSystemContext, "this");
            Intrinsics.checkNotNullParameter(receiver, "receiver");
            if (receiver instanceof SimpleType) {
                return receiver instanceof NewCapturedType ? (NewCapturedType) receiver : null;
            }
            throw new IllegalArgumentException(("ClassicTypeSystemContext couldn't handle: " + receiver + ", " + Reflection.getOrCreateKotlinClass(receiver.getClass())).toString());
        }

        public static DefinitelyNotNullTypeMarker asDefinitelyNotNullType(ClassicTypeSystemContext classicTypeSystemContext, SimpleTypeMarker receiver) {
            Intrinsics.checkNotNullParameter(classicTypeSystemContext, "this");
            Intrinsics.checkNotNullParameter(receiver, "receiver");
            if (receiver instanceof SimpleType) {
                return receiver instanceof DefinitelyNotNullType ? (DefinitelyNotNullType) receiver : null;
            }
            throw new IllegalArgumentException(("ClassicTypeSystemContext couldn't handle: " + receiver + ", " + Reflection.getOrCreateKotlinClass(receiver.getClass())).toString());
        }

        public static boolean isMarkedNullable(ClassicTypeSystemContext classicTypeSystemContext, SimpleTypeMarker receiver) {
            Intrinsics.checkNotNullParameter(classicTypeSystemContext, "this");
            Intrinsics.checkNotNullParameter(receiver, "receiver");
            if (receiver instanceof SimpleType) {
                return ((SimpleType) receiver).isMarkedNullable();
            }
            throw new IllegalArgumentException(("ClassicTypeSystemContext couldn't handle: " + receiver + ", " + Reflection.getOrCreateKotlinClass(receiver.getClass())).toString());
        }

        public static TypeConstructorMarker typeConstructor(ClassicTypeSystemContext classicTypeSystemContext, SimpleTypeMarker receiver) {
            Intrinsics.checkNotNullParameter(classicTypeSystemContext, "this");
            Intrinsics.checkNotNullParameter(receiver, "receiver");
            if (receiver instanceof SimpleType) {
                return ((SimpleType) receiver).getConstructor();
            }
            throw new IllegalArgumentException(("ClassicTypeSystemContext couldn't handle: " + receiver + ", " + Reflection.getOrCreateKotlinClass(receiver.getClass())).toString());
        }

        public static int argumentsCount(ClassicTypeSystemContext classicTypeSystemContext, KotlinTypeMarker receiver) {
            Intrinsics.checkNotNullParameter(classicTypeSystemContext, "this");
            Intrinsics.checkNotNullParameter(receiver, "receiver");
            if (receiver instanceof KotlinType) {
                return ((KotlinType) receiver).getArguments().size();
            }
            throw new IllegalArgumentException(("ClassicTypeSystemContext couldn't handle: " + receiver + ", " + Reflection.getOrCreateKotlinClass(receiver.getClass())).toString());
        }

        public static TypeArgumentMarker getArgument(ClassicTypeSystemContext classicTypeSystemContext, KotlinTypeMarker receiver, int i) {
            Intrinsics.checkNotNullParameter(classicTypeSystemContext, "this");
            Intrinsics.checkNotNullParameter(receiver, "receiver");
            if (receiver instanceof KotlinType) {
                return ((KotlinType) receiver).getArguments().get(i);
            }
            throw new IllegalArgumentException(("ClassicTypeSystemContext couldn't handle: " + receiver + ", " + Reflection.getOrCreateKotlinClass(receiver.getClass())).toString());
        }

        public static boolean isStarProjection(ClassicTypeSystemContext classicTypeSystemContext, TypeArgumentMarker receiver) {
            Intrinsics.checkNotNullParameter(classicTypeSystemContext, "this");
            Intrinsics.checkNotNullParameter(receiver, "receiver");
            if (receiver instanceof TypeProjection) {
                return ((TypeProjection) receiver).isStarProjection();
            }
            throw new IllegalArgumentException(("ClassicTypeSystemContext couldn't handle: " + receiver + ", " + Reflection.getOrCreateKotlinClass(receiver.getClass())).toString());
        }

        public static TypeVariance getVariance(ClassicTypeSystemContext classicTypeSystemContext, TypeArgumentMarker receiver) {
            Intrinsics.checkNotNullParameter(classicTypeSystemContext, "this");
            Intrinsics.checkNotNullParameter(receiver, "receiver");
            if (receiver instanceof TypeProjection) {
                Variance projectionKind = ((TypeProjection) receiver).getProjectionKind();
                Intrinsics.checkNotNullExpressionValue(projectionKind, "this.projectionKind");
                return TypeSystemContextKt.convertVariance(projectionKind);
            }
            throw new IllegalArgumentException(("ClassicTypeSystemContext couldn't handle: " + receiver + ", " + Reflection.getOrCreateKotlinClass(receiver.getClass())).toString());
        }

        public static KotlinTypeMarker getType(ClassicTypeSystemContext classicTypeSystemContext, TypeArgumentMarker receiver) {
            Intrinsics.checkNotNullParameter(classicTypeSystemContext, "this");
            Intrinsics.checkNotNullParameter(receiver, "receiver");
            if (receiver instanceof TypeProjection) {
                return ((TypeProjection) receiver).getType().unwrap();
            }
            throw new IllegalArgumentException(("ClassicTypeSystemContext couldn't handle: " + receiver + ", " + Reflection.getOrCreateKotlinClass(receiver.getClass())).toString());
        }

        public static int parametersCount(ClassicTypeSystemContext classicTypeSystemContext, TypeConstructorMarker receiver) {
            Intrinsics.checkNotNullParameter(classicTypeSystemContext, "this");
            Intrinsics.checkNotNullParameter(receiver, "receiver");
            if (receiver instanceof TypeConstructor) {
                return ((TypeConstructor) receiver).getParameters().size();
            }
            throw new IllegalArgumentException(("ClassicTypeSystemContext couldn't handle: " + receiver + ", " + Reflection.getOrCreateKotlinClass(receiver.getClass())).toString());
        }

        public static TypeParameterMarker getParameter(ClassicTypeSystemContext classicTypeSystemContext, TypeConstructorMarker receiver, int i) {
            Intrinsics.checkNotNullParameter(classicTypeSystemContext, "this");
            Intrinsics.checkNotNullParameter(receiver, "receiver");
            if (receiver instanceof TypeConstructor) {
                TypeParameterDescriptor typeParameterDescriptor = ((TypeConstructor) receiver).getParameters().get(i);
                Intrinsics.checkNotNullExpressionValue(typeParameterDescriptor, "this.parameters[index]");
                return typeParameterDescriptor;
            }
            throw new IllegalArgumentException(("ClassicTypeSystemContext couldn't handle: " + receiver + ", " + Reflection.getOrCreateKotlinClass(receiver.getClass())).toString());
        }

        public static Collection<KotlinTypeMarker> supertypes(ClassicTypeSystemContext classicTypeSystemContext, TypeConstructorMarker receiver) {
            Intrinsics.checkNotNullParameter(classicTypeSystemContext, "this");
            Intrinsics.checkNotNullParameter(receiver, "receiver");
            if (receiver instanceof TypeConstructor) {
                Collection<KotlinType> collectionMo1678getSupertypes = ((TypeConstructor) receiver).mo1678getSupertypes();
                Intrinsics.checkNotNullExpressionValue(collectionMo1678getSupertypes, "this.supertypes");
                return collectionMo1678getSupertypes;
            }
            throw new IllegalArgumentException(("ClassicTypeSystemContext couldn't handle: " + receiver + ", " + Reflection.getOrCreateKotlinClass(receiver.getClass())).toString());
        }

        public static TypeVariance getVariance(ClassicTypeSystemContext classicTypeSystemContext, TypeParameterMarker receiver) {
            Intrinsics.checkNotNullParameter(classicTypeSystemContext, "this");
            Intrinsics.checkNotNullParameter(receiver, "receiver");
            if (receiver instanceof TypeParameterDescriptor) {
                Variance variance = ((TypeParameterDescriptor) receiver).getVariance();
                Intrinsics.checkNotNullExpressionValue(variance, "this.variance");
                return TypeSystemContextKt.convertVariance(variance);
            }
            throw new IllegalArgumentException(("ClassicTypeSystemContext couldn't handle: " + receiver + ", " + Reflection.getOrCreateKotlinClass(receiver.getClass())).toString());
        }

        public static boolean areEqualTypeConstructors(ClassicTypeSystemContext classicTypeSystemContext, TypeConstructorMarker c1, TypeConstructorMarker c2) {
            Intrinsics.checkNotNullParameter(classicTypeSystemContext, "this");
            Intrinsics.checkNotNullParameter(c1, "c1");
            Intrinsics.checkNotNullParameter(c2, "c2");
            if (c1 instanceof TypeConstructor) {
                if (c2 instanceof TypeConstructor) {
                    return Intrinsics.areEqual(c1, c2);
                }
                throw new IllegalArgumentException(("ClassicTypeSystemContext couldn't handle: " + c2 + ", " + Reflection.getOrCreateKotlinClass(c2.getClass())).toString());
            }
            throw new IllegalArgumentException(("ClassicTypeSystemContext couldn't handle: " + c1 + ", " + Reflection.getOrCreateKotlinClass(c1.getClass())).toString());
        }

        public static boolean isClassTypeConstructor(ClassicTypeSystemContext classicTypeSystemContext, TypeConstructorMarker receiver) {
            Intrinsics.checkNotNullParameter(classicTypeSystemContext, "this");
            Intrinsics.checkNotNullParameter(receiver, "receiver");
            if (receiver instanceof TypeConstructor) {
                return ((TypeConstructor) receiver).mo1677getDeclarationDescriptor() instanceof ClassDescriptor;
            }
            throw new IllegalArgumentException(("ClassicTypeSystemContext couldn't handle: " + receiver + ", " + Reflection.getOrCreateKotlinClass(receiver.getClass())).toString());
        }

        public static boolean isCommonFinalClassConstructor(ClassicTypeSystemContext classicTypeSystemContext, TypeConstructorMarker receiver) {
            Intrinsics.checkNotNullParameter(classicTypeSystemContext, "this");
            Intrinsics.checkNotNullParameter(receiver, "receiver");
            if (receiver instanceof TypeConstructor) {
                ClassifierDescriptor classifierDescriptorMo1677getDeclarationDescriptor = ((TypeConstructor) receiver).mo1677getDeclarationDescriptor();
                ClassDescriptor classDescriptor = classifierDescriptorMo1677getDeclarationDescriptor instanceof ClassDescriptor ? (ClassDescriptor) classifierDescriptorMo1677getDeclarationDescriptor : null;
                return (classDescriptor == null || !ModalityUtilsKt.isFinalClass(classDescriptor) || classDescriptor.getKind() == ClassKind.ENUM_ENTRY || classDescriptor.getKind() == ClassKind.ANNOTATION_CLASS) ? false : true;
            }
            throw new IllegalArgumentException(("ClassicTypeSystemContext couldn't handle: " + receiver + ", " + Reflection.getOrCreateKotlinClass(receiver.getClass())).toString());
        }

        public static TypeArgumentListMarker asArgumentList(ClassicTypeSystemContext classicTypeSystemContext, SimpleTypeMarker receiver) {
            Intrinsics.checkNotNullParameter(classicTypeSystemContext, "this");
            Intrinsics.checkNotNullParameter(receiver, "receiver");
            if (receiver instanceof SimpleType) {
                return (TypeArgumentListMarker) receiver;
            }
            throw new IllegalArgumentException(("ClassicTypeSystemContext couldn't handle: " + receiver + ", " + Reflection.getOrCreateKotlinClass(receiver.getClass())).toString());
        }

        public static SimpleTypeMarker captureFromArguments(ClassicTypeSystemContext classicTypeSystemContext, SimpleTypeMarker type, CaptureStatus status) {
            Intrinsics.checkNotNullParameter(classicTypeSystemContext, "this");
            Intrinsics.checkNotNullParameter(type, "type");
            Intrinsics.checkNotNullParameter(status, "status");
            if (type instanceof SimpleType) {
                return NewCapturedTypeKt.captureFromArguments((SimpleType) type, status);
            }
            throw new IllegalArgumentException(("ClassicTypeSystemContext couldn't handle: " + type + ", " + Reflection.getOrCreateKotlinClass(type.getClass())).toString());
        }

        public static boolean isAnyConstructor(ClassicTypeSystemContext classicTypeSystemContext, TypeConstructorMarker receiver) {
            Intrinsics.checkNotNullParameter(classicTypeSystemContext, "this");
            Intrinsics.checkNotNullParameter(receiver, "receiver");
            if (receiver instanceof TypeConstructor) {
                return KotlinBuiltIns.isTypeConstructorForGivenClass((TypeConstructor) receiver, StandardNames.FqNames.any);
            }
            throw new IllegalArgumentException(("ClassicTypeSystemContext couldn't handle: " + receiver + ", " + Reflection.getOrCreateKotlinClass(receiver.getClass())).toString());
        }

        public static boolean isNothingConstructor(ClassicTypeSystemContext classicTypeSystemContext, TypeConstructorMarker receiver) {
            Intrinsics.checkNotNullParameter(classicTypeSystemContext, "this");
            Intrinsics.checkNotNullParameter(receiver, "receiver");
            if (receiver instanceof TypeConstructor) {
                return KotlinBuiltIns.isTypeConstructorForGivenClass((TypeConstructor) receiver, StandardNames.FqNames.nothing);
            }
            throw new IllegalArgumentException(("ClassicTypeSystemContext couldn't handle: " + receiver + ", " + Reflection.getOrCreateKotlinClass(receiver.getClass())).toString());
        }

        public static TypeArgumentMarker asTypeArgument(ClassicTypeSystemContext classicTypeSystemContext, KotlinTypeMarker receiver) {
            Intrinsics.checkNotNullParameter(classicTypeSystemContext, "this");
            Intrinsics.checkNotNullParameter(receiver, "receiver");
            if (receiver instanceof KotlinType) {
                return TypeUtilsKt.asTypeProjection((KotlinType) receiver);
            }
            throw new IllegalArgumentException(("ClassicTypeSystemContext couldn't handle: " + receiver + ", " + Reflection.getOrCreateKotlinClass(receiver.getClass())).toString());
        }

        /* JADX WARN: Multi-variable type inference failed */
        public static boolean isSingleClassifierType(ClassicTypeSystemContext classicTypeSystemContext, SimpleTypeMarker receiver) {
            Intrinsics.checkNotNullParameter(classicTypeSystemContext, "this");
            Intrinsics.checkNotNullParameter(receiver, "receiver");
            if (receiver instanceof SimpleType) {
                if (!KotlinTypeKt.isError((KotlinType) receiver)) {
                    SimpleType simpleType = (SimpleType) receiver;
                    if (!(simpleType.getConstructor().mo1677getDeclarationDescriptor() instanceof TypeAliasDescriptor) && (simpleType.getConstructor().mo1677getDeclarationDescriptor() != null || (receiver instanceof CapturedType) || (receiver instanceof NewCapturedType) || (receiver instanceof DefinitelyNotNullType) || (simpleType.getConstructor() instanceof IntegerLiteralTypeConstructor))) {
                        return true;
                    }
                }
                return false;
            }
            throw new IllegalArgumentException(("ClassicTypeSystemContext couldn't handle: " + receiver + ", " + Reflection.getOrCreateKotlinClass(receiver.getClass())).toString());
        }

        public static KotlinTypeMarker intersectTypes(ClassicTypeSystemContext classicTypeSystemContext, List<? extends KotlinTypeMarker> types) {
            Intrinsics.checkNotNullParameter(classicTypeSystemContext, "this");
            Intrinsics.checkNotNullParameter(types, "types");
            return IntersectionTypeKt.intersectTypes(types);
        }

        public static KotlinTypeMarker createFlexibleType(ClassicTypeSystemContext classicTypeSystemContext, SimpleTypeMarker lowerBound, SimpleTypeMarker upperBound) {
            Intrinsics.checkNotNullParameter(classicTypeSystemContext, "this");
            Intrinsics.checkNotNullParameter(lowerBound, "lowerBound");
            Intrinsics.checkNotNullParameter(upperBound, "upperBound");
            if (lowerBound instanceof SimpleType) {
                if (upperBound instanceof SimpleType) {
                    KotlinTypeFactory kotlinTypeFactory = KotlinTypeFactory.INSTANCE;
                    return KotlinTypeFactory.flexibleType((SimpleType) lowerBound, (SimpleType) upperBound);
                }
                throw new IllegalArgumentException(("ClassicTypeSystemContext couldn't handle: " + classicTypeSystemContext + ", " + Reflection.getOrCreateKotlinClass(classicTypeSystemContext.getClass())).toString());
            }
            throw new IllegalArgumentException(("ClassicTypeSystemContext couldn't handle: " + classicTypeSystemContext + ", " + Reflection.getOrCreateKotlinClass(classicTypeSystemContext.getClass())).toString());
        }

        public static KotlinTypeMarker withNullability(ClassicTypeSystemContext classicTypeSystemContext, KotlinTypeMarker receiver, boolean z) {
            Intrinsics.checkNotNullParameter(classicTypeSystemContext, "this");
            Intrinsics.checkNotNullParameter(receiver, "receiver");
            if (receiver instanceof SimpleTypeMarker) {
                return classicTypeSystemContext.withNullability((SimpleTypeMarker) receiver, z);
            }
            if (!(receiver instanceof FlexibleTypeMarker)) {
                throw new IllegalStateException("sealed".toString());
            }
            FlexibleTypeMarker flexibleTypeMarker = (FlexibleTypeMarker) receiver;
            return classicTypeSystemContext.createFlexibleType(classicTypeSystemContext.withNullability(classicTypeSystemContext.lowerBound(flexibleTypeMarker), z), classicTypeSystemContext.withNullability(classicTypeSystemContext.upperBound(flexibleTypeMarker), z));
        }

        public static AbstractTypeCheckerContext newBaseTypeCheckerContext(ClassicTypeSystemContext classicTypeSystemContext, boolean z, boolean z2) {
            Intrinsics.checkNotNullParameter(classicTypeSystemContext, "this");
            return new ClassicTypeCheckerContext(z, z2, false, null, 12, null);
        }

        public static KotlinTypeMarker makeDefinitelyNotNullOrNotNull(ClassicTypeSystemContext classicTypeSystemContext, KotlinTypeMarker receiver) {
            Intrinsics.checkNotNullParameter(classicTypeSystemContext, "this");
            Intrinsics.checkNotNullParameter(receiver, "receiver");
            if (receiver instanceof UnwrappedType) {
                return ClassicTypeSystemContextKt.makeDefinitelyNotNullOrNotNullInternal((UnwrappedType) receiver);
            }
            throw new IllegalArgumentException(("ClassicTypeSystemContext couldn't handle: " + receiver + ", " + Reflection.getOrCreateKotlinClass(receiver.getClass())).toString());
        }

        public static boolean isProjectionNotNull(ClassicTypeSystemContext classicTypeSystemContext, CapturedTypeMarker receiver) {
            Intrinsics.checkNotNullParameter(classicTypeSystemContext, "this");
            Intrinsics.checkNotNullParameter(receiver, "receiver");
            if (receiver instanceof NewCapturedType) {
                return ((NewCapturedType) receiver).isProjectionNotNull();
            }
            throw new IllegalArgumentException(("ClassicTypeSystemContext couldn't handle: " + receiver + ", " + Reflection.getOrCreateKotlinClass(receiver.getClass())).toString());
        }

        public static boolean isNullableType(ClassicTypeSystemContext classicTypeSystemContext, KotlinTypeMarker receiver) {
            Intrinsics.checkNotNullParameter(classicTypeSystemContext, "this");
            Intrinsics.checkNotNullParameter(receiver, "receiver");
            if (receiver instanceof KotlinType) {
                return TypeUtils.isNullableType((KotlinType) receiver);
            }
            throw new IllegalArgumentException(("ClassicTypeSystemContext couldn't handle: " + receiver + ", " + Reflection.getOrCreateKotlinClass(receiver.getClass())).toString());
        }

        public static SimpleTypeMarker original(ClassicTypeSystemContext classicTypeSystemContext, DefinitelyNotNullTypeMarker receiver) {
            Intrinsics.checkNotNullParameter(classicTypeSystemContext, "this");
            Intrinsics.checkNotNullParameter(receiver, "receiver");
            if (receiver instanceof DefinitelyNotNullType) {
                return ((DefinitelyNotNullType) receiver).getOriginal();
            }
            throw new IllegalArgumentException(("ClassicTypeSystemContext couldn't handle: " + receiver + ", " + Reflection.getOrCreateKotlinClass(receiver.getClass())).toString());
        }

        /* JADX WARN: Multi-variable type inference failed */
        public static boolean isPrimitiveType(ClassicTypeSystemContext classicTypeSystemContext, SimpleTypeMarker receiver) {
            Intrinsics.checkNotNullParameter(classicTypeSystemContext, "this");
            Intrinsics.checkNotNullParameter(receiver, "receiver");
            if (receiver instanceof KotlinType) {
                return KotlinBuiltIns.isPrimitiveType((KotlinType) receiver);
            }
            throw new IllegalArgumentException(("ClassicTypeSystemContext couldn't handle: " + receiver + ", " + Reflection.getOrCreateKotlinClass(receiver.getClass())).toString());
        }

        public static boolean hasAnnotation(ClassicTypeSystemContext classicTypeSystemContext, KotlinTypeMarker receiver, FqName fqName) {
            Intrinsics.checkNotNullParameter(classicTypeSystemContext, "this");
            Intrinsics.checkNotNullParameter(receiver, "receiver");
            Intrinsics.checkNotNullParameter(fqName, "fqName");
            if (receiver instanceof KotlinType) {
                return ((KotlinType) receiver).getAnnotations().hasAnnotation(fqName);
            }
            throw new IllegalArgumentException(("ClassicTypeSystemContext couldn't handle: " + receiver + ", " + Reflection.getOrCreateKotlinClass(receiver.getClass())).toString());
        }

        public static TypeParameterMarker getTypeParameterClassifier(ClassicTypeSystemContext classicTypeSystemContext, TypeConstructorMarker receiver) {
            Intrinsics.checkNotNullParameter(classicTypeSystemContext, "this");
            Intrinsics.checkNotNullParameter(receiver, "receiver");
            if (receiver instanceof TypeConstructor) {
                ClassifierDescriptor classifierDescriptorMo1677getDeclarationDescriptor = ((TypeConstructor) receiver).mo1677getDeclarationDescriptor();
                return classifierDescriptorMo1677getDeclarationDescriptor instanceof TypeParameterDescriptor ? (TypeParameterDescriptor) classifierDescriptorMo1677getDeclarationDescriptor : null;
            }
            throw new IllegalArgumentException(("ClassicTypeSystemContext couldn't handle: " + receiver + ", " + Reflection.getOrCreateKotlinClass(receiver.getClass())).toString());
        }

        public static boolean isInlineClass(ClassicTypeSystemContext classicTypeSystemContext, TypeConstructorMarker receiver) {
            Intrinsics.checkNotNullParameter(classicTypeSystemContext, "this");
            Intrinsics.checkNotNullParameter(receiver, "receiver");
            if (receiver instanceof TypeConstructor) {
                ClassifierDescriptor classifierDescriptorMo1677getDeclarationDescriptor = ((TypeConstructor) receiver).mo1677getDeclarationDescriptor();
                ClassDescriptor classDescriptor = classifierDescriptorMo1677getDeclarationDescriptor instanceof ClassDescriptor ? (ClassDescriptor) classifierDescriptorMo1677getDeclarationDescriptor : null;
                return Intrinsics.areEqual((Object) (classDescriptor != null ? Boolean.valueOf(InlineClassesUtilsKt.isInlineClass(classDescriptor)) : null), (Object) true);
            }
            throw new IllegalArgumentException(("ClassicTypeSystemContext couldn't handle: " + receiver + ", " + Reflection.getOrCreateKotlinClass(receiver.getClass())).toString());
        }

        public static KotlinTypeMarker getRepresentativeUpperBound(ClassicTypeSystemContext classicTypeSystemContext, TypeParameterMarker receiver) {
            Intrinsics.checkNotNullParameter(classicTypeSystemContext, "this");
            Intrinsics.checkNotNullParameter(receiver, "receiver");
            if (receiver instanceof TypeParameterDescriptor) {
                return TypeUtilsKt.getRepresentativeUpperBound((TypeParameterDescriptor) receiver);
            }
            throw new IllegalArgumentException(("ClassicTypeSystemContext couldn't handle: " + receiver + ", " + Reflection.getOrCreateKotlinClass(receiver.getClass())).toString());
        }

        public static KotlinTypeMarker getSubstitutedUnderlyingType(ClassicTypeSystemContext classicTypeSystemContext, KotlinTypeMarker receiver) {
            Intrinsics.checkNotNullParameter(classicTypeSystemContext, "this");
            Intrinsics.checkNotNullParameter(receiver, "receiver");
            if (receiver instanceof KotlinType) {
                return InlineClassesUtilsKt.substitutedUnderlyingType((KotlinType) receiver);
            }
            throw new IllegalArgumentException(("ClassicTypeSystemContext couldn't handle: " + receiver + ", " + Reflection.getOrCreateKotlinClass(receiver.getClass())).toString());
        }

        public static PrimitiveType getPrimitiveType(ClassicTypeSystemContext classicTypeSystemContext, TypeConstructorMarker receiver) {
            Intrinsics.checkNotNullParameter(classicTypeSystemContext, "this");
            Intrinsics.checkNotNullParameter(receiver, "receiver");
            if (receiver instanceof TypeConstructor) {
                ClassifierDescriptor classifierDescriptorMo1677getDeclarationDescriptor = ((TypeConstructor) receiver).mo1677getDeclarationDescriptor();
                Objects.requireNonNull(classifierDescriptorMo1677getDeclarationDescriptor, "null cannot be cast to non-null type org.jetbrains.kotlin.descriptors.ClassDescriptor");
                return KotlinBuiltIns.getPrimitiveType((ClassDescriptor) classifierDescriptorMo1677getDeclarationDescriptor);
            }
            throw new IllegalArgumentException(("ClassicTypeSystemContext couldn't handle: " + receiver + ", " + Reflection.getOrCreateKotlinClass(receiver.getClass())).toString());
        }

        public static PrimitiveType getPrimitiveArrayType(ClassicTypeSystemContext classicTypeSystemContext, TypeConstructorMarker receiver) {
            Intrinsics.checkNotNullParameter(classicTypeSystemContext, "this");
            Intrinsics.checkNotNullParameter(receiver, "receiver");
            if (receiver instanceof TypeConstructor) {
                ClassifierDescriptor classifierDescriptorMo1677getDeclarationDescriptor = ((TypeConstructor) receiver).mo1677getDeclarationDescriptor();
                Objects.requireNonNull(classifierDescriptorMo1677getDeclarationDescriptor, "null cannot be cast to non-null type org.jetbrains.kotlin.descriptors.ClassDescriptor");
                return KotlinBuiltIns.getPrimitiveArrayType((ClassDescriptor) classifierDescriptorMo1677getDeclarationDescriptor);
            }
            throw new IllegalArgumentException(("ClassicTypeSystemContext couldn't handle: " + receiver + ", " + Reflection.getOrCreateKotlinClass(receiver.getClass())).toString());
        }

        public static boolean isUnderKotlinPackage(ClassicTypeSystemContext classicTypeSystemContext, TypeConstructorMarker receiver) {
            Intrinsics.checkNotNullParameter(classicTypeSystemContext, "this");
            Intrinsics.checkNotNullParameter(receiver, "receiver");
            if (receiver instanceof TypeConstructor) {
                ClassifierDescriptor classifierDescriptorMo1677getDeclarationDescriptor = ((TypeConstructor) receiver).mo1677getDeclarationDescriptor();
                return Intrinsics.areEqual((Object) (classifierDescriptorMo1677getDeclarationDescriptor == null ? null : Boolean.valueOf(KotlinBuiltIns.isUnderKotlinPackage(classifierDescriptorMo1677getDeclarationDescriptor))), (Object) true);
            }
            throw new IllegalArgumentException(("ClassicTypeSystemContext couldn't handle: " + receiver + ", " + Reflection.getOrCreateKotlinClass(receiver.getClass())).toString());
        }

        public static FqNameUnsafe getClassFqNameUnsafe(ClassicTypeSystemContext classicTypeSystemContext, TypeConstructorMarker receiver) {
            Intrinsics.checkNotNullParameter(classicTypeSystemContext, "this");
            Intrinsics.checkNotNullParameter(receiver, "receiver");
            if (receiver instanceof TypeConstructor) {
                ClassifierDescriptor classifierDescriptorMo1677getDeclarationDescriptor = ((TypeConstructor) receiver).mo1677getDeclarationDescriptor();
                Objects.requireNonNull(classifierDescriptorMo1677getDeclarationDescriptor, "null cannot be cast to non-null type org.jetbrains.kotlin.descriptors.ClassDescriptor");
                return DescriptorUtilsKt.getFqNameUnsafe((ClassDescriptor) classifierDescriptorMo1677getDeclarationDescriptor);
            }
            throw new IllegalArgumentException(("ClassicTypeSystemContext couldn't handle: " + receiver + ", " + Reflection.getOrCreateKotlinClass(receiver.getClass())).toString());
        }
    }
}
