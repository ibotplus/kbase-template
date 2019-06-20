package com.eastrobot.kbs.template.exception;

import com.eastrobot.kbs.template.model.entity.ResponseEntity;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author <a href="yogurt_lei@foxmail.com">Yogurt_lei</a>
 * @version v1.0 , 2019-06-13 11:37
 */
@RestController
public class NotFoundException implements ErrorController {
    @Override
    public String getErrorPath() {
        return "/error";
    }

    @RequestMapping("/error")
    public ResponseEntity error() {
        return ResponseEntity.ofFailure(ResultCode.PAGE_NOT_FOUND);
    }
}
