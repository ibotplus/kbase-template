package com.eastrobot.kbs.template.exception;

/**
 * dao层异常
 *
 * @author <a href="yogurt_lei@foxmail.com">Yogurt_lei</a>
 * @version v1.0 , 2018-09-05 10:20
 */
public class DaoException extends RuntimeException {

    public DaoException(String message) {
        super(message);
    }
}
