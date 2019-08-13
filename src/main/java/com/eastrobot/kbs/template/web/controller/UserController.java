package com.eastrobot.kbs.template.web.controller;


import com.eastrobot.kbs.common.version.ApiVersion;
import com.eastrobot.kbs.template.model.entity.ResponseEntity;
import com.eastrobot.kbs.template.model.vo.req.UserReq;
import com.eastrobot.kbs.template.model.vo.resp.UserResp;
import com.eastrobot.kbs.template.service.IUserService;
import com.eastrobot.kbs.template.util.pageable.PageInfo;
import com.eastrobot.kbs.template.util.pageable.PageInfoRequest;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

/**
 * <p>
 * Controller
 * </p>
 *
 * @author yogurt_lei
 * @since 2019-06-19˙
 */
@Api(description = "用户接口", tags = "sys")
@ApiVersion
@Validated
@RestController
@RequestMapping("/sys/")
public class UserController {

    @Resource
    private IUserService userService;

    @ApiOperation(value = "创建用户", notes = "返回表示创建成功")
    @PostMapping("/user")
    public ResponseEntity<UserResp> create(@ApiParam(value = "UserReq Create RequestBody")
                                         @Validated(UserReq.Create.class)
                                         @RequestBody UserReq vo) {
        return ResponseEntity.ok(userService.saveOrUpdate(vo));
    }

    @ApiOperation(value = "删除用户")
    @DeleteMapping("{id}")
    public ResponseEntity delete(@NotEmpty @PathVariable String id) {
        return ResponseEntity.ok(userService.deleteById(id));
    }

    @ApiOperation(value = "修改用户")
    @PutMapping("/user")
    public ResponseEntity<UserResp> update(@ApiParam(value = "UserReq Create RequestBody", type = "DatasourceVO")
                                          @Validated(UserReq.Update.class)
                                          @RequestBody UserReq vo) {
        return ResponseEntity.ok(userService.saveOrUpdate(vo));
    }

    @ApiOperation(value = "根据id查询实体")
    @GetMapping("{id}")
    public ResponseEntity<UserResp> getOne(@PathVariable String id) {
        return ResponseEntity.ok(userService.findById(id));
    }

    @ApiOperation(value = "分页", notes = "返回查询结果")
    @GetMapping("/users")
    public ResponseEntity<PageInfo<UserResp>> list(@ApiParam(value = "Page Request", type = "PageRequest")
                                             @Valid PageInfoRequest pageInfoRequest) {
        return ResponseEntity.ok(userService.pageForResult(pageInfoRequest));
    }

}
