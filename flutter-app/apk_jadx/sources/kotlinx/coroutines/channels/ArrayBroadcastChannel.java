package kotlinx.coroutines.channels;

import androidx.exifinterface.media.ExifInterface;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CancellationException;
import java.util.concurrent.locks.ReentrantLock;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.ranges.RangesKt;
import kotlinx.coroutines.CancellableContinuationImplKt;
import kotlinx.coroutines.DebugKt;
import kotlinx.coroutines.internal.ConcurrentKt;
import kotlinx.coroutines.internal.Symbol;
import kotlinx.coroutines.selects.SelectInstance;
import kotlinx.coroutines.selects.SelectKt;

/* JADX INFO: compiled from: ArrayBroadcastChannel.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000\u0084\u0001\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0010\u0003\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\b\u0005\n\u0002\u0010\t\n\u0002\b\u0006\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0011\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0010\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\u0018\u0002\b\u0000\u0018\u0000*\u0004\b\u0000\u0010\u00012\b\u0012\u0004\u0012\u00028\u00000K2\b\u0012\u0004\u0012\u00028\u00000L:\u0001IB\u000f\u0012\u0006\u0010\u0003\u001a\u00020\u0002¢\u0006\u0004\b\u0004\u0010\u0005J\u0019\u0010\t\u001a\u00020\b2\b\u0010\u0007\u001a\u0004\u0018\u00010\u0006H\u0017¢\u0006\u0004\b\t\u0010\nJ\u001f\u0010\t\u001a\u00020\r2\u000e\u0010\u0007\u001a\n\u0018\u00010\u000bj\u0004\u0018\u0001`\fH\u0016¢\u0006\u0004\b\t\u0010\u000eJ\u0019\u0010\u000f\u001a\u00020\b2\b\u0010\u0007\u001a\u0004\u0018\u00010\u0006H\u0002¢\u0006\u0004\b\u000f\u0010\nJ\u000f\u0010\u0010\u001a\u00020\rH\u0002¢\u0006\u0004\b\u0010\u0010\u0011J\u0019\u0010\u0012\u001a\u00020\b2\b\u0010\u0007\u001a\u0004\u0018\u00010\u0006H\u0016¢\u0006\u0004\b\u0012\u0010\nJ\u000f\u0010\u0014\u001a\u00020\u0013H\u0002¢\u0006\u0004\b\u0014\u0010\u0015J\u0017\u0010\u0017\u001a\u00028\u00002\u0006\u0010\u0016\u001a\u00020\u0013H\u0002¢\u0006\u0004\b\u0017\u0010\u0018J\u0017\u0010\u001b\u001a\u00020\u001a2\u0006\u0010\u0019\u001a\u00028\u0000H\u0014¢\u0006\u0004\b\u001b\u0010\u001cJ#\u0010\u001f\u001a\u00020\u001a2\u0006\u0010\u0019\u001a\u00028\u00002\n\u0010\u001e\u001a\u0006\u0012\u0002\b\u00030\u001dH\u0014¢\u0006\u0004\b\u001f\u0010 J\u0015\u0010\"\u001a\b\u0012\u0004\u0012\u00028\u00000!H\u0016¢\u0006\u0004\b\"\u0010#J4\u0010'\u001a\u00020\r2\u0010\b\u0002\u0010%\u001a\n\u0012\u0004\u0012\u00028\u0000\u0018\u00010$2\u0010\b\u0002\u0010&\u001a\n\u0012\u0004\u0012\u00028\u0000\u0018\u00010$H\u0082\u0010¢\u0006\u0004\b'\u0010(R\u001c\u0010*\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u001a0)8\u0002X\u0082\u0004¢\u0006\u0006\n\u0004\b*\u0010+R\u0014\u0010/\u001a\u00020,8TX\u0094\u0004¢\u0006\u0006\u001a\u0004\b-\u0010.R\u0018\u00102\u001a\u000600j\u0002`18\u0002X\u0082\u0004¢\u0006\u0006\n\u0004\b2\u00103R\u0017\u0010\u0003\u001a\u00020\u00028\u0006¢\u0006\f\n\u0004\b\u0003\u00104\u001a\u0004\b5\u00106R$\u0010;\u001a\u00020\u00132\u0006\u00107\u001a\u00020\u00138B@BX\u0082\u000e¢\u0006\f\u001a\u0004\b8\u0010\u0015\"\u0004\b9\u0010:R\u0014\u0010<\u001a\u00020\b8TX\u0094\u0004¢\u0006\u0006\u001a\u0004\b<\u0010=R\u0014\u0010>\u001a\u00020\b8TX\u0094\u0004¢\u0006\u0006\u001a\u0004\b>\u0010=R$\u0010A\u001a\u00020\u00022\u0006\u00107\u001a\u00020\u00028B@BX\u0082\u000e¢\u0006\f\u001a\u0004\b?\u00106\"\u0004\b@\u0010\u0005R0\u0010D\u001a\u001e\u0012\n\u0012\b\u0012\u0004\u0012\u00028\u00000$0Bj\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00028\u00000$`C8\u0002X\u0082\u0004¢\u0006\u0006\n\u0004\bD\u0010ER$\u0010H\u001a\u00020\u00132\u0006\u00107\u001a\u00020\u00138B@BX\u0082\u000e¢\u0006\f\u001a\u0004\bF\u0010\u0015\"\u0004\bG\u0010:¨\u0006J"}, d2 = {"Lkotlinx/coroutines/channels/ArrayBroadcastChannel;", ExifInterface.LONGITUDE_EAST, "", "capacity", "<init>", "(I)V", "", "cause", "", "cancel", "(Ljava/lang/Throwable;)Z", "Ljava/util/concurrent/CancellationException;", "Lkotlinx/coroutines/CancellationException;", "", "(Ljava/util/concurrent/CancellationException;)V", "cancelInternal", "checkSubOffers", "()V", "close", "", "computeMinHead", "()J", "index", "elementAt", "(J)Ljava/lang/Object;", "element", "", "offerInternal", "(Ljava/lang/Object;)Ljava/lang/Object;", "Lkotlinx/coroutines/selects/SelectInstance;", "select", "offerSelectInternal", "(Ljava/lang/Object;Lkotlinx/coroutines/selects/SelectInstance;)Ljava/lang/Object;", "Lkotlinx/coroutines/channels/ReceiveChannel;", "openSubscription", "()Lkotlinx/coroutines/channels/ReceiveChannel;", "Lkotlinx/coroutines/channels/ArrayBroadcastChannel$Subscriber;", "addSub", "removeSub", "updateHead", "(Lkotlinx/coroutines/channels/ArrayBroadcastChannel$Subscriber;Lkotlinx/coroutines/channels/ArrayBroadcastChannel$Subscriber;)V", "", "buffer", "[Ljava/lang/Object;", "", "getBufferDebugString", "()Ljava/lang/String;", "bufferDebugString", "Ljava/util/concurrent/locks/ReentrantLock;", "Lkotlinx/coroutines/internal/ReentrantLock;", "bufferLock", "Ljava/util/concurrent/locks/ReentrantLock;", "I", "getCapacity", "()I", "value", "getHead", "setHead", "(J)V", "head", "isBufferAlwaysFull", "()Z", "isBufferFull", "getSize", "setSize", "size", "", "Lkotlinx/coroutines/internal/SubscribersList;", "subscribers", "Ljava/util/List;", "getTail", "setTail", "tail", "Subscriber", "kotlinx-coroutines-core", "Lkotlinx/coroutines/channels/AbstractSendChannel;", "Lkotlinx/coroutines/channels/BroadcastChannel;"}, k = 1, mv = {1, 5, 1}, xi = 48)
public final class ArrayBroadcastChannel<E> extends AbstractSendChannel<E> implements BroadcastChannel<E> {
    private volatile /* synthetic */ long _head;
    private volatile /* synthetic */ int _size;
    private volatile /* synthetic */ long _tail;
    private final Object[] buffer;
    private final ReentrantLock bufferLock;
    private final int capacity;
    private final List<Subscriber<E>> subscribers;

    @Override // kotlinx.coroutines.channels.AbstractSendChannel
    protected boolean isBufferAlwaysFull() {
        return false;
    }

    public final int getCapacity() {
        return this.capacity;
    }

    public ArrayBroadcastChannel(int i) {
        super(null);
        this.capacity = i;
        if (!(i >= 1)) {
            throw new IllegalArgumentException(("ArrayBroadcastChannel capacity must be at least 1, but " + getCapacity() + " was specified").toString());
        }
        this.bufferLock = new ReentrantLock();
        this.buffer = new Object[i];
        this._head = 0L;
        this._tail = 0L;
        this._size = 0;
        this.subscribers = ConcurrentKt.subscriberList();
    }

    /* JADX INFO: renamed from: getHead, reason: from getter */
    private final long get_head() {
        return this._head;
    }

    private final void setHead(long j) {
        this._head = j;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: renamed from: getTail, reason: from getter */
    public final long get_tail() {
        return this._tail;
    }

    private final void setTail(long j) {
        this._tail = j;
    }

    /* JADX INFO: renamed from: getSize, reason: from getter */
    private final int get_size() {
        return this._size;
    }

    private final void setSize(int i) {
        this._size = i;
    }

    @Override // kotlinx.coroutines.channels.AbstractSendChannel
    protected boolean isBufferFull() {
        return get_size() >= this.capacity;
    }

    @Override // kotlinx.coroutines.channels.BroadcastChannel
    public ReceiveChannel<E> openSubscription() {
        Subscriber subscriber = new Subscriber(this);
        updateHead$default(this, subscriber, null, 2, null);
        return subscriber;
    }

    @Override // kotlinx.coroutines.channels.AbstractSendChannel, kotlinx.coroutines.channels.SendChannel
    public boolean close(Throwable cause) {
        if (!super.close(cause)) {
            return false;
        }
        checkSubOffers();
        return true;
    }

    @Override // kotlinx.coroutines.channels.BroadcastChannel
    public void cancel(CancellationException cause) {
        cancel(cause);
    }

    /* JADX INFO: Access modifiers changed from: private */
    @Override // kotlinx.coroutines.channels.BroadcastChannel
    /* JADX INFO: renamed from: cancelInternal, reason: merged with bridge method [inline-methods] */
    public final boolean cancel(Throwable cause) {
        boolean zClose = close(cause);
        Iterator<Subscriber<E>> it = this.subscribers.iterator();
        while (it.hasNext()) {
            it.next().cancel(cause);
        }
        return zClose;
    }

    @Override // kotlinx.coroutines.channels.AbstractSendChannel
    protected Object offerInternal(E element) {
        ReentrantLock reentrantLock = this.bufferLock;
        reentrantLock.lock();
        try {
            Closed<?> closedForSend = getClosedForSend();
            if (closedForSend != null) {
                return closedForSend;
            }
            int i = get_size();
            if (i >= getCapacity()) {
                return AbstractChannelKt.OFFER_FAILED;
            }
            long j = get_tail();
            this.buffer[(int) (j % ((long) getCapacity()))] = element;
            setSize(i + 1);
            setTail(j + 1);
            Unit unit = Unit.INSTANCE;
            reentrantLock.unlock();
            checkSubOffers();
            return AbstractChannelKt.OFFER_SUCCESS;
        } finally {
            reentrantLock.unlock();
        }
    }

    @Override // kotlinx.coroutines.channels.AbstractSendChannel
    protected Object offerSelectInternal(E element, SelectInstance<?> select) {
        ReentrantLock reentrantLock = this.bufferLock;
        reentrantLock.lock();
        try {
            Closed<?> closedForSend = getClosedForSend();
            if (closedForSend != null) {
                return closedForSend;
            }
            int i = get_size();
            if (i >= getCapacity()) {
                return AbstractChannelKt.OFFER_FAILED;
            }
            if (!select.trySelect()) {
                return SelectKt.getALREADY_SELECTED();
            }
            long j = get_tail();
            this.buffer[(int) (j % ((long) getCapacity()))] = element;
            setSize(i + 1);
            setTail(j + 1);
            Unit unit = Unit.INSTANCE;
            reentrantLock.unlock();
            checkSubOffers();
            return AbstractChannelKt.OFFER_SUCCESS;
        } finally {
            reentrantLock.unlock();
        }
    }

    private final void checkSubOffers() {
        boolean z;
        Iterator<Subscriber<E>> it = this.subscribers.iterator();
        boolean z2 = false;
        loop0: while (true) {
            z = z2;
            while (it.hasNext()) {
                if (it.next().checkOffer()) {
                    break;
                } else {
                    z = true;
                }
            }
            z2 = true;
        }
        if (z2 || !z) {
            updateHead$default(this, null, null, 3, null);
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    static /* synthetic */ void updateHead$default(ArrayBroadcastChannel arrayBroadcastChannel, Subscriber subscriber, Subscriber subscriber2, int i, Object obj) {
        if ((i & 1) != 0) {
            subscriber = null;
        }
        if ((i & 2) != 0) {
            subscriber2 = null;
        }
        arrayBroadcastChannel.updateHead(subscriber, subscriber2);
    }

    private final void updateHead(Subscriber<E> addSub, Subscriber<E> removeSub) {
        Send sendTakeFirstSendOrPeekClosed;
        Symbol symbolTryResumeSend;
        while (true) {
            ReentrantLock reentrantLock = this.bufferLock;
            reentrantLock.lock();
            if (addSub != null) {
                try {
                    addSub.setSubHead(get_tail());
                    boolean zIsEmpty = this.subscribers.isEmpty();
                    this.subscribers.add(addSub);
                    if (!zIsEmpty) {
                        return;
                    }
                } finally {
                    reentrantLock.unlock();
                }
            }
            if (removeSub != null) {
                this.subscribers.remove(removeSub);
                if (get_head() != removeSub.get_subHead()) {
                    return;
                }
            }
            long jComputeMinHead = computeMinHead();
            long j = get_tail();
            long j2 = get_head();
            long jCoerceAtMost = RangesKt.coerceAtMost(jComputeMinHead, j);
            if (jCoerceAtMost <= j2) {
                return;
            }
            int i = get_size();
            while (j2 < jCoerceAtMost) {
                this.buffer[(int) (j2 % ((long) getCapacity()))] = null;
                boolean z = i >= getCapacity();
                j2++;
                setHead(j2);
                i--;
                setSize(i);
                if (z) {
                    do {
                        sendTakeFirstSendOrPeekClosed = takeFirstSendOrPeekClosed();
                        if (sendTakeFirstSendOrPeekClosed != null && !(sendTakeFirstSendOrPeekClosed instanceof Closed)) {
                            symbolTryResumeSend = sendTakeFirstSendOrPeekClosed.tryResumeSend(null);
                        }
                    } while (symbolTryResumeSend == null);
                    if (DebugKt.getASSERTIONS_ENABLED()) {
                        if (!(symbolTryResumeSend == CancellableContinuationImplKt.RESUME_TOKEN)) {
                            throw new AssertionError();
                        }
                    }
                    this.buffer[(int) (j % ((long) getCapacity()))] = sendTakeFirstSendOrPeekClosed.getElement();
                    setSize(i + 1);
                    setTail(j + 1);
                    Unit unit = Unit.INSTANCE;
                    reentrantLock.unlock();
                    sendTakeFirstSendOrPeekClosed.completeResumeSend();
                    checkSubOffers();
                    addSub = null;
                    removeSub = null;
                }
            }
            return;
        }
    }

    private final long computeMinHead() {
        Iterator<Subscriber<E>> it = this.subscribers.iterator();
        long jCoerceAtMost = Long.MAX_VALUE;
        while (it.hasNext()) {
            jCoerceAtMost = RangesKt.coerceAtMost(jCoerceAtMost, it.next().get_subHead());
        }
        return jCoerceAtMost;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final E elementAt(long index) {
        return (E) this.buffer[(int) (index % ((long) this.capacity))];
    }

    /* JADX INFO: compiled from: ArrayBroadcastChannel.kt */
    @Metadata(d1 = {"\u0000J\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\u0003\n\u0002\b\u0004\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0010\t\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\b\u0002\u0018\u0000*\u0004\b\u0001\u0010\u00012\b\u0012\u0004\u0012\u00028\u00010'2\b\u0012\u0004\u0012\u00028\u00010(B\u0015\u0012\f\u0010\u0003\u001a\b\u0012\u0004\u0012\u00028\u00010\u0002¢\u0006\u0004\b\u0004\u0010\u0005J\r\u0010\u0007\u001a\u00020\u0006¢\u0006\u0004\b\u0007\u0010\bJ\u0019\u0010\u000b\u001a\u00020\u00062\b\u0010\n\u001a\u0004\u0018\u00010\tH\u0016¢\u0006\u0004\b\u000b\u0010\fJ\u000f\u0010\r\u001a\u00020\u0006H\u0002¢\u0006\u0004\b\r\u0010\bJ\u0011\u0010\u000f\u001a\u0004\u0018\u00010\u000eH\u0002¢\u0006\u0004\b\u000f\u0010\u0010J\u0011\u0010\u0011\u001a\u0004\u0018\u00010\u000eH\u0014¢\u0006\u0004\b\u0011\u0010\u0010J\u001d\u0010\u0014\u001a\u0004\u0018\u00010\u000e2\n\u0010\u0013\u001a\u0006\u0012\u0002\b\u00030\u0012H\u0014¢\u0006\u0004\b\u0014\u0010\u0015R\u001a\u0010\u0003\u001a\b\u0012\u0004\u0012\u00028\u00010\u00028\u0002X\u0082\u0004¢\u0006\u0006\n\u0004\b\u0003\u0010\u0016R\u0014\u0010\u0017\u001a\u00020\u00068TX\u0094\u0004¢\u0006\u0006\u001a\u0004\b\u0017\u0010\bR\u0014\u0010\u0018\u001a\u00020\u00068TX\u0094\u0004¢\u0006\u0006\u001a\u0004\b\u0018\u0010\bR\u0014\u0010\u0019\u001a\u00020\u00068TX\u0094\u0004¢\u0006\u0006\u001a\u0004\b\u0019\u0010\bR\u0014\u0010\u001a\u001a\u00020\u00068TX\u0094\u0004¢\u0006\u0006\u001a\u0004\b\u001a\u0010\bR$\u0010!\u001a\u00020\u001b2\u0006\u0010\u001c\u001a\u00020\u001b8F@FX\u0086\u000e¢\u0006\f\u001a\u0004\b\u001d\u0010\u001e\"\u0004\b\u001f\u0010 R\u0018\u0010$\u001a\u00060\"j\u0002`#8\u0002X\u0082\u0004¢\u0006\u0006\n\u0004\b$\u0010%¨\u0006&"}, d2 = {"Lkotlinx/coroutines/channels/ArrayBroadcastChannel$Subscriber;", ExifInterface.LONGITUDE_EAST, "Lkotlinx/coroutines/channels/ArrayBroadcastChannel;", "broadcastChannel", "<init>", "(Lkotlinx/coroutines/channels/ArrayBroadcastChannel;)V", "", "checkOffer", "()Z", "", "cause", "close", "(Ljava/lang/Throwable;)Z", "needsToCheckOfferWithoutLock", "", "peekUnderLock", "()Ljava/lang/Object;", "pollInternal", "Lkotlinx/coroutines/selects/SelectInstance;", "select", "pollSelectInternal", "(Lkotlinx/coroutines/selects/SelectInstance;)Ljava/lang/Object;", "Lkotlinx/coroutines/channels/ArrayBroadcastChannel;", "isBufferAlwaysEmpty", "isBufferAlwaysFull", "isBufferEmpty", "isBufferFull", "", "value", "getSubHead", "()J", "setSubHead", "(J)V", "subHead", "Ljava/util/concurrent/locks/ReentrantLock;", "Lkotlinx/coroutines/internal/ReentrantLock;", "subLock", "Ljava/util/concurrent/locks/ReentrantLock;", "kotlinx-coroutines-core", "Lkotlinx/coroutines/channels/AbstractChannel;", "Lkotlinx/coroutines/channels/ReceiveChannel;"}, k = 1, mv = {1, 5, 1}, xi = 48)
    private static final class Subscriber<E> extends AbstractChannel<E> implements ReceiveChannel<E> {
        private volatile /* synthetic */ long _subHead;
        private final ArrayBroadcastChannel<E> broadcastChannel;
        private final ReentrantLock subLock;

        @Override // kotlinx.coroutines.channels.AbstractChannel
        protected boolean isBufferAlwaysEmpty() {
            return false;
        }

        public Subscriber(ArrayBroadcastChannel<E> arrayBroadcastChannel) {
            super(null);
            this.broadcastChannel = arrayBroadcastChannel;
            this.subLock = new ReentrantLock();
            this._subHead = 0L;
        }

        /* JADX INFO: renamed from: getSubHead, reason: from getter */
        public final long get_subHead() {
            return this._subHead;
        }

        public final void setSubHead(long j) {
            this._subHead = j;
        }

        @Override // kotlinx.coroutines.channels.AbstractChannel
        protected boolean isBufferEmpty() {
            return get_subHead() >= this.broadcastChannel.get_tail();
        }

        @Override // kotlinx.coroutines.channels.AbstractSendChannel
        protected boolean isBufferAlwaysFull() {
            throw new IllegalStateException("Should not be used".toString());
        }

        @Override // kotlinx.coroutines.channels.AbstractSendChannel
        protected boolean isBufferFull() {
            throw new IllegalStateException("Should not be used".toString());
        }

        @Override // kotlinx.coroutines.channels.AbstractSendChannel, kotlinx.coroutines.channels.SendChannel
        public boolean close(Throwable cause) {
            boolean zClose = super.close(cause);
            if (zClose) {
                ArrayBroadcastChannel.updateHead$default(this.broadcastChannel, null, this, 1, null);
                ReentrantLock reentrantLock = this.subLock;
                reentrantLock.lock();
                try {
                    setSubHead(this.broadcastChannel.get_tail());
                    Unit unit = Unit.INSTANCE;
                } finally {
                    reentrantLock.unlock();
                }
            }
            return zClose;
        }

        /* JADX WARN: Code restructure failed: missing block: B:39:0x0073, code lost:
        
            if (r4 != null) goto L41;
         */
        /* JADX WARN: Code restructure failed: missing block: B:41:0x0076, code lost:
        
            close(r4.closeCause);
         */
        /* JADX WARN: Code restructure failed: missing block: B:42:0x007b, code lost:
        
            return r2;
         */
        /* JADX WARN: Multi-variable type inference failed */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct add '--show-bad-code' argument
        */
        public final boolean checkOffer() {
            /*
                r10 = this;
                r0 = 1
                r1 = 0
                r2 = r1
            L3:
                boolean r3 = r10.needsToCheckOfferWithoutLock()
                r4 = 0
                if (r3 == 0) goto L73
                java.util.concurrent.locks.ReentrantLock r3 = r10.subLock
                boolean r3 = r3.tryLock()
                if (r3 != 0) goto L14
                goto L73
            L14:
                java.lang.Object r3 = r10.peekUnderLock()     // Catch: java.lang.Throwable -> L6c
                kotlinx.coroutines.internal.Symbol r5 = kotlinx.coroutines.channels.AbstractChannelKt.POLL_FAILED     // Catch: java.lang.Throwable -> L6c
                if (r3 != r5) goto L22
            L1c:
                java.util.concurrent.locks.ReentrantLock r3 = r10.subLock
                r3.unlock()
                goto L3
            L22:
                boolean r5 = r3 instanceof kotlinx.coroutines.channels.Closed     // Catch: java.lang.Throwable -> L6c
                if (r5 == 0) goto L2f
                r4 = r3
                kotlinx.coroutines.channels.Closed r4 = (kotlinx.coroutines.channels.Closed) r4     // Catch: java.lang.Throwable -> L6c
            L29:
                java.util.concurrent.locks.ReentrantLock r0 = r10.subLock
                r0.unlock()
                goto L73
            L2f:
                kotlinx.coroutines.channels.ReceiveOrClosed r5 = r10.takeFirstReceiveOrPeekClosed()     // Catch: java.lang.Throwable -> L6c
                if (r5 != 0) goto L36
                goto L29
            L36:
                boolean r6 = r5 instanceof kotlinx.coroutines.channels.Closed     // Catch: java.lang.Throwable -> L6c
                if (r6 == 0) goto L3b
                goto L29
            L3b:
                kotlinx.coroutines.internal.Symbol r4 = r5.tryResumeReceive(r3, r4)     // Catch: java.lang.Throwable -> L6c
                if (r4 != 0) goto L42
                goto L1c
            L42:
                boolean r2 = kotlinx.coroutines.DebugKt.getASSERTIONS_ENABLED()     // Catch: java.lang.Throwable -> L6c
                if (r2 == 0) goto L58
                kotlinx.coroutines.internal.Symbol r2 = kotlinx.coroutines.CancellableContinuationImplKt.RESUME_TOKEN     // Catch: java.lang.Throwable -> L6c
                if (r4 != r2) goto L4e
                r2 = r0
                goto L4f
            L4e:
                r2 = r1
            L4f:
                if (r2 == 0) goto L52
                goto L58
            L52:
                java.lang.AssertionError r0 = new java.lang.AssertionError     // Catch: java.lang.Throwable -> L6c
                r0.<init>()     // Catch: java.lang.Throwable -> L6c
                throw r0     // Catch: java.lang.Throwable -> L6c
            L58:
                long r6 = r10.get_subHead()     // Catch: java.lang.Throwable -> L6c
                r8 = 1
                long r6 = r6 + r8
                r10.setSubHead(r6)     // Catch: java.lang.Throwable -> L6c
                java.util.concurrent.locks.ReentrantLock r2 = r10.subLock
                r2.unlock()
                r5.completeResumeReceive(r3)
                r2 = r0
                goto L3
            L6c:
                r0 = move-exception
                java.util.concurrent.locks.ReentrantLock r1 = r10.subLock
                r1.unlock()
                throw r0
            L73:
                if (r4 != 0) goto L76
                goto L7b
            L76:
                java.lang.Throwable r0 = r4.closeCause
                r10.close(r0)
            L7b:
                return r2
            */
            throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.channels.ArrayBroadcastChannel.Subscriber.checkOffer():boolean");
        }

        @Override // kotlinx.coroutines.channels.AbstractChannel
        protected Object pollInternal() {
            boolean z;
            ReentrantLock reentrantLock = this.subLock;
            reentrantLock.lock();
            try {
                Object objPeekUnderLock = peekUnderLock();
                if ((objPeekUnderLock instanceof Closed) || objPeekUnderLock == AbstractChannelKt.POLL_FAILED) {
                    z = false;
                } else {
                    setSubHead(get_subHead() + 1);
                    z = true;
                }
                reentrantLock.unlock();
                Closed closed = objPeekUnderLock instanceof Closed ? (Closed) objPeekUnderLock : null;
                if (closed != null) {
                    close(closed.closeCause);
                }
                if (checkOffer() ? true : z) {
                    ArrayBroadcastChannel.updateHead$default(this.broadcastChannel, null, null, 3, null);
                }
                return objPeekUnderLock;
            } catch (Throwable th) {
                reentrantLock.unlock();
                throw th;
            }
        }

        @Override // kotlinx.coroutines.channels.AbstractChannel
        protected Object pollSelectInternal(SelectInstance<?> select) {
            ReentrantLock reentrantLock = this.subLock;
            reentrantLock.lock();
            try {
                Object objPeekUnderLock = peekUnderLock();
                boolean z = false;
                if (!(objPeekUnderLock instanceof Closed) && objPeekUnderLock != AbstractChannelKt.POLL_FAILED) {
                    if (!select.trySelect()) {
                        objPeekUnderLock = SelectKt.getALREADY_SELECTED();
                    } else {
                        setSubHead(get_subHead() + 1);
                        z = true;
                    }
                }
                reentrantLock.unlock();
                Closed closed = objPeekUnderLock instanceof Closed ? (Closed) objPeekUnderLock : null;
                if (closed != null) {
                    close(closed.closeCause);
                }
                if (checkOffer() ? true : z) {
                    ArrayBroadcastChannel.updateHead$default(this.broadcastChannel, null, null, 3, null);
                }
                return objPeekUnderLock;
            } catch (Throwable th) {
                reentrantLock.unlock();
                throw th;
            }
        }

        private final boolean needsToCheckOfferWithoutLock() {
            if (getClosedForReceive() != null) {
                return false;
            }
            return (isBufferEmpty() && this.broadcastChannel.getClosedForReceive() == null) ? false : true;
        }

        private final Object peekUnderLock() {
            long j = get_subHead();
            Closed<?> closedForReceive = this.broadcastChannel.getClosedForReceive();
            if (j < this.broadcastChannel.get_tail()) {
                Object objElementAt = this.broadcastChannel.elementAt(j);
                Closed<?> closedForReceive2 = getClosedForReceive();
                return closedForReceive2 != null ? closedForReceive2 : objElementAt;
            }
            if (closedForReceive != null) {
                return closedForReceive;
            }
            Closed<?> closedForReceive3 = getClosedForReceive();
            return closedForReceive3 == null ? AbstractChannelKt.POLL_FAILED : closedForReceive3;
        }
    }

    @Override // kotlinx.coroutines.channels.AbstractSendChannel
    protected String getBufferDebugString() {
        return "(buffer:capacity=" + this.buffer.length + ",size=" + get_size() + ')';
    }
}
