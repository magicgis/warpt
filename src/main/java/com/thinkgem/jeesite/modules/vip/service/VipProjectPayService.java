/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.vip.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.vip.dao.VipProjectPayDao;
import com.thinkgem.jeesite.modules.vip.entity.VipProjectPay;
import com.thinkgem.jeesite.modules.vip.entity.VipUserCost;
import com.thinkgem.jeesite.modules.vip.entity.VipUserWallet;

/**
 * 会员项目充值Service
 * 
 * @author swbssd
 * @version 2018-01-14
 */
@Service
@Transactional(readOnly = true)
public class VipProjectPayService extends
		CrudService<VipProjectPayDao, VipProjectPay> {

	@Autowired
	private VipUserWalletService vipUserWalletService;
	@Autowired
	private VipUserCostService vipUserCostService;
	
	public VipProjectPay get(String id) {
		return super.get(id);
	}

	public List<VipProjectPay> findList(VipProjectPay vipProjectPay) {
		return super.findList(vipProjectPay);
	}

	public Page<VipProjectPay> findPage(Page<VipProjectPay> page,
			VipProjectPay vipProjectPay) {
		return super.findPage(page, vipProjectPay);
	}

	@Transactional(readOnly = false)
	public void save(VipProjectPay vipProjectPay) {
		//新增消费次数，扣减钱包余额
		VipUserCost vipUserCost = new VipUserCost();
		vipUserCost.setOfficeId(vipProjectPay.getOfficeId());
		vipUserCost.setVipId(vipProjectPay.getVipId());
		vipUserCost.setCostMoeny(vipProjectPay.getProjectMoeny());
		vipUserCost.setCostScore(0.0);
		vipUserCost.setRemarks(vipProjectPay.getProjectName()+"充值");
		vipUserCostService.save(vipUserCost);
		super.save(vipProjectPay);
	}
	
	@Transactional(readOnly = false)
	public void saveSimp(VipProjectPay vipProjectPay) {
		super.save(vipProjectPay);
	}	

	@Transactional(readOnly = false)
	public void delete(VipProjectPay vipProjectPay) {
		super.delete(vipProjectPay);
	}

}