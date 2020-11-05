package com.ust.shbay.service.setting;

import com.ust.shbay.common.constant.Constant;
import com.ust.shbay.common.constant.SettingConstant;
import com.ust.shbay.common.dto.SmsConfig;
import com.ust.shbay.common.exception.SException;
import com.ust.shbay.manager.dao.TSettingMapper;
import com.ust.shbay.manager.entity.TSetting;
import com.ust.shbay.manager.entity.TSettingExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SettingServiceImpl implements SettingService {
    @Autowired
    private TSettingMapper tSettingMapper;

    @Override
    public SmsConfig getSmsConf(String type){
        TSettingExample example = new TSettingExample();
        TSettingExample.Criteria criteria = example.createCriteria();
        criteria.andModuleEqualTo(SettingConstant.SETTING_MODULE.ALI_SMS);
        criteria.andDelFlagEqualTo(Constant.Status.NORMAL);
        List<TSetting> tSettingList = tSettingMapper.selectByExample(example);
        String accessKey = null,secretKey = null,templateCode = null,signName = null;
        if(tSettingList.size() > 0){
            for (TSetting tSetting : tSettingList) {
                if(tSetting.getId().equals(SettingConstant.SETTING_ID.ALI_SMS_ACCESS_KEY_ID)){
                    accessKey = tSetting.getValue();
                }
                if(tSetting.getId().equals(SettingConstant.SETTING_ID.ALI_SMS_ACCESS_KEY_SECRET)){
                    secretKey = tSetting.getValue();
                }
                if(type.equals("code")&&tSetting.getId().equals(SettingConstant.SETTING_ID.ALI_SMS_TEMPLATE_CODE)){
                    templateCode = tSetting.getValue();
                }
                if(type.equals("message")&&tSetting.getId().equals(SettingConstant.SETTING_ID.ALI_SMS_MESS_TEMPLATE_CODE)){
                    templateCode = tSetting.getValue();
                }
                if(tSetting.getId().equals(SettingConstant.SETTING_ID.ALI_SMS_SIGN_NAME)){
                    signName = tSetting.getValue();
                }
            }
        }
//        else{
//            throw new SException("验证码发送失败，阿里云短信配置异常！");
//        }
//        if(accessKey == null || secretKey == null || templateCode == null || signName == null){
//            throw new SException("验证码发送失败，阿里云短信配置异常！");
//        }
        SmsConfig smsConfig = new SmsConfig();
        smsConfig.setAccessKey(accessKey);
        smsConfig.setSecretKey(secretKey);
        smsConfig.setSignName(signName);
        smsConfig.setTemplateCode(templateCode);
        return smsConfig;
    }
}
