package com.xunlei.downloadprovider.frame.user;

import com.xunlei.downloadprovider.commonview.XLToast;
import com.xunlei.downloadprovider.commonview.XLToast.XLToastType;
import com.xunlei.downloadprovider.commonview.dialog.x;

// compiled from: ReportActivity.java
final class aw implements Runnable {
    final /* synthetic */ x a;
    final /* synthetic */ au b;

    aw(au auVar, x xVar) {
        this.b = auVar;
        this.a = xVar;
    }

    public final void run() {
        this.a.dismiss();
        XLToast.b(this.b.a, XLToastType.XLTOAST_TYPE_SUC, "\u4e3e\u62a5\u6210\u529f");
        this.b.a.onBackPressed();
    }
}
