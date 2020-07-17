/**
 * 对公众平台发送给公众账号的消息加解密示例代码.
 * 
 * @copyright Copyright (c) 1998-2014 Tencent Inc.
 */

// ------------------------------------------------------------------------

package com.bostoli.wxh5userapi.common;

import lombok.extern.slf4j.Slf4j;

import java.security.MessageDigest;
import java.text.MessageFormat;
import java.util.Arrays;
import java.util.Random;

/**
 * SHA1 class
 *
 * 计算公众平台的消息签名接口.
 */
@Slf4j
public class SHA1 {
	private static String pattern = "jsapi_ticket={0}&noncestr={1}&timestamp={2}&url={3}";

	/**
	 * 用SHA1算法生成安全签名
	 * @param token 票据
	 * @param timestamp 时间戳
	 * @param nonce 随机字符串
	 * @param encrypt 密文
	 * @return 安全签名
	 * @throws com.bostoli.wxh5userapi.common.AesException
	 */
	public static String getSHA1(String token, String timestamp, String nonce, String encrypt) throws com.bostoli.wxh5userapi.common.AesException
			  {
		try {
			String[] array = new String[] { token, timestamp, nonce };
			StringBuffer sb = new StringBuffer();
			// 字符串排序
			Arrays.sort(array);
			for (int i = 0; i < 3; i++) {
				sb.append(array[i]);
			}
			String str = sb.toString();
			// SHA1签名生成
			MessageDigest md = MessageDigest.getInstance("SHA-1");
			md.update(str.getBytes());
			byte[] digest = md.digest();

			StringBuffer hexstr = new StringBuffer();
			String shaHex = "";
			for (int i = 0; i < digest.length; i++) {
				shaHex = Integer.toHexString(digest[i] & 0xFF);
				if (shaHex.length() < 2) {
					hexstr.append(0);
				}
				hexstr.append(shaHex);
			}
			return hexstr.toString();
		} catch (Exception e) {
			e.printStackTrace();
			throw new com.bostoli.wxh5userapi.common.AesException(com.bostoli.wxh5userapi.common.AesException.ComputeSignatureError);
		}
	}
	public static String getRandomStr() {
		String base = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
		Random random = new Random();
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < 16; i++) {
			int number = random.nextInt(base.length());
			sb.append(base.charAt(number));
		}
		return sb.toString();
	}

	public static String getJsSdk(String jsTicket, String timestamp, String nonce, String url) throws com.bostoli.wxh5userapi.common.AesException
	{
		try {
			String plain = MessageFormat.format(pattern, jsTicket, nonce, timestamp, url);
			log.info("Plain for sign is {}", plain);
			MessageDigest md = MessageDigest.getInstance("SHA-1");
			md.update(plain.getBytes());
			byte[] digest = md.digest();
			StringBuffer hexstr = new StringBuffer();
			String shaHex = "";
			for (int i = 0; i < digest.length; i++) {
				shaHex = Integer.toHexString(digest[i] & 0xFF);
				if (shaHex.length() < 2) {
					hexstr.append(0);
				}
				hexstr.append(shaHex);
			}
			return hexstr.toString();
		} catch (Exception e) {
			e.printStackTrace();
			throw new com.bostoli.wxh5userapi.common.AesException(com.bostoli.wxh5userapi.common.AesException.ComputeSignatureError);
		}
	}


	public static void main(String[] args) throws Exception{
		String str = "jsapi_ticket=LIKLckvwlJT9cWIhEQTwfIB6vm98NswH37ERxlBYo3FEqU9mbvWNNckg7GcsNSAhB-PMUHLUIB1rvklEYi38GA&noncestr=YeT5hdTlyZddJRrP&timestamp=1592731037&url=https://g.bostoli.com/share.html?state=9";
		MessageDigest md = MessageDigest.getInstance("SHA-1");
		md.update(str.getBytes());
		byte[] digest = md.digest();
		StringBuffer hexstr = new StringBuffer();
		String shaHex = "";
		for (int i = 0; i < digest.length; i++) {
			shaHex = Integer.toHexString(digest[i] & 0xFF);
			if (shaHex.length() < 2) {
				hexstr.append(0);
			}
			hexstr.append(shaHex);
		}
		System.out.println(hexstr.toString());
	}



}
