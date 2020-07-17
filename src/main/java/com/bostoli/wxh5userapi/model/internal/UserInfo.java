package com.bostoli.wxh5userapi.model.internal;

import com.bostoli.wxh5userapi.common.StringUtils;
import com.bostoli.wxh5userapi.model.wx.beans.UserInfoResponse;
import lombok.Data;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

@Data
@Entity
@ToString
public class UserInfo {
    @Id
    @SequenceGenerator(name= "user_generator", sequenceName = "user_seq")
    @GeneratedValue(generator = "user_generator")
    Long id;

    String openId;

    String externalId;

    String state;

    String name;

    String phone;

    String nickname;

    String sex;

    String province;

    String city;

    String country;

    String headimgurl;

    String unionid;

    long createdTime;

    long lastUpdateTime;

    public void append(UserInfoResponse response) {
        this.setNickname(StringUtils.base64Encode(response.getNickname()));
        this.setSex(response.getSex());
        this.setProvince(response.getProvince());
        this.setCity(response.getCity());
        this.setCountry(response.getCountry());
        this.setHeadimgurl(response.getHeadimgurl());
        this.setUnionid(response.getUnionid());
    }

}
