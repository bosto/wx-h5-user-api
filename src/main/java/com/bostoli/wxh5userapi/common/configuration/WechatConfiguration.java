package com.bostoli.wxh5userapi.common.configuration;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@ConfigurationProperties("wechat.client")
@Configuration
@Data
public class WechatConfiguration {
    String appid;
    String secret;
    String url;

}
