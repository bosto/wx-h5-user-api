package com.bostoli.wxh5userapi.controller;

import com.bostoli.wxh5userapi.model.internal.UserInfo;
import com.bostoli.wxh5userapi.model.view.UserInfoView;
import com.bostoli.wxh5userapi.model.view.WXShareResponse;
import com.bostoli.wxh5userapi.service.AccessTokenService;
import com.bostoli.wxh5userapi.service.UserInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.net.URLDecoder;

@RestController
@Slf4j
public class UserInfoController {
    @Autowired
    UserInfoService userInfoService;

    @Autowired
    AccessTokenService accessTokenService;

    @GetMapping("/user")
    public UserInfoView getUser(@RequestParam("code") String jsCode,
                                @RequestParam("state") String state) {
        return userInfoService.getOrNewUser(jsCode, state);
    }

    @GetMapping("/cam10/user")
    public UserInfoView getUser(@RequestAttribute("MID_CUSTOMER") UserInfo userInfo) {
        return UserInfoView.valueOf(userInfo);
    }

    @GetMapping("/wxshare")
    public WXShareResponse wxshare(@RequestParam("url") String url) {
        String urlString = url;
        try {
            urlString = URLDecoder.decode(url, "UTF-8");
        } catch (Exception e) {
            log.error("Url decode fail for string {}", url);
        }

        return accessTokenService.getShare(urlString);
    }

//    @PostMapping("/user/{id}")
//    public UserInfoView User(@RequestParam("code") String jsCode,
//                             @RequestParam("state") String state) {
//        return UserInfoView.valueOf(userInfo);
//    }





}
