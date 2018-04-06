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
import com.thinkgem.jeesite.common.utils.DateUtils;
import com.thinkgem.jeesite.common.utils.MessageUtil;
import com.thinkgem.jeesite.modules.vip.entity.VipUserBase;
import com.thinkgem.jeesite.modules.vip.entity.VipUserCost;
import com.thinkgem.jeesite.modules.vip.entity.VipUserWallet;
import com.thinkgem.jeesite.modules.vip.utils.WalletUtils;
import com.thinkgem.jeesite.modules.vip.dao.VipUserCostDao;
import com.war.wechat.base.util.JSONUtil;

/**
 * 会员消费记录Service
 * 
 * @author swbssd
 * @version 2017-09-17
 */
@Service
@Transactional(readOnly = true)
public class VipUserCostService extends
		CrudService<VipUserCostDao, VipUserCost> {

	@Autowired
	private VipUserBaseService vipUserBaseService;
	@Autowired
	private VipUserWalletService vipUserWalletService;

	public VipUserCost get(String id) {
		return super.get(id);
	}

	public List<VipUserCost> findList(VipUserCost vipUserCost) {
		return super.findList(vipUserCost);
	}

	public Page<VipUserCost> findPage(Page<VipUserCost> page,
			VipUserCost vipUserCost) {
		return super.findPage(page, vipUserCost);
	}

	@Transactional(readOnly = false)
	public void save(VipUserCost vipUserCost) {
		VipUserBase vipUserBase = vipUserBaseService
				.get(vipUserCost.getVipId());
		vipUserCost.setVipName(vipUserBase.getVipName());
		vipUserCost.setVipPhone(vipUserBase.getVipPhone());
		vipUserCost.setCostTime(DateUtils.getDateTime());
		boolean isNewRecord = vipUserCost.getIsNewRecord();
		super.save(vipUserCost);
		VipUserWallet vipUserWallet = vipUserWalletService
				.findByVipId(vipUserCost.getVipId());
		if (isNewRecord) { // 新增消费
			if (vipUserCost.getCostMoeny() > 0) {
				// 减少可用
				double restMoeny = WalletUtils.subtract(
						vipUserWallet.getRestMoeny(),
						vipUserCost.getCostMoeny());
				if (restMoeny < 0) {
					throw new RuntimeException("消费金额余额不足，请充值!");
				}
				vipUserWallet.setRestMoeny(restMoeny);
				// 增加已用
				vipUserWallet
						.setUseMoeny(WalletUtils.add(
								vipUserWallet.getUseMoeny(),
								vipUserCost.getCostMoeny()));
				// 刷新总额
				vipUserWallet.setAllMoeny(WalletUtils.add(
						vipUserWallet.getRestMoeny(),
						vipUserWallet.getUseMoeny()));
			}
			// 积分消费
			if (vipUserCost.getCostScore() > 0) {
				// 减少可用
				double restScore = WalletUtils.subtract(
						vipUserWallet.getRestScore(),
						vipUserCost.getCostScore());
				if (restScore < 0) {
					throw new RuntimeException("消费积分余额不足!");
				}
				vipUserWallet.setRestScore(restScore);
				// 增加已用
				vipUserWallet
						.setUseScore(WalletUtils.add(
								vipUserWallet.getUseScore(),
								vipUserCost.getCostScore()));
				// 刷新总额
				vipUserWallet.setAllScore(WalletUtils.add(
						vipUserWallet.getRestScore(),
						vipUserWallet.getUseScore()));
			}
			vipUserWalletService.save(vipUserWallet);
			// 发送短信
			String appendTxt = vipUserCost.getRemarks() == null ? ""
					: vipUserCost.getRemarks()+" ";
			if (vipUserCost.getCostMoeny() > 0) {
				appendTxt += "金额：" + vipUserCost.getCostMoeny() + "";
			}
			if (vipUserCost.getCostScore() > 0) {
				appendTxt += "积分：" + vipUserCost.getCostScore() + "";
			}
			// String content = "尊敬的会员[" + vipUserBase.getVipPhone() + "]，"
			// + appendTxt + "当前账户余额：" + vipUserWallet.getRestMoeny()
			// + "当前积分余额：" + vipUserWallet.getRestScore();
			// 发送短信意见
			Map<String, String> contentMap = new HashMap<String, String>();
			contentMap.put("name", vipUserBase.getVipPhone());
			contentMap.put("msg", appendTxt);
			contentMap
					.put("msg1", String.valueOf(vipUserWallet.getRestMoeny()));
			contentMap
					.put("msg2", String.valueOf(vipUserWallet.getRestScore()));
			MessageUtil.getInterface().send("COST_CODE", vipUserBase.getVipPhone(),
					contentMap);
		} else { // 修改消费

		}
	}

	@Transactional(readOnly = false)
	public void delete(VipUserCost vipUserCost) {
		super.delete(vipUserCost);
	}

}