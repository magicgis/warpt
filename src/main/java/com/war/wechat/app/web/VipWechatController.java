/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.war.wechat.app.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.thinkgem.jeesite.common.utils.MessageUtil;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import com.thinkgem.jeesite.modules.vip.entity.VipUserBase;
import com.thinkgem.jeesite.modules.vip.service.VipUserBaseService;

/**
 * 微信小程序端查询VIP数据
 * 
 * @author swbssd
 * @version 2017-09-17
 */
@Controller
@RequestMapping(value = "${wechatPath}/vip/vipWechat")
public class VipWechatController extends BaseController {

	@Autowired
	private VipUserBaseService vipUserBaseService;


	/**
	 * 
	 * 
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "sendMessge")
	public Map<String, Object> sendMessge(HttpServletRequest request) {
		String id = request.getParameter("id");
		Map<String, Object> returnMap = new HashMap<String, Object>();
		
		return returnMap;
	}

}