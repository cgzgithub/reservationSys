package com.ust.shbay.common.utils;

import com.alibaba.fastjson.JSON;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import com.ust.shbay.common.dto.SmsConfig;
import com.ust.shbay.common.exception.SException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;


/**
 * @author Exrickx
 */
@Component
@Service
@Slf4j
public class SmsUtil {

    /**
     * 生成一个六位数的验证码
     * @return
     */
    public String getCode(){

        //首先定义一个字符串，用于保存需要生成的数字字母库。
        String randomNumberSize = "0123456789";

        //定义一个用于保存生成数字字母的变量
        String randomNumber = "";
        for(int i = 6; i>0; i--) {
            randomNumber+=randomNumberSize.charAt((int)(Math.random()*10));
        }
        return randomNumber;
    }



    /**
     * 发送短信
     * @param mobile 手机号
     * @param smsConfig 短信配置
     * @param code 验证码
     * @return
     * @throws ClientException
     */
    public SendSmsResponse sendSms(String mobile, SmsConfig smsConfig, String code) throws ClientException {

        if(smsConfig.getAccessKey() == null || "".equals(smsConfig.getAccessKey())
                ||smsConfig.getSecretKey() == null || "".equals(smsConfig.getSecretKey())
                ||smsConfig.getSignName() == null || "".equals(smsConfig.getSignName())
                ||smsConfig.getTemplateCode() == null || "".equals(smsConfig.getTemplateCode())){
            throw new SException("验证码发送失败，阿里云短信配置异常！");
        }
        Map<String, String> mapParam = new HashMap<>();
        mapParam.put("code", code);

        //请求失败这里会抛ClientException异常
        SendSmsResponse sendSmsResponse = sendMessage(mobile, smsConfig, mapParam);

        return sendSmsResponse;
    }


    /**
     * 公共模板
     * @param mobile 手机号
     * @param map 参数
     * @param smsConfig 短信配置
     * @return
     * @throws ClientException
     */
    public SendSmsResponse sendMessage(String mobile, SmsConfig smsConfig ,Map<String,String> map) throws ClientException {

        //缺少配置，不发短信
        if(smsConfig.getAccessKey() == null || "".equals(smsConfig.getAccessKey())
                ||smsConfig.getSecretKey() == null || "".equals(smsConfig.getSecretKey())
                ||smsConfig.getSignName() == null || "".equals(smsConfig.getSignName())
                ||smsConfig.getTemplateCode() == null || "".equals(smsConfig.getTemplateCode())){
            return null;
        }
        //设置超时时间-可自行调整
        System.setProperty("sun.net.client.defaultConnectTimeout", "10000");
        System.setProperty("sun.net.client.defaultReadTimeout", "10000");
        final String product = "Dysmsapi";
        final String domain = "dysmsapi.aliyuncs.com";
        //初始化ascClient,暂时不支持多region（请勿修改）
        IClientProfile profile = DefaultProfile.getProfile("cn-hangzhou", smsConfig.getAccessKey(), smsConfig.getSecretKey());

        DefaultProfile.addEndpoint("cn-hangzhou", product, domain);
        IAcsClient acsClient = new DefaultAcsClient(profile);
        //组装请求对象
        SendSmsRequest request = new SendSmsRequest();
        //使用post提交
        request.setSysMethod(MethodType.POST);
        //必填:待发送手机号。支持以逗号分隔的形式进行批量调用，批量上限为1000个手机号码,批量调用相对于单条调用及时性稍有延迟,验证码类型的短信推荐使用单条调用的方式；
        //发送国际/港澳台消息时，接收号码格式为00+国际区号+号码，如“0085200000000”
        request.setPhoneNumbers(mobile);
        //必填:短信签名
//        request.setSignName("贝云");
        request.setSignName(smsConfig.getSignName());

        //必填:短信模板-可在短信控制台中找到，发送国际/港澳台消息时，请使用国际/港澳台短信模版
        request.setTemplateCode(smsConfig.getTemplateCode());

        //可选:模板中的变量替换JSON串,如模板内容为"您的验证码为${code}"时,此处的值为
        //友情提示:如果JSON中需要带换行符,请参照标准的JSON协议对换行符的要求,比如短信内容中包含\r\n的情况在JSON中需要表示成\\r\\n,否则会导致JSON在服务端解析失败
        //短信模板参数
        String jsonParam = JSON.toJSONString(map);
        request.setTemplateParam(jsonParam);

        //请求失败这里会抛ClientException异常
        SendSmsResponse sendSmsResponse = acsClient.getAcsResponse(request);

        return sendSmsResponse;
    }
}
