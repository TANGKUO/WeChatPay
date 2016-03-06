package cn.soloho.starter;

/**
 * Created by solo on 16/3/4.
 */
public interface Const {

    String PACKAGE_TAG = "cn.soloho.wechatpay";

    String KEY_START_PACKAGE = PACKAGE_TAG + ".START_PACKAGE";
    String KEY_START_ACTIVITY = PACKAGE_TAG + ".START_ACTIVITY";
    String KEY_USE_CMD = ".IS_ROOT";

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
    String ACTIVITY_WECHAT_PAY = PACKAGE_WECHAT + ".plugin.offline.ui.WalletOfflineEntranceUI"; // or WalletOfflineCoinPurseUI
    String ACTIVITY_WECHAT_LAUNCHERUI = PACKAGE_WECHAT + ".ui.LauncherUI";
}
