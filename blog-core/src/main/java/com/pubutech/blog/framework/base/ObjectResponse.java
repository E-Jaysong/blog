package com.pubutech.blog.framework.base;


import com.pubutech.blog.business.enums.MsgCodeEnum;

import java.io.Serializable;
import java.util.UUID;

public class ObjectResponse<Data> implements Serializable {

    private String returnCode;

    private String returnMsg;

    private Long cost;

    private Data data;

    public ObjectResponse(){}

    public ObjectResponse(String returnCode, String returnMsg){
        this.returnCode = returnCode;
        this.returnMsg = returnMsg;
    }

    public ObjectResponse(String returnCode, String returnMsg, Long cost){
        this.returnCode = returnCode;
        this.returnMsg = returnMsg;
        this.cost = cost;
    }

    public ObjectResponse(String returnCode, String returnMsg, Data data){
        this(returnCode, returnMsg);
        this.data = data;
    }

    public ObjectResponse(String returnCode, String returnMsg, Long cost, Data data){
        this(returnCode, returnMsg, cost);
        this.data = data;
    }

    public static ObjectResponse result(MsgCodeEnum msgCodeEnum){
        return new ObjectResponse(msgCodeEnum.getReturnCode(), msgCodeEnum.getReturnMsg());
    }

    public static ObjectResponse result(MsgCodeEnum msgCodeEnum, Long cost){
        return new ObjectResponse(msgCodeEnum.getReturnCode(), msgCodeEnum.getReturnMsg(), cost);
    }

    public static <Data> ObjectResponse<Data> result(MsgCodeEnum msgCodeEnum, Data data){
        return new ObjectResponse(msgCodeEnum.getReturnCode(), msgCodeEnum.getReturnMsg(), data);
    }

    public static <Data> ObjectResponse<Data> result(MsgCodeEnum msgCodeEnum, Long cost, Data data){
        return new ObjectResponse(msgCodeEnum.getReturnCode(), msgCodeEnum.getReturnMsg(), cost, data);
    }

    public static String getToken(){
        return UUID.randomUUID().toString().replace("-", "");
    }

    public static <Data> ObjectResponse<Data> success(Data data){
        return result(MsgCodeEnum.SUCCESS, data);
    }

    public static <Data> ObjectResponse<Data> fail(Data data){
        return result(MsgCodeEnum.FAIL, data);
    }

    public static ObjectResponse success(){
        return result(MsgCodeEnum.SUCCESS);
    }

    public static ObjectResponse fail(){
        return result(MsgCodeEnum.FAIL);
    }

    public String getReturnCode() {
        return returnCode;
    }

    public void setReturnCode(String returnCode) {
        this.returnCode = returnCode;
    }

    public String getReturnMsg() {
        return returnMsg;
    }

    public void setReturnMsg(String returnMsg) {
        this.returnMsg = returnMsg;
    }

    public Long getCost() {
        return cost;
    }

    public void setCost(Long cost) {
        this.cost = cost;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }
}
