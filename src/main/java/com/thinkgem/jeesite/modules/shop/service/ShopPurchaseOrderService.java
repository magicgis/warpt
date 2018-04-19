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
import com.thinkgem.jeesite.modules.shop.dao.ShopPurchaseOrderDao;
import com.thinkgem.jeesite.modules.shop.dao.ShopPurchaseOrderItemDao;
import com.thinkgem.jeesite.modules.shop.entity.ShopProduct;
import com.thinkgem.jeesite.modules.shop.entity.ShopPurchaseOrder;
import com.thinkgem.jeesite.modules.shop.entity.ShopPurchaseOrderItem;
import com.thinkgem.jeesite.modules.shop.entity.ShopPurchaseSupplier;
import com.thinkgem.jeesite.modules.shop.entity.ShopStockInfo;
import com.thinkgem.jeesite.modules.shop.entity.ShopStockItem;
import com.thinkgem.jeesite.modules.shop.entity.ShopSupplierAccount;
import com.thinkgem.jeesite.modules.shop.utils.ShopUtils;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;

/**
 * 商品采购单Service
 * 
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
	private ShopStockItemService shopStockItemService;
	@Autowired
	private ShopProductService shopProductService;
	@Autowired
	private ShopSupplierAccountService shopSupplierAccountService;

	public ShopPurchaseOrder get(String id) {
		ShopPurchaseOrder shopPurchaseOrder = super.get(id);
		shopPurchaseOrder.setShopPurchaseOrderItemList(
				shopPurchaseOrderItemDao.findList(new ShopPurchaseOrderItem(shopPurchaseOrder)));
		return shopPurchaseOrder;
	}

	public ShopPurchaseOrder getEdit(String id) throws Exception {
		ShopPurchaseOrder shopPurchaseOrder = null;
		// 仓库
		ShopStockInfo parm = new ShopStockInfo();
		parm.setOfficeId(UserUtils.getUser().getOffice().getId());
		List<ShopStockInfo> stockList = shopStockInfoService.findList(parm);
		// 供应商
		ShopPurchaseSupplier parm2 = new ShopPurchaseSupplier();
		parm2.setOfficeId(UserUtils.getUser().getOffice().getId());
		List<ShopPurchaseSupplier> supplierList = shopPurchaseSupplierService.findList(parm2);
		// 商品
		ShopProduct parm3 = new ShopProduct();
		parm3.setOfficeId(UserUtils.getUser().getOffice().getId());
		List<ShopProduct> productList = shopProductService.findList(parm3);
		if (stockList.isEmpty() || supplierList.isEmpty() || productList.isEmpty()) {
			throw new Exception("请先初始化商品、供应商、仓库，再进行单据录入。");
		}
		// 获取对象
		if (!StringUtils.isEmpty(id)) {
			shopPurchaseOrder = get(id);
		} else {
			shopPurchaseOrder = new ShopPurchaseOrder();
			shopPurchaseOrder.setSubjectType(ShopUtils.SUBJECT_TYPE_1002);
			// 新增初始化=========
			// 默认第一个仓库
			ShopStockInfo shopStockInfo = stockList.get(0);
			shopPurchaseOrder.setStockId(shopStockInfo.getId());
			shopPurchaseOrder.setStockName(shopStockInfo.getStockName());
			// 默认第一个供应商
			ShopPurchaseSupplier shopPurchaseSupplier = supplierList.get(0);
			shopPurchaseOrder.setSupplierId(shopPurchaseSupplier.getId());
			shopPurchaseOrder.setSupplierName(shopPurchaseSupplier.getSupplierName());
			// 初始化字表
			// ShopPurchaseOrderItem initItem = new ShopPurchaseOrderItem();
			// initItem.setProductId(""); //商品默认不选择
			// shopPurchaseOrder.getShopPurchaseOrderItemList().add(initItem);
			// shopPurchaseOrder.getShopPurchaseOrderItemList().add(initItem);
			// shopPurchaseOrder.getShopPurchaseOrderItemList().add(initItem);
		}

		// 对象绑定回去
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
	public void saveOrde(ShopPurchaseOrder shopPurchaseOrder) throws Exception {
		// 生产单据编号
		shopPurchaseOrder.setOrderNo(ShopUtils.generateBillCode("CG"));
		super.save(shopPurchaseOrder);
		for (ShopPurchaseOrderItem shopPurchaseOrderItem : shopPurchaseOrder.getShopPurchaseOrderItemList()) {
			if (shopPurchaseOrderItem.getProductId() == null) {
				continue;
			}
			shopPurchaseOrderItem.setShopPurchaseOrder(shopPurchaseOrder);
			shopPurchaseOrderItem.preInsert();
			shopPurchaseOrderItemDao.insert(shopPurchaseOrderItem);
			// 更新该产品库存
			ShopStockItem parm = new ShopStockItem();
			parm.setOfficeId(shopPurchaseOrder.getOfficeId());
			parm.setStockId(shopPurchaseOrder.getStockId());
			parm.setProductId(shopPurchaseOrderItem.getProductId());
			List<ShopStockItem> stockItemList = shopStockItemService.findList(parm);
			if (stockItemList.isEmpty()) { // 如果不存在商品，则创建
				ShopProduct shopProduct = shopProductService.get(shopPurchaseOrderItem.getProductId());
				ShopStockItem shopStockItem = new ShopStockItem();
				shopStockItem.setStockNum(shopPurchaseOrderItem.getStockNum()); // 库存初始化
				shopStockItem.setOfficeId(shopPurchaseOrder.getOfficeId());
				shopStockItem.setStockId(shopPurchaseOrder.getStockId());
				shopStockItem.setStockName(shopPurchaseOrder.getStockName());
				shopStockItem.setProductTypeId(shopProduct.getProductTypeId());
				shopStockItem.setProductId(shopPurchaseOrderItem.getProductId());
				shopStockItem.setProductName(shopPurchaseOrderItem.getProductName());
				shopStockItem.setProductNo(shopPurchaseOrderItem.getProductNo());
				shopStockItem.setWarnStock(shopProduct.getWarnStock());
				shopStockItemService.save(shopStockItem);
			} else if (stockItemList.size() == 1) { // 增加库存
				ShopStockItem shopStockItem = stockItemList.get(0);
				shopStockItem.setStockNum(shopPurchaseOrderItem.getStockNum() + shopStockItem.getStockNum());
				shopStockItemService.save(shopStockItem);
			} else {
				throw new Exception("库存存在多个该商品，不合法，请联系管理员处理！");
			}
		}

		// 新增供应商付款单
		ShopSupplierAccount shopSupplierAccount = new ShopSupplierAccount();
		shopSupplierAccount.setOfficeId(shopPurchaseOrder.getOfficeId());
		shopSupplierAccount.setSupplierId(shopPurchaseOrder.getSupplierId());
		shopSupplierAccount.setOrderId(shopPurchaseOrder.getId());
		shopSupplierAccount.setBusinData(shopPurchaseOrder.getBusinData());
		shopSupplierAccount.setAccountNo(shopPurchaseOrder.getOrderNo());
		shopSupplierAccount.setSubjectType(shopPurchaseOrder.getSubjectType());
		shopSupplierAccount.setMeetMoney(shopPurchaseOrder.getOrderSum());
		shopSupplierAccount.setFactMoney(shopPurchaseOrder.getSendSum());
		shopSupplierAccount.setLessMoney(
				ShopUtils.subtract(shopSupplierAccount.getMeetMoney(), shopSupplierAccount.getFactMoney()));
		shopSupplierAccountService.save(shopSupplierAccount);

	}

	@Transactional(readOnly = false)
	public void delete(ShopPurchaseOrder shopPurchaseOrder) {
		super.delete(shopPurchaseOrder);
		shopPurchaseOrderItemDao.delete(new ShopPurchaseOrderItem(shopPurchaseOrder));
	}

	/**
	 * 新增一个采购商品项
	 * 
	 * @param productId
	 * @param stockId
	 * @param supplierId
	 * @return
	 * @throws Exception
	 */
	public ShopPurchaseOrderItem addOrderItem(String productNo, String productId, String stockId, String supplierId)
			throws Exception {
		// 选择产品
		ShopProduct shopProduct = null;
		if (StringUtils.isEmpty(productId)) { // 扫描查询商品
			ShopProduct parm = new ShopProduct();
			parm.setProductNo(productNo);
			List<ShopProduct> productList = shopProductService.findList(parm);
			if (productList == null || productList.isEmpty()) {
				throw new Exception("无此条码商品!");
			}
			if (productList.size() > 1) {
				throw new Exception("商品条码存在重复，请进行修正后再录入!");
			}
			shopProduct = productList.get(0);
		} else { // 选择商品
			shopProduct = shopProductService.get(productId);
		}
		// 仓库库存查询
		// ShopStockInfo stockInfo = shopStockInfoService.get(stockId);
		ShopStockItem stockItemParm = new ShopStockItem();
		stockItemParm.setProductId(shopProduct.getId());
		stockItemParm.setStockId(stockId);
		ShopStockItem stockItem = shopStockItemService.findProductStockNum(stockItemParm);
		// 供应商查询
		ShopPurchaseSupplier supplier = shopPurchaseSupplierService.get(supplierId);

		// 初始化开始
		ShopPurchaseOrderItem orderItem = new ShopPurchaseOrderItem();
		orderItem.setProductId(shopProduct.getId());
		orderItem.setProductName(shopProduct.getProductName());
		orderItem.setProductNo(shopProduct.getProductNo());
		orderItem.setUnit(shopProduct.getUnit());
		orderItem.setSpec(shopProduct.getSpec());
		orderItem.setPurchaseNum(1); // 默认采购1个
		orderItem.setStockNum(stockItem.getStockNum());
		orderItem.setOrderMoney(shopProduct.getBuyPrice());
		orderItem.setDiscount(supplier.getDiscount());
		orderItem.setDisMoney(ShopUtils.multiply(shopProduct.getBuyPrice(), supplier.getDiscount(), 0.01));
		orderItem.setAllMoney(ShopUtils.multiply(orderItem.getPurchaseNum(), shopProduct.getBuyPrice()));
		orderItem.setCountMoney(ShopUtils.multiply(orderItem.getPurchaseNum(), shopProduct.getBuyPrice(),
				supplier.getDiscount(), 0.01));

		return orderItem;
	}

}