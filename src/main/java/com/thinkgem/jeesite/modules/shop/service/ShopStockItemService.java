/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.shop.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.shop.entity.ShopStockItem;
import com.thinkgem.jeesite.modules.shop.dao.ShopStockItemDao;

/**
 * 仓库库存Service
 * @author swbssd
 * @version 2018-04-17
 */
@Service
@Transactional(readOnly = true)
public class ShopStockItemService extends CrudService<ShopStockItemDao, ShopStockItem> {

	public ShopStockItem get(String id) {
		return super.get(id);
	}
	
	public List<ShopStockItem> findList(ShopStockItem shopStockItem) {
		return super.findList(shopStockItem);
	}
	
	public Page<ShopStockItem> findPage(Page<ShopStockItem> page, ShopStockItem shopStockItem) {
		return super.findPage(page, shopStockItem);
	}
	
	@Transactional(readOnly = false)
	public void save(ShopStockItem shopStockItem) {
		super.save(shopStockItem);
	}
	
	@Transactional(readOnly = false)
	public void delete(ShopStockItem shopStockItem) {
		super.delete(shopStockItem);
	}
	
}