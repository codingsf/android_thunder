package com.tencent.open;

import android.annotation.SuppressLint;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.SystemClock;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebSettings.RenderPriority;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.Toast;
import com.qq.e.comm.constants.Constants.KEYS;
import com.tencent.connect.auth.QQToken;
import com.tencent.connect.common.Constants;
import com.tencent.mm.sdk.modelbase.BaseResp.ErrCode;
import com.tencent.open.a.f;
import com.tencent.open.b.g;
import com.tencent.open.c.a.a;
import com.tencent.open.c.b;
import com.tencent.open.utils.ServerSetting;
import com.tencent.open.utils.Util;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.UiError;
import com.umeng.socialize.common.SocializeConstants;
import com.xunlei.downloadprovider.web.base.core.BaseJsInterface;
import com.xunlei.downloadprovider.web.core.JsInterface;
import com.xunlei.tdlive.R;
import com.xunlei.xiazaibao.sdk.XZBDevice;
import java.lang.ref.WeakReference;
import org.json.JSONException;
import org.json.JSONObject;

// compiled from: ProGuard
public class PKDialog extends b implements a {
    private static final int MSG_CANCEL = 2;
    private static final int MSG_COMPLETE = 1;
    private static final int MSG_ON_LOAD = 4;
    private static final int MSG_SHOW_PROCESS = 5;
    private static final int MSG_SHOW_TIPS = 3;
    private static final String TAG = "openSDK_LOG.PKDialog";
    private static final int WEBVIEW_HEIGHT = 185;
    static Toast sToast;
    private IUiListener listener;
    private com.tencent.open.c.a mFlMain;
    private Handler mHandler;
    private OnTimeListener mListener;
    private String mUrl;
    private WeakReference<Context> mWeakContext;
    private b mWebView;
    private int mWebviewHeight;

    // compiled from: ProGuard
    private class FbWebViewClient extends WebViewClient {
        private FbWebViewClient() {
        }

        public boolean shouldOverrideUrlLoading(WebView webView, String str) {
            f.a(TAG, new StringBuilder("Redirect URL: ").append(str).toString());
            if (str.startsWith(ServerSetting.getInstance().getEnvUrl((Context) PKDialog.this.mWeakContext.get(), ServerSetting.DEFAULT_REDIRECT_URI))) {
                PKDialog.this.mListener.onComplete(Util.parseUrlToJson(str));
                PKDialog.this.dismiss();
                return true;
            } else if (str.startsWith(Constants.CANCEL_URI)) {
                PKDialog.this.mListener.onCancel();
                PKDialog.this.dismiss();
                return true;
            } else if (!str.startsWith(Constants.CLOSE_URI)) {
                return false;
            } else {
                PKDialog.this.dismiss();
                return true;
            }
        }

        public void onReceivedError(WebView webView, int i, String str, String str2) {
            super.onReceivedError(webView, i, str, str2);
            PKDialog.this.mListener.onError(new UiError(i, str, str2));
            if (!(PKDialog.this.mWeakContext == null || PKDialog.this.mWeakContext.get() == null)) {
                Toast.makeText((Context) PKDialog.this.mWeakContext.get(), "\u7f51\u7edc\u8fde\u63a5\u5f02\u5e38\u6216\u7cfb\u7edf\u9519\u8bef", 0).show();
            }
            PKDialog.this.dismiss();
        }

        public void onPageStarted(WebView webView, String str, Bitmap bitmap) {
            f.a(TAG, new StringBuilder("Webview loading URL: ").append(str).toString());
            super.onPageStarted(webView, str, bitmap);
        }

        public void onPageFinished(WebView webView, String str) {
            super.onPageFinished(webView, str);
            PKDialog.this.mWebView.setVisibility(0);
        }
    }

    // compiled from: ProGuard
    private class JsListener extends a.b {
        private JsListener() {
        }

        public void onComplete(String str) {
            PKDialog.this.mHandler.obtainMessage(MSG_COMPLETE, str).sendToTarget();
            f.e(TAG, new StringBuilder("JsListener onComplete").append(str).toString());
            PKDialog.this.dismiss();
        }

        public void onCancel(String str) {
            PKDialog.this.mHandler.obtainMessage(MSG_CANCEL, str).sendToTarget();
            PKDialog.this.dismiss();
        }

        public void showMsg(String str) {
            PKDialog.this.mHandler.obtainMessage(MSG_SHOW_TIPS, str).sendToTarget();
        }

        public void onLoad(String str) {
            PKDialog.this.mHandler.obtainMessage(MSG_ON_LOAD, str).sendToTarget();
        }
    }

