package com.xunlei.downloadprovider.download.taskDetail.widget;

import android.view.View;
import android.view.View.OnClickListener;
import com.xunlei.downloadprovider.download.report.a;
import com.xunlei.downloadprovider.download.util.n;

// compiled from: TaskDetailSpeedInfoView.java
final class i implements OnClickListener {
    final /* synthetic */ TaskDetailSpeedInfoView a;

    i(TaskDetailSpeedInfoView taskDetailSpeedInfoView) {
        this.a = taskDetailSpeedInfoView;
    }

    public final void onClick(View view) {
        if (this.a.b != null) {
            this.a.c.c(this.a.b);
            a.a("dl_retry", n.e(this.a.b), n.c(this.a.b) ? 1 : 0);
        }
    }
}
