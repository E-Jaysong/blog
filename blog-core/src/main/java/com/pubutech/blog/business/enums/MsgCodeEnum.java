package com.pubutech.blog.business.enums;

public enum MsgCodeEnum {

    SUCCESS("000000", "成功"),
    FAIL("000001", "失败"),
    SYS_ERROR("000500","系统出错"),
    WRONG_SIGN("000002", "签名有误"),
    WRONG_CALL("000003", "接口标识有误"),
    BODY_ERROR("000004", "body参数出错"),
    EMPTY_REQUEST("000005", "空请求"),
    DATA_EXISTS("000006", "数据已存在"),
    HEAD_ERROR("000007", "head参数有误"),
    LOGIN_TYPE_ERROR("000008", "head参数有误"),
    SCENCE_EMPTY("000009", "场景码为空"),
    INNEED_PARAM_EMPTY("000010", "必填参数为空"),
    DATA_NOT_EXISTS("000011", "无数据"),
    MOBILE_IS_USED("100000", "手机号已被注册"),
    WRONG_DYNAMICPWD("100001", "验证码错误"),
    DYNAMICPWD_OVER_DUE("100002", "验证码已过期"),
    USER_NOT_EXIST("100003", "用户不存在"),
    PASSWORD_NOT_CORRECT("100004", "密码错误"),
    TOKEN_UNVALID("100005", "token失效"),
    USER_NOT_LOGIN("100006", "用户未登录"),
    FILE_NOT_EXIST("100007", "文件不存在"),
    LOAN_ACCT_NOT_EXIST("100008", "账户信息不存在"),
    USER_CONCAT_EMPTY("100009", "联系人列表为空"),
    USER_APPLY_SUBMITED("100010", "用户已开户"),
    STUDENT_PROTOCOL_CONFIRMED("100011", "学生协议已确认"),
    DEBITCARD_BINDED("100012", "借记卡已绑定"),
    USER_INFO_UPLOADED("100013", "用户信息已上传"),
    JOB_INFO_UPLOADED("100014", "用户工作信息已上传"),
    PERSON_FILE_UPLOADED("100015", "用户活体文件已上传"),
    ID_NOT_CHECK("200000", "身份认证未完成"),
    DEBIT_CARD_NOT_CHECK("200001", "信用卡绑定未完成"),
    MAIN_CARD_NOT_CHECK("200002", "主卡未指定"),
    LOAN_ACCT_NOT_SUPPORT("200003", "金融账号未申请"),
    USER_INFO_NOT_COMPLETE("200004", "用户信息未完成"),
    DEBIT_CARD_NOT_COMPLETE("200005", "银行卡未绑定"),
    MAIN_CARD_NOT_COMPLETE("200006", "银行卡主卡未指定"),
    NO_GET_DYNAMICPWD("002007", "未获取验证码"),
    DEPRECATED_DYNAMICPWD("002008", "验证码已过期"),
    ID_CHECKED("002009", "身份认证已做过"),
    JSON_PARSE_FAIL("003000", "JSON转换失败"),
    ALREADY_RECEIVE("003001", "您已经领取过了"),
    PARNER_RESPONSE_EMPTY("003002", "合作方返回数据为空"),
    CHECK_ACCOUNT_FAIL("004000", "用户不符合条件"),
    OPPORTUNITY_HAS_BEEN_EXHAUSTED("004001", "机会已用完"),
    PLEASE_SEND_QUICK_PAY_SMS_FIRST("004002", "请先发送快捷支付短信"),

    //配置化code枚举
    PRODUCT_NOT_CONFIGURE("500001","该产品暂未配置"),
    BIND_CARD_FAIL("500002","银行卡绑卡失败"),
    BIND_CREDIT_CARD_FAIL("500003","信用卡绑卡失败"),
    SET_BANK_CARD_FAIL("500004","设置账户扣款银行卡失败"),
    SET_TRADE_PASSWORD_FAIL("500005","设置交易密码失败"),
    FORGET_TRADE_PASSWORD_FAIL("500006","找回交易密码失败"),
    STUDENT_CONFIRM_PROTOCOL_FAIL("500007","非学生身份承诺协议确认失败"),


    //配置平台模块
    PLEASE_LOGIN_FIRST("600001","请先登录"),
    WRONG_NAME_OR_PASSWORD("600002","用户名或密码错误"),

    SYSTEM_BUSY("999998","系统繁忙,请稍后再试"),
    SYSTEM_ERROR("999999","服务器开小差了");


    MsgCodeEnum(String returnCode, String returnMsg) {
        this.returnCode = returnCode;
        this.returnMsg = returnMsg;
    }

    private String returnCode;

    private String returnMsg;

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
}
