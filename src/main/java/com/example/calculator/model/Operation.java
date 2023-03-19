package com.example.calculator.model;

public class Operation {
    private String expr;  //计算公式
    private String result; //计算结果值
    private String respCode; //响应码,200为正常
    private String errMsg; //非正常情况下，错误提示信息

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getExpr() {
        return expr;
    }

    public void setExpr(String expr) {
        this.expr = expr;
    }

    public String getRespCode() {
        return respCode;
    }

    public void setRespCode(String respCode) {
        this.respCode = respCode;
    }

    public String getErrMsg() {
        return errMsg;
    }

    public void setErrMsg(String errMsg) {
        this.errMsg = errMsg;
    }

    @Override
    public String toString() {
        return String.format("计算公式：%s，计算结果：%s，返回码：%s，错误信息：%s", expr,result,respCode,errMsg);
    }

}
