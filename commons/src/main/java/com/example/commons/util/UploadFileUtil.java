package com.example.commons.util;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@Component
@Slf4j
public class UploadFileUtil {

    @Value("${web.upload-path}")
    private String uploadPath;

    public String uploadFile(MultipartFile file) {
        //获取原图片的名称。
        String fileName = file.getOriginalFilename();
        // 拼接访问图片的地址。
        String visibleUri = "/" + fileName;
        // 图片的实际存放地址。
        String saveUri = uploadPath + "/" + fileName;
        log.info("图片原文件名={} 图片访问地址={} 图片保存真实地址={}",fileName,visibleUri,saveUri);
        File saveFile = new File(saveUri);
        if (!saveFile.exists()) {
            // 创建文件夹。
            saveFile.mkdirs();
            try {
                file.transferTo(saveFile);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return visibleUri;
    }
}
