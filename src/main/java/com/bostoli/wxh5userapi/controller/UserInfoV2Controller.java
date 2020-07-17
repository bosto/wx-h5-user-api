package com.bostoli.wxh5userapi.controller;

import com.bostoli.wxh5userapi.model.internal.UserInfo;
import com.bostoli.wxh5userapi.model.view.UserInfoView;
import com.bostoli.wxh5userapi.service.UserInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
public class UserInfoV2Controller {
    @Autowired
    UserInfoService userInfoService;

    @GetMapping("/v2/user")
    public UserInfoView getUserProfile(@RequestParam("code") String jsCode,
                                       @RequestParam("state") String state) {
        return userInfoService.getOrNewUserV2(jsCode, state);
    }

    @GetMapping("/cam10/v2/user")
    public UserInfoView getUser(@RequestAttribute("MID_CUSTOMER") UserInfo userInfo) {
        return UserInfoView.valueOf(userInfo);
    }

}
