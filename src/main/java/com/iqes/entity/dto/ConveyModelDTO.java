package com.iqes.entity.dto;

import com.alibaba.fastjson.JSONObject;

/**
 * 数据传输模版对象
 */
public class ConveyModelDTO {
    // 版本号
    private String Version;
    // 错误代码
    private String ErrorCode;
    // 错误信息
    private String ErrorMessage;
    // 传输数据
    private Object data;

    public String getVersion() {
        return Version;
    }

    public void setVersion(String version) {
        Version = version;
    }

    public String getErrorCode() {
        return ErrorCode;
    }

    public void setErrorCode(String errorCode) {
        ErrorCode = errorCode;
    }

    public String getErrorMessage() {
        return ErrorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        ErrorMessage = errorMessage;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
