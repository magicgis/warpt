/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.war.wechat.app.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.modules.vip.entity.VipUserBase;
import com.thinkgem.jeesite.modules.vip.service.VipUserBaseService;
import com.thinkgem.jeesite.modules.vip.service.VipUserWalletService;

/**
 * 会员基本信息APP类
 */
@Service
@Transactional(readOnly = true)
public class VipAppService implements WechatLoginInterface{
	@Autowired
	private VipUserBaseService vipUserBaseService;
	
	@Override
	public String getBindingUser(String openid) {
		VipUserBase parm = new VipUserBase();
		parm.setOpenId(openid);
		List<VipUserBase> vipUserlList = vipUserBaseService.findList(parm);
		String phone = null;
		for (VipUserBase vipUserBase : vipUserlList) {
			phone = vipUserBase.getVipPhone();
		}
		return phone;
	}

	@Override
	@Transactional(readOnly = false)
	public void bindingUserOpenid(String phone, String openid) {
		VipUserBase parm = new VipUserBase();
		parm.setVipPhone(phone);
		//可能存在一个手机号多次注册会员，多张卡
		List<VipUserBase> vipUserlList = vipUserBaseService.findList(parm);
		if(vipUserlList==null || vipUserlList.isEmpty()) {
			throw new RuntimeException("该手机未注册会员");
		}
		for (VipUserBase vipUserBase : vipUserlList) {
			vipUserBase.setOpenId(openid);
			vipUserBaseService.save(vipUserBase);
		}
		List<String> userIdList = new ArrayList<String>();
		for (VipUserBase vipUserBase : vipUserlList) {
			userIdList.add(vipUserBase.getId());
		}
	}
}