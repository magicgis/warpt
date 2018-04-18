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
import com.thinkgem.jeesite.modules.shop.entity.ShopPurchaseOrder;
import com.thinkgem.jeesite.modules.shop.dao.ShopPurchaseOrderDao;
import com.thinkgem.jeesite.modules.shop.entity.ShopPurchaseOrderItem;
import com.thinkgem.jeesite.modules.shop.entity.ShopPurchaseSupplier;
import com.thinkgem.jeesite.modules.shop.entity.ShopStockInfo;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import com.thinkgem.jeesite.modules.shop.dao.ShopPurchaseOrderItemDao;

/**
 * 商品采购单Service
 * @author swbssd
 * @version 2018-04-06
 */
@Service
@Transactional(readOnly = true)
public class ShopPurchaseOrderService extends CrudService<ShopPurchaseOrderDao, ShopPurchaseOrder> {

	@Autowired
	private ShopPurchaseOrderItemDao shopPurchaseOrderItemDao;
	@Autowired
	private ShopPurchaseSupplierService shopPurchaseSupplierService;
	@Autowired
	private ShopStockInfoService shopStockInfoService;
	@Autowired
	private ShopProductService shopProductService;
	
	public ShopPurchaseOrder get(String id) {
		ShopPurchaseOrder shopPurchaseOrder = super.get(id);
		shopPurchaseOrder.setShopPurchaseOrderItemList(shopPurchaseOrderItemDao.findList(new ShopPurchaseOrderItem(shopPurchaseOrder)));
		return shopPurchaseOrder;
	}
	
	public ShopPurchaseOrder getEdit(String id) throws Exception {
		ShopPurchaseOrder shopPurchaseOrder = null;
		//仓库
		ShopStockInfo parm = new ShopStockInfo();
		parm.setOfficeId(UserUtils.getUser().getOffice().getId());
		List<ShopStockInfo> stockList = shopStockInfoService.findList(parm);
		//供应商
		ShopPurchaseSupplier parm2 = new ShopPurchaseSupplier();
		parm2.setOfficeId(UserUtils.getUser().getOffice().getId());
		List<ShopPurchaseSupplier> supplierList = shopPurchaseSupplierService.findList(parm2);
		//商品
		ShopProduct parm3 = new ShopProduct();
		parm3.setOfficeId(UserUtils.getUser().getOffice().getId());
		List<ShopProduct> productList = shopProductService.findList(parm3);
		if(stockList.isEmpty() || supplierList.isEmpty() || productList.isEmpty()) {
			throw new Exception("请先初始化商品、供应商、仓库，再进行单据录入。");
		}
		//获取对象
		if(!StringUtils.isEmpty(id)) {
			shopPurchaseOrder = get(id);
		}else {
			shopPurchaseOrder = new ShopPurchaseOrder();
			//新增初始化=========
			//默认第一个仓库
			ShopStockInfo shopStockInfo = stockList.get(0);
			shopPurchaseOrder.setStockId(shopStockInfo.getId());
			shopPurchaseOrder.setStockName(shopStockInfo.getStockName());
			//默认第一个供应商
			ShopPurchaseSupplier shopPurchaseSupplier = supplierList.get(0);
			shopPurchaseOrder.setSupplierId(shopPurchaseSupplier.getId());
			shopPurchaseOrder.setSupplierName(shopPurchaseSupplier.getSupplierName());
			//初始化字表
//			ShopPurchaseOrderItem initItem = new ShopPurchaseOrderItem();
//			initItem.setProductId(""); //商品默认不选择
//			shopPurchaseOrder.getShopPurchaseOrderItemList().add(initItem);
//			shopPurchaseOrder.getShopPurchaseOrderItemList().add(initItem);
//			shopPurchaseOrder.getShopPurchaseOrderItemList().add(initItem);
		}
		
		//对象绑定回去
		shopPurchaseOrder.setStockList(stockList);
		shopPurchaseOrder.setSupplierList(supplierList);
		shopPurchaseOrder.setProductList(productList);
				
		return shopPurchaseOrder;
	}	
	
	public List<ShopPurchaseOrder> findList(ShopPurchaseOrder shopPurchaseOrder) {
		return super.findList(shopPurchaseOrder);
	}
	
	public Page<ShopPurchaseOrder> findPage(Page<ShopPurchaseOrder> page, ShopPurchaseOrder shopPurchaseOrder) {
		return super.findPage(page, shopPurchaseOrder);
	}
	
	@Transactional(readOnly = false)
	public void save(ShopPurchaseOrder shopPurchaseOrder) {
		super.save(shopPurchaseOrder);
		for (ShopPurchaseOrderItem shopPurchaseOrderItem : shopPurchaseOrder.getShopPurchaseOrderItemList()){
			if (shopPurchaseOrderItem.getId() == null){
				continue;
			}
			if (ShopPurchaseOrderItem.DEL_FLAG_NORMAL.equals(shopPurchaseOrderItem.getDelFlag())){
				if (StringUtils.isBlank(shopPurchaseOrderItem.getId())){
					shopPurchaseOrderItem.setShopPurchaseOrder(shopPurchaseOrder);
					shopPurchaseOrderItem.preInsert();
					shopPurchaseOrderItemDao.insert(shopPurchaseOrderItem);
				}else{
					shopPurchaseOrderItem.preUpdate();
					shopPurchaseOrderItemDao.update(shopPurchaseOrderItem);
				}
			}else{
				shopPurchaseOrderItemDao.delete(shopPurchaseOrderItem);
			}
		}
	}
	
	@Transactional(readOnly = false)
	public void delete(ShopPurchaseOrder shopPurchaseOrder) {
		super.delete(shopPurchaseOrder);
		shopPurchaseOrderItemDao.delete(new ShopPurchaseOrderItem(shopPurchaseOrder));
	}
	
}