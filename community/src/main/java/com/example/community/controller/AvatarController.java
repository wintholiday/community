package com.example.community.controller;

import com.example.community.pojo.Result;
import com.example.community.utils.AliOSSUtils;
import com.example.community.service.UsersService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Slf4j
@RestController
public class AvatarController {
    @Autowired
    private AliOSSUtils aliOSSUtils;
    //本地存储文件
    /*@PostMapping("/upload")
    public Result upload(String username , Integer age , MultipartFile image) throws Exception {
        log.info("文件上传: {}, {}, {}", username, age, image);
        //获取原始文件名 - 1.jpg  123.0.0.jpg
        String originalFilename = image.getOriginalFilename();

        //构造唯一的文件名 (不能重复) - uuid(通用唯一识别码) de49685b-61c0-4b11-80fa-c71e95924018
        int index = originalFilename.lastIndexOf(".");
        String extname = originalFilename.substring(index);
        String newFileName = UUID.randomUUID().toString() + extname;
        log.info("新的文件名: {}", newFileName);

        //将文件存储在服务器的磁盘目录中 E:\images
        image.transferTo(new File("E:\\images\\"+newFileName));

        return Result.success();
    }*/

    @PostMapping("/upload")
    public Result upload(MultipartFile image) throws IOException {
        log.info("头像上传, 文件名: {}", image.getOriginalFilename());

        //调用阿里云OSS工具类进行文件上传
        String url = aliOSSUtils.upload(image);
        log.info("头像上传完成,文件访问的url: {}", url);

        return Result.success(url);
    }
    @Autowired
    private UsersService userService;


    // 用户上传新头像并更新用户信息
    @PostMapping("/updateAvatar")
    public Result updateAvatar(@RequestParam("userId") int userId, @RequestParam("avatarImage") MultipartFile avatarImage) {
        try {
            // 上传新头像到OSS
            String avatarUrl = aliOSSUtils.upload(avatarImage);

            // 更新用户信息中的头像地址
            userService.updateAvatar(userId, avatarUrl);

            return Result.success("Avatar updated successfully");
        } catch (IOException e) {
            return Result.error("Failed to update avatar");
        }
    }








}
