package com.markeveryday.utils;

import com.qiniu.api.auth.AuthException;
import com.qiniu.api.auth.digest.Mac;
import com.qiniu.api.config.Config;
import com.qiniu.api.io.IoApi;
import com.qiniu.api.io.PutExtra;
import com.qiniu.api.io.PutRet;
import com.qiniu.api.rs.PutPolicy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;


/**
 * @author liming
 */
@Component
public class QiNiuUtil {


    private static final Logger logger = LoggerFactory.getLogger(QiNiuUtil.class);

    /**
     * 上传文件到七牛存储
     *
     * @return 成功返回路径, 失败返回null
     */
    public static String upload(MultipartFile file) {

        // 获取密钥
        Config.ACCESS_KEY = Constants.QiNiu.accessKey;
        Config.SECRET_KEY = Constants.QiNiu.secretKey;

        Mac mac = new Mac(Config.ACCESS_KEY, Config.SECRET_KEY);
        String bucketName = Constants.QiNiu.bucketName;
        PutPolicy putPolicy = new PutPolicy(bucketName);

        String uploadToken = null;
        try {
            uploadToken = putPolicy.token(mac);
        } catch (AuthException e) {
            logger.error("Authorize error,{}", e);
            e.printStackTrace();
        } catch (org.json.JSONException e) {
            logger.error("Authorize error,{}", e);
            e.printStackTrace();
        }
        PutExtra extra = new PutExtra();

        // key = 存在云端的文件名，prefix为文件的前缀,加时间戳这样可以上传同名图片

        String key = "";
        try {
            key = URLEncoder.encode(Constants.QiNiu.imagePrefix, "UTF-8")
                    + file.getOriginalFilename() + System.currentTimeMillis();
        } catch (UnsupportedEncodingException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
        PutRet ret = null;
        try {
            ret = IoApi.Put(uploadToken, key, file.getInputStream(), extra);
        } catch (Exception e) {
            logger.error("上传到七牛失败", e);
        }
        if (ret.ok()) {
            return Constants.QiNiu.imageUrlPrefix + key;
        } else {
            return null;
        }
    }
}
