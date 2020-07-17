package com.bostoli.wxh5userapi.model.wx.beans;

import lombok.Data;

@Data
public class JsTicket extends WechatBasicResponse{
    String ticket;
    Long expires_in;
}
