package collect.jhjz.com.mytest.network;

import com.google.gson.Gson;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import collect.jhjz.com.mytest.network.bean.RespBean;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by AbyssKitty on 2016/10/11.
 * Version 1.0
 * Net 自定义封装
 * 实现get post等方法，可在外部直接使用。
 */
public class NetModle<T> {

    /* - resp里的list - */
    public final static int RESP_LIST = 0x560001;

    /* - resp里的obj - */
    public final static int RESP_OBJ = 0x560002;

    /* - resp里的info - */
    public final static int RESP_INFO = 0x560003;

    /* - list - */
    public final static int LIST = 0x560004;

    /* - obj - */
    public final static int OBJ = 0x560005;

    /* - info - */
    public final static int INFO = 0x560006;

    /**
     * 单例对象初始化，必须使用private修饰
     * */
    private NetModle(){}

    /**
     * 全局handers
     * */
    private Map<String,String> map = new HashMap<>();
    private Map<String,String> dmap = new HashMap<>();

    /**
     * Builder模式的初始化，保留
     * */
    private NetModle(Builder builder){
        this.map = builder.map;
    }

    /**
     * 获取单例
     * */
    public static NetModle getInstance(){
        return NetMoudleHolder.netModle;
    }

    /**
     * 静态内部类，实现线程安全、延迟加载、高效的单例模式。
     * */
    private static class NetMoudleHolder{
        private static NetModle netModle = new NetModle();
    }

