/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.shop.dao;

import java.util.List;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.shop.entity.ShopCustomerAccount;

/**
 * 客户收款DAO接口
 * @author swbssd
 * @version 2018-04-20
 */
@MyBatisDao
public interface ShopCustomerAccountDao extends CrudDao<ShopCustomerAccount> {
	/**
	 * 求和统计某客户
	 * @param shopSupplierAccount
	 * @return
	 */
	public List<ShopCustomerAccount> findCountPage(ShopCustomerAccount shopCustomerAccount);
	
}