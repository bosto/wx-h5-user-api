package com.bostoli.wxh5userapi.model.view;

import com.bostoli.wxh5userapi.model.internal.UserInfo;
import lombok.Data;

@Data
public class UserInfoView {
    String token;
    Long id;
    String name;
    String phone;

    String nickname;

    String sex;

    String province;

    String city;

    String country;

    String headimgurl;

    String unionid;

    public static com.bostoli.wxh5userapi.model.view.UserInfoView valueOf(UserInfo userInfo) {
        com.bostoli.wxh5userapi.model.view.UserInfoView userInfoView = new com.bostoli.wxh5userapi.model.view.UserInfoView();
        userInfoView.setName(userInfo.getName());
        userInfoView.setPhone(userInfo.getPhone());
        userInfoView.setId(userInfo.getId());
        return userInfoView;
    }
}
