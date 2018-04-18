/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.shop.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.shop.entity.ShopStockItem;

/**
 * 仓库库存DAO接口
 * @author swbssd
 * @version 2018-04-17
 */
@MyBatisDao
public interface ShopStockItemDao extends CrudDao<ShopStockItem> {
	
}