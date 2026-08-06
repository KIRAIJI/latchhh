package com.shangxian.pinkink.ui.main;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import com.scwang.smart.refresh.layout.util.SmartUtil;
import com.shangxian.pinkink.bean.ThemeCategoryBean;
import com.shangxian.pinkink.constants.AppConfig;
import com.shangxian.pinkink.ui.main.IndexFragment2;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators.LinePagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.ColorTransitionPagerTitleView;

/* JADX INFO: compiled from: IndexFragment2.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata(d1 = {"\u0000%\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002*\u0001\u0000\b\n\u0018\u00002\u00020\u0001J\b\u0010\u0002\u001a\u00020\u0003H\u0016J\u0010\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u0007H\u0016J\u0018\u0010\b\u001a\u00020\t2\u0006\u0010\u0006\u001a\u00020\u00072\u0006\u0010\n\u001a\u00020\u0003H\u0016¨\u0006\u000b"}, d2 = {"com/shangxian/pinkink/ui/main/IndexFragment2$IndexHeader$setThemeCategory$1", "Lnet/lucode/hackware/magicindicator/buildins/commonnavigator/abs/CommonNavigatorAdapter;", "getCount", "", "getIndicator", "Lnet/lucode/hackware/magicindicator/buildins/commonnavigator/abs/IPagerIndicator;", "context", "Landroid/content/Context;", "getTitleView", "Lnet/lucode/hackware/magicindicator/buildins/commonnavigator/abs/IPagerTitleView;", "index", "app_release"}, k = 1, mv = {1, 6, 0}, xi = 48)
public final class IndexFragment2$IndexHeader$setThemeCategory$1 extends CommonNavigatorAdapter {
    final /* synthetic */ List<ThemeCategoryBean> $themeCategoryList;
    final /* synthetic */ IndexFragment2.IndexHeader this$0;
    final /* synthetic */ IndexFragment2 this$1;

    IndexFragment2$IndexHeader$setThemeCategory$1(List<ThemeCategoryBean> list, IndexFragment2.IndexHeader indexHeader, IndexFragment2 indexFragment2) {
        this.$themeCategoryList = list;
        this.this$0 = indexHeader;
        this.this$1 = indexFragment2;
    }

    @Override // net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter
    public int getCount() {
        return this.$themeCategoryList.size();
    }

    @Override // net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter
    public IPagerTitleView getTitleView(Context context, final int index) {
        Intrinsics.checkNotNullParameter(context, "context");
        ColorTransitionPagerTitleView colorTransitionPagerTitleView = new ColorTransitionPagerTitleView(context);
        colorTransitionPagerTitleView.setNormalColor(Color.parseColor("#FF999999"));
        colorTransitionPagerTitleView.setSelectedColor(Color.parseColor("#FF333333"));
        colorTransitionPagerTitleView.setTextSize(2, 16.0f);
        colorTransitionPagerTitleView.setText(AppConfig.INSTANCE.isEnglishLanguage() ? this.$themeCategoryList.get(index).getNameEn() : this.$themeCategoryList.get(index).getName());
        final IndexFragment2.IndexHeader indexHeader = this.this$0;
        final IndexFragment2 indexFragment2 = this.this$1;
        final List<ThemeCategoryBean> list = this.$themeCategoryList;
        colorTransitionPagerTitleView.setOnClickListener(new View.OnClickListener() { // from class: com.shangxian.pinkink.ui.main.IndexFragment2$IndexHeader$setThemeCategory$1$$ExternalSyntheticLambda0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                IndexFragment2$IndexHeader$setThemeCategory$1.m232getTitleView$lambda0(indexHeader, index, indexFragment2, list, view);
            }
        });
        return colorTransitionPagerTitleView;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: renamed from: getTitleView$lambda-0, reason: not valid java name */
    public static final void m232getTitleView$lambda0(IndexFragment2.IndexHeader this$0, int i, IndexFragment2 this$1, List themeCategoryList, View view) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Intrinsics.checkNotNullParameter(this$1, "this$1");
        Intrinsics.checkNotNullParameter(themeCategoryList, "$themeCategoryList");
        this$0.mFragmentContainerHelper.handlePageSelected(i);
        this$1.selectedThemeCategoryId = ((ThemeCategoryBean) themeCategoryList.get(i)).getId();
        this$1.pageNum = 1;
        this$1.getThemesViewModel().getThemeList(this$1.selectedThemeCategoryId, this$1.pageNum, this$1.pageSize, false);
    }

    @Override // net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter
    public IPagerIndicator getIndicator(Context context) {
        Intrinsics.checkNotNullParameter(context, "context");
        LinePagerIndicator linePagerIndicator = new LinePagerIndicator(context);
        linePagerIndicator.setMode(2);
        linePagerIndicator.setLayoutParams(new ViewGroup.LayoutParams(SmartUtil.dp2px(12.0f), SmartUtil.dp2px(3.0f)));
        linePagerIndicator.setRoundRadius(SmartUtil.dp2px(1.5f));
        linePagerIndicator.setColors(Integer.valueOf(Color.parseColor("#FFFF7A9A")));
        return linePagerIndicator;
    }
}
