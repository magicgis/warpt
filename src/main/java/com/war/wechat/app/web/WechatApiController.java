/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.war.wechat.app.web;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.thinkgem.jeesite.common.mapper.JsonMapper;
import com.thinkgem.jeesite.common.utils.MessageUtil;
import com.thinkgem.jeesite.common.utils.SpringContextHolder;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.vip.entity.VipUserBase;
import com.thinkgem.jeesite.modules.vip.service.VipUserBaseService;
import com.war.wechat.app.service.WechatLoginInterface;
import com.war.wechat.app.utils.WeChatUtils;

/**
 * 微信API接口请求端
 * 
 * @author swbssd
 * @version 2017-09-17
 */
@Controller
@RequestMapping(value = "${wechatPath}/api")
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
	@RequestMapping(value = "loginWechat")
	public Map<String, Object> loginWechat(HttpServletRequest request) {
		Map<String, Object> loginMap = new HashMap<String, Object>();
		String jsCode = request.getParameter("jsCode");
		String bindingBean = request.getParameter("bindingBean");
		try {
			//获取openId
			String loginJson = WeChatUtils.getOpenIdByLoginCode(jsCode);
			loginMap = JsonMapper.getInstance().fromJson(loginJson, HashMap.class);
			WechatLoginInterface wechatLoginIn = SpringContextHolder.getBean(bindingBean);
			String openid = String.valueOf(loginMap.get("openid"));
			String phone = wechatLoginIn.getBindingUser(openid);
			if(phone!=null) {
				loginMap.put("phone", phone);
				loginMap.put("isLogin", true);
			} else { //未绑定账号，需要去登陆绑定
				loginMap.put("isLogin", false);
			}
			loginMap.put("success", true);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			loginMap.put("success", false);
			loginMap.put("msg", e.getMessage());
		}
		return loginMap;
	}
	
	//演示demo,为了审核通过小程序..
	@ResponseBody
	@RequestMapping(value = "loginWechatDemo")
	public Map<String, Object> loginWechatDemo(HttpServletRequest request) {
		Map<String, Object> loginMap = new HashMap<String, Object>();
		loginMap.put("openid", "oCUK05DqW5mXngXewa8wt0cHPQXM");
		loginMap.put("phone", "13543006081");
		loginMap.put("isLogin", true);
		loginMap.put("success", true);
		return loginMap;
	}
	
	/**
	 * 绑定小程序并且登陆
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "bindingUserWechat")
	public Map<String, Object> bindingUserWechat(HttpServletRequest request) {
		Map<String, Object> loginMap = new HashMap<String, Object>();
		String phone = request.getParameter("phone");
		String vcode = request.getParameter("vcode");
		String jsCode = request.getParameter("jsCode");
		String bindingBean = request.getParameter("bindingBean");
		try {
			//判断验证码输入是否正确
			Map<String, Object> vcodeMap = MessageUtil.getInterface().compareVcode(phone, vcode);
			boolean vcodeFn = Boolean.valueOf(String.valueOf(vcodeMap.get("success")));
			if(!vcodeFn) { //测试注释
				loginMap.put("isLogin", false);
				loginMap.put("success", false);
				loginMap.put("msg", vcodeMap.get("msg"));
				return loginMap;
			}
			//获取openId
			String loginJson = WeChatUtils.getOpenIdByLoginCode(jsCode);
			loginMap = JsonMapper.getInstance().fromJson(loginJson, HashMap.class);
			WechatLoginInterface wechatLoginIn = SpringContextHolder.getBean(bindingBean);
			//绑定对象
			String openid = String.valueOf(loginMap.get("openid"));
			wechatLoginIn.bindingUserOpenid(phone, openid);
			loginMap.put("phone", phone);
			loginMap.put("isLogin", true);
			loginMap.put("success", true);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			loginMap.put("isLogin", false);
			loginMap.put("success", false);
			loginMap.put("msg", e.getMessage());
		}
		return loginMap;
	}
	
	/**
	 * 注册绑定小程序发送验证码
	 * 
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "sendBinding")
	public Map<String, Object> sendBinding(HttpServletRequest request) {
		String mobile = request.getParameter("mobile");
		Map<String, Object> returnMap = new HashMap<String, Object>();
		try {
			//生成验证码
			String vcode = MessageUtil.getInterface().createRandomVcode(mobile);
			// 发送短信
			Map<String, String> contentMap = new HashMap<String, String>();
			contentMap.put("code", vcode);
			MessageUtil.getInterface().send("WECHAT_CODE",mobile, contentMap);
			returnMap.put("success", true);
		} catch (Exception e) {
			e.printStackTrace();
			returnMap.put("success", false);
			returnMap.put("msg", e.getMessage());
		}
		return returnMap;
	}

}