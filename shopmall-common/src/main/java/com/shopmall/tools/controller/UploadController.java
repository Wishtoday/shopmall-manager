package com.shopmall.tools.controller;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import com.shopmall.constant.ShopConstants;
import com.shopmall.constant.SystemConfigConstants;
import com.shopmall.enums.ShopCommonEnum;
import com.shopmall.exception.ShopMallException;
import com.shopmall.oss.config.OssProperties;
import com.shopmall.oss.service.OssTemplate;
import com.shopmall.tools.dto.LocalStorageDto;
import com.shopmall.tools.service.LocalStorageService;
import com.shopmall.utils.RedisUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;

/**
 * @author zhuxiying
 * @date 2023-12-12
 */
@Api(tags = "上传统一管理")
@RestController
@RequestMapping("/api/upload")
@Slf4j
@SuppressWarnings("unchecked")
public class UploadController {

    @Autowired
    private LocalStorageService localStorageService;
    @Autowired
    private RedisUtils redisUtils;
    @Autowired
    private OssProperties ossProperties;
    @Autowired
    private OssTemplate ossTemplate;

    @ApiOperation("上传文件")
    @PostMapping
    public ResponseEntity <Object> create(@RequestParam(defaultValue = "") String name,
                                          @RequestParam(defaultValue = "") String type,
                                          @RequestParam("file") MultipartFile[] files) {

        String localUrl = redisUtils.getY(ShopConstants.ADMIN_API_URL);
        if(StrUtil.isBlank(type)){
            localUrl = redisUtils.getY(SystemConfigConstants.API_URL) + "/api";
        }
        String mode = redisUtils.getY(SystemConfigConstants.FILE_STORE_MODE);
        StringBuilder url = new StringBuilder();
        if (ShopCommonEnum.STORE_MODE_1.getValue().toString().equals(mode)) { //存在走本地
            if(StrUtil.isBlank(localUrl)){
                throw new ShopMallException("本地上传,请先登陆系统配置后台/移动端API地址");
            }
            for (MultipartFile file : files) {
                LocalStorageDto localStorageDTO = localStorageService.create(name, file);
                if ("".equals(url.toString())) {
                    url = url.append(localUrl + "/file/" + localStorageDTO.getType() + "/" + localStorageDTO.getRealName());
                } else {
                    url = url.append(","+localUrl + "/file/" + localStorageDTO.getType() + "/" + localStorageDTO.getRealName());
                }
            }
        } else {
            // 走oss存储
            for (MultipartFile file : files) {
                String [] originalFilename = file.getOriginalFilename().split("\\.");
                String fileName = ossProperties.getBucketName()+"/file/"+originalFilename[0] + "-" + IdUtil.simpleUUID() + StrUtil.DOT + FileUtil.extName(file.getOriginalFilename());
                try {
                    ossTemplate.putObject(ossProperties.getBucketName(), fileName, file.getInputStream());
                } catch (Exception e) {
                    e.printStackTrace();
                }
                String fileUrl = String.format(ossProperties.getCustomDomain(), fileName);
                if ("".equals(url.toString())) {
                    url = url.append(fileUrl);
                }else{
                    url = url.append(","+fileUrl);
                }
            }
        }

        Map<String, Object> map = new HashMap<>(2);
        map.put("errno", 0);
        map.put("link", url);
        return new ResponseEntity(map, HttpStatus.CREATED);
    }


}
