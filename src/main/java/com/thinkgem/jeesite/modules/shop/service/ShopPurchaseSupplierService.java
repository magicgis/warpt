/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.shop.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.shop.entity.ShopPurchaseSupplier;
import com.thinkgem.jeesite.modules.shop.dao.ShopPurchaseSupplierDao;

/**
 * 供应商Service
 * @author swbssd
 * @version 2018-04-12
 */
@Service
@Transactional(readOnly = true)
public class ShopPurchaseSupplierService extends CrudService<ShopPurchaseSupplierDao, ShopPurchaseSupplier> {

	public ShopPurchaseSupplier get(String id) {
		return super.get(id);
	}
	
	public List<ShopPurchaseSupplier> findList(ShopPurchaseSupplier shopPurchaseSupplier) {
		return super.findList(shopPurchaseSupplier);
	}
	
	public Page<ShopPurchaseSupplier> findPage(Page<ShopPurchaseSupplier> page, ShopPurchaseSupplier shopPurchaseSupplier) {
		return super.findPage(page, shopPurchaseSupplier);
	}
	
	@Transactional(readOnly = false)
	public void save(ShopPurchaseSupplier shopPurchaseSupplier) {
		super.save(shopPurchaseSupplier);
	}
	
	@Transactional(readOnly = false)
	public void delete(ShopPurchaseSupplier shopPurchaseSupplier) {
		super.delete(shopPurchaseSupplier);
	}
	
}