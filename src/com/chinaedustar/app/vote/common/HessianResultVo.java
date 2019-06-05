package com.chinaedustar.app.vote.common;

/**
 * 接口返回对象
 * 
 * @author liangzh
 *
 * 2013年12月10日 上午11:44:04
 *
 */
public class HessianResultVo {

    //状态（0-失败，1-成功）
    private String status;
    private String message;
    private String data;

    public String getStatus() {
        return this.status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getData() {
        return this.data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
