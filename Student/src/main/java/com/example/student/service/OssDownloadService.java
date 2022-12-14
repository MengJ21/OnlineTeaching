package com.example.student.service;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.model.OSSObject;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.*;

@Service
public class OssDownloadService {
    public void download(HttpServletResponse response,String url,String fileName){
        // Endpoint以华东1（杭州）为例，其它Region请按实际情况填写。
        String endpoint = "oss-cn-hangzhou.aliyuncs.com";
        // 阿里云账号AccessKey拥有所有API的访问权限，风险很高。强烈建议您创建并使用RAM用户进行API访问或日常运维，请登录RAM控制台创建RAM用户。
        String accessKeyId = "LTAI5tDpgm5Hj5BudFU5UX7b";
        String accessKeySecret = "DpTIpHoMURhfmKBPGiz8ldGr6iWA2m";
        // 填写Bucket名称，例如examplebucket。
        String bucketName = "the-course-test";
        BufferedInputStream input = null;
        OutputStream outputStream = null;

        // 填写不包含Bucket名称在内的Object完整路径，例如testfolder/exampleobject.txt。
        String objectName = url;
        String outputFileName = fileName;

        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);

        OSSObject ossObject = ossClient.getObject(bucketName, objectName);

        try {
            response.reset();
            response.setCharacterEncoding("utf-8");
            response.setContentType("application/x-msdownload");
            response.addHeader("Content-Disposition",
                    "attachment;filename=" + new String(outputFileName.getBytes("gb2312"), "ISO8859-1"));

            input = new BufferedInputStream(ossObject.getObjectContent());
            byte[] buffBytes = new byte[1024];
            outputStream = response.getOutputStream();
            int read = 0;
            while ((read = input.read(buffBytes)) != -1) {
                outputStream.write(buffBytes, 0, read);
            }
            outputStream.flush();
            // 数据读取完成后，获取的流必须关闭，否则会造成连接泄漏，导致请求无连接可用，程序无法正常工作。
            ossObject.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            try {
                if (outputStream != null) {
                    outputStream.close();
                }
                if (input != null) {
                    input.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        ossClient.shutdown();
    }
    public String getFile(String url) {
        // Endpoint以华东1（杭州）为例，其它Region请按实际情况填写。
        String endpoint = "oss-cn-hangzhou.aliyuncs.com";
        // 阿里云账号AccessKey拥有所有API的访问权限，风险很高。强烈建议您创建并使用RAM用户进行API访问或日常运维，请登录RAM控制台创建RAM用户。
        String accessKeyId = "LTAI5tDpgm5Hj5BudFU5UX7b";
        String accessKeySecret = "DpTIpHoMURhfmKBPGiz8ldGr6iWA2m";
        // 填写Bucket名称，例如examplebucket。
        String bucketName = "the-course-test";
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);

        OSSObject ossObject = ossClient.getObject(bucketName, url);
        InputStream inputStream = ossObject.getObjectContent();
        // 创建字节数组输出流，用来输出读取到的内容 。
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        // 缓存。
        byte[] buffer = new byte[1024];
        // 每次读取到内容的长度。
        int len = -1;
        while (true) {
            try {
                if ((len = inputStream.read(buffer)) == -1) break;
            } catch (IOException e) {
                e.printStackTrace();
            } //当等于-1说明没有数据可以读取了
            baos.write(buffer, 0, len);   //把读取到的内容写到输出流中
        }
        String content = baos.toString();
        //<5>关闭输入流和输出流
        try {
            inputStream.close();
            baos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return baos.toString();
    }
}
