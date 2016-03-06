package cn.soloho.starter;

import android.app.IntentService;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;

import java.io.DataOutputStream;
import java.io.IOException;

public class StarerIntentService extends IntentService {
    private static final String ACTION_START = "cn.soloho.starter.action.START";

    private static final String EXTRA_PACKAGE = "cn.soloho.starter.extra.PACKAGE";
    private static final String EXTRA_LAUNCHER = "cn.soloho.starter.extra.LAUNCHER";
    private static final String EXTRA_ACTIVITY = "cn.soloho.starter.extra.ACTIVITY";

    public StarerIntentService() {
        super("StarerIntentService");
    }

    public static void startActionStart(Context context, String packageName, String launcherName, String activityName) {
        Intent intent = new Intent(context, StarerIntentService.class);
        intent.setAction(ACTION_START);
        intent.putExtra(EXTRA_PACKAGE, packageName);
        intent.putExtra(EXTRA_LAUNCHER, launcherName);
        intent.putExtra(EXTRA_ACTIVITY, activityName);
        context.startService(intent);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            if (ACTION_START.equals(action)) {
                final String packageName = intent.getStringExtra(EXTRA_PACKAGE);
                final String launcherName = intent.getStringExtra(EXTRA_LAUNCHER);
                final String activityName = intent.getStringExtra(EXTRA_ACTIVITY);
                handleActionStart(packageName, launcherName, activityName);
            }
        }
    }

    private void handleActionStart(String packageName, String launcherName, String activityName) {
        SharedPreferences pref = getSharedPreferences(Const.PACKAGE_TAG, Context.MODE_PRIVATE);
        boolean userCmd = pref.getBoolean(Const.KEY_USE_CMD, true); // 默认使用CMD
        boolean startWithExposed = !userCmd;
        if (userCmd) {
            try {
                int res = startWithCmd(packageName, activityName);
                if (res != 0) {
                    Log.w(Const.PACKAGE_TAG, "Root not grant");
                    startWithExposed = true;
                }
            } catch (IOException | InterruptedException e) {
                Log.w(Const.PACKAGE_TAG, "Device not root", e);
                startWithExposed = true;
            }
        }

        if (startWithExposed) {
            pref.edit().putBoolean(Const.KEY_USE_CMD, false).apply();
            startWithExposed(packageName, launcherName, activityName);
        }
    }

    private int startWithCmd(String packageName, String activityName) throws IOException, InterruptedException {
        Log.v(Const.PACKAGE_TAG, "Start with cmd");
        String cmd = "am start -n " + packageName + "/" + activityName;
        Process process = Runtime.getRuntime().exec("su");
        DataOutputStream os = new DataOutputStream(process.getOutputStream());
        os.write(cmd.getBytes());
        os.writeBytes("\n");
        os.flush();
        os.writeBytes("exit\n");
        os.flush();
        int res = process.waitFor();
        process.destroy();
        return res;
    }

    private void startWithExposed(String packageName, String launcherName, String activityName) {
        Log.v(Const.PACKAGE_TAG, "Start with exposed");
        Intent starter = new Intent();
        starter.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        ComponentName componentName = new ComponentName(packageName, launcherName);
        starter.setComponent(componentName);
        starter.putExtra(Const.KEY_START_PACKAGE, packageName);
        starter.putExtra(Const.KEY_START_ACTIVITY, activityName);
        startActivity(starter);
    }
}
