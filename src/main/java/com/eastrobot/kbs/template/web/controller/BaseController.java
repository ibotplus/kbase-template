package com.eastrobot.kbs.template.web.controller;

import com.eastrobot.kbs.template.util.EnvironmentUtil;

/**
 * @author <a href="yogurt_lei@foxmail.com">Yogurt_lei</a>
 * @version v1.0 , 2019-06-17 14:49
 */
public interface BaseController {

    /**
     * 授权成功后, token对应的uid
     */
    default String ofUid() {
        return EnvironmentUtil.ofUid();
    }
}
