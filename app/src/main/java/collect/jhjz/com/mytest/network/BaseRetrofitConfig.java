package collect.jhjz.com.mytest.network;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.util.Log;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import collect.jhjz.com.mytest.BaseApplication;
import collect.jhjz.com.mytest.tool.DateUtil;
import collect.jhjz.com.mytest.tool.EncryptUtil;

/**
 *
 * 配置文件，包括baseUrl、追加公共参数等信息
 */
public class BaseRetrofitConfig {

    /**
     * 总url路径 - 注意：Retrofit2.0 的baseUrl必须以“ / ”结尾，不然会抛出IllegalArgumentException异常
     * Retrofit2.0 支持全URL路径输入 所以在这里的路径就是个摆设，但却不能缺少
     * */
    public static final String baseUrl = "http://127.0.0.1:8080/api/v1/";

    /**
     * 连接超时时间，默认15s
     * */
    public static final int OKHTTP_OVERTIME = 10;


    /**
     * 需要追加的公共参数
     * Parame ->  未访问服务器之前，把此map拼接到上传的参数中
     * hander ->  开始访问服务器之前，把此map添加到hander响应头
     * */
    private static Map<String,String> Parame = new HashMap<>();
    private static Map<String,String> header = new HashMap<>();

    /**
     * 对 NetModle 开放获取公共参数的方法,此参数直接追加在parame中
     * 过时方法，已摒弃，需要在程序中动态添加
     * */
    @Deprecated
    public static Map getCommonParameter(){
        Parame.clear();
        Parame = new HashMap<>();
        //Parame.put("","");
        return Parame;
    }
    /**
     * 对 NetModle 开放获取公共参数方法，此方法追加在headers里
     * 过时方法，已摒弃，根据项目需求，-必须或者需要-在程序中动态添加
     * */
    @Deprecated
    public static Map getHanderCommonParameter() {
        header.clear();
//        header = getHeasers();
        header = new HashMap<>();
        //hander.put("","");
        return header;
    }

    private static String SoftVersion = "v1";//接口版本

    private static String AppKey = "4bafd51251cd4c1f99973bdd00ba23fe";

    private static String AppToken = "2e02b271f96647d1b2b88db993a05044";

    private static String SecretKey = "HONGXUNKUAICHEQINGDAOJIAOZHOU";

    private static String Accept = "application/json";//接收的数据类型，如json、xml。

    private static String code = "05577f7aef1149c18e67e9d616444c56"; //动态的用户id

    private static String str = "";

    private static String str2 = "";

    private static String str3 = "";

    private static String str4 = "";

    public static Map<String,String> getHeasers(){
        Map<String,String> header = new HashMap<>();
        String AppVersion = BaseRetrofitConfig.getAppVersionName(BaseApplication.context);
        String AppName = BaseRetrofitConfig.getApplicationName(BaseApplication.context);
        try {
            str = EncryptUtil.base64Encoder(AppKey + ":" + AppToken + ":" + AppName + ":" + AppVersion + ":" + BaseRetrofitConfig.getTimestap());
            str2 = EncryptUtil.base64Encoder(SoftVersion);
            str3 = EncryptUtil.base64Encoder(code);
            str4 = EncryptUtil.createSignature(AppKey, AppToken, SecretKey);
        } catch (Exception e) {
            System.err.println("app验证错误！");
        }
        header.put("Accept", Accept);
        header.put("AppInfo", str);
        header.put("SoftVersion", str2);
        header.put("accountSid", str3);
        header.put("sig",str4);
        return header;
    }

    /**
     * 时间戳（只是服务器做记录）
     * @return
     */
    public static String getTimestap() {
        return DateUtil.format(new Date(), DateUtil.PIN_PATTERN_DATE_MINUTE);
    }

    /**
     * 返回当前版本名
     */
    public static String getAppVersionName(Context context) {
        String versionName = "";
        try {
            PackageManager pm = context.getPackageManager();
            PackageInfo pi = pm.getPackageInfo(context.getPackageName(), 0);
            versionName = pi.versionName;
            if (versionName == null || versionName.length() <= 0) {
                return "";
            }
        } catch (Exception e) {
            Log.e("VersionInfo", "Exception", e);
            e.printStackTrace();
        }
        return versionName;
    }

    /**
     * 获取当前应用名
     * @param context
     * @return
     */
    public static String getApplicationName(Context context) {
        PackageManager packageManager = null;
        ApplicationInfo applicationInfo = null;
        try {
            packageManager = context.getPackageManager();
            applicationInfo = packageManager.getApplicationInfo(context.getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            applicationInfo = null;
        }
        String applicationName =
                (String) packageManager.getApplicationLabel(applicationInfo);
        return applicationName;
    }
}
