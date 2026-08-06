package kotlin.reflect.jvm.internal.impl.resolve.constants;

import kotlin.Unit;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.descriptors.ModuleDescriptor;
import kotlin.reflect.jvm.internal.impl.types.ErrorUtils;
import kotlin.reflect.jvm.internal.impl.types.SimpleType;

/* JADX INFO: compiled from: constantValues.kt */
/* JADX INFO: loaded from: classes2.dex */
public abstract class ErrorValue extends ConstantValue<Unit> {
    public static final Companion Companion = new Companion(null);

    public ErrorValue() {
        super(Unit.INSTANCE);
    }

    @Override // kotlin.reflect.jvm.internal.impl.resolve.constants.ConstantValue
    public Unit getValue() {
        throw new UnsupportedOperationException();
    }

    /* JADX INFO: compiled from: constantValues.kt */
    public static final class ErrorValueWithMessage extends ErrorValue {
        private final String message;

        public ErrorValueWithMessage(String message) {
            Intrinsics.checkNotNullParameter(message, "message");
            this.message = message;
        }

        @Override // kotlin.reflect.jvm.internal.impl.resolve.constants.ConstantValue
        public SimpleType getType(ModuleDescriptor module) {
            Intrinsics.checkNotNullParameter(module, "module");
            SimpleType simpleTypeCreateErrorType = ErrorUtils.createErrorType(this.message);
            Intrinsics.checkNotNullExpressionValue(simpleTypeCreateErrorType, "createErrorType(message)");
            return simpleTypeCreateErrorType;
        }

        @Override // kotlin.reflect.jvm.internal.impl.resolve.constants.ConstantValue
        public String toString() {
            return this.message;
        }
    }

    /* JADX INFO: compiled from: constantValues.kt */
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final ErrorValue create(String message) {
            Intrinsics.checkNotNullParameter(message, "message");
            return new ErrorValueWithMessage(message);
        }
    }
}
