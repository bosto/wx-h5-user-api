package com.bostoli.wxh5userapi.common;

import java.nio.charset.Charset;
import java.util.Base64;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtils {
    private static Base64.Encoder encoder = Base64.getEncoder();

    private static Base64.Decoder decoder = Base64.getDecoder();

    private static Charset DEFAULT_CHARSET = Charset.forName("UTF-8");

    public static String base64Encode(String original) {
        if (original == null || original.equals("")) {
            return "";

        }
        return encoder.encodeToString(original.getBytes(DEFAULT_CHARSET));
    }

    public static String base64Decode(String original) {
        if (original == null || original.equals("")) {
            return "";

        }
        return new String(decoder.decode(original),DEFAULT_CHARSET);
    }



    public static String filter(String str) {
        String regEx="[`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(str);
        return m.replaceAll("").trim();
    }


}
