package com.eastrobot.kbs.template.exception;

/**
 * Service层异常
 *
 * @author <a href="yogurt_lei@foxmail.com">Yogurt_lei</a>
 * @version v1.0 , 2018-09-05 10:20
 */
public class BusinessException extends RuntimeException {

    public BusinessException(Throwable cause) {
        super(cause);
    }

    public BusinessException(String message) {
        super(message);
    }
}
