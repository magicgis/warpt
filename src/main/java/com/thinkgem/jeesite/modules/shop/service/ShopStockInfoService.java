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
import com.thinkgem.jeesite.modules.shop.dao.ShopStockInfoDao;
import com.thinkgem.jeesite.modules.shop.entity.ShopProduct;
import com.thinkgem.jeesite.modules.shop.entity.ShopStockInfo;
import com.thinkgem.jeesite.modules.shop.entity.ShopStockItem;
import com.thinkgem.jeesite.modules.shop.utils.ShopUtils;

/**
 * 仓库基础信息Service
 * @author swbssd
 * @version 2018-04-06
 */
@Service
@Transactional(readOnly = true)
public class ShopStockInfoService extends CrudService<ShopStockInfoDao, ShopStockInfo> {
	
	public ShopStockInfo get(String id) {
		return super.get(id);
	}
	
	public List<ShopStockInfo> findList(ShopStockInfo shopStockInfo) {
		return super.findList(shopStockInfo);
	}
	
	public Page<ShopStockInfo> findPage(Page<ShopStockInfo> page, ShopStockInfo shopStockInfo) {
		return super.findPage(page, shopStockInfo);
	}
	
	@Transactional(readOnly = false)
	public void save(ShopStockInfo shopStockInfo) {
		super.save(shopStockInfo);
		//新增或修改仓库之后，初始化产品当前仓库库存
		ShopUtils.saveStockItemByStock(shopStockInfo);
	}
	
	@Transactional(readOnly = false)
	public void delete(ShopStockInfo shopStockInfo) {
		super.delete(shopStockInfo);
	}
	
}