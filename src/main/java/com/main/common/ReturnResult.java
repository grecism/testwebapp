package com.main.common;

import java.io.Serializable;

/**
 * 
 *<p>Title	: ReturnResult</p>
 * @Description	: 返回参数接收
 * @author	: admin
 * @date	: 2017年11月27日下午3:04:50
 * @param <T>
 */
public class ReturnResult<T> implements Serializable {

    private static final long serialVersionUID = 1L;

    private String status;
    private String message;// 中文描述
    private T content;//返回实体参数

    public ReturnResult() {
        super();
    }

    public ReturnResult(String status) {
        this.status = status;
    }

    public ReturnResult(String status, String message) {
        this.status = status;
        this.message = message;
    }
    
    public ReturnResult(String status, String message, T entity) {
        this.status = status;
        this.message = message;
        this.content = entity;
    }

    public ReturnResult(StatusResponse status, String message) {
        super();
        this.status = status.errCode;
        this.message = message;
    }

    public ReturnResult(StatusResponse status, String message, T content) {
        super();
        this.status = status.errCode;
        this.message = message;
        this.content = content;
    }

   
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getContent() {
        return content;
    }

    public void setContent(T content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "ReturnResult [status=" + status + ", message=" + message
                + ", content=" + content + "]";
    }


}
