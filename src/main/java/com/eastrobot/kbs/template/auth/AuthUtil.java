package com.eastrobot.kbs.template.auth;

import com.eastrobot.kbs.template.model.entity.ResponseEntity;
import org.springframework.http.MediaType;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author <a href="yogurt_lei@foxmail.com">Yogurt_lei</a>
 * @version v1.0 , 2019-06-13 11:56
 */
public class AuthUtil {

    public static void flushResponse(HttpServletResponse response, ResponseEntity responseEntity) throws IOException {
        response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
        response.getWriter().write(responseEntity.toString());
        response.getWriter().flush();
    }
}
