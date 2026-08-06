package com.shangxian.pinkink.ui.main;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.constraintlayout.core.motion.utils.TypedValues;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.NotificationCompat;
import androidx.core.view.PointerIconCompat;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.lazyee.klib.extension.AnyExtensionsKt;
import com.lazyee.klib.extension.NumberExtensionsKt;
import com.lazyee.klib.extension.ViewExtensionsKt;
import com.lazyee.klib.mvvm.ViewModel;
import com.shangxian.pinkink.R;
import com.shangxian.pinkink.base.BaseFragment;
import com.shangxian.pinkink.bean.AlbumBean;
import com.shangxian.pinkink.bean.UserCreateThemeBean;
import com.shangxian.pinkink.constants.AppConfig;
import com.shangxian.pinkink.databinding.FragmentAlbumBinding;
import com.shangxian.pinkink.databinding.ItemAlbumBinding;
import com.shangxian.pinkink.event.AIAlbumCoverChangedEvent;
import com.shangxian.pinkink.event.UserCreateThemeAddedEvent;
import com.shangxian.pinkink.event.UserCreateThemeDeleteEvent;
import com.shangxian.pinkink.event.UserCreateThemeSaveEvent;
import com.shangxian.pinkink.mvvm.viewmodel.ThemesViewModel;
import com.shangxian.pinkink.ui.album.AlbumDetailActivity;
import com.shangxian.pinkink.ui.dialog.ConfirmDialog;
import com.shangxian.pinkink.ui.dialog.CreateAlbumDialog;
import com.shangxian.pinkink.ui.dialog.RenameAlbumDialog;
import com.shangxian.pinkink.ui.main.AlbumFragment;
import com.shangxian.pinkink.ui.popup.BottomDeletePopupWindow;
import com.shangxian.pinkink.ui.themes.ThemesListActivity;
import com.shangxian.pinkink.ui.themes.UserThemesActivity;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/* JADX INFO: compiled from: AlbumFragment.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata(d1 = {"\u0000s\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\b\u0003\n\u0002\b\u0003\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006*\u0003\u0015\u0018\u001b\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001:\u000289B\u0005¢\u0006\u0002\u0010\u0003J\b\u0010&\u001a\u00020'H\u0016J\b\u0010(\u001a\u00020'H\u0016J\u0010\u0010)\u001a\u00020'2\u0006\u0010*\u001a\u00020+H\u0007J\u0010\u0010,\u001a\u00020'2\u0006\u0010*\u001a\u00020-H\u0007J\u0010\u0010.\u001a\u00020'2\u0006\u0010*\u001a\u00020/H\u0007J\u0010\u00100\u001a\u00020'2\u0006\u0010*\u001a\u000201H\u0007J\u0012\u00102\u001a\u00020'2\b\u00103\u001a\u0004\u0018\u000104H\u0016J\b\u00105\u001a\u00020'H\u0016J\u0010\u00106\u001a\u00020'2\u0006\u00107\u001a\u00020\u0005H\u0002R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u0012\u0010\u0007\u001a\u00060\bR\u00020\u0000X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u000b0\nX\u0082\u0004¢\u0006\u0002\n\u0000R\u001b\u0010\f\u001a\u00020\r8BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\u0010\u0010\u0011\u001a\u0004\b\u000e\u0010\u000fR\u000e\u0010\u0012\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0013\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\u0014\u001a\u00020\u0015X\u0082\u0004¢\u0006\u0004\n\u0002\u0010\u0016R\u0010\u0010\u0017\u001a\u00020\u0018X\u0082\u0004¢\u0006\u0004\n\u0002\u0010\u0019R\u0010\u0010\u001a\u001a\u00020\u001bX\u0082\u0004¢\u0006\u0004\n\u0002\u0010\u001cR\u000e\u0010\u001d\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u001e\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u001f\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u001b\u0010 \u001a\u00020!8CX\u0082\u0084\u0002¢\u0006\f\n\u0004\b$\u0010\u0011\u001a\u0004\b\"\u0010#R\u0014\u0010%\u001a\b\u0012\u0004\u0012\u00020\u000b0\nX\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006:"}, d2 = {"Lcom/shangxian/pinkink/ui/main/AlbumFragment;", "Lcom/shangxian/pinkink/base/BaseFragment;", "Lcom/shangxian/pinkink/databinding/FragmentAlbumBinding;", "()V", "aiItemHeight", "", "aiItemWidth", "albumAdapter", "Lcom/shangxian/pinkink/ui/main/AlbumFragment$AlbumAdapter;", "albumList", "", "Lcom/shangxian/pinkink/bean/AlbumBean;", "bottomDeletePopupWindow", "Lcom/shangxian/pinkink/ui/popup/BottomDeletePopupWindow;", "getBottomDeletePopupWindow", "()Lcom/shangxian/pinkink/ui/popup/BottomDeletePopupWindow;", "bottomDeletePopupWindow$delegate", "Lkotlin/Lazy;", "bottomMargin", "leftMargin", "mBottomDeletePopupWindowActionCallback", "com/shangxian/pinkink/ui/main/AlbumFragment$mBottomDeletePopupWindowActionCallback$1", "Lcom/shangxian/pinkink/ui/main/AlbumFragment$mBottomDeletePopupWindowActionCallback$1;", "mCreateAlbumDialogActionCallback", "com/shangxian/pinkink/ui/main/AlbumFragment$mCreateAlbumDialogActionCallback$1", "Lcom/shangxian/pinkink/ui/main/AlbumFragment$mCreateAlbumDialogActionCallback$1;", "mRenameAlbumDialogActionCallback", "com/shangxian/pinkink/ui/main/AlbumFragment$mRenameAlbumDialogActionCallback$1", "Lcom/shangxian/pinkink/ui/main/AlbumFragment$mRenameAlbumDialogActionCallback$1;", "rightMargin", "themesItemHeight", "themesItemWidth", "themesViewModel", "Lcom/shangxian/pinkink/mvvm/viewmodel/ThemesViewModel;", "getThemesViewModel", "()Lcom/shangxian/pinkink/mvvm/viewmodel/ThemesViewModel;", "themesViewModel$delegate", "userCreateAlbumList", "initData", "", "initView", "onAIAlbumCoverChanged", NotificationCompat.CATEGORY_EVENT, "Lcom/shangxian/pinkink/event/AIAlbumCoverChangedEvent;", "onAlbumUserCreateThemeAdded", "Lcom/shangxian/pinkink/event/UserCreateThemeAddedEvent;", "onAlbumUserCreateThemeDelete", "Lcom/shangxian/pinkink/event/UserCreateThemeDeleteEvent;", "onAlbumUserCreateThemeSave", "Lcom/shangxian/pinkink/event/UserCreateThemeSaveEvent;", "onCreate", "savedInstanceState", "Landroid/os/Bundle;", "onDestroy", "updateSelectedAlbumCount", "count", "AlbumAdapter", "AlbumViewHolder", "app_release"}, k = 1, mv = {1, 6, 0}, xi = 48)
public final class AlbumFragment extends BaseFragment<FragmentAlbumBinding> {
    private final int aiItemHeight;
    private final int aiItemWidth;
    private final int bottomMargin;
    private final int leftMargin;
    private final int rightMargin;
    private final int themesItemHeight;
    private final int themesItemWidth;

    /* JADX INFO: renamed from: themesViewModel$delegate, reason: from kotlin metadata */
    private final Lazy themesViewModel = LazyKt.lazy(new Function0<ThemesViewModel>() { // from class: com.shangxian.pinkink.ui.main.AlbumFragment$themesViewModel$2
        {
            super(0);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // kotlin.jvm.functions.Function0
        public final ThemesViewModel invoke() {
            return (ThemesViewModel) new ViewModelProvider(this.this$0).get(ThemesViewModel.class);
        }
    });
    private final List<AlbumBean> albumList = new ArrayList();
    private final List<AlbumBean> userCreateAlbumList = new ArrayList();
    private final AlbumAdapter albumAdapter = new AlbumAdapter(this);

    /* JADX INFO: renamed from: bottomDeletePopupWindow$delegate, reason: from kotlin metadata */
    private final Lazy bottomDeletePopupWindow = LazyKt.lazy(new Function0<BottomDeletePopupWindow>() { // from class: com.shangxian.pinkink.ui.main.AlbumFragment$bottomDeletePopupWindow$2
        {
            super(0);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // kotlin.jvm.functions.Function0
        public final BottomDeletePopupWindow invoke() {
            FragmentActivity fragmentActivityRequireActivity = this.this$0.requireActivity();
            Intrinsics.checkNotNullExpressionValue(fragmentActivityRequireActivity, "requireActivity()");
            return new BottomDeletePopupWindow(fragmentActivityRequireActivity).setActionCallback(this.this$0.mBottomDeletePopupWindowActionCallback);
        }
    });
    private final AlbumFragment$mCreateAlbumDialogActionCallback$1 mCreateAlbumDialogActionCallback = new CreateAlbumDialog.ActionCallback() { // from class: com.shangxian.pinkink.ui.main.AlbumFragment$mCreateAlbumDialogActionCallback$1
        @Override // com.shangxian.pinkink.ui.dialog.CreateAlbumDialog.ActionCallback
        public void onConfirmAlbumName(String albumName) {
            Intrinsics.checkNotNullParameter(albumName, "albumName");
            AlbumBean albumBean = new AlbumBean(0L, null, albumName, null, 0L, 0L, null, false, null, 0, PointerIconCompat.TYPE_ZOOM_OUT, null);
            this.this$0.getThemesViewModel().createAlbum(albumBean);
            this.this$0.albumList.add(albumBean);
            this.this$0.albumAdapter.notifyDataSetChanged();
        }
    };
    private final AlbumFragment$mRenameAlbumDialogActionCallback$1 mRenameAlbumDialogActionCallback = new RenameAlbumDialog.ActionCallback() { // from class: com.shangxian.pinkink.ui.main.AlbumFragment$mRenameAlbumDialogActionCallback$1
        @Override // com.shangxian.pinkink.ui.dialog.RenameAlbumDialog.ActionCallback
        public void onRenameAlbumName(AlbumBean album, String albumName) {
            Intrinsics.checkNotNullParameter(album, "album");
            Intrinsics.checkNotNullParameter(albumName, "albumName");
            album.setName(albumName);
            this.this$0.getThemesViewModel().updateAlbum(album);
            this.this$0.albumAdapter.notifyDataSetChanged();
        }
    };
    private final AlbumFragment$mBottomDeletePopupWindowActionCallback$1 mBottomDeletePopupWindowActionCallback = new BottomDeletePopupWindow.ActionCallback() { // from class: com.shangxian.pinkink.ui.main.AlbumFragment$mBottomDeletePopupWindowActionCallback$1
        @Override // com.shangxian.pinkink.ui.popup.BottomDeletePopupWindow.ActionCallback
        public void onDelete() {
            final List<AlbumBean> selectedAlbumList = this.this$0.albumAdapter.getSelectedAlbumList();
            if (selectedAlbumList.isEmpty()) {
                AlbumFragment albumFragment = this.this$0;
                albumFragment.onShowShortToast(albumFragment.getString(R.string.toast_no_select_group));
                return;
            }
            final FragmentActivity activity = this.this$0.getActivity();
            if (activity == null) {
                return;
            }
            final AlbumFragment albumFragment2 = this.this$0;
            ConfirmDialog confirmDialog = new ConfirmDialog(activity);
            String string = activity.getString(R.string.string_alert_ask_delete_group);
            Intrinsics.checkNotNullExpressionValue(string, "getString(R.string.string_alert_ask_delete_group)");
            confirmDialog.setMessage(string).setActionCallback(new ConfirmDialog.ActionCallback() { // from class: com.shangxian.pinkink.ui.main.AlbumFragment$mBottomDeletePopupWindowActionCallback$1$onDelete$1$1
                @Override // com.shangxian.pinkink.ui.dialog.ConfirmDialog.ActionCallback
                public void onCancel() {
                }

                @Override // com.shangxian.pinkink.ui.dialog.ConfirmDialog.ActionCallback
                public void onConfirm() {
                    ThemesViewModel themesViewModel = albumFragment2.getThemesViewModel();
                    List<AlbumBean> list = selectedAlbumList;
                    final AlbumFragment albumFragment3 = albumFragment2;
                    final List<AlbumBean> list2 = selectedAlbumList;
                    final FragmentActivity fragmentActivity = activity;
                    themesViewModel.deleteAlbum(list, new Function0<Unit>() { // from class: com.shangxian.pinkink.ui.main.AlbumFragment$mBottomDeletePopupWindowActionCallback$1$onDelete$1$1$onConfirm$1
                        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                        {
                            super(0);
                        }

                        @Override // kotlin.jvm.functions.Function0
                        public /* bridge */ /* synthetic */ Unit invoke() {
                            invoke2();
                            return Unit.INSTANCE;
                        }

                        /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
                        public final void invoke2() {
                            albumFragment3.albumList.removeAll(list2);
                            albumFragment3.albumAdapter.notifyDataSetChanged();
                            albumFragment3.onShowShortToast(fragmentActivity.getString(R.string.toast_selected_grop_has_been_deleted_successfully));
                        }
                    });
                }
            }).show();
        }
    };

    /* JADX WARN: Type inference failed for: r0v11, types: [com.shangxian.pinkink.ui.main.AlbumFragment$mCreateAlbumDialogActionCallback$1] */
    /* JADX WARN: Type inference failed for: r0v12, types: [com.shangxian.pinkink.ui.main.AlbumFragment$mRenameAlbumDialogActionCallback$1] */
    /* JADX WARN: Type inference failed for: r0v13, types: [com.shangxian.pinkink.ui.main.AlbumFragment$mBottomDeletePopupWindowActionCallback$1] */
    public AlbumFragment() {
        int screenWidth = AnyExtensionsKt.getScreenWidth(this) - NumberExtensionsKt.dp2px(30);
        this.aiItemWidth = screenWidth;
        this.aiItemHeight = screenWidth / 2;
        int screenWidth2 = (AnyExtensionsKt.getScreenWidth(this) - NumberExtensionsKt.dp2px(30)) / 2;
        this.themesItemWidth = screenWidth2;
        this.themesItemHeight = (int) (((double) screenWidth2) * 1.733d);
        this.leftMargin = NumberExtensionsKt.dp2px(15);
        this.rightMargin = NumberExtensionsKt.dp2px(15);
        this.bottomMargin = NumberExtensionsKt.dp2px(10);
    }

    /* JADX INFO: Access modifiers changed from: private */
    @ViewModel
    public final ThemesViewModel getThemesViewModel() {
        return (ThemesViewModel) this.themesViewModel.getValue();
    }

    private final BottomDeletePopupWindow getBottomDeletePopupWindow() {
        return (BottomDeletePopupWindow) this.bottomDeletePopupWindow.getValue();
    }

    @Override // androidx.fragment.app.Fragment
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }

    @Override // com.lazyee.klib.base.ViewBindingFragment
    public void initData() {
        super.initData();
        List<AlbumBean> list = this.albumList;
        String string = getString(R.string.ai_image_library);
        int item_type_ai = AlbumBean.INSTANCE.getITEM_TYPE_AI();
        String aiAlbumCoverUrl = AppConfig.INSTANCE.getAiAlbumCoverUrl();
        String aiThemeCategoryId = AppConfig.INSTANCE.getAiThemeCategoryId();
        Intrinsics.checkNotNullExpressionValue(string, "getString(R.string.ai_image_library)");
        list.add(new AlbumBean(0L, aiThemeCategoryId, string, null, 0L, 0L, aiAlbumCoverUrl, false, null, item_type_ai, 441, null));
        String string2 = getString(R.string.string_my_themes);
        Intrinsics.checkNotNullExpressionValue(string2, "getString(R.string.string_my_themes)");
        AlbumBean albumBean = new AlbumBean(0L, null, string2, null, 0L, 0L, null, false, null, AlbumBean.INSTANCE.getITEM_TYPE_USER_CREATE(), TypedValues.PositionType.TYPE_PERCENT_Y, null);
        getThemesViewModel().fullAlbum(albumBean);
        this.albumList.add(albumBean);
        this.userCreateAlbumList.addAll(getThemesViewModel().getAllAlbumByUser());
        this.albumList.addAll(this.userCreateAlbumList);
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.lazyee.klib.base.ViewBindingFragment
    public void initView() {
        super.initView();
        initData();
        final FragmentAlbumBinding fragmentAlbumBinding = (FragmentAlbumBinding) getMViewBinding();
        fragmentAlbumBinding.rvAlbum.setLayoutManager(this.albumAdapter.getGridLayoutManager());
        fragmentAlbumBinding.rvAlbum.setAdapter(this.albumAdapter);
        fragmentAlbumBinding.ivAdd.setOnClickListener(new View.OnClickListener() { // from class: com.shangxian.pinkink.ui.main.AlbumFragment$$ExternalSyntheticLambda0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                AlbumFragment.m211initView$lambda4$lambda1(this.f$0, view);
            }
        });
        fragmentAlbumBinding.ivEdit.setOnClickListener(new View.OnClickListener() { // from class: com.shangxian.pinkink.ui.main.AlbumFragment$$ExternalSyntheticLambda1
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                AlbumFragment.m212initView$lambda4$lambda2(this.f$0, fragmentAlbumBinding, view);
            }
        });
        fragmentAlbumBinding.ivExitEditMode.setOnClickListener(new View.OnClickListener() { // from class: com.shangxian.pinkink.ui.main.AlbumFragment$$ExternalSyntheticLambda2
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                AlbumFragment.m213initView$lambda4$lambda3(this.f$0, fragmentAlbumBinding, view);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: renamed from: initView$lambda-4$lambda-1, reason: not valid java name */
    public static final void m211initView$lambda4$lambda1(AlbumFragment this$0, View view) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        FragmentActivity activity = this$0.getActivity();
        if (activity == null) {
            return;
        }
        new CreateAlbumDialog(activity).setActionCallback(this$0.mCreateAlbumDialogActionCallback).show();
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: renamed from: initView$lambda-4$lambda-2, reason: not valid java name */
    public static final void m212initView$lambda4$lambda2(AlbumFragment this$0, FragmentAlbumBinding this_run, View view) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Intrinsics.checkNotNullParameter(this_run, "$this_run");
        this$0.albumAdapter.setEditMode(true);
        LinearLayout llEditModeTitleBar = this_run.llEditModeTitleBar;
        Intrinsics.checkNotNullExpressionValue(llEditModeTitleBar, "llEditModeTitleBar");
        ViewExtensionsKt.visible(llEditModeTitleBar);
        this$0.getBottomDeletePopupWindow().showAtLocation(this_run.getRoot(), 80, 0, 0);
        this$0.updateSelectedAlbumCount(0);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: renamed from: initView$lambda-4$lambda-3, reason: not valid java name */
    public static final void m213initView$lambda4$lambda3(AlbumFragment this$0, FragmentAlbumBinding this_run, View view) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Intrinsics.checkNotNullParameter(this_run, "$this_run");
        this$0.albumAdapter.setEditMode(false);
        LinearLayout llEditModeTitleBar = this_run.llEditModeTitleBar;
        Intrinsics.checkNotNullExpressionValue(llEditModeTitleBar, "llEditModeTitleBar");
        ViewExtensionsKt.gone(llEditModeTitleBar);
        this$0.getBottomDeletePopupWindow().dismiss();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public final void onAIAlbumCoverChanged(AIAlbumCoverChangedEvent event) {
        Object next;
        Intrinsics.checkNotNullParameter(event, "event");
        Iterator<T> it = this.albumList.iterator();
        while (true) {
            if (!it.hasNext()) {
                next = null;
                break;
            } else {
                next = it.next();
                if (((AlbumBean) next).getItemType() == AlbumBean.INSTANCE.getITEM_TYPE_AI()) {
                    break;
                }
            }
        }
        AlbumBean albumBean = (AlbumBean) next;
        if (albumBean != null) {
            albumBean.setCover(event.getCover());
        }
        this.albumAdapter.notifyDataSetChanged();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public final void onAlbumUserCreateThemeDelete(UserCreateThemeDeleteEvent event) {
        String composeImagePath;
        Object next;
        Intrinsics.checkNotNullParameter(event, "event");
        List listFilterNotNull = CollectionsKt.filterNotNull(event.getDeleteUserCreateThemeList());
        Iterator<T> it = this.albumList.iterator();
        while (true) {
            composeImagePath = null;
            if (!it.hasNext()) {
                next = null;
                break;
            }
            next = it.next();
            AlbumBean albumBean = (AlbumBean) next;
            if (albumBean.getColumnId() == event.getAlbumColumnId() && albumBean.getItemType() != AlbumBean.INSTANCE.getITEM_TYPE_AI()) {
                break;
            }
        }
        AlbumBean albumBean2 = (AlbumBean) next;
        if (albumBean2 == null) {
            return;
        }
        albumBean2.getUserCreateThemeList().removeAll(listFilterNotNull);
        UserCreateThemeBean userCreateThemeBean = (UserCreateThemeBean) CollectionsKt.firstOrNull((List) albumBean2.getUserCreateThemeList());
        if (TextUtils.isEmpty(userCreateThemeBean == null ? null : userCreateThemeBean.getComposeImagePath())) {
            if (userCreateThemeBean != null) {
                composeImagePath = userCreateThemeBean.getBackgroundImagePath();
            }
        } else if (userCreateThemeBean != null) {
            composeImagePath = userCreateThemeBean.getComposeImagePath();
        }
        if (composeImagePath == null) {
            composeImagePath = "";
        }
        albumBean2.setCover(composeImagePath);
        this.albumAdapter.notifyDataSetChanged();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public final void onAlbumUserCreateThemeAdded(UserCreateThemeAddedEvent event) {
        String composeImagePath;
        Object next;
        Intrinsics.checkNotNullParameter(event, "event");
        List listFilterNotNull = CollectionsKt.filterNotNull(event.getAddedUserCreateThemeList());
        Iterator<T> it = this.albumList.iterator();
        while (true) {
            composeImagePath = null;
            if (!it.hasNext()) {
                next = null;
                break;
            }
            next = it.next();
            AlbumBean albumBean = (AlbumBean) next;
            if (albumBean.getColumnId() == event.getAlbumColumnId() && albumBean.getItemType() != AlbumBean.INSTANCE.getITEM_TYPE_AI()) {
                break;
            }
        }
        AlbumBean albumBean2 = (AlbumBean) next;
        if (albumBean2 == null) {
            return;
        }
        albumBean2.getUserCreateThemeList().addAll(listFilterNotNull);
        UserCreateThemeBean userCreateThemeBean = (UserCreateThemeBean) CollectionsKt.firstOrNull((List) albumBean2.getUserCreateThemeList());
        if (TextUtils.isEmpty(userCreateThemeBean == null ? null : userCreateThemeBean.getComposeImagePath())) {
            if (userCreateThemeBean != null) {
                composeImagePath = userCreateThemeBean.getBackgroundImagePath();
            }
        } else if (userCreateThemeBean != null) {
            composeImagePath = userCreateThemeBean.getComposeImagePath();
        }
        if (composeImagePath == null) {
            composeImagePath = "";
        }
        albumBean2.setCover(composeImagePath);
        this.albumAdapter.notifyDataSetChanged();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public final void onAlbumUserCreateThemeSave(UserCreateThemeSaveEvent event) {
        String composeImagePath;
        Object next;
        Object next2;
        UserCreateThemeBean userCreateThemeBean;
        Intrinsics.checkNotNullParameter(event, "event");
        Iterator<T> it = this.albumList.iterator();
        while (true) {
            composeImagePath = null;
            if (!it.hasNext()) {
                next = null;
                break;
            }
            next = it.next();
            AlbumBean albumBean = (AlbumBean) next;
            if (albumBean.getColumnId() == event.getUserCreateThemeBean().getAlbumColumnId() && albumBean.getItemType() != AlbumBean.INSTANCE.getITEM_TYPE_AI()) {
                break;
            }
        }
        AlbumBean albumBean2 = (AlbumBean) next;
        if (albumBean2 == null) {
            return;
        }
        List<UserCreateThemeBean> userCreateThemeList = albumBean2.getUserCreateThemeList();
        if (userCreateThemeList == null) {
            userCreateThemeBean = null;
        } else {
            Iterator<T> it2 = userCreateThemeList.iterator();
            while (true) {
                if (!it2.hasNext()) {
                    next2 = null;
                    break;
                } else {
                    next2 = it2.next();
                    if (((UserCreateThemeBean) next2).getColumnId() == event.getUserCreateThemeBean().getColumnId()) {
                        break;
                    }
                }
            }
            userCreateThemeBean = (UserCreateThemeBean) next2;
        }
        if (userCreateThemeBean == null) {
            albumBean2.getUserCreateThemeList().add(event.getUserCreateThemeBean());
        } else {
            albumBean2.getUserCreateThemeList().set(albumBean2.getUserCreateThemeList().indexOf(userCreateThemeBean), event.getUserCreateThemeBean());
        }
        UserCreateThemeBean userCreateThemeBean2 = (UserCreateThemeBean) CollectionsKt.firstOrNull((List) albumBean2.getUserCreateThemeList());
        if (TextUtils.isEmpty(userCreateThemeBean2 == null ? null : userCreateThemeBean2.getComposeImagePath())) {
            if (userCreateThemeBean2 != null) {
                composeImagePath = userCreateThemeBean2.getBackgroundImagePath();
            }
        } else if (userCreateThemeBean2 != null) {
            composeImagePath = userCreateThemeBean2.getComposeImagePath();
        }
        if (composeImagePath == null) {
            composeImagePath = "";
        }
        albumBean2.setCover(composeImagePath);
        this.albumAdapter.notifyDataSetChanged();
    }

    @Override // com.lazyee.klib.base.BaseFragment, androidx.fragment.app.Fragment
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Multi-variable type inference failed */
    public final void updateSelectedAlbumCount(int count) {
        ((FragmentAlbumBinding) getMViewBinding()).tvSelectCount.setText(getString(R.string.string_already_selected_x_group, Integer.valueOf(count)));
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: compiled from: AlbumFragment.kt */
    @Metadata(d1 = {"\u0000@\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0004\b\u0082\u0004\u0018\u00002\f\u0012\b\u0012\u00060\u0002R\u00020\u00030\u0001B\u0005¢\u0006\u0002\u0010\u0004J\u0006\u0010\u0007\u001a\u00020\bJ\b\u0010\t\u001a\u00020\nH\u0016J\f\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\r0\fJ\u001c\u0010\u000e\u001a\u00020\u000f2\n\u0010\u0010\u001a\u00060\u0002R\u00020\u00032\u0006\u0010\u0011\u001a\u00020\nH\u0016J\u001c\u0010\u0012\u001a\u00060\u0002R\u00020\u00032\u0006\u0010\u0013\u001a\u00020\u00142\u0006\u0010\u0015\u001a\u00020\nH\u0016J\u0010\u0010\u0016\u001a\u00020\u000f2\u0006\u0010\u0017\u001a\u00020\u0006H\u0007R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006\u0018"}, d2 = {"Lcom/shangxian/pinkink/ui/main/AlbumFragment$AlbumAdapter;", "Landroidx/recyclerview/widget/RecyclerView$Adapter;", "Lcom/shangxian/pinkink/ui/main/AlbumFragment$AlbumViewHolder;", "Lcom/shangxian/pinkink/ui/main/AlbumFragment;", "(Lcom/shangxian/pinkink/ui/main/AlbumFragment;)V", "isEditMode", "", "getGridLayoutManager", "Landroidx/recyclerview/widget/GridLayoutManager;", "getItemCount", "", "getSelectedAlbumList", "", "Lcom/shangxian/pinkink/bean/AlbumBean;", "onBindViewHolder", "", "holder", "position", "onCreateViewHolder", "parent", "Landroid/view/ViewGroup;", "viewType", "setEditMode", "mode", "app_release"}, k = 1, mv = {1, 6, 0}, xi = 48)
    final class AlbumAdapter extends RecyclerView.Adapter<AlbumViewHolder> {
        private boolean isEditMode;
        final /* synthetic */ AlbumFragment this$0;

        public AlbumAdapter(AlbumFragment this$0) {
            Intrinsics.checkNotNullParameter(this$0, "this$0");
            this.this$0 = this$0;
        }

        public final void setEditMode(boolean mode) {
            this.isEditMode = mode;
            if (!mode) {
                Iterator it = this.this$0.albumList.iterator();
                while (it.hasNext()) {
                    ((AlbumBean) it.next()).setSelected(false);
                }
            }
            notifyDataSetChanged();
        }

        public final List<AlbumBean> getSelectedAlbumList() {
            List list = this.this$0.albumList;
            ArrayList arrayList = new ArrayList();
            for (Object obj : list) {
                if (((AlbumBean) obj).isSelected()) {
                    arrayList.add(obj);
                }
            }
            return arrayList;
        }

        public final GridLayoutManager getGridLayoutManager() {
            final GridLayoutManager gridLayoutManager = new GridLayoutManager(this.this$0.getContext(), 2);
            final AlbumFragment albumFragment = this.this$0;
            gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() { // from class: com.shangxian.pinkink.ui.main.AlbumFragment$AlbumAdapter$getGridLayoutManager$1
                @Override // androidx.recyclerview.widget.GridLayoutManager.SpanSizeLookup
                public int getSpanSize(int position) {
                    return position >= albumFragment.albumList.size() ? gridLayoutManager.getSpanCount() : ((AlbumBean) albumFragment.albumList.get(position)).getItemType() == AlbumBean.INSTANCE.getITEM_TYPE_AI() ? 2 : 1;
                }
            });
            return gridLayoutManager;
        }

        @Override // androidx.recyclerview.widget.RecyclerView.Adapter
        public AlbumViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            Intrinsics.checkNotNullParameter(parent, "parent");
            AlbumFragment albumFragment = this.this$0;
            ItemAlbumBinding itemAlbumBindingInflate = ItemAlbumBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
            Intrinsics.checkNotNullExpressionValue(itemAlbumBindingInflate, "inflate(LayoutInflater.f…nt.context),parent,false)");
            return new AlbumViewHolder(albumFragment, itemAlbumBindingInflate);
        }

        @Override // androidx.recyclerview.widget.RecyclerView.Adapter
        public int getItemCount() {
            return this.this$0.albumList.size();
        }

        @Override // androidx.recyclerview.widget.RecyclerView.Adapter
        public void onBindViewHolder(AlbumViewHolder holder, int position) {
            Intrinsics.checkNotNullParameter(holder, "holder");
            holder.bind((AlbumBean) this.this$0.albumList.get(position), this.isEditMode);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: compiled from: AlbumFragment.kt */
    @Metadata(d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\b\u0082\u0004\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0016\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nR\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u000b"}, d2 = {"Lcom/shangxian/pinkink/ui/main/AlbumFragment$AlbumViewHolder;", "Landroidx/recyclerview/widget/RecyclerView$ViewHolder;", "binding", "Lcom/shangxian/pinkink/databinding/ItemAlbumBinding;", "(Lcom/shangxian/pinkink/ui/main/AlbumFragment;Lcom/shangxian/pinkink/databinding/ItemAlbumBinding;)V", "bind", "", "albumBean", "Lcom/shangxian/pinkink/bean/AlbumBean;", "isEditMode", "", "app_release"}, k = 1, mv = {1, 6, 0}, xi = 48)
    final class AlbumViewHolder extends RecyclerView.ViewHolder {
        private final ItemAlbumBinding binding;
        final /* synthetic */ AlbumFragment this$0;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AlbumViewHolder(AlbumFragment this$0, ItemAlbumBinding binding) {
            super(binding.getRoot());
            Intrinsics.checkNotNullParameter(this$0, "this$0");
            Intrinsics.checkNotNullParameter(binding, "binding");
            this.this$0 = this$0;
            this.binding = binding;
        }

        public final void bind(final AlbumBean albumBean, final boolean isEditMode) {
            LinearLayout.LayoutParams layoutParams;
            Intrinsics.checkNotNullParameter(albumBean, "albumBean");
            if (albumBean.getItemType() == AlbumBean.INSTANCE.getITEM_TYPE_AI()) {
                layoutParams = new LinearLayout.LayoutParams(this.this$0.aiItemWidth, this.this$0.aiItemHeight);
            } else {
                layoutParams = new LinearLayout.LayoutParams(this.this$0.themesItemWidth, this.this$0.themesItemHeight);
            }
            layoutParams.bottomMargin = this.this$0.bottomMargin;
            if (albumBean.getItemType() == AlbumBean.INSTANCE.getITEM_TYPE_AI()) {
                layoutParams.leftMargin = this.this$0.leftMargin;
                layoutParams.rightMargin = this.this$0.rightMargin;
            } else if (albumBean.getItemType() != AlbumBean.INSTANCE.getITEM_TYPE_USER_CREATE() && this.this$0.userCreateAlbumList.indexOf(albumBean) % 2 == 0) {
                layoutParams.leftMargin = this.this$0.leftMargin / 2;
                layoutParams.rightMargin = this.this$0.rightMargin;
            } else {
                layoutParams.leftMargin = this.this$0.leftMargin;
                layoutParams.rightMargin = this.this$0.rightMargin / 2;
            }
            this.binding.clItem.setLayoutParams(layoutParams);
            if (albumBean.getItemType() == AlbumBean.INSTANCE.getITEM_TYPE_AI() || albumBean.getItemType() == AlbumBean.INSTANCE.getITEM_TYPE_USER_CREATE()) {
                ImageView imageView = this.binding.ivRename;
                Intrinsics.checkNotNullExpressionValue(imageView, "binding.ivRename");
                ViewExtensionsKt.gone(imageView);
                ImageView imageView2 = this.binding.ivSelect;
                Intrinsics.checkNotNullExpressionValue(imageView2, "binding.ivSelect");
                ViewExtensionsKt.gone(imageView2);
            } else if (isEditMode) {
                ImageView imageView3 = this.binding.ivRename;
                Intrinsics.checkNotNullExpressionValue(imageView3, "binding.ivRename");
                ViewExtensionsKt.visible(imageView3);
                ImageView imageView4 = this.binding.ivSelect;
                Intrinsics.checkNotNullExpressionValue(imageView4, "binding.ivSelect");
                ViewExtensionsKt.visible(imageView4);
            } else {
                ImageView imageView5 = this.binding.ivRename;
                Intrinsics.checkNotNullExpressionValue(imageView5, "binding.ivRename");
                ViewExtensionsKt.gone(imageView5);
                ImageView imageView6 = this.binding.ivSelect;
                Intrinsics.checkNotNullExpressionValue(imageView6, "binding.ivSelect");
                ViewExtensionsKt.gone(imageView6);
            }
            if (albumBean.getItemType() == AlbumBean.INSTANCE.getITEM_TYPE_AI()) {
                TextView textView = this.binding.tvThemeCount;
                Intrinsics.checkNotNullExpressionValue(textView, "binding.tvThemeCount");
                ViewExtensionsKt.gone(textView);
            } else {
                TextView textView2 = this.binding.tvThemeCount;
                Intrinsics.checkNotNullExpressionValue(textView2, "binding.tvThemeCount");
                ViewExtensionsKt.visible(textView2);
            }
            ConstraintLayout constraintLayout = this.binding.clItem;
            final AlbumFragment albumFragment = this.this$0;
            constraintLayout.setOnClickListener(new View.OnClickListener() { // from class: com.shangxian.pinkink.ui.main.AlbumFragment$AlbumViewHolder$$ExternalSyntheticLambda1
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    AlbumFragment.AlbumViewHolder.m216bind$lambda1(albumFragment, albumBean, view);
                }
            });
            this.binding.ivSelect.setSelected(albumBean.isSelected());
            ImageView imageView7 = this.binding.ivSelect;
            final AlbumFragment albumFragment2 = this.this$0;
            imageView7.setOnClickListener(new View.OnClickListener() { // from class: com.shangxian.pinkink.ui.main.AlbumFragment$AlbumViewHolder$$ExternalSyntheticLambda0
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    AlbumFragment.AlbumViewHolder.m217bind$lambda3(albumBean, this, albumFragment2, view);
                }
            });
            ImageView imageView8 = this.binding.ivRename;
            final AlbumFragment albumFragment3 = this.this$0;
            imageView8.setOnClickListener(new View.OnClickListener() { // from class: com.shangxian.pinkink.ui.main.AlbumFragment$AlbumViewHolder$$ExternalSyntheticLambda2
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    AlbumFragment.AlbumViewHolder.m218bind$lambda5(albumFragment3, albumBean, view);
                }
            });
            ConstraintLayout constraintLayout2 = this.binding.clItem;
            final AlbumFragment albumFragment4 = this.this$0;
            constraintLayout2.setOnClickListener(new View.OnClickListener() { // from class: com.shangxian.pinkink.ui.main.AlbumFragment$AlbumViewHolder$$ExternalSyntheticLambda3
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    AlbumFragment.AlbumViewHolder.m219bind$lambda7(isEditMode, albumFragment4, albumBean, view);
                }
            });
            this.binding.tvAlbumName.setText(albumBean.getName());
            this.binding.tvThemeCount.setText(String.valueOf(albumBean.getUserCreateThemeList().size()));
            Glide.with(this.this$0).load(albumBean.getCover()).placeholder(R.drawable.ic_album_place_holder2).fitCenter().into(this.binding.ivCover);
        }

        /* JADX INFO: Access modifiers changed from: private */
        /* JADX INFO: renamed from: bind$lambda-1, reason: not valid java name */
        public static final void m216bind$lambda1(AlbumFragment this$0, AlbumBean albumBean, View view) {
            Intrinsics.checkNotNullParameter(this$0, "this$0");
            Intrinsics.checkNotNullParameter(albumBean, "$albumBean");
            FragmentActivity activity = this$0.getActivity();
            if (activity == null) {
                return;
            }
            if (albumBean.getItemType() == AlbumBean.INSTANCE.getITEM_TYPE_AI()) {
                ThemesListActivity.INSTANCE.gotoThis(activity, albumBean.getName(), albumBean.getId());
            } else if (albumBean.getItemType() == AlbumBean.INSTANCE.getITEM_TYPE_USER_CREATE()) {
                UserThemesActivity.Companion.gotoThis$default(UserThemesActivity.INSTANCE, activity, false, 2, null);
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        /* JADX INFO: renamed from: bind$lambda-3, reason: not valid java name */
        public static final void m217bind$lambda3(AlbumBean albumBean, AlbumViewHolder this$0, AlbumFragment this$1, View view) {
            Intrinsics.checkNotNullParameter(albumBean, "$albumBean");
            Intrinsics.checkNotNullParameter(this$0, "this$0");
            Intrinsics.checkNotNullParameter(this$1, "this$1");
            albumBean.setSelected(!albumBean.isSelected());
            this$0.binding.ivSelect.setSelected(albumBean.isSelected());
            List list = this$1.albumList;
            int i = 0;
            if (!(list instanceof Collection) || !list.isEmpty()) {
                Iterator it = list.iterator();
                while (it.hasNext()) {
                    if (((AlbumBean) it.next()).isSelected() && (i = i + 1) < 0) {
                        CollectionsKt.throwCountOverflow();
                    }
                }
            }
            this$1.updateSelectedAlbumCount(i);
        }

        /* JADX INFO: Access modifiers changed from: private */
        /* JADX INFO: renamed from: bind$lambda-5, reason: not valid java name */
        public static final void m218bind$lambda5(AlbumFragment this$0, AlbumBean albumBean, View view) {
            Intrinsics.checkNotNullParameter(this$0, "this$0");
            Intrinsics.checkNotNullParameter(albumBean, "$albumBean");
            if (this$0.getActivity() == null) {
                return;
            }
            FragmentActivity activity = this$0.getActivity();
            Intrinsics.checkNotNull(activity);
            Intrinsics.checkNotNullExpressionValue(activity, "activity!!");
            new RenameAlbumDialog(activity, albumBean).setActionCallback(this$0.mRenameAlbumDialogActionCallback).show();
        }

        /* JADX INFO: Access modifiers changed from: private */
        /* JADX INFO: renamed from: bind$lambda-7, reason: not valid java name */
        public static final void m219bind$lambda7(boolean z, AlbumFragment this$0, AlbumBean albumBean, View view) {
            FragmentActivity activity;
            Intrinsics.checkNotNullParameter(this$0, "this$0");
            Intrinsics.checkNotNullParameter(albumBean, "$albumBean");
            if (z || (activity = this$0.getActivity()) == null) {
                return;
            }
            AlbumDetailActivity.INSTANCE.gotoThis(activity, albumBean);
        }
    }
}
