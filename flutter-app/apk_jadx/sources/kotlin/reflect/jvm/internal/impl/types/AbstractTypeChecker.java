package kotlin.reflect.jvm.internal.impl.types;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import kotlin.NoWhenBranchMatchedException;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.types.AbstractTypeCheckerContext;
import kotlin.reflect.jvm.internal.impl.types.model.CaptureStatus;
import kotlin.reflect.jvm.internal.impl.types.model.KotlinTypeMarker;
import kotlin.reflect.jvm.internal.impl.types.model.SimpleTypeMarker;
import kotlin.reflect.jvm.internal.impl.types.model.TypeArgumentListMarker;
import kotlin.reflect.jvm.internal.impl.types.model.TypeArgumentMarker;
import kotlin.reflect.jvm.internal.impl.types.model.TypeConstructorMarker;
import kotlin.reflect.jvm.internal.impl.types.model.TypeVariance;
import kotlin.reflect.jvm.internal.impl.utils.SmartList;

/* JADX INFO: compiled from: AbstractTypeChecker.kt */
/* JADX INFO: loaded from: classes2.dex */
public final class AbstractTypeChecker {
    public static final AbstractTypeChecker INSTANCE = new AbstractTypeChecker();
    public static boolean RUN_SLOW_ASSERTIONS;

    /* JADX INFO: compiled from: AbstractTypeChecker.kt */
    public /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;
        public static final /* synthetic */ int[] $EnumSwitchMapping$1;

