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
import com.thinkgem.jeesite.modules.shop.entity.ShopCustomerAccount;
import com.thinkgem.jeesite.modules.shop.dao.ShopCustomerAccountDao;

/**
 * 客户收款Service
 * @author swbssd
 * @version 2018-04-20
 */
@Service
@Transactional(readOnly = true)
public class ShopCustomerAccountService extends CrudService<ShopCustomerAccountDao, ShopCustomerAccount> {

	@Autowired
	private ShopCustomerAccountDao shopCustomerAccountDao;
	
	public ShopCustomerAccount get(String id) {
		return super.get(id);
	}
	
	public List<ShopCustomerAccount> findList(ShopCustomerAccount shopCustomerAccount) {
		return super.findList(shopCustomerAccount);
	}
	
	public Page<ShopCustomerAccount> findPage(Page<ShopCustomerAccount> page, ShopCustomerAccount shopCustomerAccount) {
		return super.findPage(page, shopCustomerAccount);
	}
	
	@Transactional(readOnly = false)
	public void save(ShopCustomerAccount shopCustomerAccount) {
		super.save(shopCustomerAccount);
	}
	
	@Transactional(readOnly = false)
	public void delete(ShopCustomerAccount shopCustomerAccount) {
		super.delete(shopCustomerAccount);
	}

	public List<ShopCustomerAccount> findCountPage(ShopCustomerAccount shopCustomerAccount) {
		// TODO Auto-generated method stub
		return shopCustomerAccountDao.findCountPage(shopCustomerAccount);
	}
	
}