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
import com.thinkgem.jeesite.modules.shop.dao.ShopStockItemDao;
import com.thinkgem.jeesite.modules.shop.entity.ShopStockItem;

/**
 * 仓库库存Service
 * 
 * @author swbssd
 * @version 2018-04-17
 */
@Service
@Transactional(readOnly = true)
public class ShopStockItemService extends CrudService<ShopStockItemDao, ShopStockItem> {
	@Autowired
	private ShopStockItemDao shopStockItemDao;

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
	public void delete(ShopStockItem shopStockItem) {
		super.delete(shopStockItem);
	}

	@Transactional(readOnly = false)
	public void save(ShopStockItem shopStockItem) {
		super.save(shopStockItem);
	}

	/**
	 * 产品库存总数
	 * 
	 * @param shopStockItem
	 * @return
	 */
	public ShopStockItem findProductStockNum(ShopStockItem shopStockItem) {
		List<ShopStockItem> stockItemList = shopStockItemDao.findProductStockNum(shopStockItem);
		return stockItemList.isEmpty() ? null : stockItemList.get(0);
	}

	public void deleteByProductId(ShopStockItem shopStockItem) {
		// TODO Auto-generated method stub
		shopStockItemDao.deleteByProductId(shopStockItem);
	}

}