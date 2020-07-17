package com.bostoli.wxh5userapi.common;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.bostoli.wxh5userapi.model.internal.UserInfo;

import java.util.Date;

import static com.bostoli.wxh5userapi.common.AESTools.*;
import static com.bostoli.wxh5userapi.common.AESTools.DEFAULT_KEY;
import static com.bostoli.wxh5userapi.common.DateTools.offSetMin;


public class JwtTokenUtils {

    private static String JWT_KEY = new String(DEFAULT_KEY.getEncoded());


    public static String nextToken(UserInfo midCustomer) {
        return tokenOf(midCustomer, offSetMin(new Date(), 30));
    }

    public static String tokenOf(UserInfo midCustomer, Date expiredDate) {
        String encodedJsCode = encode(DEFAULT_KEY, midCustomer.getOpenId());
        String token="";
        token= JWT.create().withClaim("openId",encodedJsCode)
                .withExpiresAt(expiredDate)
                .sign(Algorithm.HMAC256(JWT_KEY));
        return token;
    }

    public static String toOpenId(String jwtToken) {
        return decode(DEFAULT_KEY,JWT.decode(jwtToken).getClaim("openId").asString());
    }

    public static void main(String[] args) {
        UserInfo midCustomer = new UserInfo();
        midCustomer.setOpenId("o0FsX52lxoGWEHwTnQFicpsPvqTU");
        midCustomer.setExternalId("dad");
        String jwt = tokenOf(midCustomer, new Date());
        System.out.println(jwt);
        System.out.println(toOpenId(jwt));
    }






}
