package collect.jhjz.com.mytest.network.bean;

import java.util.List;

/**
 * Created by AbyssKitty on 2016/10/12.
 * 数据实体类
 */
public class RespDate {
    /*
    * 以下为接收数据的实体，可根据需求变更。
    *
    * 数据统一返回String类型，或者使用泛型
    * */
    public String returnCode; //返回码 正确=SUCCESS
    public String message;    //返回信息
    public String total;         //条数
    public String key;        //0
    public List list;       //list数据
    public Object obj;        //obj数据
    public Object info;
}
