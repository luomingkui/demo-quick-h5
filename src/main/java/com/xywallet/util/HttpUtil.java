package com.xywallet.util;

import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

public class HttpUtil {

	public static String httpPostByDefaultTime(URI url, Map<String, String> maps, String charset) throws IOException {
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		for (String key : maps.keySet()) {
			params.add(new BasicNameValuePair(key, maps.get(key)));
		}
		return httpPost(url, params, 30000, 30000, charset);
	}

	public static String httpPostByMap(URI url, Map<String, String> maps, int socketTimeout, int connectTimeout,
			String charset) throws IOException {
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		for (String key : maps.keySet()) {
			params.add(new BasicNameValuePair(key, maps.get(key)));
		}
		return httpPost(url, params, socketTimeout, connectTimeout, charset);
	}

	public static String httpPost(URI uri, List<NameValuePair> param, int socketTimeout, int connectTimeout,
			String charset) throws IOException {
		String result = "";
		CloseableHttpClient client = HttpClientBuilder.create().build();
		try {
			HttpPost httpost = new HttpPost(uri);
			httpost.setHeader("Content-Type", "application/x-www-form-urlencoded;charset=" + charset);
			HttpEntity entity = null;
			if (param == null) {
				entity = new StringEntity("");
			} else {
				entity = new UrlEncodedFormEntity(param, "UTF-8");
			}
			httpost.setEntity(entity);
			RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(socketTimeout)
					.setConnectTimeout(connectTimeout).build();
			httpost.setConfig(requestConfig);
			HttpResponse response = client.execute(httpost);
			HttpEntity responseEntity = response.getEntity();
			result = EntityUtils.toString(responseEntity, "utf-8");
			client.close();
		} finally {
			if (client != null) {
				client.close();
			}
		}
		System.out.println("Response:,{}"+result);
		return result;
	}
	
	
	/**
	 * 构造提交表单HTML数据
	 * 
	 * @param sParaTemp
	 *            请求参数数组
	 * @param url
	 *            网关地址
	 * @return 提交表单HTML文本
	 */
	public static String buildForm(String url,Map<String, String> sParaTemp){
		List<String> keys = new ArrayList<String>(sParaTemp.keySet());
		StringBuffer sbHtml = new StringBuffer();
		sbHtml.append("<form id=\"submitform\" name=\"submitform\" action=\"" + url + "\" method=\"post\">");

		for (int i = 0; i < keys.size(); i++) {
			String name = (String) keys.get(i);
			String value = (String) sParaTemp.get(name);

			sbHtml.append("<input type=\"hidden\" name=\"" + name + "\" value=\"" + value + "\"/>");
		}

		// submit按钮控件请不要含有name属性
		sbHtml.append("<input type=\"submit\"  style=\"display:none;\"></form>");
		sbHtml.append("<script>document.forms['submitform'].submit();</script>");
		return sbHtml.toString();
	}
}
