package cn.soloho.starter;

/**
 * Created by solo on 16/3/4.
 */
public interface Const {

    String PACKAGE_TAG = "cn.soloho.wechatpay";

    String KEY_IS_START = PACKAGE_TAG + ".IS_START";

    ///////////////////////////////////////////////////////////////////////////
    // System
    ///////////////////////////////////////////////////////////////////////////
    String ACTIVITY_APP_ACTIVITY = "android.app.Activity";
    String METHOD_ACTIVITY_ON_CREATE = "onCreate";
    String METHOD_ACTIVITY_ON_NEW_INTENT = "onNewIntent";

    ///////////////////////////////////////////////////////////////////////////
    // WeChat
    ///////////////////////////////////////////////////////////////////////////
    String PACKAGE_WECHAT = "com.tencent.mm";
    String ACTIVITY_WECHAT_PAY = PACKAGE_WECHAT + ".plugin.offline.ui.WalletOfflineEntranceUI";
    String ACTIVITY_WECHAT_LAUNCHERUI = PACKAGE_WECHAT + ".ui.LauncherUI";
}
