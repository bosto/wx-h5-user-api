package com.bostoli.wxh5userapi.model.wx.beans;

import lombok.Data;

@Data
public class AccessTokenResponse extends WechatBasicResponse{
    String access_token;
    Long expires_in;
    String refresh_token;
    String openid;
    String scope;
}
