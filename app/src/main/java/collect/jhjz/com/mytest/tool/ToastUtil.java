package collect.jhjz.com.mytest.tool;

import android.widget.Toast;

import collect.jhjz.com.mytest.BaseApplication;

/**
 * Created by Administrator on 2017/5/3.
 */
public class ToastUtil {

    private static Toast toast;

    public static void showToast(String str){
        if(toast == null){
            toast = Toast.makeText(BaseApplication.context,""+str,Toast.LENGTH_SHORT);
        }else{
            toast.setText(""+str);
        }
        toast.show();
    }

    public static void showToastLong(String str){
        if(toast == null){
            toast = Toast.makeText(BaseApplication.context,""+str,Toast.LENGTH_LONG);
        }else{
            toast.setText(""+str);
        }
        toast.show();
    }
}
