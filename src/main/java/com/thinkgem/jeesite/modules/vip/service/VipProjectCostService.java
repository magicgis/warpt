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
import com.thinkgem.jeesite.modules.vip.dao.VipProjectCostDao;
import com.thinkgem.jeesite.modules.vip.entity.VipProjectCost;
import com.thinkgem.jeesite.modules.vip.entity.VipProjectPay;

/**
 * 会员项目消费Service
 * 
 * @author swbssd
 * @version 2018-01-14
 */
@Service
@Transactional(readOnly = true)
public class VipProjectCostService extends
		CrudService<VipProjectCostDao, VipProjectCost> {

	@Autowired
	private VipProjectPayService vipProjectPayService;

	public VipProjectCost get(String id) {
		return super.get(id);
	}

	public List<VipProjectCost> findList(VipProjectCost vipProjectCost) {
		return super.findList(vipProjectCost);
	}

	public Page<VipProjectCost> findPage(Page<VipProjectCost> page,
			VipProjectCost vipProjectCost) {
		return super.findPage(page, vipProjectCost);
	}

	@Transactional(readOnly = false)
	public void save(VipProjectCost vipProjectCost) {
		// 更新消费次数
		VipProjectPay vipProjectPay = vipProjectPayService.get(vipProjectCost
				.getProjectId());
		if (vipProjectPay.getRestNum() < vipProjectCost.getCostNum()) {
			throw new RuntimeException("项目剩余次数" + vipProjectPay.getRestNum()
					+ ";消费次数不足！");
		}
		vipProjectPay.setRestNum(vipProjectPay.getRestNum()
				- vipProjectCost.getCostNum());
		vipProjectPay.setUseNum(vipProjectPay.getUseNum()
				+ vipProjectCost.getCostNum());
		vipProjectPayService.saveSimp(vipProjectPay);
		vipProjectCost.setOfficeId(vipProjectPay.getOfficeId());
		vipProjectCost.setVipId(vipProjectPay.getVipId());
		vipProjectCost.setVipName(vipProjectPay.getVipName());
		vipProjectCost.setVipPhone(vipProjectPay.getVipPhone());
		super.save(vipProjectCost);
		// 发送短信意见
		Map<String, String> contentMap = new HashMap<String, String>();
		contentMap.put("name", vipProjectCost.getVipPhone());
		contentMap.put("msg", vipProjectCost.getProjectName());
		contentMap.put("msg1", String.valueOf(vipProjectCost.getCostNum()));
		contentMap.put("msg2", String.valueOf(vipProjectPay.getRestNum()));
		MessageUtil.getInterface().send("PROJECT_CODE",
				vipProjectCost.getVipPhone(), contentMap);
	}

	@Transactional(readOnly = false)
	public void delete(VipProjectCost vipProjectCost) {
		super.delete(vipProjectCost);
	}

}