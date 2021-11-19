package com.zhixingkeji.api.common;

import lombok.Data;
import java.io.Serializable;


@Data
public class ResultTemplate implements Serializable {
    private int code;
    private String msg;
    private Object data;


//    成功方法 传入data数据 调用构造方法
    public static ResultTemplate success(Object data) {
        return success(200,"操作成功",data);
    }


    public static ResultTemplate success(int code ,String mess, Object data) {
        ResultTemplate m = new ResultTemplate();
        m.setCode(code);
        m.setData(data);
        m.setMsg(mess);
        return m;
    }

    //    失败方法 传入错误信息 调用构造方法
    public static ResultTemplate fail(String msg) {

        return fail(400,msg,null);
    }


    public static ResultTemplate fail(int code,String msg, Object data) {
        ResultTemplate m = new ResultTemplate();
        m.setCode(code);
        m.setMsg(msg);
        m.setData(data);

        return m;
    }

}
