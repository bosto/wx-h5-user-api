package com.bostoli.wxh5userapi.service;

import com.bostoli.wxh5userapi.client.WechatClient;
import com.bostoli.wxh5userapi.common.DateTools;
import com.bostoli.wxh5userapi.common.JwtTokenUtils;
import com.bostoli.wxh5userapi.common.configuration.WechatConfiguration;
import com.bostoli.wxh5userapi.dao.UserInfoRepository;
import com.bostoli.wxh5userapi.model.internal.UserInfo;
import com.bostoli.wxh5userapi.model.view.UserInfoView;
import com.bostoli.wxh5userapi.model.wx.beans.AccessTokenResponse;
import com.bostoli.wxh5userapi.model.wx.beans.UserInfoResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.Date;

import static com.bostoli.wxh5userapi.common.DateTools.offSetDate;
import static com.bostoli.wxh5userapi.common.JwtTokenUtils.tokenOf;
import static com.bostoli.wxh5userapi.common.StringUtils.base64Encode;


@Service
@Slf4j
@CacheConfig(cacheNames = "user")
public class UserInfoService {
    UserInfoRepository userInfoRepository;

    WechatClient wechatClient;

    String appid;

    String secret;

    public UserInfoService(UserInfoRepository userInfoRepository, WechatClient wechatClient,
                           WechatConfiguration wechatConfiguration) {
        this.wechatClient = wechatClient;
        this.userInfoRepository = userInfoRepository;
        this.appid = wechatConfiguration.getAppid();
        this.secret = wechatConfiguration.getSecret();
    }

    public UserInfo findByUserId(Long id) {
        return userInfoRepository.findById(id).get();
    }

    public AccessTokenResponse getAccessToken(String code, String state) {
        AccessTokenResponse token = null;
        try {
            token = wechatClient.code2openId(appid, secret, code, "authorization_code");
        } catch (Exception e) {
            log.error("Get user access token fail");
        }
        return token;
    }

    public String getOpenId(String code, String state) {
        AccessTokenResponse token = getAccessToken(code,state);
        String openId = token.getOpenid();
        return openId;
    }


    @Cacheable(key = "#openId")
    public UserInfo findUserInfoByOpenId(String openId) {
        return userInfoRepository.findByOpenId(openId);
    }

    public UserInfoView getOrNewUserV2(String code, String state) {
        AccessTokenResponse token = getAccessToken(code, state);
        UserInfo userInfo = userInfoRepository.findByOpenId(token.getOpenid());
        if (userInfo ==null) {
            userInfo = new UserInfo();
            userInfo.setOpenId(token.getOpenid());
            long now = DateTools.now();
            userInfo.setCreatedTime(now);
            userInfo.setLastUpdateTime(now);
            userInfo = userInfoRepository.save(userInfo);
            userInfo.setExternalId(base64Encode(String.valueOf(userInfo.getId())  + ":" + token.getOpenid()));
        }
        UserInfoResponse userInfoResponse =
                wechatClient.userInfo(token.getAccess_token(), token.getOpenid(),"zh_CN");
        userInfo.append(userInfoResponse);
        userInfo.setLastUpdateTime(DateTools.now());
        userInfo = userInfoRepository.save(userInfo);
        log.info("Get User is {}", userInfo.toString());
        UserInfoView response = UserInfoView.valueOf(userInfo);
        response.setToken(tokenOf(userInfo, offSetDate(new Date(), 4)));
        return response;
    }


    public UserInfoView getOrNewUser(String code, String state) {
        String openId = getOpenId(code, state);
        UserInfo userInfo = userInfoRepository.findByOpenId(openId);

        if (userInfo ==null) {
            userInfo = new UserInfo();
            userInfo.setOpenId(openId);
            long now = DateTools.now();
            userInfo.setCreatedTime(now);
            userInfo.setLastUpdateTime(now);
            userInfo = userInfoRepository.save(userInfo);
            userInfo.setExternalId(base64Encode(String.valueOf(userInfo.getId())  + ":" + openId));
            userInfo = userInfoRepository.save(userInfo);
        }
        log.info("Get User is {}", userInfo.toString());
        UserInfoView response = UserInfoView.valueOf(userInfo);
        response.setToken(tokenOf(userInfo, offSetDate(new Date(), 4)));
        return response;
    }

    public UserInfoView getOrNewUserProfile(String code, String state) {
        String openId = getOpenId(code, state);
        UserInfo userInfo = userInfoRepository.findByOpenId(openId);

        if (userInfo ==null) {
            userInfo = new UserInfo();
            userInfo.setOpenId(openId);
            long now = DateTools.now();
            userInfo.setCreatedTime(now);
            userInfo.setLastUpdateTime(now);
            userInfo = userInfoRepository.save(userInfo);
            userInfo.setExternalId(base64Encode(String.valueOf(userInfo.getId())  + ":" + openId));
            userInfo = userInfoRepository.save(userInfo);
        }
        log.info("Get User is {}", userInfo.toString());
        UserInfoView response = UserInfoView.valueOf(userInfo);
        response.setToken(tokenOf(userInfo, offSetDate(new Date(), 4)));
        return response;
    }


    public UserInfo authUser(String jwt) {
        String openId = JwtTokenUtils.toOpenId(jwt);
        UserInfo userInfo = userInfoRepository.findByOpenId(openId);

        if (userInfo!=null) {
            return userInfo;
        } else {
            log.error("user not found fail");
            throw new RuntimeException("User not found in db");
        }
    }

}
