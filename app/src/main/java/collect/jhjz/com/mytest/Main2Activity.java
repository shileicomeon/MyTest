package collect.jhjz.com.mytest;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import org.greenrobot.eventbus.EventBus;

public class Main2Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
    }

    public void dianji(View view) {
        EventBus.getDefault().post("s");
        finish();
      /*  Intent intent = new Intent();
        intent.setAction("hello");
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
        finish();*/
    }

    public void dianji1(View view) {
        EventBus.getDefault().post("a");
        finish();
        /*Intent intent = new Intent();
        intent.setAction("hellow");
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
        finish();*/
    }
}
