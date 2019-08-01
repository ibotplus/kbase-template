package com.eastrobot.kbs.template.web.controller;


import com.eastrobot.kbs.common.version.ApiVersion;
import com.eastrobot.kbs.template.model.vo.UserVO;
import com.eastrobot.kbs.template.service.IUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<String> create(@ApiParam(value = "UserVO Create RequestBody")
                                         @Validated(UserVO.Create.class)
                                         @RequestBody UserVO vo) {
        return ResponseEntity.ok(userService.save(vo));
    }

    @ApiOperation(value = "删除用户")
    @DeleteMapping("{id}")
    public ResponseEntity delete(@NotEmpty @PathVariable String id) {
        return ResponseEntity.ok(userService.deleteById(id));
    }

    @ApiOperation(value = "修改用户")
    @PutMapping("/user")
    public ResponseEntity<Boolean> update(@ApiParam(value = "UserVO Create RequestBody", type = "DatasourceVO")
                                          @Validated(UserVO.Update.class)
                                          @RequestBody UserVO vo) {
        return ResponseEntity.ok(userService.update(vo));
    }

    @ApiOperation(value = "根据id查询实体")
    @GetMapping("{id}")
    public ResponseEntity<UserVO> getOne(@PathVariable String id) {
        return ResponseEntity.ok(userService.findById(id));
    }

    @ApiOperation(value = "分页", notes = "返回查询结果")
    @GetMapping("/users")
    public ResponseEntity<Page<UserVO>> list(@ApiParam(value = "Page Request", type = "PageRequest")
                                             @Valid PageRequest pageRequest) {
        return ResponseEntity.ok(userService.pageForUser(pageRequest));
    }

}
