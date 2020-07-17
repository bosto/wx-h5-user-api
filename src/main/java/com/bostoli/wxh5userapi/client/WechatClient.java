package com.bostoli.wxh5userapi.client;

import com.bostoli.wxh5userapi.common.configuration.WechatClientConfiguration;
import com.bostoli.wxh5userapi.model.wx.beans.AccessTokenResponse;
import com.bostoli.wxh5userapi.model.wx.beans.JsTicket;
import com.bostoli.wxh5userapi.model.wx.beans.UserInfoResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(
        url = "${wechat.client.url}",
        name = "wechat-client",
        configuration = {WechatClientConfiguration.class})
public interface WechatClient {

    @GetMapping("/sns/oauth2/access_token")
    public AccessTokenResponse code2openId (
            @RequestParam("appid") String appId,
            @RequestParam("secret") String secret,
            @RequestParam("code") String jsCode,
            @RequestParam(value = "grant_type", defaultValue = "authorization_code") String grantType
    );

    @GetMapping("/cgi-bin/token")
    public AccessTokenResponse accessToken (
            @RequestParam("appid") String appId,
            @RequestParam("secret") String secret,
            @RequestParam(value = "grant_type", defaultValue = "client_credential") String grantType
    );

    @GetMapping("/cgi-bin/ticket/getticket")
    public JsTicket jsTicket (
            @RequestParam("access_token") String accessToken,
            @RequestParam(value = "type",defaultValue = "jsapi") String jsapi
    );

    @GetMapping("/sns/userinfo") //?access_token=ACCESS_TOKEN&openid=OPENID&lang=zh_CN
    public UserInfoResponse userInfo(
            @RequestParam("access_token") String accessToken,
            @RequestParam("openid") String openid,
            @RequestParam(value = "lang", defaultValue = "zh_CN") String lang);


}
