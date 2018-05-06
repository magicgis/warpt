/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.vip.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.common.utils.MessageUtil;
import com.thinkgem.jeesite.modules.vip.dao.VipUserBaseDao;
import com.thinkgem.jeesite.modules.vip.entity.VipUserBase;
import com.thinkgem.jeesite.modules.vip.entity.VipUserWallet;
import com.war.wechat.base.util.JSONUtil;

/**
 * 会员基本信息Service
 * 
 * @author swbssd
 * @version 2017-09-17
 */
@Service
@Transactional(readOnly = true)
public class VipUserBaseService extends
		CrudService<VipUserBaseDao, VipUserBase> {
	@Autowired
	private VipUserWalletService vipUserWalletService;
	@Autowired
	private VipUserBaseDao vipUserBaseDao;

	public VipUserBase get(String id) {
		return super.get(id);
	}

	public List<VipUserBase> findList(VipUserBase vipUserBase) {
		return super.findList(vipUserBase);
	}

	public Page<VipUserBase> findPage(Page<VipUserBase> page,
			VipUserBase vipUserBase) {
		return super.findPage(page, vipUserBase);
	}

	@Transactional(readOnly = false)
	public void save(VipUserBase vipUserBase) {
		vipUserBase.setAreaName(vipUserBase.getArea().getName());
		boolean isNewRecord = vipUserBase.getIsNewRecord();
		super.save(vipUserBase);
		if (isNewRecord) { // 新增初始化钱包
			VipUserWallet vipUserWallet = new VipUserWallet();
			vipUserWallet.setVipId(vipUserBase.getId());
			vipUserWallet.setOfficeId(vipUserBase.getOfficeId());
			vipUserWallet.setVipName(vipUserBase.getVipName());
			vipUserWallet.setVipPhone(vipUserBase.getVipPhone());
			vipUserWallet.setAllMoeny(0.00);
			vipUserWallet.setRestMoeny(0.00);
			vipUserWallet.setUseMoeny(0.00);
			vipUserWallet.setAllScore(0.00);
			vipUserWallet.setRestScore(0.00);
			vipUserWallet.setUseScore(0.00);
			vipUserWalletService.save(vipUserWallet);
			// 发送短信
			// String content =
			// "尊敬的["+vipUserBase.getVipPhone()+"]，您已注册成为英树会员！";
			// 发送短信意见
			Map<String, String> contentMap = new HashMap<String, String>();
			contentMap.put("name", vipUserBase.getVipPhone());
			MessageUtil.getInterface().send("REGISTER_CODE", vipUserBase.getVipPhone(),
					contentMap);
		}
	}

	@Transactional(readOnly = false)
	public void delete(VipUserBase vipUserBase) {
		super.delete(vipUserBase);
	}
	
	/**
	 * 获取用户的VIP和钱包信息
	 * @param vipUserBase
	 * @return 
	 */
	public List<VipUserBase> findUserAllVip(String phone,String openid){
		VipUserBase pram = new VipUserBase();
		pram.setVipPhone(phone);
		pram.setOpenId(openid);
		return vipUserBaseDao.findUserAllVip(pram);
	}

}