        static {
            int[] iArr = new int[TypeVariance.valuesCustom().length];
            iArr[TypeVariance.INV.ordinal()] = 1;
            iArr[TypeVariance.OUT.ordinal()] = 2;
            iArr[TypeVariance.IN.ordinal()] = 3;
            $EnumSwitchMapping$0 = iArr;
            int[] iArr2 = new int[AbstractTypeCheckerContext.LowerCapturedTypePolicy.valuesCustom().length];
            iArr2[AbstractTypeCheckerContext.LowerCapturedTypePolicy.CHECK_ONLY_LOWER.ordinal()] = 1;
            iArr2[AbstractTypeCheckerContext.LowerCapturedTypePolicy.CHECK_SUBTYPE_AND_LOWER.ordinal()] = 2;
            iArr2[AbstractTypeCheckerContext.LowerCapturedTypePolicy.SKIP_LOWER.ordinal()] = 3;
            $EnumSwitchMapping$1 = iArr2;
        }
    }

    private AbstractTypeChecker() {
    }

    public static /* synthetic */ boolean isSubtypeOf$default(AbstractTypeChecker abstractTypeChecker, AbstractTypeCheckerContext abstractTypeCheckerContext, KotlinTypeMarker kotlinTypeMarker, KotlinTypeMarker kotlinTypeMarker2, boolean z, int i, Object obj) {
        if ((i & 8) != 0) {
            z = false;
        }
        return abstractTypeChecker.isSubtypeOf(abstractTypeCheckerContext, kotlinTypeMarker, kotlinTypeMarker2, z);
    }

    public final boolean isSubtypeOf(AbstractTypeCheckerContext context, KotlinTypeMarker subType, KotlinTypeMarker superType, boolean z) {
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(subType, "subType");
        Intrinsics.checkNotNullParameter(superType, "superType");
        if (subType == superType) {
            return true;
        }
        if (context.customIsSubtypeOf(subType, superType)) {
            return completeIsSubTypeOf(context, context.prepareType(context.refineType(subType)), context.prepareType(context.refineType(superType)), z);
        }
        return false;
    }

    public final boolean equalTypes(AbstractTypeCheckerContext context, KotlinTypeMarker a, KotlinTypeMarker b) {
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(a, "a");
        Intrinsics.checkNotNullParameter(b, "b");
        if (a == b) {
            return true;
        }
        if (isCommonDenotableType(context, a) && isCommonDenotableType(context, b)) {
            KotlinTypeMarker kotlinTypeMarkerRefineType = context.refineType(a);
            KotlinTypeMarker kotlinTypeMarkerRefineType2 = context.refineType(b);
            SimpleTypeMarker simpleTypeMarkerLowerBoundIfFlexible = context.lowerBoundIfFlexible(kotlinTypeMarkerRefineType);
            if (!context.areEqualTypeConstructors(context.typeConstructor(kotlinTypeMarkerRefineType), context.typeConstructor(kotlinTypeMarkerRefineType2))) {
                return false;
            }
            if (context.argumentsCount(simpleTypeMarkerLowerBoundIfFlexible) == 0) {
                return context.hasFlexibleNullability(kotlinTypeMarkerRefineType) || context.hasFlexibleNullability(kotlinTypeMarkerRefineType2) || context.isMarkedNullable(simpleTypeMarkerLowerBoundIfFlexible) == context.isMarkedNullable(context.lowerBoundIfFlexible(kotlinTypeMarkerRefineType2));
            }
        }
        return isSubtypeOf$default(this, context, a, b, false, 8, null) && isSubtypeOf$default(this, context, b, a, false, 8, null);
    }

    private final boolean completeIsSubTypeOf(AbstractTypeCheckerContext abstractTypeCheckerContext, KotlinTypeMarker kotlinTypeMarker, KotlinTypeMarker kotlinTypeMarker2, boolean z) {
        Boolean boolCheckSubtypeForSpecialCases = checkSubtypeForSpecialCases(abstractTypeCheckerContext, abstractTypeCheckerContext.lowerBoundIfFlexible(kotlinTypeMarker), abstractTypeCheckerContext.upperBoundIfFlexible(kotlinTypeMarker2));
        if (boolCheckSubtypeForSpecialCases != null) {
            boolean zBooleanValue = boolCheckSubtypeForSpecialCases.booleanValue();
            abstractTypeCheckerContext.addSubtypeConstraint(kotlinTypeMarker, kotlinTypeMarker2, z);
            return zBooleanValue;
        }
        Boolean boolAddSubtypeConstraint = abstractTypeCheckerContext.addSubtypeConstraint(kotlinTypeMarker, kotlinTypeMarker2, z);
        if (boolAddSubtypeConstraint != null) {
            return boolAddSubtypeConstraint.booleanValue();
        }
        return isSubtypeOfForSingleClassifierType(abstractTypeCheckerContext, abstractTypeCheckerContext.lowerBoundIfFlexible(kotlinTypeMarker), abstractTypeCheckerContext.upperBoundIfFlexible(kotlinTypeMarker2));
    }

    private final Boolean checkSubtypeForIntegerLiteralType(AbstractTypeCheckerContext abstractTypeCheckerContext, SimpleTypeMarker simpleTypeMarker, SimpleTypeMarker simpleTypeMarker2) {
        if (!abstractTypeCheckerContext.isIntegerLiteralType(simpleTypeMarker) && !abstractTypeCheckerContext.isIntegerLiteralType(simpleTypeMarker2)) {
            return null;
        }
        if (abstractTypeCheckerContext.isIntegerLiteralType(simpleTypeMarker) && abstractTypeCheckerContext.isIntegerLiteralType(simpleTypeMarker2)) {
            return true;
        }
        if (abstractTypeCheckerContext.isIntegerLiteralType(simpleTypeMarker)) {
            if (checkSubtypeForIntegerLiteralType$isTypeInIntegerLiteralType(abstractTypeCheckerContext, this, simpleTypeMarker, simpleTypeMarker2, false)) {
                return true;
            }
        } else if (abstractTypeCheckerContext.isIntegerLiteralType(simpleTypeMarker2) && (checkSubtypeForIntegerLiteralType$isIntegerLiteralTypeInIntersectionComponents(abstractTypeCheckerContext, simpleTypeMarker) || checkSubtypeForIntegerLiteralType$isTypeInIntegerLiteralType(abstractTypeCheckerContext, this, simpleTypeMarker2, simpleTypeMarker, true))) {
            return true;
        }
        return null;
    }

    private static final boolean checkSubtypeForIntegerLiteralType$isTypeInIntegerLiteralType(AbstractTypeCheckerContext abstractTypeCheckerContext, AbstractTypeChecker abstractTypeChecker, SimpleTypeMarker simpleTypeMarker, SimpleTypeMarker simpleTypeMarker2, boolean z) {
        Collection<KotlinTypeMarker> collectionPossibleIntegerTypes = abstractTypeCheckerContext.possibleIntegerTypes(simpleTypeMarker);
        if (!(collectionPossibleIntegerTypes instanceof Collection) || !collectionPossibleIntegerTypes.isEmpty()) {
            for (KotlinTypeMarker kotlinTypeMarker : collectionPossibleIntegerTypes) {
                if (Intrinsics.areEqual(abstractTypeCheckerContext.typeConstructor(kotlinTypeMarker), abstractTypeCheckerContext.typeConstructor(simpleTypeMarker2)) || (z && isSubtypeOf$default(abstractTypeChecker, abstractTypeCheckerContext, simpleTypeMarker2, kotlinTypeMarker, false, 8, null))) {
                    return true;
                }
            }
        }
        return false;
    }

    /* JADX WARN: Removed duplicated region for block: B:26:? A[RETURN, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    private static final boolean checkSubtypeForIntegerLiteralType$isIntegerLiteralTypeInIntersectionComponents(kotlin.reflect.jvm.internal.impl.types.AbstractTypeCheckerContext r4, kotlin.reflect.jvm.internal.impl.types.model.SimpleTypeMarker r5) {
        /*
            kotlin.reflect.jvm.internal.impl.types.model.TypeConstructorMarker r5 = r4.typeConstructor(r5)
            boolean r0 = r5 instanceof kotlin.reflect.jvm.internal.impl.types.model.IntersectionTypeConstructorMarker
            r1 = 1
            r2 = 0
            if (r0 == 0) goto L4d
            java.util.Collection r5 = r4.supertypes(r5)
            java.lang.Iterable r5 = (java.lang.Iterable) r5
            boolean r0 = r5 instanceof java.util.Collection
            if (r0 == 0) goto L1f
            r0 = r5
            java.util.Collection r0 = (java.util.Collection) r0
            boolean r0 = r0.isEmpty()
            if (r0 == 0) goto L1f
        L1d:
            r4 = r2
            goto L4a
        L1f:
            java.util.Iterator r5 = r5.iterator()
        L23:
            boolean r0 = r5.hasNext()
            if (r0 == 0) goto L1d
            java.lang.Object r0 = r5.next()
            kotlin.reflect.jvm.internal.impl.types.model.KotlinTypeMarker r0 = (kotlin.reflect.jvm.internal.impl.types.model.KotlinTypeMarker) r0
            kotlin.reflect.jvm.internal.impl.types.model.SimpleTypeMarker r0 = r4.asSimpleType(r0)
            if (r0 != 0) goto L37
            r0 = 0
            goto L3f
        L37:
            boolean r0 = r4.isIntegerLiteralType(r0)
            java.lang.Boolean r0 = java.lang.Boolean.valueOf(r0)
        L3f:
            java.lang.Boolean r3 = java.lang.Boolean.valueOf(r1)
            boolean r0 = kotlin.jvm.internal.Intrinsics.areEqual(r0, r3)
            if (r0 == 0) goto L23
            r4 = r1
        L4a:
            if (r4 == 0) goto L4d
            goto L4e
        L4d:
            r1 = r2
        L4e:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlin.reflect.jvm.internal.impl.types.AbstractTypeChecker.checkSubtypeForIntegerLiteralType$isIntegerLiteralTypeInIntersectionComponents(kotlin.reflect.jvm.internal.impl.types.AbstractTypeCheckerContext, kotlin.reflect.jvm.internal.impl.types.model.SimpleTypeMarker):boolean");
    }

    private final boolean hasNothingSupertype(AbstractTypeCheckerContext abstractTypeCheckerContext, SimpleTypeMarker simpleTypeMarker) {
        AbstractTypeCheckerContext.SupertypesPolicy.LowerIfFlexible lowerIfFlexible;
        TypeConstructorMarker typeConstructorMarkerTypeConstructor = abstractTypeCheckerContext.typeConstructor(simpleTypeMarker);
        if (abstractTypeCheckerContext.isClassTypeConstructor(typeConstructorMarkerTypeConstructor)) {
            return abstractTypeCheckerContext.isNothingConstructor(typeConstructorMarkerTypeConstructor);
        }
        if (abstractTypeCheckerContext.isNothingConstructor(abstractTypeCheckerContext.typeConstructor(simpleTypeMarker))) {
            return true;
        }
        abstractTypeCheckerContext.initialize();
        ArrayDeque<SimpleTypeMarker> supertypesDeque = abstractTypeCheckerContext.getSupertypesDeque();
        Intrinsics.checkNotNull(supertypesDeque);
        Set<SimpleTypeMarker> supertypesSet = abstractTypeCheckerContext.getSupertypesSet();
        Intrinsics.checkNotNull(supertypesSet);
        supertypesDeque.push(simpleTypeMarker);
        while (!supertypesDeque.isEmpty()) {
            if (supertypesSet.size() > 1000) {
                throw new IllegalStateException(("Too many supertypes for type: " + simpleTypeMarker + ". Supertypes = " + CollectionsKt.joinToString$default(supertypesSet, null, null, null, 0, null, null, 63, null)).toString());
            }
            SimpleTypeMarker current = supertypesDeque.pop();
            Intrinsics.checkNotNullExpressionValue(current, "current");
            if (supertypesSet.add(current)) {
                if (abstractTypeCheckerContext.isClassType(current)) {
                    lowerIfFlexible = AbstractTypeCheckerContext.SupertypesPolicy.None.INSTANCE;
                } else {
                    lowerIfFlexible = AbstractTypeCheckerContext.SupertypesPolicy.LowerIfFlexible.INSTANCE;
                }
                if (!(!Intrinsics.areEqual(lowerIfFlexible, AbstractTypeCheckerContext.SupertypesPolicy.None.INSTANCE))) {
                    lowerIfFlexible = null;
                }
                if (lowerIfFlexible == null) {
                    continue;
                } else {
                    Iterator<KotlinTypeMarker> it = abstractTypeCheckerContext.supertypes(abstractTypeCheckerContext.typeConstructor(current)).iterator();
                    while (it.hasNext()) {
                        SimpleTypeMarker simpleTypeMarkerMo1681transformType = lowerIfFlexible.mo1681transformType(abstractTypeCheckerContext, it.next());
                        if (abstractTypeCheckerContext.isNothingConstructor(abstractTypeCheckerContext.typeConstructor(simpleTypeMarkerMo1681transformType))) {
                            abstractTypeCheckerContext.clear();
                            return true;
                        }
                        supertypesDeque.add(simpleTypeMarkerMo1681transformType);
                    }
                }
            }
        }
        abstractTypeCheckerContext.clear();
        return false;
    }

    /* JADX WARN: Removed duplicated region for block: B:53:0x00e4 A[LOOP:2: B:41:0x00ba->B:53:0x00e4, LOOP_END] */
    /* JADX WARN: Removed duplicated region for block: B:84:0x00f0 A[SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    private final boolean isSubtypeOfForSingleClassifierType(kotlin.reflect.jvm.internal.impl.types.AbstractTypeCheckerContext r19, kotlin.reflect.jvm.internal.impl.types.model.SimpleTypeMarker r20, kotlin.reflect.jvm.internal.impl.types.model.SimpleTypeMarker r21) {
        /*
            Method dump skipped, instruction units count: 407
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlin.reflect.jvm.internal.impl.types.AbstractTypeChecker.isSubtypeOfForSingleClassifierType(kotlin.reflect.jvm.internal.impl.types.AbstractTypeCheckerContext, kotlin.reflect.jvm.internal.impl.types.model.SimpleTypeMarker, kotlin.reflect.jvm.internal.impl.types.model.SimpleTypeMarker):boolean");
    }

    public final boolean isSubtypeForSameConstructor(AbstractTypeCheckerContext abstractTypeCheckerContext, TypeArgumentListMarker capturedSubArguments, SimpleTypeMarker superType) {
        boolean zEqualTypes;
        Intrinsics.checkNotNullParameter(abstractTypeCheckerContext, "<this>");
        Intrinsics.checkNotNullParameter(capturedSubArguments, "capturedSubArguments");
        Intrinsics.checkNotNullParameter(superType, "superType");
        TypeConstructorMarker typeConstructorMarkerTypeConstructor = abstractTypeCheckerContext.typeConstructor(superType);
        int iParametersCount = abstractTypeCheckerContext.parametersCount(typeConstructorMarkerTypeConstructor);
        if (iParametersCount > 0) {
            int i = 0;
            while (true) {
                int i2 = i + 1;
                TypeArgumentMarker argument = abstractTypeCheckerContext.getArgument(superType, i);
                if (!abstractTypeCheckerContext.isStarProjection(argument)) {
                    KotlinTypeMarker type = abstractTypeCheckerContext.getType(argument);
                    TypeArgumentMarker typeArgumentMarker = abstractTypeCheckerContext.get(capturedSubArguments, i);
                    abstractTypeCheckerContext.getVariance(typeArgumentMarker);
                    TypeVariance typeVariance = TypeVariance.INV;
                    KotlinTypeMarker type2 = abstractTypeCheckerContext.getType(typeArgumentMarker);
                    TypeVariance typeVarianceEffectiveVariance = effectiveVariance(abstractTypeCheckerContext.getVariance(abstractTypeCheckerContext.getParameter(typeConstructorMarkerTypeConstructor, i)), abstractTypeCheckerContext.getVariance(argument));
                    if (typeVarianceEffectiveVariance != null) {
                        if (abstractTypeCheckerContext.argumentsDepth <= 100) {
                            abstractTypeCheckerContext.argumentsDepth++;
                            int i3 = WhenMappings.$EnumSwitchMapping$0[typeVarianceEffectiveVariance.ordinal()];
                            if (i3 == 1) {
                                zEqualTypes = equalTypes(abstractTypeCheckerContext, type2, type);
                            } else if (i3 == 2) {
                                zEqualTypes = isSubtypeOf$default(this, abstractTypeCheckerContext, type2, type, false, 8, null);
                            } else {
                                if (i3 != 3) {
                                    throw new NoWhenBranchMatchedException();
                                }
                                zEqualTypes = isSubtypeOf$default(this, abstractTypeCheckerContext, type, type2, false, 8, null);
                            }
                            abstractTypeCheckerContext.argumentsDepth--;
                            if (!zEqualTypes) {
                                return false;
                            }
                        } else {
                            throw new IllegalStateException(Intrinsics.stringPlus("Arguments depth is too high. Some related argument: ", type2).toString());
                        }
                    } else {
                        return abstractTypeCheckerContext.isErrorTypeEqualsToAnything();
                    }
                }
                if (i2 >= iParametersCount) {
                    break;
                }
                i = i2;
            }
        }
        return true;
    }

    private final boolean isCommonDenotableType(AbstractTypeCheckerContext abstractTypeCheckerContext, KotlinTypeMarker kotlinTypeMarker) {
        return abstractTypeCheckerContext.isDenotable(abstractTypeCheckerContext.typeConstructor(kotlinTypeMarker)) && !abstractTypeCheckerContext.isDynamic(kotlinTypeMarker) && !abstractTypeCheckerContext.isDefinitelyNotNullType(kotlinTypeMarker) && Intrinsics.areEqual(abstractTypeCheckerContext.typeConstructor(abstractTypeCheckerContext.lowerBoundIfFlexible(kotlinTypeMarker)), abstractTypeCheckerContext.typeConstructor(abstractTypeCheckerContext.upperBoundIfFlexible(kotlinTypeMarker)));
    }

    public final TypeVariance effectiveVariance(TypeVariance declared, TypeVariance useSite) {
        Intrinsics.checkNotNullParameter(declared, "declared");
        Intrinsics.checkNotNullParameter(useSite, "useSite");
        if (declared == TypeVariance.INV) {
            return useSite;
        }
        if (useSite == TypeVariance.INV || declared == useSite) {
            return declared;
        }
        return null;
    }

    /* JADX WARN: Removed duplicated region for block: B:32:0x006b  */
    /* JADX WARN: Removed duplicated region for block: B:38:0x007d  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    private final java.lang.Boolean checkSubtypeForSpecialCases(kotlin.reflect.jvm.internal.impl.types.AbstractTypeCheckerContext r13, kotlin.reflect.jvm.internal.impl.types.model.SimpleTypeMarker r14, kotlin.reflect.jvm.internal.impl.types.model.SimpleTypeMarker r15) {
        /*
            Method dump skipped, instruction units count: 270
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlin.reflect.jvm.internal.impl.types.AbstractTypeChecker.checkSubtypeForSpecialCases(kotlin.reflect.jvm.internal.impl.types.AbstractTypeCheckerContext, kotlin.reflect.jvm.internal.impl.types.model.SimpleTypeMarker, kotlin.reflect.jvm.internal.impl.types.model.SimpleTypeMarker):java.lang.Boolean");
    }

    private final List<SimpleTypeMarker> collectAllSupertypesWithGivenTypeConstructor(AbstractTypeCheckerContext abstractTypeCheckerContext, SimpleTypeMarker simpleTypeMarker, TypeConstructorMarker typeConstructorMarker) {
        AbstractTypeCheckerContext.SupertypesPolicy.LowerIfFlexible lowerIfFlexibleSubstitutionSupertypePolicy;
        SmartList smartListFastCorrespondingSupertypes = abstractTypeCheckerContext.fastCorrespondingSupertypes(simpleTypeMarker, typeConstructorMarker);
        if (smartListFastCorrespondingSupertypes == null) {
            if (!abstractTypeCheckerContext.isClassTypeConstructor(typeConstructorMarker) && abstractTypeCheckerContext.isClassType(simpleTypeMarker)) {
                return CollectionsKt.emptyList();
            }
            if (abstractTypeCheckerContext.isCommonFinalClassConstructor(typeConstructorMarker)) {
                if (abstractTypeCheckerContext.areEqualTypeConstructors(abstractTypeCheckerContext.typeConstructor(simpleTypeMarker), typeConstructorMarker)) {
                    SimpleTypeMarker simpleTypeMarkerCaptureFromArguments = abstractTypeCheckerContext.captureFromArguments(simpleTypeMarker, CaptureStatus.FOR_SUBTYPING);
                    if (simpleTypeMarkerCaptureFromArguments != null) {
                        simpleTypeMarker = simpleTypeMarkerCaptureFromArguments;
                    }
                    return CollectionsKt.listOf(simpleTypeMarker);
                }
                return CollectionsKt.emptyList();
            }
            smartListFastCorrespondingSupertypes = new SmartList();
            abstractTypeCheckerContext.initialize();
            ArrayDeque<SimpleTypeMarker> supertypesDeque = abstractTypeCheckerContext.getSupertypesDeque();
            Intrinsics.checkNotNull(supertypesDeque);
            Set<SimpleTypeMarker> supertypesSet = abstractTypeCheckerContext.getSupertypesSet();
            Intrinsics.checkNotNull(supertypesSet);
            supertypesDeque.push(simpleTypeMarker);
            while (!supertypesDeque.isEmpty()) {
                if (supertypesSet.size() > 1000) {
                    throw new IllegalStateException(("Too many supertypes for type: " + simpleTypeMarker + ". Supertypes = " + CollectionsKt.joinToString$default(supertypesSet, null, null, null, 0, null, null, 63, null)).toString());
                }
                SimpleTypeMarker current = supertypesDeque.pop();
                Intrinsics.checkNotNullExpressionValue(current, "current");
                if (supertypesSet.add(current)) {
                    SimpleTypeMarker simpleTypeMarkerCaptureFromArguments2 = abstractTypeCheckerContext.captureFromArguments(current, CaptureStatus.FOR_SUBTYPING);
                    if (simpleTypeMarkerCaptureFromArguments2 == null) {
                        simpleTypeMarkerCaptureFromArguments2 = current;
                    }
                    if (abstractTypeCheckerContext.areEqualTypeConstructors(abstractTypeCheckerContext.typeConstructor(simpleTypeMarkerCaptureFromArguments2), typeConstructorMarker)) {
                        smartListFastCorrespondingSupertypes.add(simpleTypeMarkerCaptureFromArguments2);
                        lowerIfFlexibleSubstitutionSupertypePolicy = AbstractTypeCheckerContext.SupertypesPolicy.None.INSTANCE;
                    } else if (abstractTypeCheckerContext.argumentsCount(simpleTypeMarkerCaptureFromArguments2) == 0) {
                        lowerIfFlexibleSubstitutionSupertypePolicy = AbstractTypeCheckerContext.SupertypesPolicy.LowerIfFlexible.INSTANCE;
                    } else {
                        lowerIfFlexibleSubstitutionSupertypePolicy = abstractTypeCheckerContext.substitutionSupertypePolicy(simpleTypeMarkerCaptureFromArguments2);
                    }
                    if (!(!Intrinsics.areEqual(lowerIfFlexibleSubstitutionSupertypePolicy, AbstractTypeCheckerContext.SupertypesPolicy.None.INSTANCE))) {
                        lowerIfFlexibleSubstitutionSupertypePolicy = null;
                    }
                    if (lowerIfFlexibleSubstitutionSupertypePolicy != null) {
                        Iterator<KotlinTypeMarker> it = abstractTypeCheckerContext.supertypes(abstractTypeCheckerContext.typeConstructor(current)).iterator();
                        while (it.hasNext()) {
                            supertypesDeque.add(lowerIfFlexibleSubstitutionSupertypePolicy.mo1681transformType(abstractTypeCheckerContext, it.next()));
                        }
                    }
                }
            }
            abstractTypeCheckerContext.clear();
        }
        return smartListFastCorrespondingSupertypes;
    }

    private final List<SimpleTypeMarker> collectAndFilter(AbstractTypeCheckerContext abstractTypeCheckerContext, SimpleTypeMarker simpleTypeMarker, TypeConstructorMarker typeConstructorMarker) {
        return selectOnlyPureKotlinSupertypes(abstractTypeCheckerContext, collectAllSupertypesWithGivenTypeConstructor(abstractTypeCheckerContext, simpleTypeMarker, typeConstructorMarker));
    }

    /* JADX WARN: Multi-variable type inference failed */
    private final List<SimpleTypeMarker> selectOnlyPureKotlinSupertypes(AbstractTypeCheckerContext abstractTypeCheckerContext, List<? extends SimpleTypeMarker> list) {
        if (list.size() < 2) {
            return list;
        }
        ArrayList arrayList = new ArrayList();
        Iterator it = list.iterator();
        while (true) {
            boolean z = true;
            if (!it.hasNext()) {
                break;
            }
            Object next = it.next();
            TypeArgumentListMarker typeArgumentListMarkerAsArgumentList = abstractTypeCheckerContext.asArgumentList((SimpleTypeMarker) next);
            AbstractTypeCheckerContext abstractTypeCheckerContext2 = abstractTypeCheckerContext;
            int size = abstractTypeCheckerContext2.size(typeArgumentListMarkerAsArgumentList);
            int i = 0;
            while (true) {
                if (i >= size) {
                    break;
                }
                if (!(abstractTypeCheckerContext.asFlexibleType(abstractTypeCheckerContext.getType(abstractTypeCheckerContext2.get(typeArgumentListMarkerAsArgumentList, i))) == null)) {
                    z = false;
                    break;
                }
                i++;
            }
            if (z) {
                arrayList.add(next);
            }
        }
        ArrayList arrayList2 = arrayList;
        return arrayList2.isEmpty() ^ true ? arrayList2 : list;
    }

    public final List<SimpleTypeMarker> findCorrespondingSupertypes(AbstractTypeCheckerContext abstractTypeCheckerContext, SimpleTypeMarker subType, TypeConstructorMarker superConstructor) {
        AbstractTypeCheckerContext.SupertypesPolicy.LowerIfFlexible lowerIfFlexible;
        Intrinsics.checkNotNullParameter(abstractTypeCheckerContext, "<this>");
        Intrinsics.checkNotNullParameter(subType, "subType");
        Intrinsics.checkNotNullParameter(superConstructor, "superConstructor");
        if (abstractTypeCheckerContext.isClassType(subType)) {
            return collectAndFilter(abstractTypeCheckerContext, subType, superConstructor);
        }
        if (!abstractTypeCheckerContext.isClassTypeConstructor(superConstructor) && !abstractTypeCheckerContext.isIntegerLiteralTypeConstructor(superConstructor)) {
            return collectAllSupertypesWithGivenTypeConstructor(abstractTypeCheckerContext, subType, superConstructor);
        }
        SmartList<SimpleTypeMarker> smartList = new SmartList();
        abstractTypeCheckerContext.initialize();
        ArrayDeque<SimpleTypeMarker> supertypesDeque = abstractTypeCheckerContext.getSupertypesDeque();
        Intrinsics.checkNotNull(supertypesDeque);
        Set<SimpleTypeMarker> supertypesSet = abstractTypeCheckerContext.getSupertypesSet();
        Intrinsics.checkNotNull(supertypesSet);
        supertypesDeque.push(subType);
        while (!supertypesDeque.isEmpty()) {
            if (supertypesSet.size() > 1000) {
                throw new IllegalStateException(("Too many supertypes for type: " + subType + ". Supertypes = " + CollectionsKt.joinToString$default(supertypesSet, null, null, null, 0, null, null, 63, null)).toString());
            }
            SimpleTypeMarker current = supertypesDeque.pop();
            Intrinsics.checkNotNullExpressionValue(current, "current");
            if (supertypesSet.add(current)) {
                if (abstractTypeCheckerContext.isClassType(current)) {
                    smartList.add(current);
                    lowerIfFlexible = AbstractTypeCheckerContext.SupertypesPolicy.None.INSTANCE;
                } else {
                    lowerIfFlexible = AbstractTypeCheckerContext.SupertypesPolicy.LowerIfFlexible.INSTANCE;
                }
                if (!(!Intrinsics.areEqual(lowerIfFlexible, AbstractTypeCheckerContext.SupertypesPolicy.None.INSTANCE))) {
                    lowerIfFlexible = null;
                }
                if (lowerIfFlexible != null) {
                    Iterator<KotlinTypeMarker> it = abstractTypeCheckerContext.supertypes(abstractTypeCheckerContext.typeConstructor(current)).iterator();
                    while (it.hasNext()) {
                        supertypesDeque.add(lowerIfFlexible.mo1681transformType(abstractTypeCheckerContext, it.next()));
                    }
                }
            }
        }
        abstractTypeCheckerContext.clear();
        ArrayList arrayList = new ArrayList();
        for (SimpleTypeMarker it2 : smartList) {
            Intrinsics.checkNotNullExpressionValue(it2, "it");
            CollectionsKt.addAll(arrayList, collectAndFilter(abstractTypeCheckerContext, it2, superConstructor));
        }
        return arrayList;
    }
}
