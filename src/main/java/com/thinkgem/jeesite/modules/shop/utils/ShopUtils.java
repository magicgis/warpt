package com.thinkgem.jeesite.modules.shop.utils;

import java.util.List;

import com.thinkgem.jeesite.common.utils.SpringContextHolder;
import com.thinkgem.jeesite.modules.shop.entity.ShopProduct;
import com.thinkgem.jeesite.modules.shop.entity.ShopStockInfo;
import com.thinkgem.jeesite.modules.shop.entity.ShopStockItem;
import com.thinkgem.jeesite.modules.shop.service.ShopProductService;
import com.thinkgem.jeesite.modules.shop.service.ShopStockInfoService;
import com.thinkgem.jeesite.modules.shop.service.ShopStockItemService;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;

public class ShopUtils {

	/**
	 * 新增或修改仓库之后，初始化产品当前仓库库存
	 * 
	 * @param shopStockInfo
	 */
	public static void saveStockItemByStock(ShopStockInfo shopStockInfo) {
		ShopProductService shopProductService = SpringContextHolder.getBean("shopProductService");
		ShopStockItemService shopStockItemService = SpringContextHolder.getBean("shopStockItemService");
		// 判断是修改还是新增
		ShopProduct parm = new ShopProduct();
		parm.setOfficeId(shopStockInfo.getOfficeId());
		List<ShopProduct> productList = shopProductService.findList(parm);
		for (ShopProduct shopProduct : productList) {
			// 判断是修改还是新增
			ShopStockItem pram = new ShopStockItem();
			pram.setOfficeId(shopStockInfo.getOfficeId());
			pram.setProductId(shopProduct.getId());
			pram.setStockId(shopStockInfo.getId());
			List<ShopStockItem> itemList = shopStockItemService.findList(pram);
			ShopStockItem shopStockItem = null;
			if (itemList.isEmpty()) { // 产品无库存表初始化
				shopStockItem = new ShopStockItem();
				shopStockItem.setStockNum(0); // 库存初始化
			} else { // 产品无有库存表
				shopStockItem = itemList.get(0);
			}
			shopStockItem.setOfficeId(shopStockInfo.getOfficeId());
			shopStockItem.setStockId(shopStockInfo.getId());
			shopStockItem.setStockName(shopStockInfo.getStockName());
			shopStockItem.setProductTypeId(shopProduct.getProductTypeId());
			shopStockItem.setProductName(shopProduct.getProductName());
			shopStockItem.setProductId(shopProduct.getId());
			shopStockItem.setProductName(shopProduct.getProductName());
			shopStockItem.setProductNo(shopProduct.getProductNo());
			shopStockItem.setWarnStock(shopProduct.getWarnStock());
			shopStockItemService.save(shopStockItem);
		}
	}

	/**
	 * 新增或修改产品之后，初始化各个仓库该产品的库存
	 * 
	 * @param shopStockInfo
	 */
	public static void saveStockItemByProduct(ShopProduct shopProduct) {
		ShopStockItemService shopStockItemService = SpringContextHolder.getBean("shopStockItemService");
		ShopStockInfoService shopStockInfoService = SpringContextHolder.getBean("shopStockInfoService");
		// 仓库
		ShopStockInfo parm = new ShopStockInfo();
		parm.setOfficeId(shopProduct.getOfficeId());
		List<ShopStockInfo> stockList = shopStockInfoService.findList(parm);
		for (ShopStockInfo shopStockInfo : stockList) {
			// 判断是修改还是新增
			ShopStockItem pram = new ShopStockItem();
			pram.setOfficeId(shopStockInfo.getOfficeId());
			pram.setProductId(shopProduct.getId());
			pram.setStockId(shopStockInfo.getId());
			List<ShopStockItem> itemList = shopStockItemService.findList(pram);
			ShopStockItem shopStockItem = null;
			if (itemList.isEmpty()) {
				shopStockItem = new ShopStockItem();
				shopStockItem.setStockNum(0); // 库存初始化
			} else {
				shopStockItem = itemList.get(0);
			}
			shopStockItem.setOfficeId(shopStockInfo.getOfficeId());
			shopStockItem.setStockId(shopStockInfo.getId());
			shopStockItem.setStockName(shopStockInfo.getStockName());
			shopStockItem.setProductTypeId(shopProduct.getProductTypeId());
			shopStockItem.setProductName(shopProduct.getProductName());
			shopStockItem.setProductId(shopProduct.getId());
			shopStockItem.setProductName(shopProduct.getProductName());
			shopStockItem.setProductNo(shopProduct.getProductNo());
			shopStockItem.setWarnStock(shopProduct.getWarnStock());
			shopStockItemService.save(shopStockItem);
		}
	}

}
