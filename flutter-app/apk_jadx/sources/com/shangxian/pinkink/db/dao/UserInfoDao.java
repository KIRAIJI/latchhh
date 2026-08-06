package com.shangxian.pinkink.db.dao;

import com.shangxian.pinkink.bean.UserInfoBean;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.coroutines.Continuation;

/* JADX INFO: compiled from: UserInfoDao.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata(d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u0005\bg\u0018\u00002\u00020\u0001J\u0019\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H§@ø\u0001\u0000¢\u0006\u0002\u0010\u0006J\u0019\u0010\u0007\u001a\u00020\u00032\u0006\u0010\b\u001a\u00020\tH§@ø\u0001\u0000¢\u0006\u0002\u0010\nJ\u001b\u0010\u000b\u001a\u0004\u0018\u00010\u00052\u0006\u0010\f\u001a\u00020\tH§@ø\u0001\u0000¢\u0006\u0002\u0010\nJ\u0019\u0010\r\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H§@ø\u0001\u0000¢\u0006\u0002\u0010\u0006\u0082\u0002\u0004\n\u0002\b\u0019¨\u0006\u000e"}, d2 = {"Lcom/shangxian/pinkink/db/dao/UserInfoDao;", "", "addUserInfo", "", "userInfo", "Lcom/shangxian/pinkink/bean/UserInfoBean;", "(Lcom/shangxian/pinkink/bean/UserInfoBean;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "deleteUserByUserId", "userId", "", "(Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getUserByToken", "token", "updateUserInfo", "app_release"}, k = 1, mv = {1, 6, 0}, xi = 48)
public interface UserInfoDao {
    Object addUserInfo(UserInfoBean userInfoBean, Continuation<? super Unit> continuation);

    Object deleteUserByUserId(String str, Continuation<? super Unit> continuation);

    Object getUserByToken(String str, Continuation<? super UserInfoBean> continuation);

    Object updateUserInfo(UserInfoBean userInfoBean, Continuation<? super Unit> continuation);
}
