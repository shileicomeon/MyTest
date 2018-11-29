package collect.jhjz.com.mytest.network;


import collect.jhjz.com.mytest.network.bean.RespBean;

/**
 * Created by AbyssKitty on 2016/10/18.
 * 使用接口 网络数据回调接口
 */
public interface OnNetSubscriberListener {
//    void onNext(RespBean bean,Object data);
    void onNext(RespBean bean);
    void onError(Throwable e);
//    void onCompleted();
}
