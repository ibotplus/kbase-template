package com.eastrobot.kbs.template.exception;

import io.swagger.annotations.ApiModel;

/**
 * global response error code and it's meaning,
 * {@link org.springframework.http.HttpStatus}
 * {@link GlobalExceptionHandler}
 *
 * @author <a href="yogurt_lei@foxmail.com">Yogurt_lei</a>
 * @version v1.0 , 2019-03-02 16:03
 */
@ApiModel
public enum ErrorCode {
    /**
     * general failure
     */
    FAILURE("0000", "FAILURE"),
    /**
     * api called failed,
     */
    API_CALLED_FAILED("1000", "API CALLED FAILED"),
    /**
     * controller parameter validate failed
     */
    PARAMETER_VALIDATE_FAILED("1001", "PARAMETER VALIDATE FAILED"),
    /**
     * business execute failed
     */
    BUSINESS_EXECUTE_FAILED("1002", "BUSINESS EXECUTE FAILED"),
    /**
     * database operated failed. note: it will rollback transaction
     */
    DATABASE_OPERATED_FAILED("1003", "DATABASE OPERATED FAILED"),
    /**
     * datasource exception
     */
    DATASOURCE_EXCEPTION("1006", "DATASOURCE EXCEPTION"),
    /**
     * wrong entity id exception
     */
    WRONG_ENTITY_ID_EXCEPTION("1008", "WRONG ENTITY ID EXCEPTION"),
    /**
     * undefined server exception
     */
    UNDEFINED_SERVER_EXCEPTION("9999", "UNDEFINED SERVER EXCEPTION");

    String code;
    String meaning;

    ErrorCode(String code, String meaning) {
        this.code = code;
        this.meaning = meaning;
    }
}
