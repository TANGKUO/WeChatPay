package cn.soloho.wechatpay;

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
        if (!loadPackageParam.packageName.equals("com.tencent.mm")) {
            return;
        }
        XposedBridge.log("检测到你加载微信: " + loadPackageParam.packageName);

        XposedHelpers.findAndHookMethod("com.tencent.mm.ui.LauncherUI", loadPackageParam.classLoader, "onCreate", Bundle.class, new XC_MethodHook() {
            @Override
            protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                super.afterHookedMethod(param);

                XposedBridge.log("当前界面是: " + param.thisObject.toString());

                Activity activity = (Activity) param.thisObject;

                try {
                    boolean startPay = activity.getIntent().getBooleanExtra("StartPay", false);
                    if (startPay) {
                        XposedBridge.log("启动支付: " + param.thisObject.toString());

                        Intent starter = new Intent(activity, MainActivity.class);
                        starter.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        ComponentName componentName = new ComponentName(
                                "com.tencent.mm",
                                "com.tencent.mm.plugin.offline.ui.WalletOfflineEntranceUI");
                        starter.setComponent(componentName);
                        activity.startActivity(starter);
                    } else {
                        XposedBridge.log("不需要启动支付" + param.thisObject.toString());
                    }
                } catch (Exception e) {
                    XposedBridge.log("启动支付失败了");
                    XposedBridge.log(e);
                }
            }
        });
    }
}
