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
import com.thinkgem.jeesite.modules.shop.entity.ShopCustomerAccount;
import com.thinkgem.jeesite.modules.shop.entity.ShopCustomerInfo;
import com.thinkgem.jeesite.modules.shop.service.ShopCustomerAccountService;
import com.thinkgem.jeesite.modules.shop.service.ShopCustomerInfoService;
import com.thinkgem.jeesite.modules.shop.utils.ShopUtils;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import com.thinkgem.jeesite.modules.vip.dao.VipUserPayDao;
import com.thinkgem.jeesite.modules.vip.entity.VipUserBase;
import com.thinkgem.jeesite.modules.vip.entity.VipUserPay;
import com.thinkgem.jeesite.modules.vip.entity.VipUserWallet;
import com.thinkgem.jeesite.modules.vip.utils.WalletUtils;

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
	@Autowired
	private ShopCustomerInfoService shopCustomerInfoService;	
	@Autowired
	private ShopCustomerAccountService shopCustomerAccountService;

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
		//充值金额=实际+赠送
		vipUserPay.setPayMoeny(WalletUtils.add(vipUserPay.getRealMoeny(),vipUserPay.getGiveMoeny()));
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
			
			//如果赠送金额大于0，则进行客户收款负数录入，计入成本
			if(vipUserPay.getGiveMoeny() > 0) {
				//查找客户信息
				ShopCustomerInfo parm = new ShopCustomerInfo();
				parm.setVipId(vipUserPay.getVipId());
				parm.setOfficeId(vipUserPay.getOfficeId());
				List<ShopCustomerInfo> list = shopCustomerInfoService.findList(parm);
				//如果不存在，则没关联客户信息表老数据，不进行录入
				if(list!=null && !list.isEmpty()) {
					ShopCustomerInfo shopCustomerInfo = list.get(0);
					ShopCustomerAccount shopCustomerAccount = new ShopCustomerAccount();
					shopCustomerAccount.setOfficeId(vipUserPay.getOfficeId());
					shopCustomerAccount.setCustomerId(shopCustomerInfo.getId());
					shopCustomerAccount.setCustomerName(shopCustomerInfo.getCustomerName());
					//生成单据号
					shopCustomerAccount.setAccountNo("CZC_"+vipUserPay.getId());
					shopCustomerAccount.setSubjectType(ShopUtils.SUBJECT_TYPE_1005);
					shopCustomerAccount.setBusinData(DateUtils.getDateTime());
					shopCustomerAccount.setFactMoney(vipUserPay.getGiveMoeny()*-1);
					shopCustomerAccount.setMeetMoney(shopCustomerAccount.getFactMoney());
					shopCustomerAccount.setLessMoney(ShopUtils.subtract(shopCustomerAccount.getMeetMoney(), shopCustomerAccount.getFactMoney()));
					shopCustomerAccount.setRemarks("客户充值赠送金额扣减成本");
					shopCustomerAccountService.save(shopCustomerAccount);
				}

			}
			
			// 发送短信
			// String content = "尊敬的会员["+vipUserBase.getVipPhone()+"]，您已充值成功！"
			// + "当前账户余额："+vipUserWallet.getRestMoeny()+
			// "当前积分余额："+vipUserWallet.getRestScore();
			// 发送短信意见
			if (!StringUtils.equals(vipUserBase.getLevelName(), "一级代理")
					&& !StringUtils.equals(vipUserBase.getLevelName(), "二级代理")
					&& !StringUtils.equals(vipUserBase.getLevelName(), "特约代理")) {
				Map<String, String> contentMap = new HashMap<String, String>();
				contentMap.put("name", vipUserBase.getVipPhone());
				contentMap
						.put("msg1", String.valueOf(vipUserWallet.getRestMoeny()));
				contentMap
						.put("msg2", String.valueOf(vipUserWallet.getRestScore()));
				MessageUtil.getInterface().send("PAY_CODE", vipUserBase.getVipPhone(), contentMap);
			}
		} else { // 修改充值

		}

	}

	@Transactional(readOnly = false)
	public void delete(VipUserPay vipUserPay) {
		//查询完整记录
		vipUserPay = this.get(vipUserPay.getId());
		//钱包还原
		VipUserWallet vipUserWallet = vipUserWalletService
				.findByVipId(vipUserPay.getVipId());
		if (vipUserPay.getPayMoeny() > 0) {
			// 减少可用
			double restMoeny = WalletUtils.subtract(
					vipUserWallet.getRestMoeny(),
					vipUserPay.getPayMoeny());
			if (restMoeny < 0) {
				throw new RuntimeException("该会员钱包余额不足，无法撤销充值!");
			}
			vipUserWallet.setRestMoeny(restMoeny);
			vipUserWallet.setAllMoeny(WalletUtils.add(
					vipUserWallet.getRestMoeny(),
					vipUserWallet.getUseMoeny()));
		}
		if (vipUserPay.getGetScore() > 0) {
			// 减少可用
			double restScore = WalletUtils.subtract(
					vipUserWallet.getRestScore(),
					vipUserPay.getGetScore());
			if (restScore < 0) {
				throw new RuntimeException("该会员消费积分余额不足，无法撤销充值!");
			}
			vipUserWallet.setRestScore(restScore);
			vipUserWallet.setAllScore(WalletUtils.add(
					vipUserWallet.getRestScore(),
					vipUserWallet.getUseScore()));
		}
		vipUserWalletService.save(vipUserWallet);
		
		//如果赠送金额大于0，则进行客户收款负数录入，计入成本
		if(vipUserPay.getGiveMoeny() > 0) {
			//查找客户信息
			ShopCustomerInfo parm = new ShopCustomerInfo();
			parm.setVipId(vipUserPay.getVipId());
			parm.setOfficeId(vipUserPay.getOfficeId());
			ShopCustomerAccount custParm = new ShopCustomerAccount();
			custParm.setAccountNo("CZC_"+vipUserPay.getId());
			List<ShopCustomerAccount> accountList = shopCustomerAccountService.findList(custParm);
			if(accountList!=null && !accountList.isEmpty()) {
				ShopCustomerAccount shopCustomerAccount = accountList.get(0);
				shopCustomerAccountService.delete(shopCustomerAccount);
			}
			
		}
		//最后删除
		super.delete(vipUserPay);
		// 发送短信
		VipUserBase vipUserBase = vipUserBaseService.get(vipUserPay.getVipId());
		if (!StringUtils.equals(vipUserBase.getLevelName(), "一级代理")
				&& !StringUtils.equals(vipUserBase.getLevelName(), "二级代理")
				&& !StringUtils.equals(vipUserBase.getLevelName(), "特约代理")) {
			Map<String, String> contentMap = new HashMap<String, String>();
			contentMap.put("name", vipUserBase.getVipPhone()+"(充值撤销)");
			contentMap
					.put("msg1", String.valueOf(vipUserWallet.getRestMoeny()));
			contentMap
					.put("msg2", String.valueOf(vipUserWallet.getRestScore()));
			MessageUtil.getInterface().send("PAY_CODE", vipUserBase.getVipPhone(), contentMap);
		}
	}
	
	/**
	 * 导出会员充值
	 * @param exportPath
	 * @throws IOException 
	 * @throws FileNotFoundException 
	 */
	public void exportExcel(String exportPath) throws FileNotFoundException, IOException {
		//查询当前数据
		String userId = UserUtils.getUser().getOffice().getId();
		VipUserPay parm = new VipUserPay();
		parm.setOfficeId(userId);
		List<VipUserPay> payList = this.findList(parm);
		//设置表头
		List<String> headerList = Lists.newArrayList();
		headerList.add("会员手机");
		headerList.add("会员名称");
		headerList.add("充值总额");
		headerList.add("充值金额");
		headerList.add("赠送金额");
		headerList.add("获得积分");
		headerList.add("充值时间");
		headerList.add("备注");
		ExportExcel ee = new ExportExcel("会员充值记录", headerList);
		//设置表体
		for (VipUserPay vipUserPay : payList) {
			Row row = ee.addRow();
			ee.addCell(row, 0,vipUserPay.getVipPhone());
			ee.addCell(row, 1,vipUserPay.getVipName());
			ee.addCell(row, 2,vipUserPay.getPayMoeny());
			ee.addCell(row, 3,vipUserPay.getRealMoeny());
			ee.addCell(row, 4,vipUserPay.getGiveMoeny());
			ee.addCell(row, 5,vipUserPay.getGetScore());
			ee.addCell(row, 6,vipUserPay.getPayTime());
			ee.addCell(row, 7,vipUserPay.getRemarks());
		}
		ee.writeFile(exportPath);
		ee.dispose();
	}

}