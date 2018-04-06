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
import com.thinkgem.jeesite.modules.vip.dao.VipUserPayDao;
import com.thinkgem.jeesite.modules.vip.entity.VipUserBase;
import com.thinkgem.jeesite.modules.vip.entity.VipUserPay;
import com.thinkgem.jeesite.modules.vip.entity.VipUserWallet;
import com.thinkgem.jeesite.modules.vip.utils.WalletUtils;
import com.war.wechat.base.util.JSONUtil;

/**
 * 会员充值记录Service
 * 
 * @author swbssd
 * @version 2017-09-17
 */
@Service
@Transactional(readOnly = true)
public class VipUserPayService extends CrudService<VipUserPayDao, VipUserPay> {

	@Autowired
	private VipUserBaseService vipUserBaseService;
	@Autowired
	private VipUserWalletService vipUserWalletService;

	public VipUserPay get(String id) {
		return super.get(id);
	}

	public List<VipUserPay> findList(VipUserPay vipUserPay) {
		return super.findList(vipUserPay);
	}

	public Page<VipUserPay> findPage(Page<VipUserPay> page,
			VipUserPay vipUserPay) {
		return super.findPage(page, vipUserPay);
	}

	@Transactional(readOnly = false)
	public void save(VipUserPay vipUserPay) {
		VipUserBase vipUserBase = vipUserBaseService.get(vipUserPay.getVipId());
		vipUserPay.setVipName(vipUserBase.getVipName());
		vipUserPay.setVipPhone(vipUserBase.getVipPhone());
		vipUserPay.setPayTime(DateUtils.getDateTime());
		boolean isNewRecord = vipUserPay.getIsNewRecord();
		super.save(vipUserPay);
		VipUserWallet vipUserWallet = vipUserWalletService
				.findByVipId(vipUserPay.getVipId());
		if (isNewRecord) { // 新增充值
			if (vipUserPay.getPayMoeny() > 0) {
				vipUserWallet
						.setRestMoeny(WalletUtils.add(
								vipUserWallet.getRestMoeny(),
								vipUserPay.getPayMoeny()));
				vipUserWallet.setAllMoeny(WalletUtils.add(
						vipUserWallet.getRestMoeny(),
						vipUserWallet.getUseMoeny()));
			}
			if (vipUserPay.getGetScore() > 0) {
				vipUserWallet
						.setRestScore(WalletUtils.add(
								vipUserWallet.getRestScore(),
								vipUserPay.getGetScore()));
				vipUserWallet.setAllScore(WalletUtils.add(
						vipUserWallet.getRestScore(),
						vipUserWallet.getUseScore()));
			}
			vipUserWalletService.save(vipUserWallet);
			// 发送短信
			// String content = "尊敬的会员["+vipUserBase.getVipPhone()+"]，您已充值成功！"
			// + "当前账户余额："+vipUserWallet.getRestMoeny()+
			// "当前积分余额："+vipUserWallet.getRestScore();
			// 发送短信意见
			Map<String, String> contentMap = new HashMap<String, String>();
			contentMap.put("name", vipUserBase.getVipPhone());
			contentMap
					.put("msg1", String.valueOf(vipUserWallet.getRestMoeny()));
			contentMap
					.put("msg2", String.valueOf(vipUserWallet.getRestScore()));
			MessageUtil.getInterface().send("PAY_CODE", vipUserBase.getVipPhone(), contentMap);
		} else { // 修改充值

		}

	}

	@Transactional(readOnly = false)
	public void delete(VipUserPay vipUserPay) {
		super.delete(vipUserPay);
	}

}