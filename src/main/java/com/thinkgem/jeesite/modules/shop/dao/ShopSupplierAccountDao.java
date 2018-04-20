/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.shop.dao;

import java.util.List;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.shop.entity.ShopSupplierAccount;

/**
 * 供应商付款DAO接口
 * @author swbssd
 * @version 2018-04-19
 */
@MyBatisDao
public interface ShopSupplierAccountDao extends CrudDao<ShopSupplierAccount> {
	/**
	 * 求和统计某供应商
	 * @param shopSupplierAccount
	 * @return
	 */
	public List<ShopSupplierAccount> findCountPage(ShopSupplierAccount shopSupplierAccount);
}