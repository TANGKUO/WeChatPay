package cn.soloho.starter;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;

import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

/**
 * Created by kjsolo on 16/3/4.
 */
public class WeChatPay implements IXposedHookLoadPackage {

    @Override
    public void handleLoadPackage(XC_LoadPackage.LoadPackageParam loadPackageParam) throws Throwable {
        if (!loadPackageParam.packageName.equals(Const.PACKAGE_WECHAT)) {
            return;
        }
        XposedBridge.log(String.format("%s, Loaded: %s", Const.PACKAGE_TAG, loadPackageParam.packageName));
        hookOnCreate(loadPackageParam);
        hookOnNewIntent(loadPackageParam);
    }

    /**
     * Hook onCreate method
     *
     * @param loadPackageParam
     */
    private void hookOnCreate(XC_LoadPackage.LoadPackageParam loadPackageParam) {
        // com.tencent.mm.ui.LauncherUI
        XposedHelpers.findAndHookMethod(Const.ACTIVITY_APP_ACTIVITY, loadPackageParam.classLoader, Const.METHOD_ACTIVITY_ON_CREATE, Bundle.class, new XC_MethodHook() {
            @Override
            protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                super.afterHookedMethod(param);
                startActivity(param);
            }
        });
    }

    /**
     * Hook onNewIntent method
     *
     * @param loadPackageParam
     */
    private void hookOnNewIntent(XC_LoadPackage.LoadPackageParam loadPackageParam) {
        // com.tencent.mm.ui.LauncherUI
        XposedHelpers.findAndHookMethod(Const.ACTIVITY_APP_ACTIVITY, loadPackageParam.classLoader, Const.METHOD_ACTIVITY_ON_NEW_INTENT, Intent.class, new XC_MethodHook() {
            @Override
            protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                super.afterHookedMethod(param);
                startActivity(param);
            }
        });
    }

    /**
     * Start activity
     *
     * @param param
     */
    private void startActivity(XC_MethodHook.MethodHookParam param) {
        try {
            XposedBridge.log(String.format("%s, Current activity: %s", Const.PACKAGE_TAG, param.thisObject.toString()));
            Activity activity = (Activity) param.thisObject;
            boolean extra = activity.getIntent().getBooleanExtra(Const.KEY_IS_START, false);
            if (extra) {
                XposedBridge.log(String.format("%s, Start from: %s", Const.PACKAGE_TAG, param.thisObject.toString()));

                Intent starter = new Intent(activity, MainActivity.class);
                starter.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                ComponentName componentName = new ComponentName(
                        Const.PACKAGE_WECHAT,
                        Const.ACTIVITY_WECHAT_PAY);
                starter.setComponent(componentName);
                activity.startActivity(starter);
            }
        } catch (Exception e) {
            XposedBridge.log("Start failed");
            XposedBridge.log(e);
        }
    }


}
