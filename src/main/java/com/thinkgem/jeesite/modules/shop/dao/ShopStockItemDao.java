/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.shop.dao;

import java.util.List;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.shop.entity.ShopStockItem;
import com.thinkgem.jeesite.modules.sys.entity.Menu;

/**
 * 仓库库存DAO接口
 * @author swbssd
 * @version 2018-04-17
 */
@MyBatisDao
public interface ShopStockItemDao extends CrudDao<ShopStockItem> {
	/**
	 * 产品库存总数
	 * @param shopStockItem
	 * @return
	 */
	public List<ShopStockItem> findProductStockNum(ShopStockItem shopStockItem);

	public void deleteByProductId(ShopStockItem shopStockItem);
}