    // compiled from: ProGuard
    private static class OnTimeListener implements IUiListener {
        private String mAction;
        String mAppid;
        String mUrl;
        private WeakReference<Context> mWeakCtx;
        private IUiListener mWeakL;

        public OnTimeListener(Context context, String str, String str2, String str3, IUiListener iUiListener) {
            this.mWeakCtx = new WeakReference(context);
            this.mAction = str;
            this.mUrl = str2;
            this.mAppid = str3;
            this.mWeakL = iUiListener;
        }

        private void onComplete(String str) {
            try {
                onComplete(Util.parseJson(str));
            } catch (JSONException e) {
                e.printStackTrace();
                onError(new UiError(-4, Constants.MSG_JSON_ERROR, str));
            }
        }

        public void onComplete(Object obj) {
            JSONObject jSONObject = (JSONObject) obj;
            g.a().a(this.mAction + "_H5", SystemClock.elapsedRealtime(), 0, 0, jSONObject.optInt(KEYS.RET, ErrCode.ERR_BAN), this.mUrl, false);
            if (this.mWeakL != null) {
                this.mWeakL.onComplete(jSONObject);
                this.mWeakL = null;
            }
        }

        public void onError(UiError uiError) {
            g.a().a(this.mAction + "_H5", SystemClock.elapsedRealtime(), 0, 0, uiError.errorCode, uiError.errorMessage != null ? uiError.errorMessage + this.mUrl : this.mUrl, false);
            if (this.mWeakL != null) {
                this.mWeakL.onError(uiError);
                this.mWeakL = null;
            }
        }

        public void onCancel() {
            if (this.mWeakL != null) {
                this.mWeakL.onCancel();
                this.mWeakL = null;
            }
        }
    }

    // compiled from: ProGuard
    private class THandler extends Handler {
        private OnTimeListener mL;

        public THandler(OnTimeListener onTimeListener, Looper looper) {
            super(looper);
            this.mL = onTimeListener;
        }

        public void handleMessage(Message message) {
            f.b(TAG, new StringBuilder("msg = ").append(message.what).toString());
            switch (message.what) {
                case MSG_COMPLETE:
                    this.mL.onComplete((String) message.obj);
                case MSG_CANCEL:
                    this.mL.onCancel();
                case MSG_SHOW_TIPS:
                    if (PKDialog.this.mWeakContext != null && PKDialog.this.mWeakContext.get() != null) {
                        PKDialog.showTips((Context) PKDialog.this.mWeakContext.get(), (String) message.obj);
                    }
                case MSG_SHOW_PROCESS:
                    if (PKDialog.this.mWeakContext != null && PKDialog.this.mWeakContext.get() != null) {
                        PKDialog.showProcessDialog((Context) PKDialog.this.mWeakContext.get(), (String) message.obj);
                    }
                default:
                    break;
            }
        }
    }

    public PKDialog(Context context, String str, String str2, IUiListener iUiListener, QQToken qQToken) {
        super(context, 16973840);
        this.mWeakContext = new WeakReference(context);
        this.mUrl = str2;
        this.mListener = new OnTimeListener(context, str, str2, qQToken.getAppId(), iUiListener);
        this.mHandler = new THandler(this.mListener, context.getMainLooper());
        this.listener = iUiListener;
        this.mWebviewHeight = Math.round(185.0f * context.getResources().getDisplayMetrics().density);
        f.e(TAG, new StringBuilder("density=").append(context.getResources().getDisplayMetrics().density).append("; webviewHeight=").append(this.mWebviewHeight).toString());
    }

    public void onBackPressed() {
        super.onBackPressed();
    }

    protected void onCreate(Bundle bundle) {
        requestWindowFeature(MSG_COMPLETE);
        super.onCreate(bundle);
        getWindow().setSoftInputMode(R.styleable.Toolbar_titleMarginBottom);
        getWindow().setSoftInputMode(MSG_COMPLETE);
        createViews();
        initViews();
    }

