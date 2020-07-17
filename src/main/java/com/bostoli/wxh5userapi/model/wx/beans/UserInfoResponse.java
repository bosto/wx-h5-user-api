package com.bostoli.wxh5userapi.model.wx.beans;

import lombok.Data;

@Data
public class UserInfoResponse {
    String openid;
    String nickname;
    String sex;
    String province;
    String city;
    String country;
    String headimgurl;
    String[] privilege;
    String unionid;
}
