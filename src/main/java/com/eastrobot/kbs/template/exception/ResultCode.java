package com.eastrobot.kbs.template.exception;

import io.swagger.annotations.ApiModel;

/**
 * global response code and it's meaning,
 * {@link GlobalExceptionHandler}
 *
 * @author <a href="yogurt_lei@foxmail.com">Yogurt_lei</a>
 * @version v1.0 , 2019-03-02 16:03
 */
@ApiModel
public enum ResultCode {
    /**
     * request handle success
     */
    SUCCESS("0", "SUCCESS"),
    /**
     * general failure, not recommended to use it
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
    UNDEFINED_SERVER_EXCEPTION("9999", "UNDEFINED SERVER EXCEPTION"),

    //*********************************************************
    //*************** login authentication start **************
    //*********************************************************
    /**
     * SESSION AUTHENTICATION EXCEPTION
     */
    AUTH_SESSION_AUTHENTICATION("100", "SESSION AUTHENTICATION EXCEPTION"),
    /**
     * USERNAME NOT FOUND EXCEPTION
     */
    AUTH_USERNAME_NOT_FOUND("101", "USERNAME NOT FOUND EXCEPTION"),
    /**
     * BAD CREDENTIALS EXCEPTION
     */
    AUTH_BAD_CREDENTIALS("102", "BAD CREDENTIALS EXCEPTION"),
    /**
     * UNKNOWN AUTHENTICATE EXCEPTION
     */
    AUTH_UNKNOWN_AUTHENTICATE("103", "UNKNOWN AUTHENTICATE EXCEPTION"),
    /**
     * jwt: user logout
     */
    JWT_USER_LOGOUT("104", "USER LOGOUT"),
    /**
     * jwt: renew token
     */
    JWT_RENEW_TOKEN("105", "RENEW TOKEN"),
    /**
     * jwt: illegal token
     */
    JWT_ILLEGAL_TOKEN("106", "ILLEGAL TOKEN");
    //*********************************************************
    //*************** login authentication end ****************
    //*********************************************************

    public String code;
    public String meaning;

    ResultCode(String code, String meaning) {
        this.code = code;
        this.meaning = meaning;
    }
}
