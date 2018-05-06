package com.war.wechat.app.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

/**
 * 微信小程序登陆接口基类
 * 
 * @author swbssd
 *
 */
public interface WechatLoginInterface {
	/**
	 * 通过openid获取绑定的手机号(如果对象为空，则未绑定)
	 * 
	 * @param openid
	 * @return
	 */
	public String getBindingUser(String openid);
	
	/**
	 * 通过手机号，openid进行用户绑定
	 * @param phone
	 * @param openid
	 */
	@Transactional(readOnly = false)
	public void bindingUserOpenid(String phone, String openid);
	
}
