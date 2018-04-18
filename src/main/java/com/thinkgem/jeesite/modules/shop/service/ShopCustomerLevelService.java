/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.shop.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.shop.entity.ShopCustomerLevel;
import com.thinkgem.jeesite.modules.shop.dao.ShopCustomerLevelDao;

/**
 * 客户级别Service
 * @author swbssd
 * @version 2018-04-17
 */
@Service
@Transactional(readOnly = true)
public class ShopCustomerLevelService extends CrudService<ShopCustomerLevelDao, ShopCustomerLevel> {

	public ShopCustomerLevel get(String id) {
		return super.get(id);
	}
	
	public List<ShopCustomerLevel> findList(ShopCustomerLevel shopCustomerLevel) {
		return super.findList(shopCustomerLevel);
	}
	
	public Page<ShopCustomerLevel> findPage(Page<ShopCustomerLevel> page, ShopCustomerLevel shopCustomerLevel) {
		return super.findPage(page, shopCustomerLevel);
	}
	
	@Transactional(readOnly = false)
	public void save(ShopCustomerLevel shopCustomerLevel) {
		super.save(shopCustomerLevel);
	}
	
	@Transactional(readOnly = false)
	public void delete(ShopCustomerLevel shopCustomerLevel) {
		super.delete(shopCustomerLevel);
	}
	
}