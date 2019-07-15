package com.eastrobot.kbs.template.web.controller;

import com.eastrobot.kbs.template.model.entity.ResponseEntity;
import com.eastrobot.kbs.template.util.FileUploadUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;


@Slf4j
@Api(tags = "文件上传")
@RestController
@RequestMapping("/file")
public class FileController {

    @ApiOperation("上传文件")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "file", value = "文件", dataType = "__file", required = true, paramType = "form")
    })
    @PostMapping(
            value = "/",
            produces = {MediaType.APPLICATION_JSON_UTF8_VALUE},
            consumes = {MediaType.MULTIPART_FORM_DATA_VALUE}
    )
    public ResponseEntity upload(MultipartFile file) throws IOException {
        String voice = FileUploadUtil.upload(file.getInputStream(), file.getContentType(), file.getOriginalFilename());
        return ResponseEntity.ofSuccess(voice);
    }

    @ApiOperation("删除文件")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "filePath", value = "文件路径", required = true)
    })
    @DeleteMapping("/")
    public ResponseEntity delete(String filePath) {
        return ResponseEntity.ofSuccess(FileUploadUtil.delete(filePath));
    }


}
