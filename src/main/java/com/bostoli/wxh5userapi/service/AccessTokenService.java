package com.bostoli.wxh5userapi.service;


import com.bostoli.wxh5userapi.client.WechatClient;
import com.bostoli.wxh5userapi.common.configuration.WechatConfiguration;
import com.bostoli.wxh5userapi.model.view.WXShareResponse;
import com.bostoli.wxh5userapi.model.wx.beans.AccessTokenResponse;
import com.bostoli.wxh5userapi.model.wx.beans.JsTicket;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import static com.bostoli.wxh5userapi.common.DateTools.nowString;
import static com.bostoli.wxh5userapi.common.SHA1.getJsSdk;
import static com.bostoli.wxh5userapi.common.SHA1.getRandomStr;

@Service
@CacheConfig(cacheNames = "token")
@Slf4j
public class AccessTokenService {

    WechatClient wechatClient;

    WechatConfiguration wechatConfiguration;

    Integer failCount = 0;

    Integer maxFailCount = 5;

    public AccessTokenService(WechatClient wechatClient, WechatConfiguration wechatConfiguration) {
        this.wechatClient = wechatClient;
        this.wechatConfiguration = wechatConfiguration;
    }
    //value = "emp" ,key = "targetClass + methodName +#p0

    public String getAccessToken() {
        return getAccessToken(wechatConfiguration.getAppid(), wechatConfiguration.getSecret());

    }

    @Cacheable(key = "#appId + #secret")
    public String getAccessToken(String appId, String secret) {
        AccessTokenResponse accessTokenResponse =
                wechatClient.accessToken(appId, secret,"client_credential");

        if (accessTokenResponse.success()) {
            log.info("Get access token success [{}] [{}]",
                    accessTokenResponse.getAccess_token(),
                    accessTokenResponse.getExpires_in()
            );
            return accessTokenResponse.getAccess_token();
        } else {
            failCount ++;
            log.error("The {} time get access token fail [{}] [{}]",
                    failCount,
                    accessTokenResponse.getErrcode(),
                    accessTokenResponse.getErrmsg());
            if (failCount >= maxFailCount) {
                throw new RuntimeException("Get access token fail with 5 times retry");
            }
            return getAccessToken();
        }
    }

    @CacheEvict(key = "#appId + #secret")
    public void cleanGetAccessToken(String appId, String secret) {
        log.info("Clean AccessToken");
    }

    @Scheduled(cron="0 0 0/1 * * ?")
    public void scheduleGetAccessToken() {
        cleanGetAccessToken(
                wechatConfiguration.getAppid(), wechatConfiguration.getSecret()
        );
    }



    // ------------------------
    public String getJSTicketToken() {
        return getJSTicketToken(getAccessToken(), "jstoken");
    }

    @CacheEvict(key = "#key")
    public void cleanJSTicketToken(String key) {
        log.info("Clean JS AccessToken");
    }


    @Scheduled(cron="0 0 0/1 * * ?")
    public void scheduleJSTicketToken() {
        cleanJSTicketToken(
                "jstoken"
        );
    }

    @Cacheable(key = "#key")
    public String getJSTicketToken(String accessToken, String key) {
        JsTicket jsTicket =
                wechatClient.jsTicket(accessToken,"jsapi");

        if (jsTicket.success()) {
            log.info("Get js key success [{}] [{}]",
                    jsTicket.getTicket(),
                    jsTicket.getExpires_in()
            );
            return jsTicket.getTicket();
        } else {
            failCount ++;
            log.error("The {} time get js key token fail [{}] [{}]",
                    failCount,
                    jsTicket.getErrcode(),
                    jsTicket.getErrmsg());
            if (failCount >= maxFailCount) {
                throw new RuntimeException("Get js key token fail with 5 times retry");
            }
            return getJSTicketToken();
        }
    }


    public WXShareResponse getShare(String url) {
        String nonce = getRandomStr();
        String timestamp = nowString();
        String jsTicket = getJSTicketToken();
        WXShareResponse response  = new WXShareResponse();
        response.setAppcode("1");
        response.setAppid(wechatConfiguration.getAppid());
        response.setUrl(url);
        response.setAppmsg("Get signature success!");
        response.setNonceStr(nonce);
        response.setTimestamp(timestamp);
        response.setJsapi_ticket(jsTicket);
        String sign = null;
        try {
            sign = getJsSdk(jsTicket,timestamp, nonce, url);
        } catch (Exception e) {
            log.error("js ticket 签名失败");
        }
        response.setSignature(sign);
        return response;
    }
}