    private void createViews() {
        this.mFlMain = new com.tencent.open.c.a((Context) this.mWeakContext.get());
        this.mFlMain.setBackgroundColor(1711276032);
        this.mFlMain.setLayoutParams(new LayoutParams(-1, -1));
        this.mWebView = new b((Context) this.mWeakContext.get());
        this.mWebView.setBackgroundColor(0);
        this.mWebView.setBackgroundDrawable(null);
        if (VERSION.SDK_INT >= 11) {
            try {
                View.class.getMethod("setLayerType", new Class[]{Integer.TYPE, Paint.class}).invoke(this.mWebView, new Object[]{Integer.valueOf(MSG_COMPLETE), new Paint()});
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        ViewGroup.LayoutParams layoutParams = new LayoutParams(-1, this.mWebviewHeight);
        layoutParams.addRule(XZBDevice.Upload, -1);
        this.mWebView.setLayoutParams(layoutParams);
        this.mFlMain.addView(this.mWebView);
        this.mFlMain.a(this);
        setContentView(this.mFlMain);
    }

    @SuppressLint({"SetJavaScriptEnabled"})
    private void initViews() {
        this.mWebView.setVerticalScrollBarEnabled(false);
        this.mWebView.setHorizontalScrollBarEnabled(false);
        this.mWebView.setWebViewClient(new FbWebViewClient());
        this.mWebView.setWebChromeClient(this.mChromeClient);
        this.mWebView.clearFormData();
        WebSettings settings = this.mWebView.getSettings();
        if (settings != null) {
            settings.setSavePassword(false);
            settings.setSaveFormData(false);
            settings.setCacheMode(-1);
            settings.setNeedInitialFocus(false);
            settings.setBuiltInZoomControls(true);
            settings.setSupportZoom(true);
            settings.setRenderPriority(RenderPriority.HIGH);
            settings.setJavaScriptEnabled(true);
            if (!(this.mWeakContext == null || this.mWeakContext.get() == null)) {
                settings.setDatabaseEnabled(true);
                settings.setDatabasePath(((Context) this.mWeakContext.get()).getApplicationContext().getDir("databases", 0).getPath());
            }
            settings.setDomStorageEnabled(true);
            this.jsBridge.a(new JsListener(), "sdk_js_if");
            this.mWebView.clearView();
            this.mWebView.loadUrl(this.mUrl);
            this.mWebView.getSettings().setSavePassword(false);
        }
    }

    public void callJs(String str, String str2) {
        this.mWebView.loadUrl(new StringBuilder(BaseJsInterface.JS_PREFIX).append(str).append(SocializeConstants.OP_OPEN_PAREN).append(str2).append(SocializeConstants.OP_CLOSE_PAREN).toString());
    }

    static {
        sToast = null;
    }

    private static void showTips(Context context, String str) {
        try {
            JSONObject parseJson = Util.parseJson(str);
            int i = parseJson.getInt(JsInterface.FUNPLAY_AD_TRPE);
            CharSequence string = parseJson.getString(SocialConstants.PARAM_SEND_MSG);
            if (i == 0) {
                if (sToast == null) {
                    sToast = Toast.makeText(context, string, 0);
                } else {
                    sToast.setView(sToast.getView());
                    sToast.setText(string);
                    sToast.setDuration(0);
                }
                sToast.show();
            } else if (i == 1) {
                if (sToast == null) {
                    sToast = Toast.makeText(context, string, MSG_COMPLETE);
                } else {
                    sToast.setView(sToast.getView());
                    sToast.setText(string);
                    sToast.setDuration(MSG_COMPLETE);
                }
                sToast.show();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private static void showProcessDialog(Context context, String str) {
        if (context != null && str != null) {
            try {
                JSONObject parseJson = Util.parseJson(str);
                parseJson.getInt(JsInterface.KEY_ACTION);
                parseJson.getString(SocialConstants.PARAM_SEND_MSG);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    private void loadUrlWithBrowser(String str, String str2, String str3) throws Exception {
        Intent intent = new Intent();
        intent.setComponent(new ComponentName(str, str2));
        intent.setAction("android.intent.action.VIEW");
        intent.addFlags(1073741824);
        intent.addFlags(268435456);
        intent.setData(Uri.parse(str3));
        if (this.mWeakContext != null && this.mWeakContext.get() != null) {
            ((Context) this.mWeakContext.get()).startActivity(intent);
        }
    }

    public void onKeyboardShown(int i) {
        if (!(this.mWeakContext == null || this.mWeakContext.get() == null)) {
            if (i >= this.mWebviewHeight || 2 != ((Context) this.mWeakContext.get()).getResources().getConfiguration().orientation) {
                this.mWebView.getLayoutParams().height = this.mWebviewHeight;
            } else {
                this.mWebView.getLayoutParams().height = i;
            }
        }
        f.e(TAG, "onKeyboardShown keyboard show");
    }

    public void onKeyboardHidden() {
        this.mWebView.getLayoutParams().height = this.mWebviewHeight;
        f.e(TAG, "onKeyboardHidden keyboard hide");
    }

    protected void onConsoleMessage(String str) {
        f.b(TAG, "--onConsoleMessage--");
        try {
            this.jsBridge.a(this.mWebView, str);
        } catch (Exception e) {
        }
    }
}
