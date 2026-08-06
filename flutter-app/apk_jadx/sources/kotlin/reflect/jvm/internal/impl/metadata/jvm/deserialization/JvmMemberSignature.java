package kotlin.reflect.jvm.internal.impl.metadata.jvm.deserialization;

import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: JvmMemberSignature.kt */
/* JADX INFO: loaded from: classes2.dex */
public abstract class JvmMemberSignature {
    public /* synthetic */ JvmMemberSignature(DefaultConstructorMarker defaultConstructorMarker) {
        this();
    }

    public abstract String asString();

    public abstract String getDesc();

    public abstract String getName();

    private JvmMemberSignature() {
    }

    /* JADX INFO: compiled from: JvmMemberSignature.kt */
    public static final class Method extends JvmMemberSignature {
        private final String desc;
        private final String name;

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof Method)) {
                return false;
            }
            Method method = (Method) obj;
            return Intrinsics.areEqual(this.name, method.name) && Intrinsics.areEqual(this.desc, method.desc);
        }

        public int hashCode() {
            return (this.name.hashCode() * 31) + this.desc.hashCode();
        }

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public Method(String name, String desc) {
            super(null);
            Intrinsics.checkNotNullParameter(name, "name");
            Intrinsics.checkNotNullParameter(desc, "desc");
            this.name = name;
            this.desc = desc;
        }

        @Override // kotlin.reflect.jvm.internal.impl.metadata.jvm.deserialization.JvmMemberSignature
        public String getDesc() {
            return this.desc;
        }

        @Override // kotlin.reflect.jvm.internal.impl.metadata.jvm.deserialization.JvmMemberSignature
        public String getName() {
            return this.name;
        }

        @Override // kotlin.reflect.jvm.internal.impl.metadata.jvm.deserialization.JvmMemberSignature
        public String asString() {
            return Intrinsics.stringPlus(getName(), getDesc());
        }
    }

    /* JADX INFO: compiled from: JvmMemberSignature.kt */
    public static final class Field extends JvmMemberSignature {
        private final String desc;
        private final String name;

        public final String component1() {
            return this.name;
        }

        public final String component2() {
            return this.desc;
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof Field)) {
                return false;
            }
            Field field = (Field) obj;
            return Intrinsics.areEqual(this.name, field.name) && Intrinsics.areEqual(this.desc, field.desc);
        }

        public int hashCode() {
            return (this.name.hashCode() * 31) + this.desc.hashCode();
        }

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public Field(String name, String desc) {
            super(null);
            Intrinsics.checkNotNullParameter(name, "name");
            Intrinsics.checkNotNullParameter(desc, "desc");
            this.name = name;
            this.desc = desc;
        }

        @Override // kotlin.reflect.jvm.internal.impl.metadata.jvm.deserialization.JvmMemberSignature
        public String getDesc() {
            return this.desc;
        }

        @Override // kotlin.reflect.jvm.internal.impl.metadata.jvm.deserialization.JvmMemberSignature
        public String getName() {
            return this.name;
        }

        @Override // kotlin.reflect.jvm.internal.impl.metadata.jvm.deserialization.JvmMemberSignature
        public String asString() {
            return getName() + ':' + getDesc();
        }
    }

    public final String toString() {
        return asString();
    }
}
