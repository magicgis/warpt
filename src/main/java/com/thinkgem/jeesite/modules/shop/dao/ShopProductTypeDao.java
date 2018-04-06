/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.shop.dao;

import com.thinkgem.jeesite.common.persistence.TreeDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.shop.entity.ShopProductType;

/**
 * 商品类型DAO接口
 * @author swbssd
 * @version 2018-04-06
 */
@MyBatisDao
public interface ShopProductTypeDao extends TreeDao<ShopProductType> {
	
}