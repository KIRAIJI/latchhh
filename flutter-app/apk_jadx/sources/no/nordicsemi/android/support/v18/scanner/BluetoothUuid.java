package no.nordicsemi.android.support.v18.scanner;

import android.os.ParcelUuid;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.UUID;
import kotlin.UByte;

/* JADX INFO: loaded from: classes2.dex */
final class BluetoothUuid {
    private static final ParcelUuid BASE_UUID = ParcelUuid.fromString("00000000-0000-1000-8000-00805F9B34FB");
    static final int UUID_BYTES_128_BIT = 16;
    static final int UUID_BYTES_16_BIT = 2;
    static final int UUID_BYTES_32_BIT = 4;

    BluetoothUuid() {
    }

    static ParcelUuid parseUuidFrom(byte[] bArr) {
        long j;
        if (bArr == null) {
            throw new IllegalArgumentException("uuidBytes cannot be null");
        }
        int length = bArr.length;
        if (length != 2 && length != 4 && length != 16) {
            throw new IllegalArgumentException("uuidBytes length invalid - " + length);
        }
        if (length == 16) {
            ByteBuffer byteBufferOrder = ByteBuffer.wrap(bArr).order(ByteOrder.LITTLE_ENDIAN);
            return new ParcelUuid(new UUID(byteBufferOrder.getLong(8), byteBufferOrder.getLong(0)));
        }
        if (length == 2) {
            j = ((long) (bArr[0] & UByte.MAX_VALUE)) + ((long) ((bArr[1] & UByte.MAX_VALUE) << 8));
        } else {
            j = ((long) ((bArr[3] & UByte.MAX_VALUE) << 24)) + ((long) (bArr[0] & UByte.MAX_VALUE)) + ((long) ((bArr[1] & UByte.MAX_VALUE) << 8)) + ((long) ((bArr[2] & UByte.MAX_VALUE) << 16));
        }
        ParcelUuid parcelUuid = BASE_UUID;
        return new ParcelUuid(new UUID(parcelUuid.getUuid().getMostSignificantBits() + (j << 32), parcelUuid.getUuid().getLeastSignificantBits()));
    }
}
