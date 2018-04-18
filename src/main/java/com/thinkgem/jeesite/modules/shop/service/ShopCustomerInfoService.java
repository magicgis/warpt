/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.shop.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.shop.entity.ShopCustomerInfo;
import com.thinkgem.jeesite.modules.shop.dao.ShopCustomerInfoDao;

/**
 * 销售客户Service
 * @author swbssd
 * @version 2018-04-17
 */
@Service
@Transactional(readOnly = true)
public class ShopCustomerInfoService extends CrudService<ShopCustomerInfoDao, ShopCustomerInfo> {

	public ShopCustomerInfo get(String id) {
		return super.get(id);
	}
	
	public List<ShopCustomerInfo> findList(ShopCustomerInfo shopCustomerInfo) {
		return super.findList(shopCustomerInfo);
	}
	
	public Page<ShopCustomerInfo> findPage(Page<ShopCustomerInfo> page, ShopCustomerInfo shopCustomerInfo) {
		return super.findPage(page, shopCustomerInfo);
	}
	
	@Transactional(readOnly = false)
	public void save(ShopCustomerInfo shopCustomerInfo) {
		super.save(shopCustomerInfo);
	}
	
	@Transactional(readOnly = false)
	public void delete(ShopCustomerInfo shopCustomerInfo) {
		super.delete(shopCustomerInfo);
	}
	
}