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
import com.thinkgem.jeesite.modules.shop.entity.ShopPurchaseOrder;
import com.thinkgem.jeesite.modules.shop.dao.ShopPurchaseOrderDao;
import com.thinkgem.jeesite.modules.shop.entity.ShopPurchaseOrderItem;
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
	
	public ShopPurchaseOrder get(String id) {
		ShopPurchaseOrder shopPurchaseOrder = super.get(id);
		shopPurchaseOrder.setShopPurchaseOrderItemList(shopPurchaseOrderItemDao.findList(new ShopPurchaseOrderItem(shopPurchaseOrder)));
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