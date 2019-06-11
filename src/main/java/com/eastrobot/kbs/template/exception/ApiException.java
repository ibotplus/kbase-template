package com.eastrobot.kbs.template.exception;

/**
 * api异常 用于控制层 参数校验完毕 对参数的其他处理出错
 *
 * @author <a href="yogurt_lei@foxmail.com">Yogurt_lei</a>
 * @version v1.0 , 2018-09-05 10:16
 */
public class ApiException extends RuntimeException {

    public ApiException(String message) {
        super(message);
    }
}
