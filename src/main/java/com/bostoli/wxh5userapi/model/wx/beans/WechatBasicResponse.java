package com.bostoli.wxh5userapi.model.wx.beans;

import lombok.Data;

@Data
public class WechatBasicResponse {

    int errcode = 0;
    String errmsg;

    public boolean success() {
        return errcode == 0 || errmsg == null;
    }
}
