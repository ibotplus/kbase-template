package com.eastrobot.kbs.template.web.controller;

import com.eastrobot.kbs.template.model.entity.ResponseEntity;
import com.eastrobot.kbs.template.util.JwtUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author <a href="yogurt_lei@foxmail.com">Yogurt_lei</a>
 * @version v1.0 , 2019-03-14 17:29
 */
@Api
@RestController
@RequestMapping("/demo")
public class DemoController {

    @ApiOperation("生成token")
    @ApiImplicitParam(name = "userId", value = "userId", dataType = "string", required = true, paramType = "query")
    @GetMapping("/generate")
    public ResponseEntity generate(String userId) {
        return ResponseEntity.ofSuccess(JwtUtil.generateJwt(userId));
    }

    @ApiOperation("校验token")
    @ApiImplicitParam(name = "token", value = "token", dataType = "string", required = true, paramType = "query")
    @GetMapping("/validate")
    public ResponseEntity validate(String token) {
        return ResponseEntity.ofSuccess(JwtUtil.ofClaims(token));
    }

}