package com.xywallet.controller;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.xywallet.config.Config;
import com.xywallet.util.SignUtil;
import com.xywallet.util.RequestUtil;



@Controller
@RequestMapping("/notify")
public class NotifyController {

    @RequestMapping(value = "/quickpay")
    @ResponseBody
    public String quickPayNotify(HttpServletRequest request) {
        String result = RequestUtil.getRequestBody(request);
        System.out.println("#quickPayNotify,接收到quickpay通知信息为:" + result);
        if(StringUtils.isEmpty(result)){
        	return "fail";
        }
        @SuppressWarnings("unchecked")
		HashMap<String,String> map = JSON.parseObject(result, HashMap.class);
        if(SignUtil.verifySign(map, Config.MERCHANT_PUBLIC_KEY)){
        	System.out.println("验签成功");
        }else{
        	System.out.println("验签失败");
        }
        return "success";
    }
}
