package kotlin.reflect.jvm.internal.impl.types;

import java.util.ArrayDeque;
import java.util.Iterator;
import java.util.Set;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.types.AbstractTypeCheckerContext;
import kotlin.reflect.jvm.internal.impl.types.model.CapturedTypeMarker;
import kotlin.reflect.jvm.internal.impl.types.model.KotlinTypeMarker;
import kotlin.reflect.jvm.internal.impl.types.model.SimpleTypeMarker;
import kotlin.reflect.jvm.internal.impl.types.model.TypeConstructorMarker;

/* JADX INFO: compiled from: AbstractTypeChecker.kt */
/* JADX INFO: loaded from: classes2.dex */
public final class AbstractNullabilityChecker {
    public static final AbstractNullabilityChecker INSTANCE = new AbstractNullabilityChecker();

    private AbstractNullabilityChecker() {
    }

    public final boolean isPossibleSubtype(AbstractTypeCheckerContext context, SimpleTypeMarker subType, SimpleTypeMarker superType) {
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(subType, "subType");
        Intrinsics.checkNotNullParameter(superType, "superType");
        return runIsPossibleSubtype(context, subType, superType);
    }

    private final boolean runIsPossibleSubtype(AbstractTypeCheckerContext abstractTypeCheckerContext, SimpleTypeMarker simpleTypeMarker, SimpleTypeMarker simpleTypeMarker2) {
        if (AbstractTypeChecker.RUN_SLOW_ASSERTIONS) {
            if (!abstractTypeCheckerContext.isSingleClassifierType(simpleTypeMarker) && !abstractTypeCheckerContext.isIntersection(abstractTypeCheckerContext.typeConstructor(simpleTypeMarker))) {
                abstractTypeCheckerContext.isAllowedTypeVariable(simpleTypeMarker);
            }
            if (!abstractTypeCheckerContext.isSingleClassifierType(simpleTypeMarker2)) {
                abstractTypeCheckerContext.isAllowedTypeVariable(simpleTypeMarker2);
            }
        }
        if (abstractTypeCheckerContext.isMarkedNullable(simpleTypeMarker2) || abstractTypeCheckerContext.isDefinitelyNotNullType(simpleTypeMarker)) {
            return true;
        }
        if (((simpleTypeMarker instanceof CapturedTypeMarker) && abstractTypeCheckerContext.isProjectionNotNull((CapturedTypeMarker) simpleTypeMarker)) || hasNotNullSupertype(abstractTypeCheckerContext, simpleTypeMarker, AbstractTypeCheckerContext.SupertypesPolicy.LowerIfFlexible.INSTANCE)) {
            return true;
        }
        if (abstractTypeCheckerContext.isDefinitelyNotNullType(simpleTypeMarker2) || hasNotNullSupertype(abstractTypeCheckerContext, simpleTypeMarker2, AbstractTypeCheckerContext.SupertypesPolicy.UpperIfFlexible.INSTANCE) || abstractTypeCheckerContext.isClassType(simpleTypeMarker)) {
            return false;
        }
        return hasPathByNotMarkedNullableNodes(abstractTypeCheckerContext, simpleTypeMarker, abstractTypeCheckerContext.typeConstructor(simpleTypeMarker2));
    }

    public final boolean hasNotNullSupertype(AbstractTypeCheckerContext abstractTypeCheckerContext, SimpleTypeMarker type, AbstractTypeCheckerContext.SupertypesPolicy supertypesPolicy) {
        Intrinsics.checkNotNullParameter(abstractTypeCheckerContext, "<this>");
        Intrinsics.checkNotNullParameter(type, "type");
        Intrinsics.checkNotNullParameter(supertypesPolicy, "supertypesPolicy");
        if (!((abstractTypeCheckerContext.isClassType(type) && !abstractTypeCheckerContext.isMarkedNullable(type)) || abstractTypeCheckerContext.isDefinitelyNotNullType(type))) {
            abstractTypeCheckerContext.initialize();
            ArrayDeque<SimpleTypeMarker> supertypesDeque = abstractTypeCheckerContext.getSupertypesDeque();
            Intrinsics.checkNotNull(supertypesDeque);
            Set<SimpleTypeMarker> supertypesSet = abstractTypeCheckerContext.getSupertypesSet();
            Intrinsics.checkNotNull(supertypesSet);
            supertypesDeque.push(type);
            while (!supertypesDeque.isEmpty()) {
                if (supertypesSet.size() > 1000) {
                    throw new IllegalStateException(("Too many supertypes for type: " + type + ". Supertypes = " + CollectionsKt.joinToString$default(supertypesSet, null, null, null, 0, null, null, 63, null)).toString());
                }
                SimpleTypeMarker current = supertypesDeque.pop();
                Intrinsics.checkNotNullExpressionValue(current, "current");
                if (supertypesSet.add(current)) {
                    AbstractTypeCheckerContext.SupertypesPolicy.None none = abstractTypeCheckerContext.isMarkedNullable(current) ? AbstractTypeCheckerContext.SupertypesPolicy.None.INSTANCE : supertypesPolicy;
                    if (!(!Intrinsics.areEqual(none, AbstractTypeCheckerContext.SupertypesPolicy.None.INSTANCE))) {
                        none = null;
                    }
                    if (none == null) {
                        continue;
                    } else {
                        Iterator<KotlinTypeMarker> it = abstractTypeCheckerContext.supertypes(abstractTypeCheckerContext.typeConstructor(current)).iterator();
                        while (it.hasNext()) {
                            SimpleTypeMarker simpleTypeMarkerMo1681transformType = none.mo1681transformType(abstractTypeCheckerContext, it.next());
                            if ((abstractTypeCheckerContext.isClassType(simpleTypeMarkerMo1681transformType) && !abstractTypeCheckerContext.isMarkedNullable(simpleTypeMarkerMo1681transformType)) || abstractTypeCheckerContext.isDefinitelyNotNullType(simpleTypeMarkerMo1681transformType)) {
                                abstractTypeCheckerContext.clear();
                            } else {
                                supertypesDeque.add(simpleTypeMarkerMo1681transformType);
                            }
                        }
                    }
                }
            }
            abstractTypeCheckerContext.clear();
            return false;
        }
        return true;
    }

