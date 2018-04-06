/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.vip.web;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.vip.service.VipUserBaseService;
import com.thinkgem.jeesite.modules.vip.utils.WeChatUtils;

/**
 * 微信API接口请求端
 * 
 * @author swbssd
 * @version 2017-09-17
 */
@Controller
@RequestMapping(value = "${frontPath}/api/wechat")
public class WechatApiController extends BaseController {

	@Autowired
	private VipUserBaseService vipUserBaseService;


	/**
	 * 获取OpenId
	 * https://api.weixin.qq.com/sns/jscode2session
	 * ?appid=APPID&secret=SECRET&js_code=JSCODE&grant_type=authorization_code
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "getOpenId")
	public String getOpenId(HttpServletRequest request) {
		String jsCode = request.getParameter("jsCode");
		try {
			return WeChatUtils.getOpenIdByLoginCode(jsCode);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "error";
	}

}