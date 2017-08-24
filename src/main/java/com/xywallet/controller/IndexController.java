package com.xywallet.controller;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.xywallet.config.Config;
import com.xywallet.model.OrderInfo;
import com.xywallet.util.HttpUtil;
import com.xywallet.util.RequestUtil;
import com.xywallet.util.SignUtil;

@Controller
public class IndexController {

	@RequestMapping(value = "/")
	public String index(HttpServletRequest request) {
		return "redirect:/pay";
	}

	@RequestMapping(value = "/pay")
	public ModelAndView pay(HttpServletRequest request, Map<String, Object> model) {
		model.put("appId", Config.APP_ID);
		model.put("notifyUrl", Config.DEFAULT_NOTIFY_URL);
		model.put("mchTradeNo", new Date().getTime() + "");
		return new ModelAndView("pay.html");
	}

	@RequestMapping(value = "/query")
	public ModelAndView orderQuery(HttpServletRequest request, Map<String, Object> model) {
		model.put("appId", Config.APP_ID);
		return new ModelAndView("query.html");
	}

	@RequestMapping(value = "/doPay")
	@ResponseBody
	public String doPay(HttpServletRequest request, OrderInfo order) throws IOException {
		// 把请求参数打包成数组
		Map<String, String> sParaTemp = new HashMap<String, String>();
		sParaTemp.put("service", "xyw.quickpay.page.precreate");
		sParaTemp.put("signMethod", "RSA");
		sParaTemp.put("timestamp", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
		sParaTemp.put("charset", "UTF-8");// 参数编码字符集（不可以修改）
		sParaTemp.put("v", "1.1");
		sParaTemp.put("format", "json");
		// 随机字符串
		sParaTemp.put("canary", "hjgftgyhujklmknhgyuhijkadf");

		// 业务参数
		//业务参数
		sParaTemp.put("service", "xyw.quickpay.h5.precreate");
		sParaTemp.put("payType", "72");
		sParaTemp.put("appId", order.getAppId());
		sParaTemp.put("mchTradeNo", order.getMchTradeNo());
		sParaTemp.put("subject", order.getSubject());
		sParaTemp.put("body",order.getBody());
		sParaTemp.put("tradeAmt",order.getTradeAmt());
		sParaTemp.put("timestamp", new SimpleDateFormat().format(new Date()));
		sParaTemp.put("appUserName", order.getAppUserName());
		sParaTemp.put("clientIp",RequestUtil.getRemortIP(request));
		sParaTemp.put("cardNo", order.getCardNo());
		sParaTemp.put("cardRealName", order.getCardRealName());
		sParaTemp.put("phone", order.getPhone());
		sParaTemp.put("certType", order.getCertType());
		sParaTemp.put("certNo", order.getCertNo());
		sParaTemp.put("cardType", order.getCardType());
		sParaTemp.put("cvv", order.getCvv());
		sParaTemp.put("expireDate", order.getExpireDate());
		if(StringUtils.isNotBlank(order.getBankCode())){
			sParaTemp.put("bankCode", order.getBankCode());
		}
		sParaTemp.put("returnUrl", order.getReturnUrl());
		sParaTemp.put("notifyUrl", order.getNotifyUrl());

		System.out.println("请求参数sParaTemp==========" + sParaTemp);
		System.out.println("returnUrl=========" + order.getReturnUrl());
		System.out.println("notifyUrl=========" + order.getNotifyUrl());

		// 请求
		sParaTemp.put("sign", SignUtil.encryptSign(sParaTemp, Config.MERCHANT_PRIVATE_KEY, "RSA"));
		String codeUrl = HttpUtil.buildForm(Config.SERVICE_URL, sParaTemp);
		return codeUrl;
	}

	@RequestMapping(value = "/doQuery")
	@ResponseBody
	public String doQuery(HttpServletRequest request, OrderInfo order) throws IOException, URISyntaxException {
		// 把请求参数打包成数组
		Map<String, String> sParaTemp = new HashMap<String, String>();
		sParaTemp.put("service", "xyw.order.status.query");
		sParaTemp.put("signMethod", "RSA");
		sParaTemp.put("timestamp", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
		sParaTemp.put("charset", "UTF-8");// 参数编码字符集（不可以修改）
		sParaTemp.put("v", "1.1");
		sParaTemp.put("format", "json");
		// 随机字符串
		sParaTemp.put("canary", "hjgftgyhujklmknhgyuhijkadf");

		// 业务参数,当mchTradeNo和orderNo同时传递时,优先使用orderNo,mchTradeNo忽略不在使用
		sParaTemp.put("mchTradeNo", order.getMchTradeNo()); // 商户订单号
		sParaTemp.put("orderNo", order.getOrderNo()); // 沃雷特订单号
		sParaTemp.put("appId", order.getAppId());
		System.out.println("请求参数sParaTemp==========" + sParaTemp);
		// 构造函数，生成请求URL
		sParaTemp.put("sign", SignUtil.encryptSign(sParaTemp, Config.MERCHANT_PRIVATE_KEY, "RSA"));
		String resultString = HttpUtil.httpPostByDefaultTime(new URI(Config.SERVICE_URL), sParaTemp, "UTF-8");
		return resultString;
	}

	
	
}
