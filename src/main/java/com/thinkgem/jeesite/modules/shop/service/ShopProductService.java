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
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.shop.entity.ShopProduct;
import com.thinkgem.jeesite.modules.shop.dao.ShopProductDao;
import com.thinkgem.jeesite.modules.shop.entity.ShopProductPrice;
import com.thinkgem.jeesite.modules.shop.dao.ShopProductPriceDao;

/**
 * 商品基本信息Service
 * @author swbssd
 * @version 2018-04-06
 */
@Service
@Transactional(readOnly = true)
public class ShopProductService extends CrudService<ShopProductDao, ShopProduct> {

	@Autowired
	private ShopProductPriceDao shopProductPriceDao;
	
	public ShopProduct get(String id) {
		ShopProduct shopProduct = super.get(id);
		shopProduct.setShopProductPriceList(shopProductPriceDao.findList(new ShopProductPrice(shopProduct)));
		return shopProduct;
	}
	
	public List<ShopProduct> findList(ShopProduct shopProduct) {
		return super.findList(shopProduct);
	}
	
	public Page<ShopProduct> findPage(Page<ShopProduct> page, ShopProduct shopProduct) {
		return super.findPage(page, shopProduct);
	}
	
	@Transactional(readOnly = false)
	public void save(ShopProduct shopProduct) {
		super.save(shopProduct);
		for (ShopProductPrice shopProductPrice : shopProduct.getShopProductPriceList()){
			if (shopProductPrice.getId() == null){
				continue;
			}
			if (ShopProductPrice.DEL_FLAG_NORMAL.equals(shopProductPrice.getDelFlag())){
				if (StringUtils.isBlank(shopProductPrice.getId())){
					shopProductPrice.setShopProduct(shopProduct);
					shopProductPrice.preInsert();
					shopProductPriceDao.insert(shopProductPrice);
				}else{
					shopProductPrice.preUpdate();
					shopProductPriceDao.update(shopProductPrice);
				}
			}else{
				shopProductPriceDao.delete(shopProductPrice);
			}
		}
		//判断是否没价格了，如果没有则报错
		List<ShopProductPrice> shopProductPriceList = shopProductPriceDao.findList(new ShopProductPrice(shopProduct));
		if(shopProductPriceList==null || shopProductPriceList.isEmpty()) {
			throw new RuntimeException("销售价格表为空，保存失败，请录入销售价格再保存!");
		}
	}
	
	@Transactional(readOnly = false)
	public void delete(ShopProduct shopProduct) {
		super.delete(shopProduct);
		shopProductPriceDao.delete(new ShopProductPrice(shopProduct));
	}
	
}