/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.shop.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.shop.entity.ShopSupplierAccount;
import com.thinkgem.jeesite.modules.shop.dao.ShopSupplierAccountDao;

/**
 * 供应商付款Service
 * @author swbssd
 * @version 2018-04-19
 */
@Service
@Transactional(readOnly = true)
public class ShopSupplierAccountService extends CrudService<ShopSupplierAccountDao, ShopSupplierAccount> {

	public ShopSupplierAccount get(String id) {
		return super.get(id);
	}
	
	public List<ShopSupplierAccount> findList(ShopSupplierAccount shopSupplierAccount) {
		return super.findList(shopSupplierAccount);
	}
	
	public Page<ShopSupplierAccount> findPage(Page<ShopSupplierAccount> page, ShopSupplierAccount shopSupplierAccount) {
		return super.findPage(page, shopSupplierAccount);
	}
	
	@Transactional(readOnly = false)
	public void save(ShopSupplierAccount shopSupplierAccount) {
		super.save(shopSupplierAccount);
	}
	
	@Transactional(readOnly = false)
	public void delete(ShopSupplierAccount shopSupplierAccount) {
		super.delete(shopSupplierAccount);
	}
	
}