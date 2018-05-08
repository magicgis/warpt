/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.shop.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.shop.entity.ShopSupplierAccount;
import com.thinkgem.jeesite.modules.shop.utils.ShopUtils;
import com.thinkgem.jeesite.modules.shop.dao.ShopSupplierAccountDao;

/**
 * 供应商付款Service
 * @author swbssd
 * @version 2018-04-19
 */
@Service
@Transactional(readOnly = true)
public class ShopSupplierAccountService extends CrudService<ShopSupplierAccountDao, ShopSupplierAccount> {
	@Autowired
	private ShopSupplierAccountDao shopSupplierAccountDao;
	
	public ShopSupplierAccount get(String id) {
		return super.get(id);
	}
	
	public List<ShopSupplierAccount> findList(ShopSupplierAccount shopSupplierAccount) {
		return super.findList(shopSupplierAccount);
	}
	
	public Page<ShopSupplierAccount> findPage(Page<ShopSupplierAccount> page, ShopSupplierAccount shopSupplierAccount) {
		return super.findPage(page, shopSupplierAccount);
	}
	
	/**
	 * 求和统计某供应商
	 * @param shopSupplierAccount
	 * @return
	 */
	public List<ShopSupplierAccount> findCountPage(ShopSupplierAccount shopSupplierAccount){
		return shopSupplierAccountDao.findCountPage(shopSupplierAccount);
	}
	
	@Transactional(readOnly = false)
	public void saveByAdd(ShopSupplierAccount shopSupplierAccount) {
		//生成单据号
		shopSupplierAccount.setAccountNo(ShopUtils.generateBillCode("FKS"));
		shopSupplierAccount.setSubjectType(ShopUtils.SUBJECT_TYPE_1004);
		shopSupplierAccount.setMeetMoney(shopSupplierAccount.getFactMoney());
		shopSupplierAccount.setLessMoney(ShopUtils.subtract(shopSupplierAccount.getMeetMoney(), shopSupplierAccount.getFactMoney()));
		super.save(shopSupplierAccount);
	}
	
	@Transactional(readOnly = false)
	public void saveByOrder(ShopSupplierAccount shopSupplierAccount) {
		super.save(shopSupplierAccount);
	}	
	
	@Transactional(readOnly = false)
	public void delete(ShopSupplierAccount shopSupplierAccount) {
		super.delete(shopSupplierAccount);
	}
	
}