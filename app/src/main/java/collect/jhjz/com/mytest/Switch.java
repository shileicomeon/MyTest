package collect.jhjz.com.mytest;

/**
 * Created by AbyssKitty on 2017/1/11.
 * 控制App是调试还是正式发布全局变量。
 * ----- 此功能在App中实现 -----
 */
public class Switch {
    /**
     * 整个应用程序是否是debug模式。
     * 当正是打包发布时，将此处设置为false；
     *
     * 具体使用在：
     * 1.Logcat日志打印在控制台的统一打开或关闭
     * 2.网络拦截器的日志打印在控制台的统一打开或关闭
     *
     * */
    public static boolean isDebug = true;
}
