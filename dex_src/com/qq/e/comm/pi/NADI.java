package com.qq.e.comm.pi;

import com.qq.e.ads.cfg.DownAPPConfirmPolicy;

public interface NADI {
    void loadAd(int i);

    void setBrowserType(int i);

    void setDownAPPConfirmPolicy(DownAPPConfirmPolicy downAPPConfirmPolicy);
}
