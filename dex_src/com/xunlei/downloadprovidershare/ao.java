package com.xunlei.downloadprovidershare;

import android.text.TextUtils;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.xunlei.downloadprovider.commonview.XLToast;
import com.xunlei.downloadprovider.commonview.XLToast.XLToastType;

// compiled from: ShareHelper.java
final class ao implements Runnable {
    final /* synthetic */ String a;
    final /* synthetic */ an b;

    ao(an anVar, String str) {
        this.b = anVar;
        this.a = str;
    }

    public final void run() {
        this.b.a.d.c();
        if (TextUtils.isEmpty(this.a)) {
            XLToast.a(this.b.a.a, XLToastType.XLTOAST_TYPE_ALARM, "\u7f51\u7edc\u4e0d\u7ed9\u529b");
            d.a(this.b.a.d);
            return;
        }
        this.b.a.c.h = this.a;
        this.b.a.c.a += "?storid=" + this.a;
        d.d(this.b.a.d).b(SHARE_MEDIA.QQ, this.b.a.a, this.b.a.c, d.b(this.b.a.d));
        if (d.c(this.b.a.d) != null) {
            d.c(this.b.a.d).onShareTargetClicked(SHARE_MEDIA.QQ, this.b.a.c);
        }
    }
}
