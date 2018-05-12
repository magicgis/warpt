/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.vip.service;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.Row;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Lists;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.common.utils.DateUtils;
import com.thinkgem.jeesite.common.utils.MessageUtil;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.utils.excel.ExportExcel;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import com.thinkgem.jeesite.modules.vip.dao.VipUserCostDao;
import com.thinkgem.jeesite.modules.vip.entity.VipUserBase;
import com.thinkgem.jeesite.modules.vip.entity.VipUserCost;
import com.thinkgem.jeesite.modules.vip.entity.VipUserWallet;
import com.thinkgem.jeesite.modules.vip.utils.WalletUtils;

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
					throw new RuntimeException("该会员消费余额不足，请充值!");
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
					throw new RuntimeException("该会员消费积分余额不足，请充值!");
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
			String appendTxt = "";
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
			if (!StringUtils.equals(vipUserBase.getLevelName(), "一级代理")
					&& !StringUtils.equals(vipUserBase.getLevelName(), "二级代理")
					&& !StringUtils.equals(vipUserBase.getLevelName(), "特约代理")) {
				Map<String, String> contentMap = new HashMap<String, String>();
				contentMap.put("name", vipUserBase.getVipPhone());
				contentMap.put("msg", appendTxt);
				contentMap
						.put("msg1", String.valueOf(vipUserWallet.getRestMoeny()));
				contentMap
						.put("msg2", String.valueOf(vipUserWallet.getRestScore()));
				MessageUtil.getInterface().send("COST_CODE", vipUserBase.getVipPhone(),
						contentMap);
			}
		} else { // 修改消费

		}
	}

	/**
	 * 
	 * @param vipUserCost
	 * @param linkOrder 是否关联销售出库订单
	 */
	@Transactional(readOnly = false)
	public void delete(VipUserCost vipUserCost,boolean linkOrder) {
		//如果不是销售订单关联的删除，删除之前需要判断是否关联销售订单，如果关联不给删除
		if(!StringUtils.isEmpty(vipUserCost.getSaleId()) && !linkOrder) {
			throw new RuntimeException("该消费记录关联了销售出库单，请从销售出库单处进行删除，订单号["+vipUserCost.getSaleNo()+"]!");
		}
		//查询完整记录
		vipUserCost = this.get(vipUserCost.getId());
		//钱包还原
		VipUserWallet vipUserWallet = vipUserWalletService
				.findByVipId(vipUserCost.getVipId());
		if (vipUserCost.getCostMoeny() > 0) {
			// 增加可用
			double restMoeny = WalletUtils.add(
					vipUserWallet.getRestMoeny(),
					vipUserCost.getCostMoeny());
			vipUserWallet.setRestMoeny(restMoeny);
			// 减少已用
			vipUserWallet
					.setUseMoeny(WalletUtils.subtract(
							vipUserWallet.getUseMoeny(),
							vipUserCost.getCostMoeny()));
			// 刷新总额
			vipUserWallet.setAllMoeny(WalletUtils.add(
					vipUserWallet.getRestMoeny(),
					vipUserWallet.getUseMoeny()));
		}
		// 积分消费
		if (vipUserCost.getCostScore() > 0) {
			// 增加可用
			double restScore = WalletUtils.add(
					vipUserWallet.getRestScore(),
					vipUserCost.getCostScore());
			vipUserWallet.setRestScore(restScore);
			// 减少已用
			vipUserWallet
					.setUseScore(WalletUtils.subtract(
							vipUserWallet.getUseScore(),
							vipUserCost.getCostScore()));
			// 刷新总额
			vipUserWallet.setAllScore(WalletUtils.add(
					vipUserWallet.getRestScore(),
					vipUserWallet.getUseScore()));
		}
		vipUserWalletService.save(vipUserWallet);
		super.delete(vipUserCost);
		// 发送短信
		VipUserBase vipUserBase = vipUserBaseService.get(vipUserCost.getVipId());
		if (!StringUtils.equals(vipUserBase.getLevelName(), "一级代理")
				&& !StringUtils.equals(vipUserBase.getLevelName(), "二级代理")
				&& !StringUtils.equals(vipUserBase.getLevelName(), "特约代理")) {
			String appendTxt = "";
			if (vipUserCost.getCostMoeny() > 0) {
				appendTxt += "撤销消费金额：" + vipUserCost.getCostMoeny() + "";
			}
			if (vipUserCost.getCostScore() > 0) {
				appendTxt += "撤销消费积分：" + vipUserCost.getCostScore() + "";
			}
			Map<String, String> contentMap = new HashMap<String, String>();
			contentMap.put("name", vipUserBase.getVipPhone());
			contentMap.put("msg", appendTxt);
			contentMap
					.put("msg1", String.valueOf(vipUserWallet.getRestMoeny()));
			contentMap
					.put("msg2", String.valueOf(vipUserWallet.getRestScore()));
			MessageUtil.getInterface().send("COST_CODE", vipUserBase.getVipPhone(),
					contentMap);
		}
	}

	/**
	 * 导出会员消费
	 * @param exportPath
	 * @throws IOException 
	 * @throws FileNotFoundException 
	 */
	public void exportExcel(String exportPath) throws FileNotFoundException, IOException {
		//查询当前数据
		String userId = UserUtils.getUser().getOffice().getId();
		VipUserCost parm = new VipUserCost();
		parm.setOfficeId(userId);
		List<VipUserCost> costList = this.findList(parm);
		//设置表头
		List<String> headerList = Lists.newArrayList();
		headerList.add("会员手机");
		headerList.add("会员名称");
		headerList.add("消费金额");
		headerList.add("消费积分");
		headerList.add("消费时间");
		headerList.add("备注");
		ExportExcel ee = new ExportExcel("会员消费记录", headerList);
		//设置表体
		for (VipUserCost vipUserCost : costList) {
			Row row = ee.addRow();
			ee.addCell(row, 0,vipUserCost.getVipPhone());
			ee.addCell(row, 1,vipUserCost.getVipName());
			ee.addCell(row, 2,vipUserCost.getCostMoeny());
			ee.addCell(row, 3,vipUserCost.getCostScore());
			ee.addCell(row, 4,vipUserCost.getCostTime());
			ee.addCell(row, 5,vipUserCost.getRemarks());
		}
		ee.writeFile(exportPath);
		ee.dispose();
	}

}