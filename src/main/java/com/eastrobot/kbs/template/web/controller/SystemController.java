package com.eastrobot.kbs.template.web.controller;

import com.eastrobot.kbs.common.version.ApiVersion;
import io.swagger.annotations.Api;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(description = "系统级接口")
@ApiVersion
@Validated
@RestController
@RequestMapping("/sys")
public class SystemController {

}
