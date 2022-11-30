package com.my.store.util;

import java.io.Serializable;

/* Json格式的数据进行相应 */
public class JsonResult<E> implements Serializable {

    //状态码
    private Integer state;

    //描述信息
    private String msg;

    //数据 -- E：泛型，表示任何数据类型
    private E data;

    public JsonResult() {
    }

    public JsonResult(Integer state) {
        this.state = state;
    }

    public JsonResult(Throwable e) {
        this.msg = e.getMessage();
    }

    public JsonResult(Integer state, E data) {
        this.state = state;
        this.data = data;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public E getData() {
        return data;
    }

    public void setData(E data) {
        this.data = data;
    }
}
