package collect.jhjz.com.mytest.network;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import collect.jhjz.com.mytest.BaseApplication;
import collect.jhjz.com.mytest.Switch;
import collect.jhjz.com.mytest.network.cookie.CookieManger;
import collect.jhjz.com.mytest.network.loggingInterceptors.okHttpLog.HttpLoggingInterceptorM;
import collect.jhjz.com.mytest.network.loggingInterceptors.okHttpLog.LogInterceptor;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 * Created by AbyssKitty on 2016/10/9.
 * Version 1.0
 * 初始化Retrofit 2.+
 * 1.配置OkHttpClient、添加拦截器
 * 2.添加HttpLoggingInterceptorM 日志拦截
 * 3.配置Retrofit
 */
public class BaseRetrofitFactory {

    /**
     * 添加header响应头的集合,用于存放追加的header头的数据
     * */
    private Map<String,String> map = new HashMap<>();

    /**
     * 自定义Interceptor对象，用于在网络请求发出之前将header拦截添加进响应头
     * */
    private MyInterceptor myInterceptor = null;

    /**
     * 构造器，通过builder方式传入header响应头并初始化自定义拦截器Interceptor
     * */
    private BaseRetrofitFactory(Builder builder){
        map.clear();
        this.map = builder.map;
        myInterceptor = new MyInterceptor(map);
    }

    /**
     * okhttp的日志拦截，可在正式发布时关闭
     * */
//    private HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY);
    /**
     * 自定义okhttp的日志拦截，可在正式发布时关闭
     * */
    private HttpLoggingInterceptorM interceptorM = new HttpLoggingInterceptorM(new LogInterceptor()).setLevel(HttpLoggingInterceptorM.Level.BODY);

    /**
     * 创建自定义OkHttpClient对象，初始化日志拦截、错误重连、超时、响应头等信息
     * */
    private OkHttpClient getClient(){
        OkHttpClient client = null;
        if(Switch.isDebug){
            client = new OkHttpClient.Builder()
                    .addInterceptor(interceptorM)                                           //日志拦截
                    .cookieJar(new CookieManger(BaseApplication.context))                   //缓存cookie
                    .retryOnConnectionFailure(true)                                         //错误重连
                    .connectTimeout(BaseRetrofitConfig.OKHTTP_OVERTIME, TimeUnit.SECONDS)   //超时时间
                    .addNetworkInterceptor(myInterceptor)                                   //拦截添加响应头
                    .build();
        }else{
            client = new OkHttpClient.Builder()
                    .cookieJar(new CookieManger(BaseApplication.context))                   //缓存cookie
                    .retryOnConnectionFailure(true)                                         //错误重连
                    .connectTimeout(BaseRetrofitConfig.OKHTTP_OVERTIME, TimeUnit.SECONDS)   //超时时间
                    .addNetworkInterceptor(myInterceptor)                                   //拦截添加响应头
                    .build();
        }
        return client;
    }

    /**
     * 初始化Retrofit对象，包括baseUrl、使用Gson解析、是用RxJava等。
     * */
    private Retrofit getRetrofit(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BaseRetrofitConfig.baseUrl)                                    //base地址
                .addConverterFactory(ScalarsConverterFactory.create())                  //添加解析方式为String
                .addConverterFactory(GsonConverterFactory.create())                     //添加解析方式为Gson
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())               //添加网络RxJava
                .client(getClient())                                                    //添加自定义OkHttpClient
                .build();
        return retrofit;
    }

    /**
     * 创建Retrofit实例
     * */
    public BaseRetrofitTwoService getRetrofitService(){

        BaseRetrofitTwoService service = getRetrofit().create(BaseRetrofitTwoService.class);

        return service;
    }

    /**
     * Builder
     * */
    public static class Builder{

        Map<String,String> map = new HashMap<>();

        public Builder setHeaders(Map<String,String> map){
            this.map = map;
            return this;
        }

        public BaseRetrofitFactory build(){
            return new BaseRetrofitFactory(this);
        }
    }

    /**
     * create by AbyssKitty on 2016/10/09.
     * Version 1.0
     * 每次ping 携带请求头 从OkHttp中拦截，Retrofit2只能使用注解方式添加headers
     * */
    public class MyInterceptor implements Interceptor {

        private Map<String, String> headers;

        public MyInterceptor(Map<String, String> headers) {
            this.headers = headers;
        }

        @Override
        public Response intercept(Chain chain) throws IOException {

            Response response = null;

            //同步 避免溢出
            synchronized (this){
                Request.Builder builder = chain.request()
                        .newBuilder();

                if (headers != null && headers.size() > 0) {

                    Set<String> keys = headers.keySet();

                    for (String headerKey : keys) {

                        builder.addHeader(headerKey, headers.get(headerKey)).build();

                    }

                    try{

                        response = chain.proceed(builder.build());

                    }catch (SocketTimeoutException e){
                        e.getLocalizedMessage();
                    }
                }else{

                    response = chain.proceed(builder.build());

                }
            }
//            获取error code 暂时不用
//            Response responseError = chain.proceed(chain.request());
//            responseError.code();
            return response;
        }

    }
}
