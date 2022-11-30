package com.example.teacher.service;

import com.aliyun.oss.ClientException;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.OSSException;
import com.aliyun.oss.model.CannedAccessControlList;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

@Service
public class OssUploadService {
//    public static void main(String[] args) {
//        String uploadFile = uploadFile(new File("C:\\Users\\hp\\Desktop\\jianmo\\student.xls"));
//        System.out.println(uploadFile);
//    }
    /*
    public static String uploadFile(File multipartFile){
        String endpoint = "oss-cn-hangzhou.aliyuncs.com";
        // 阿里云账号AccessKey拥有所有API的访问权限，风险很高。强烈建议您创建并使用RAM用户进行API访问或日常运维，请登录RAM控制台创建RAM用户。
        String accessKeyId = "LTAI5tDpgm5Hj5BudFU5UX7b";
        String accessKeySecret = "DpTIpHoMURhfmKBPGiz8ldGr6iWA2m";
        // 填写Bucket名称，例如examplebucket。
        String bucketName = "the-course-test";
        // 填写本地文件的完整路径，例如D:\\localpath\\examplefile.txt。

        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
        if(!ossClient.doesBucketExist(bucketName)){
            // 创建bucket
            ossClient.createBucket(bucketName);
            // 设置oss实例访问权限：公共读
            ossClient.setBucketAcl(bucketName, CannedAccessControlList.PublicRead);
        }
        try {
            InputStream inputStream = new FileInputStream(multipartFile);
            // 创建日期目录
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
            String datePath = dateFormat.format(new Date());
            // 获取文件名
            String originname = multipartFile.getName();
            String filename = UUID.randomUUID().toString();
            String suffix = originname.substring(originname.lastIndexOf("."));
            String newName = filename + suffix;
            String fileUrl = datePath+"/"+newName;
            // 创建PutObject请求。上传到阿里服务器
            ossClient.putObject(bucketName, fileUrl, inputStream);

            return "https://"+ bucketName + "." + endpoint + "/" + newName;
        } catch (OSSException oe) {
            System.out.println("Caught an OSSException, which means your request made it to OSS, "
                    + "but was rejected with an error response for some reason.");
            System.out.println("Error Message:" + oe.getErrorMessage());
            System.out.println("Error Code:" + oe.getErrorCode());
            System.out.println("Request ID:" + oe.getRequestId());
            System.out.println("Host ID:" + oe.getHostId());
        } catch (ClientException ce) {
            System.out.println("Caught an ClientException, which means the client encountered "
                    + "a serious internal problem while trying to communicate with OSS, "
                    + "such as not being able to access the network.");
            System.out.println("Error Message:" + ce.getMessage());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            if (ossClient != null) {
                ossClient.shutdown();
            }
        }
        return null;
    }
    */
    public String uploadFile(MultipartFile multipartFile,String courseId,String chapterId,String experimentId){
        String endpoint = "oss-cn-hangzhou.aliyuncs.com";
        // 阿里云账号AccessKey拥有所有API的访问权限，风险很高。强烈建议您创建并使用RAM用户进行API访问或日常运维，请登录RAM控制台创建RAM用户。
        String accessKeyId = "LTAI5tDpgm5Hj5BudFU5UX7b";
        String accessKeySecret = "DpTIpHoMURhfmKBPGiz8ldGr6iWA2m";
        // 填写Bucket名称，例如examplebucket。
        String bucketName = "the-course-test";
        // 填写本地文件的完整路径，例如D:\\localpath\\examplefile.txt。

        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
        if(!ossClient.doesBucketExist(bucketName)){
            // 创建bucket
            ossClient.createBucket(bucketName);
            // 设置oss实例访问权限：公共读
            ossClient.setBucketAcl(bucketName, CannedAccessControlList.PublicRead);
        }
        try {
            InputStream inputStream = multipartFile.getInputStream();
            // 创建日期目录
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
            String datePath = dateFormat.format(new Date());
            // 获取文件名
            String originname = multipartFile.getOriginalFilename();
            String filename = UUID.randomUUID().toString();
            String suffix = originname.substring(originname.lastIndexOf("."));
            String newName = filename + suffix;
            String fileUrl = courseId + "course/" + chapterId + "chapter/实验" + experimentId + "/" + datePath+"/"+newName;
            // 创建PutObject请求。上传到阿里服务器
            ossClient.putObject(bucketName, fileUrl, inputStream);

            return "https://"+ bucketName + "." + endpoint + "/" + newName;
        } catch (OSSException oe) {
            System.out.println("Caught an OSSException, which means your request made it to OSS, "
                    + "but was rejected with an error response for some reason.");
            System.out.println("Error Message:" + oe.getErrorMessage());
            System.out.println("Error Code:" + oe.getErrorCode());
            System.out.println("Request ID:" + oe.getRequestId());
            System.out.println("Host ID:" + oe.getHostId());
        } catch (ClientException ce) {
            System.out.println("Caught an ClientException, which means the client encountered "
                    + "a serious internal problem while trying to communicate with OSS, "
                    + "such as not being able to access the network.");
            System.out.println("Error Message:" + ce.getMessage());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (ossClient != null) {
                ossClient.shutdown();
            }
        }
        return null;
    }
}
