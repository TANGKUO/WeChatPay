package cn.soloho.wechatpay;

import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //getSharedPreferences("WeChatPay", Context.MODE_PRIVATE).edit().putBoolean("StartPay", true).commit();

        Intent starter = new Intent(this, MainActivity.class);
        starter.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        ComponentName componentName = new ComponentName(
                "com.tencent.mm",
                "com.tencent.mm.ui.LauncherUI");
        starter.setComponent(componentName);
        starter.putExtra("StartPay", true);
        startActivity(starter);

        finish();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }
}
