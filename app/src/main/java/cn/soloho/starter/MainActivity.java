package cn.soloho.starter;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        StarerIntentService.startActionStart(this,
                Const.PACKAGE_WECHAT,
                Const.ACTIVITY_WECHAT_LAUNCHERUI,
                Const.ACTIVITY_WECHAT_PAY);
        finish();
    }

}