    /**
     * get方式
     * @param url      地址
     * @param params   数据集合
     * @param onNetSubscriberListener    回调
     * */
    public void get(String url, Map<String,String> params , OnNetSubscriberListener onNetSubscriberListener){
        get(url,params,0,null,onNetSubscriberListener);
    }
    /**
     * @param url      地址
     * @param params   数据集合
     * @param type     返回的数据类型
     * @param onNetSubscriberListener    回调
     * */
    public void get(String url, Map<String,String> params, int type , OnNetSubscriberListener onNetSubscriberListener){
        get(url,params,type,null,onNetSubscriberListener);
    }
    /**
     * get方式
     * @param url      地址
     * @param params   数据集合
     * @param type     返回的数据类型
     * @param map      自定义的header集合
     * @param onNetSubscriberListener    回调
     * */
    public void get(String url, Map<String,String> params, int type, Map<String,String> map, OnNetSubscriberListener onNetSubscriberListener){
        //params = addParams(params);
        getBaseRetrofitFactory(map)
                .getRetrofitService()
                .get(url,params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(netNSListener(type,onNetSubscriberListener));
    }
    /**
     * post方式
     * @param url      地址
     * @param params   数据集合
     * @param onNetSubscriberListener    回调
     * */
    public void post(String url, Map<String,String> params , OnNetSubscriberListener onNetSubscriberListener){
        post(url,params,0,null,onNetSubscriberListener);
    }
    /**
     * post方式
     * @param url      地址
     * @param params   数据集合
     * @param type     返回的数据类型
     * @param onNetSubscriberListener    回调
     * */
    public void post(String url, Map<String,String> params, int type , OnNetSubscriberListener onNetSubscriberListener){
        post(url,params,type,null,onNetSubscriberListener);
    }
    /**
     * post方式
     * @param url   地址
     * @param params    数据集合
     * @param type     返回的数据类型
     * @param map   自定义的header集合,不传的话使用默认的header
     * @param onNetSubscriberListener    回调
     * */
    public void post(String url, Map<String,String> params, int type, Map<String,String> map, OnNetSubscriberListener onNetSubscriberListener){
        //params = addParams(params);
        getBaseRetrofitFactory(map)
                .getRetrofitService()
                .post(url,params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(netNSListener(type,onNetSubscriberListener));
    }

    /**
     * post传实体
     * */
    public void postBody(String url, String json, int type, Map<String,String> map, OnNetSubscriberListener onNetSubscriberListener){
//        RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json"),json);
//        if(body != null){
//            LogUtil.d("-----------------------------1"+body.toString());
//
//        }else{
//            LogUtil.d("-----------------------------2");
//        }
        getBaseRetrofitFactory(map)
                .getRetrofitService()
                .postBody(url,json)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(netNSListener(type,onNetSubscriberListener));

    }
    /**
     * post传表单FieldMap
     * */
    public void postField(String url, Map<String,String> parme, int type, Map<String,String> map, OnNetSubscriberListener onNetSubscriberListener){
        getBaseRetrofitFactory(map)
                .getRetrofitService()
                .postField(url,parme)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(netNSListener(type,onNetSubscriberListener));
    }

    /**
     * 图片上传post方式
     * @param url   地址
     * @param path   图片地址
     * @param onNetSubscriberListener    回调
     * */
    public void upload(String url, String path, OnNetSubscriberListener onNetSubscriberListener){
        upload(url,path,0,null,onNetSubscriberListener);
    }

    /**
     * 图片上传post方式
     * @param url   地址
     * @param path   图片地址
     * @param type     返回的数据类型
     * @param map   自定义的header集合,不传的话使用默认的header
     * @param onNetSubscriberListener    回调
     * */
    public void upload(String url, String path, int type, Map<String,String> map, OnNetSubscriberListener onNetSubscriberListener){
        //Subscription d =
        getBaseRetrofitFactory(map)
                .getRetrofitService()
                .upLoadFile(url,getMultipartBodyPart(path))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(netNSListener(type,onNetSubscriberListener));
    }
    /**
     * 图片上传post方式
     * @param url   地址
     * @param file   图片文件
     * @param type     返回的数据类型
     * @param map   自定义的header集合,不传的话使用默认的header
     * @param onNetSubscriberListener    回调
     * */
    public void upload(String url, File file, int type, Map<String,String> map, OnNetSubscriberListener onNetSubscriberListener){
        //Subscription d =
        getBaseRetrofitFactory(map)
                .getRetrofitService()
                .upLoadFile(url,getMultipartBodyPart(file))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(netNSListener(type,onNetSubscriberListener));
    }
    /**
     * Subscriber 预处理
     * */
    private Subscriber netNSListener(final int type, final OnNetSubscriberListener onNetSubscriberListener){
        NetSubscriber<String> stringSubscriber = new NetSubscriber<String>() {
            @Override
            public void onCompleted() {
                super.onCompleted(); //执行预处理
//                if(onNetSubscriberListener != null){
//                    onNetSubscriberListener.onCompleted();
//                }
            }

            @Override
            public void onError(Throwable e){
                super.onError(e); //执行预处理
                if(onNetSubscriberListener != null){
                    onNetSubscriberListener.onError(e);
                }
            }

            @Override
            public void onNext(String s){
                super.onNext(s); //执行预处理
                Gson gson = new Gson();

                //去掉根节点 数据预处理，根据服务器数据修改!!!
                RespBean rb = gson.fromJson(s, RespBean.class);
//                    Object o = jsonGetObj(type,rb);
//                    if(onNetSubscriberListener != null){
//                        onNetSubscriberListener.onNext(rb,o);
                if(onNetSubscriberListener != null){
                    onNetSubscriberListener.onNext(rb);
                }
            }
        };
        return stringSubscriber;
    }
    /**
     * 初始数据预处理
     * */
    @Deprecated
    public Object jsonGetObj(int type, RespBean rb){
        Object o = null;
        Gson gson = new Gson();
        switch (type){
            case RESP_LIST:
                o = rb.resp.list;
                break;
            case RESP_INFO:
                o = gson.toJson(rb.resp.info);
                break;
            case RESP_OBJ:
                o = gson.toJson(rb.resp.obj);
                break;
            case LIST:
                o = rb.list;
                break;
            case INFO:
                o = gson.toJson(rb.info);
                break;
            case OBJ:
                o = gson.toJson(rb.obj);
                break;
            default:
        }
        return o;
    }
    /**
     * 文件上传
     * */
    private MultipartBody.Part getMultipartBodyPart(String mPath){
        File file = new File(mPath);
        //multipart/form-data 格式
        RequestBody requestFile =
                RequestBody.create(MediaType.parse("multipart/form-data"), file);
        //file - 为上传参数的 键名
        MultipartBody.Part body =
                MultipartBody.Part.createFormData("file", file.getName(), requestFile);
        return body;
    }
    /**
     * 文件上传
     * */
    private MultipartBody.Part getMultipartBodyPart(File file){
        //multipart/form-data 格式
        RequestBody requestFile =
                RequestBody.create(MediaType.parse("multipart/form-data"), file);
        //file - 为上传参数的 键名
        MultipartBody.Part body =
                MultipartBody.Part.createFormData("file", file.getName(), requestFile);
        return body;
    }

    /**
     * 获取RetrofitFactory对象
     * */
    private BaseRetrofitFactory getBaseRetrofitFactory(Map<String,String> map){

        BaseRetrofitFactory baseRetrofitFactory = null;

        if(map != null){

            baseRetrofitFactory = new BaseRetrofitFactory.Builder()
                    .setHeaders(map)
                    .build();
        }else{
            /* 在这里添加默认的 handers */
            dmap = BaseRetrofitConfig.getHanderCommonParameter();
            //LogUtil.d("getHanderCommonParameter="+dmap.size()+" moren");
            baseRetrofitFactory = new BaseRetrofitFactory.Builder()
                    .setHeaders(dmap)
                    .build();
        }
        return baseRetrofitFactory;
    }

    /**
     * 追加params数据
     * 可不用此方法，已摒弃
     * */
    @Deprecated
    private Map addParams(Map parame){
        Map<String,String> p = null;
        p = BaseRetrofitConfig.getCommonParameter();
        if(p != null){
            parame.putAll(p);
        }
        return parame;
    }

    /**
     * Builder，留用 暂时未用 ，当前是以静态方法方式实现的
     * */
    public class Builder{

        Map<String,String> map;

        Map<String,String> params;

        public Builder setHander(Map<String,String> map){
            this.map = map;
            return this;
        }

        public Builder setMap(Map<String,String> params){
            this.params = params;
            return this;
        }

        public NetModle build(){
            return new NetModle(this);
        }
    }
}
