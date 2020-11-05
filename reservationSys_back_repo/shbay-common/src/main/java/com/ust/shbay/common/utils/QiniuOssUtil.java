package com.ust.shbay.common.utils;

import com.google.gson.Gson;
import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.storage.BucketManager;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.Region;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.storage.model.FileInfo;
import com.qiniu.util.Auth;
import com.ust.shbay.common.constant.SettingConstant;
import com.ust.shbay.common.dto.OssConfig;
import com.ust.shbay.common.exception.SException;
import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.net.URLEncoder;

/**
 *
 */
@Slf4j
public class QiniuOssUtil {

    private static Configuration getConfiguration(Integer zone){

        Configuration cfg = null;
        if(zone.equals(SettingConstant.QINIU.ZONE.ZONE_ZERO)){
            cfg = new Configuration(Region.region0());
        }else if(zone.equals(SettingConstant.QINIU.ZONE.ZONE_ONE)){
            cfg = new Configuration(Region.region1());
        }else if(zone.equals(SettingConstant.QINIU.ZONE.ZONE_TWO)){
            cfg = new Configuration(Region.region2());
        }else if(zone.equals(SettingConstant.QINIU.ZONE.ZONE_THREE)){
            cfg = new Configuration(Region.regionNa0());
        }else if(zone.equals(SettingConstant.QINIU.ZONE.ZONE_FOUR)){
            cfg = new Configuration(Region.regionAs0());
        }else {
            cfg = new Configuration(Region.autoRegion());
        }
        return cfg;
    }

    /**
     * 通过发送http get 请求获取文件资源
     *
     * @param filepath 文件路径
     * @param bytes 文件内容
     * @param ossConfig oss配置
     * @return key 文件url
     */
    public static String upload(String filepath, byte[] bytes, OssConfig ossConfig) {

        String key;
        Auth auth = Auth.create(ossConfig.getAccessKey(), ossConfig.getSecretKey());
        String token = auth.uploadToken(ossConfig.getBucket());
        try {
            Configuration cfg = getConfiguration(ossConfig.getZone());
            UploadManager uploadManager = new UploadManager(cfg);
//            Response response = uploadManager.put(inputStream, fileName, token, null, null);
            Response response = uploadManager.put(bytes, filepath, token);
            DefaultPutRet putRet = new Gson().fromJson(response.bodyString(), DefaultPutRet.class);

            key = putRet.key;
        } catch (QiniuException ex) {
            Response r = ex.response;
            throw new SException("上传文件出错，请检查七牛云配置，" + r.toString());
        }
        return key;
    }

    /**
     * 通过发送http get 请求获取文件资源
     *
     * @param filepath 文件路径
     * @param ossConfig oss配置
     * @return url 文件url
     */
    public static String download(String filepath, OssConfig ossConfig) {

        String encodedFileName;
        try {
            encodedFileName = URLEncoder.encode(filepath, "utf-8").replace("+", "%20");
        }
        catch (UnsupportedEncodingException e){
            throw new SException("URL编码错误，" + e.getMessage());
        }

        String downloadUrl = String.format("%s%s/%s", ossConfig.getHttp(), ossConfig.getEndPoint(), encodedFileName);
        Auth auth = Auth.create(ossConfig.getAccessKey(), ossConfig.getSecretKey());
        long expireInSeconds = 3600;//1小时，可以自定义链接过期时间
        String finalUrl = auth.privateDownloadUrl(downloadUrl, expireInSeconds);
        //String finalUrl = auth.privateDownloadUrl(downloadUrl);
        return finalUrl;
    }

    /**
     * 读取字节输入流内容
     *
     * @param is
     * @return
     */
    private static byte[] readInputStream(InputStream is) {
        ByteArrayOutputStream writer = new ByteArrayOutputStream();
        byte[] buff = new byte[1024 * 2];
        int len = 0;
        try {
            while ((len = is.read(buff)) != -1) {
                writer.write(buff, 0, len);
            }
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return writer.toByteArray();
    }

    public static byte[] download1(String fileName, OssConfig ossConfig, String bucketName) {
//        OkHttpClient client = new OkHttpClient();
//        Request req = new Request.Builder().url(finalUrl).build();

//        okhttp3.Response resp = null;
//        try {
//            resp = client.newCall(req).execute();
//            System.out.println(resp.isSuccessful());
//            if (resp.isSuccessful()) {
//                ResponseBody body = resp.body();
//                InputStream is = body.byteStream();
//                byte[] data = readInputStream(is);
//            }
//        }
//        catch (IOException e){
//
//        }
//        return "aa";
        return null;
    }

    public static void deleteFile(OssConfig ossConfig, String bucketName, String key) {

        Auth auth = Auth.create (ossConfig.getAccessKey(), ossConfig.getSecretKey());
        BucketManager bucketManager = new BucketManager(auth, getConfiguration(ossConfig.getZone()));
        FileInfo fileInfo = null;
        try {
            fileInfo = bucketManager.stat(ossConfig.getBucket(), key);
        } catch (QiniuException ex) {
            log.error("获取文件失败，" + ex.response.error);
        }
        try {
            if(fileInfo != null){
                bucketManager.delete(ossConfig.getBucket(), key);
            }
        } catch (QiniuException ex) {
            throw new SException("删除文件失败，" + ex.response.error);
        }
        return;
    }
}
