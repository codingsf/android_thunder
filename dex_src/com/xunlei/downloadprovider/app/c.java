package com.xunlei.downloadprovider.app;

import android.view.View;
import com.umeng.message.MsgConstant;
import com.xunlei.downloadprovider.launch.a.a.a;
import java.util.ArrayList;

// compiled from: BaseActivity.java
final class c implements a {
    final /* synthetic */ ArrayList a;
    final /* synthetic */ BaseActivity b;

    c(BaseActivity baseActivity, ArrayList arrayList) {
        this.b = baseActivity;
        this.a = arrayList;
    }

    public final void a(com.xunlei.downloadprovider.launch.a.a aVar, View view) {
        int i = 0;
        aVar.setCancelable(false);
        aVar.a();
        aVar.a((CharSequence) "\u53bb\u8bbe\u7f6e");
        View findViewById = view.findViewById(2131756282);
        if (findViewById != null) {
            findViewById.setVisibility(this.a.contains(MsgConstant.PERMISSION_WRITE_EXTERNAL_STORAGE) ? 0 : 8);
        }
        View findViewById2 = view.findViewById(2131756283);
        if (findViewById2 != null) {
            if (!this.a.contains(MsgConstant.PERMISSION_READ_PHONE_STATE)) {
                i = 8;
            }
            findViewById2.setVisibility(i);
        }
    }
}
