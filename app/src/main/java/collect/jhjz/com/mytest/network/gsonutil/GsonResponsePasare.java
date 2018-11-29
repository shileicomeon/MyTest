package collect.jhjz.com.mytest.network.gsonutil;

import com.google.gson.Gson;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * Created by Administrator on 2016/10/19.
 */
public class GsonResponsePasare<T> implements ParameterizedType {

    public T deal(String response) {

        //不建议该方式，推荐采用GsonResponsePasare实现ParameterizedType.因为getActualTypeArguments这里涉及获取GsonResponsePasare的泛型集合
        //Type gsonType = new ParameterizedType() {//...};
        Type gsonType = this;

        T t = new Gson().fromJson(response, gsonType);

        return t;
    }

    @Override
    public Type[] getActualTypeArguments() {
        Class clz = this.getClass();
        //这里必须注意在外面使用new GsonResponsePasare<GsonResponsePasare.DataInfo>(){};实例化时必须带上{},否则获取到的superclass为Object
        Type superclass = clz.getGenericSuperclass(); //getGenericSuperclass()获得带有泛型的父类
        if (superclass instanceof Class) {
            throw new RuntimeException("Missing type parameter.");
        }
        ParameterizedType parameterized = (ParameterizedType) superclass;
        return parameterized.getActualTypeArguments();
    }

    @Override
    public Type getOwnerType() {
        return null;
    }

    @Override
    public Type getRawType() {
        return null;
    }
}
