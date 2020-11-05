package com.ust.shbay.service.file;

import com.ust.shbay.common.UUID;
import com.ust.shbay.common.constant.Constant;
import com.ust.shbay.common.constant.SettingConstant;
import com.ust.shbay.common.dto.OssConfig;
import com.ust.shbay.common.exception.SException;
import com.ust.shbay.common.utils.QiniuOssUtil;
import com.ust.shbay.manager.dao.TSettingMapper;
import com.ust.shbay.manager.entity.TSetting;
import com.ust.shbay.manager.entity.TSettingExample;
import com.ust.shbay.service.file.bo.FileUploadBO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 *
 */
@Slf4j
@Service
public class FileServiceImpl implements FileService {

    @Autowired
    private TSettingMapper tSettingMapper;

    //获取七牛云配置
    private OssConfig getOssConfig(){

        TSettingExample example = new TSettingExample();
        TSettingExample.Criteria criteria = example.createCriteria();
        criteria.andModuleEqualTo(SettingConstant.SETTING_MODULE.QINIU_OSS);
        criteria.andDelFlagEqualTo(Constant.Status.NORMAL);
        List<TSetting> tSettingList = tSettingMapper.selectByExample(example);
        OssConfig ossConfig = new OssConfig();
        if(tSettingList.size() > 0){
            for (TSetting tSetting : tSettingList) {
                if(tSetting.getId().equals(SettingConstant.SETTING_ID.QINIU_OSS_ACCESSKEY)){
                    ossConfig.setAccessKey(tSetting.getValue());
                }else if(tSetting.getId().equals(SettingConstant.SETTING_ID.QINIU_OSS_SECRETKEY)){
                    ossConfig.setSecretKey(tSetting.getValue());
                }else if(tSetting.getId().equals(SettingConstant.SETTING_ID.QINIU_OSS_BUCKET_PRIVATE)){
                    ossConfig.setBucket(tSetting.getValue());
                }else if(tSetting.getId().equals(SettingConstant.SETTING_ID.QINIU_OSS_ZONE)){
                    ossConfig.setZone(Integer.parseInt(tSetting.getValue()));
                }else if(tSetting.getId().equals(SettingConstant.SETTING_ID.QINIU_OSS_HTTP)){
                    ossConfig.setHttp(tSetting.getValue());
                }else if(tSetting.getId().equals(SettingConstant.SETTING_ID.QINIU_OSS_ENDPOINT)){
                    ossConfig.setEndPoint(tSetting.getValue());
                }
            }
        }else{
            throw new SException("文件上传出错，请检查七牛云配置");
        }
        return ossConfig;
    }

    private String getDirectory(String type) {
        //project:工程项目分类,reservation
        type = type.toLowerCase();
        if ("reservation".equals(type)) {
            return SettingConstant.QINIU.DIR.RESERVATION;
        } else if ("project".equals(type)) {
            return SettingConstant.QINIU.DIR.PROJECT;
        } else if ("other".equals(type)) {
            return SettingConstant.QINIU.DIR.OTHER;
        }

        return null;
    }

    /**
     * 上传文件
     * @param file
     * @param fileUploadBO
     * @return 文件地址
     */
    @Override
    public String upload(MultipartFile file, FileUploadBO fileUploadBO) {
        String uuid = UUID.randomUUID();
        String url;

            String fileName = file.getOriginalFilename();
            if (fileName.contains("+")) {
                fileName = fileName.replaceAll("\\+", "_");
            }
            int length = fileName.split("\\.").length;
            String subfix = fileName.split("\\.")[length - 1];
        try {
            byte[] bytes = file.getBytes();

            OssConfig ossConfig = getOssConfig();
            String dir = getDirectory(fileUploadBO.getType());
            url = QiniuOssUtil.upload(dir + uuid + "_" + fileName, bytes, ossConfig);
            if (StringUtils.isEmpty(url)) {
                throw new SException("文件上传到OSS出错");
            }
        }catch (Exception e) {
            log.error("文件上传出错", e);
            throw new SException("文件上传出错");
        }

        return url;
    }

    @Override
    public String download(String filepath) {
        OssConfig ossConfig = getOssConfig();
        String url = QiniuOssUtil.download(filepath, ossConfig);

        return url;
    }

    @Override
    public boolean delete (String key) {

        return false;
    }
}
