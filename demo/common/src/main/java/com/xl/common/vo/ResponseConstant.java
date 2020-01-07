package com.xl.common.vo;

/**
 * Description:
 * Designer: jack
 * Date: 2018/3/5
 * Version: 1.0.0
 */
public enum ResponseConstant {

    OPERATION_SUCCESS("0000", "操作成功"),
    OPERATION_FAIL("0001", "操作失败"),
    OPERATION_BIND_CARD_FAIL("0010", "创建会员失败"),
    OPERATION_WRONG_CAPTCHA_CODE("0002", "验证码错误"),
    OPERATION_WRONG_PARAM("0003", "参数错误"),
    TOKEN_NOT_FOUND("0004", "token未获取"),
    CHECK_TOKEN_ERROR("0005", "token校验失败"),
    OPERATION_WITHOUT_LOGIN("0006", "权限不足");



    private String code;
    private String message;

    ResponseConstant(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
