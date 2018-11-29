package collect.jhjz.com.mytest.network.bean;

import java.util.List;

/**
 * Created by Administrator on 2016/10/19.
 */
public class RespBean {
    public String message;    //返回信息
    public List list;       //list数据
    public Object obj;        //obj数据
    public String code;    //成功返回9999
    public String page;
    public String pageNum;

    public List depart; //部门
    public List user;

    public Object info;
    public String total;         //条数
    public String key;        //0
    public String returnCode; //返回码 正确=SUCCESS
    public RespDate resp;

    public String token;
    public String userId;
}
