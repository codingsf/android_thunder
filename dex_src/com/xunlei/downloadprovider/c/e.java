package com.xunlei.downloadprovider.c;

import com.android.volley.r.a;
import com.android.volley.w;
import com.xunlei.downloadprovider.c.a.b;

// compiled from: CommentManager.java
public final class e implements a {
    final /* synthetic */ a a;
    final /* synthetic */ a b;

    public e(a aVar, a aVar2) {
        this.b = aVar;
        this.a = aVar2;
    }

    public final void onErrorResponse(w wVar) {
        wVar.printStackTrace();
        b bVar = new b();
        bVar.a = -1;
        bVar.b = wVar.getMessage();
        this.a.a(bVar);
    }
}
