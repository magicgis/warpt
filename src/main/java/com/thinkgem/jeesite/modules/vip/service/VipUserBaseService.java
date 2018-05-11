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
import com.thinkgem.jeesite.common.utils.MessageUtil;
import com.thinkgem.jeesite.common.utils.excel.ExportExcel;
import com.thinkgem.jeesite.modules.shop.entity.ShopCustomerInfo;
import com.thinkgem.jeesite.modules.shop.service.ShopCustomerInfoService;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import com.thinkgem.jeesite.modules.vip.dao.VipUserBaseDao;
import com.thinkgem.jeesite.modules.vip.entity.VipUserBase;
import com.thinkgem.jeesite.modules.vip.entity.VipUserWallet;

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
	@Autowired
	private ShopCustomerInfoService shopCustomerInfoService;

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
			
			//注册为进销存客户
			ShopCustomerInfo shopCustomerInfo = new ShopCustomerInfo();
			shopCustomerInfo.setOfficeId(vipUserBase.getOfficeId());
			shopCustomerInfo.setLevelId(vipUserBase.getLevelId());
			shopCustomerInfo.setLevelName(vipUserBase.getLevelName());
			shopCustomerInfo.setCustomerName(vipUserBase.getVipName());
			shopCustomerInfo.setPhone(vipUserBase.getVipPhone());
			shopCustomerInfo.setDiscount(100.0); //默认折扣不设置
			shopCustomerInfo.setIsVip(1); //是会员
			shopCustomerInfo.setVipId(vipUserBase.getId());
			//排序设置
			ShopCustomerInfo parm = new ShopCustomerInfo();
			parm.setOfficeId(UserUtils.getUser().getOffice().getId());
			List<ShopCustomerInfo> customerList = shopCustomerInfoService.findList(parm);
			if (customerList.size() > 0){
				shopCustomerInfo.setSort(customerList.get(customerList.size()-1).getSort()+1);
			}else {
				shopCustomerInfo.setSort(1);
			}
			shopCustomerInfoService.save(shopCustomerInfo);
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

	/**
	 * 会员数据导出excel
	 * @return
	 * @throws IOException 
	 * @throws FileNotFoundException 
	 */
	public void exportExcel(String exportPath) throws FileNotFoundException, IOException {
		//查询当前机构会员数据
		String userId = UserUtils.getUser().getOffice().getId();
		VipUserBase parm = new VipUserBase();
		parm.setOfficeId(userId);
		List<VipUserBase> vipList = this.findList(parm);
		//设置表头
		List<String> headerList = Lists.newArrayList();
		headerList.add("会员名称");
		headerList.add("会员手机");
		headerList.add("会员等级");
		headerList.add("可用金额");
		headerList.add("已用金额");
		headerList.add("可用积分");
		headerList.add("已兑积分");
		ExportExcel ee = new ExportExcel("会员信息", headerList);
		//设置表体
		for (VipUserBase vipUserBase : vipList) {
			Row row = ee.addRow();
			ee.addCell(row, 0,vipUserBase.getVipName());
			ee.addCell(row, 1,vipUserBase.getVipPhone());
			ee.addCell(row, 2,vipUserBase.getLevelName());
			ee.addCell(row, 3,vipUserBase.getRestMoeny());
			ee.addCell(row, 4,vipUserBase.getUseMoeny());
			ee.addCell(row, 5,vipUserBase.getRestScore());
			ee.addCell(row, 6,vipUserBase.getUseScore());
		}
		ee.writeFile(exportPath);
		ee.dispose();
	}

}