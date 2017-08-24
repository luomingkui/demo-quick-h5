package com.xywallet.util;

import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Arrays;
import java.util.HashMap;

import org.apache.commons.lang3.StringUtils;

import com.alibaba.fastjson.JSON;

public class RSAUtil {

	public static String sign(String content, String privateKey, String input_charset) {
		try {
			PKCS8EncodedKeySpec priPKCS8 = new PKCS8EncodedKeySpec(Base64Util.decode(privateKey));
			KeyFactory keyf = KeyFactory.getInstance("RSA");
			PrivateKey priKey = keyf.generatePrivate(priPKCS8);

			Signature signature = Signature.getInstance("SHA1WithRSA");

			signature.initSign(priKey);
			signature.update(content.getBytes(input_charset));
			byte[] signed = signature.sign();

			return Base64Util.encode(signed);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	public static boolean verify(String content, String sign, String public_key, String input_charset) {
		try {
			KeyFactory keyFactory = KeyFactory.getInstance("RSA");
			byte[] encodedKey = Base64Util.decode(public_key);
			PublicKey pubKey = keyFactory.generatePublic(new X509EncodedKeySpec(encodedKey));

			Signature signature = Signature.getInstance("SHA1WithRSA");

			signature.initVerify(pubKey);
			signature.update(content.getBytes(input_charset));

			return signature.verify(Base64Util.decode(sign));
		} catch (Exception e) {
			e.printStackTrace();
		}

		return false;
	}

	public static void main(String[] args) {
		String prikey = "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAMm9ciulonsadVwZbUfEtX6cuvqfmdw+TYmAyGjyCKUUhJjhdrsYpw5YugkNRRrJId6XT07b+Car3YWSkroB09y36cyQxFZGoIJT+teBP0yaF7xXxfb1igqwOUX7Esc3HEnnx5kN2o1LGYdnTc0h14d/3hMVlD47yC863s2lfVH3AgMBAAECgYBWJfIlNVwHGatYIvuJlDS01pT6BUGczXYq67WpKwIKDJL6/fzHtKVR+Q2oY+RIpe8ysIaLdF+pk+bK7WZ+y9z+N8MYKYm6YsKl3Gv3h3GiRZNuwBNLiIQ7rPuZsH3KDpbI+/oHwzyBZg31r0jmivYz5mHV4eAzr+NXxxKz6+2XUQJBAOyjxuvJYLLdoKhSJjjSH4e/KXD9NROFo0amCWA3KlU5MhLeALXL00xdAlpUfperuEzm6KPSRUsEddbdfSQY+6sCQQDaPrkce7zNw8pjwwA/ucwrx09FGAOnlea3GNszxWXZFF10eJJi9XSdpsT/hZM4S3hLC0BKg7Nh8ggSssipCpblAkEA5t6mXTYcPIWTj4SOO9Ns5aBwSwcOeFxiPrQQ46kOiRUDMHsdUEBqhs8bU4up8kmbxpNllya3aggQ+68dv1EMfwJAeIQ2eHIj+oRp2Nkv5ADXrD0tmvv92KdAdk91jIsvCMbkLJMAU0E0GnKjSm8asvWKAfa+k+dSQX4D0z+C60X7uQJBAMjhjZS1S1F1JchKk1Pif2tcvzHly40suDBEZ6FKzjhhgsF9vCxVBctKiXaaOy2LqXeqZ0vQJ1lgnuvsFkfPkX0=";
		String publickey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDJvXIrpaJ7GnVcGW1HxLV+nLr6n5ncPk2JgMho8gilFISY4Xa7GKcOWLoJDUUaySHel09O2/gmq92FkpK6AdPct+nMkMRWRqCCU/rXgT9Mmhe8V8X29YoKsDlF+xLHNxxJ58eZDdqNSxmHZ03NIdeHf94TFZQ+O8gvOt7NpX1R9wIDAQAB";
		String ss = "{\"endTime\":\"2017-03-15 12:16:18\",\"mchTradeNo\":\"test1231488256033366\",\"orderNo\":\"1488256033650275764\",\"payType\":\"71\",\"sign\":\"C9GdqPZ3hgRk+i0p6GqKVLGPFfxpY/NHrnJuf+iwtPUUk9XXxx72Cyvt00Z6GnLpGk6UvSTXmgkChpJ78sZDJhK52laeI8cDXxcU6xYzxbVsJf0AJZv3cGlWXJFMlt4AgY3fQbyJBYfJvQBipm8lN0WR76MT3Li5mRcmmBnDHio=\",\"subject\":\"dfgdfsg\",\"tradeAmt\":\"1.00\",\"tradeStatus\":\"02\"}";
		@SuppressWarnings("unchecked")
		HashMap<String, String> map = JSON.parseObject(ss, HashMap.class);
		map.remove("sign");
		// 第一步：检查参数是否已经排序
		String[] keys = map.keySet().toArray(new String[0]);
		Arrays.sort(keys);

		// 第二步：把所有参数名和参数值串在一起
		StringBuilder query = new StringBuilder();
		boolean hasParam = false;
		for (String key : keys) {
			String value = map.get(key);
			// 除sign、signType字段
			if (StringUtils.isNotEmpty(key) && StringUtils.isNotEmpty(value) && !key.equals("sign")
					&& !key.equals("signMethod")) {
				if (hasParam) {
					query.append("&");
				} else {
					hasParam = true;
				}
				query.append(key).append("=").append(value);
			}
		}
		String content = query.toString();
		System.out.println(content);
		String sign = sign(content, prikey, "utf-8");
		System.out.println(sign);
		System.out.println(verify(content, sign, publickey, "utf-8"));
	}
}