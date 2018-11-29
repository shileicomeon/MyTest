package collect.jhjz.com.mytest.utils;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.content.LocalBroadcastManager;

import java.util.ArrayList;
import java.util.List;

public class LocalBroadcastManagerUtils {
    private static LocalBroadcastManagerUtils localBroadcastManagerUtils;
    private static Context context;
    private final LocalBroadcastManager localBroadcastManager;
    private final IntentFilter intentFilter;
    private final Intent intent;
    private String action;

    private LocalBroadcastManagerUtils(Context context) {
        this.context = context.getApplicationContext();
        localBroadcastManager = LocalBroadcastManager.getInstance(context);
        intent = new Intent();
        intentFilter = new IntentFilter();
    }

    public static LocalBroadcastManagerUtils getInstance(){
        if(localBroadcastManagerUtils ==null){
            if(null == localBroadcastManagerUtils){
                localBroadcastManagerUtils = getLocalBroadcastManagerUtils();
            }
        }
        return localBroadcastManagerUtils;
    }

    public void sendLocalBroadcast(String action){
        this.action = action;
        if(action!=null && !("").equals(action))
         intent.setAction(action);
         localBroadcastManager.sendBroadcast(intent);
    }
    public void registerLocalRecrive(BroadcastReceiver broadcastReceiver){

    }
    public void unLocalRegisterReceive(String filterAction){


    }

    private static LocalBroadcastManagerUtils getLocalBroadcastManagerUtils() {
        return new LocalBroadcastManagerUtils(context);
    }

}
