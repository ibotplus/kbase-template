package com.eastrobot.kbs.template.auth;

/**
 * 认证过程中使用到的常量定义
 *
 * @author <a href="yogurt_lei@foxmail.com">Yogurt_lei</a>
 * @version v1.0 , 2018-12-28 10:55
 */
interface AuthenticationConstants {
    /**
     * 账户不存在
     */
    String MSG_ACCOUNT_NOT_FOUND       = "The account is not found";
    /**
     * 域登录标志
     */
    String MSG_MISMATCHING_CREDENTIALS = "The account password does not match";
}