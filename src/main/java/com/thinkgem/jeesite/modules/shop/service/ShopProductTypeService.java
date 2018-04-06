/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.shop.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.service.TreeService;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.shop.entity.ShopProductType;
import com.thinkgem.jeesite.modules.shop.dao.ShopProductTypeDao;

/**
 * 商品类型Service
 * @author swbssd
 * @version 2018-04-06
 */
@Service
@Transactional(readOnly = true)
public class ShopProductTypeService extends TreeService<ShopProductTypeDao, ShopProductType> {

	public ShopProductType get(String id) {
		return super.get(id);
	}
	
	public List<ShopProductType> findList(ShopProductType shopProductType) {
		if (StringUtils.isNotBlank(shopProductType.getParentIds())){
			shopProductType.setParentIds(","+shopProductType.getParentIds()+",");
		}
		return super.findList(shopProductType);
	}
	
	@Transactional(readOnly = false)
	public void save(ShopProductType shopProductType) {
		super.save(shopProductType);
	}
	
	@Transactional(readOnly = false)
	public void delete(ShopProductType shopProductType) {
		super.delete(shopProductType);
	}
	
}