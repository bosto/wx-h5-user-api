package com.bostoli.wxh5userapi.model.view;

import lombok.Data;

@Data
public class WXShareResponse {
    String appmsg;
    String signature;
    String appid;
    String jsapi_ticket;
    String appcode;
    String url;
    String nonceStr;
    String timestamp;
}
