package collect.jhjz.com.mytest.network;

import rx.Subscriber;

/**
 * Created by AbyssKitty on 2016/10/12.
 * Version 1.0
 * 可以在本类中对 Subscriber 获取到的数据进行处理。
 * 例如集中处理错误信息等
 */
public class NetSubscriber<T> extends Subscriber<T> {

    @Override
    public void onCompleted() {

    }

    @Override
    public void onError(Throwable e) {
        if("".equals(e.getLocalizedMessage().toString())){
            System.err.println("========================   C Net Error  ========================");
            System.err.println("custom DEBUG ：Net Error = " + "数据解析错误，请检查解析type是否正确 （NetModle.******）");
            System.err.println("========================   E Net Error  ========================");
        }else{
//            Toast.makeText(BaseApplication.context,"错误的操作 或 服务器响应异常",Toast.LENGTH_SHORT).show();
            System.err.println("========================   C Net Error  ========================");
            System.err.println("custom DEBUG ：Net Error = " + e.getLocalizedMessage());
            System.err.println("========================   E Net Error  ========================");
        }
    }

    @Override
    public void onNext(T t) {

    }
}