    public final boolean hasPathByNotMarkedNullableNodes(AbstractTypeCheckerContext abstractTypeCheckerContext, SimpleTypeMarker start, TypeConstructorMarker end) {
        Intrinsics.checkNotNullParameter(abstractTypeCheckerContext, "<this>");
        Intrinsics.checkNotNullParameter(start, "start");
        Intrinsics.checkNotNullParameter(end, "end");
        if (isApplicableAsEndNode(abstractTypeCheckerContext, start, end)) {
            return true;
        }
        abstractTypeCheckerContext.initialize();
        ArrayDeque<SimpleTypeMarker> supertypesDeque = abstractTypeCheckerContext.getSupertypesDeque();
        Intrinsics.checkNotNull(supertypesDeque);
        Set<SimpleTypeMarker> supertypesSet = abstractTypeCheckerContext.getSupertypesSet();
        Intrinsics.checkNotNull(supertypesSet);
        supertypesDeque.push(start);
        while (!supertypesDeque.isEmpty()) {
            if (supertypesSet.size() > 1000) {
                throw new IllegalStateException(("Too many supertypes for type: " + start + ". Supertypes = " + CollectionsKt.joinToString$default(supertypesSet, null, null, null, 0, null, null, 63, null)).toString());
            }
            SimpleTypeMarker current = supertypesDeque.pop();
            Intrinsics.checkNotNullExpressionValue(current, "current");
            if (supertypesSet.add(current)) {
                AbstractTypeCheckerContext.SupertypesPolicy supertypesPolicy = abstractTypeCheckerContext.isMarkedNullable(current) ? AbstractTypeCheckerContext.SupertypesPolicy.None.INSTANCE : AbstractTypeCheckerContext.SupertypesPolicy.LowerIfFlexible.INSTANCE;
                if (!(!Intrinsics.areEqual(supertypesPolicy, AbstractTypeCheckerContext.SupertypesPolicy.None.INSTANCE))) {
                    supertypesPolicy = null;
                }
                if (supertypesPolicy == null) {
                    continue;
                } else {
                    Iterator<KotlinTypeMarker> it = abstractTypeCheckerContext.supertypes(abstractTypeCheckerContext.typeConstructor(current)).iterator();
                    while (it.hasNext()) {
                        SimpleTypeMarker simpleTypeMarkerMo1681transformType = supertypesPolicy.mo1681transformType(abstractTypeCheckerContext, it.next());
                        if (isApplicableAsEndNode(abstractTypeCheckerContext, simpleTypeMarkerMo1681transformType, end)) {
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

    private final boolean isApplicableAsEndNode(AbstractTypeCheckerContext abstractTypeCheckerContext, SimpleTypeMarker simpleTypeMarker, TypeConstructorMarker typeConstructorMarker) {
        if (abstractTypeCheckerContext.isNothing(simpleTypeMarker)) {
            return true;
        }
        if (abstractTypeCheckerContext.isMarkedNullable(simpleTypeMarker)) {
            return false;
        }
        if (abstractTypeCheckerContext.isStubTypeEqualsToAnything() && abstractTypeCheckerContext.isStubType(simpleTypeMarker)) {
            return true;
        }
        return abstractTypeCheckerContext.areEqualTypeConstructors(abstractTypeCheckerContext.typeConstructor(simpleTypeMarker), typeConstructorMarker);
    }
}
