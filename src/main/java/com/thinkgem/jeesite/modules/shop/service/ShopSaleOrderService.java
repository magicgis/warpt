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
import com.thinkgem.jeesite.common.utils.DateUtils;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.shop.dao.ShopSaleOrderDao;
import com.thinkgem.jeesite.modules.shop.dao.ShopSaleOrderItemDao;
import com.thinkgem.jeesite.modules.shop.entity.ShopCustomerAccount;
import com.thinkgem.jeesite.modules.shop.entity.ShopCustomerInfo;
import com.thinkgem.jeesite.modules.shop.entity.ShopCustomerLevel;
import com.thinkgem.jeesite.modules.shop.entity.ShopProduct;
import com.thinkgem.jeesite.modules.shop.entity.ShopProductPrice;
import com.thinkgem.jeesite.modules.shop.entity.ShopSaleOrder;
import com.thinkgem.jeesite.modules.shop.entity.ShopSaleOrderItem;
import com.thinkgem.jeesite.modules.shop.entity.ShopStockInfo;
import com.thinkgem.jeesite.modules.shop.entity.ShopStockItem;
import com.thinkgem.jeesite.modules.shop.utils.ShopUtils;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;

/**
 * 销售订单Service
 * @author swbssd
 * @version 2018-04-20
 */
@Service
@Transactional(readOnly = true)
public class ShopSaleOrderService extends CrudService<ShopSaleOrderDao, ShopSaleOrder> {

	@Autowired
	private ShopSaleOrderItemDao shopSaleOrderItemDao;
	@Autowired
	private ShopCustomerInfoService shopCustomerInfoService;
	@Autowired
	private ShopCustomerLevelService shopCustomerLevelService;
	@Autowired
	private ShopStockInfoService shopStockInfoService;
	@Autowired
	private ShopStockItemService shopStockItemService;
	@Autowired
	private ShopProductService shopProductService;
	@Autowired
	private ShopCustomerAccountService shopCustomerAccountService;
	
	public ShopSaleOrder get(String id) {
		ShopSaleOrder shopSaleOrder = super.get(id);
		shopSaleOrder.setShopSaleOrderItemList(shopSaleOrderItemDao.findList(new ShopSaleOrderItem(shopSaleOrder)));
		return shopSaleOrder;
	}
	
	public ShopSaleOrder getEdit(String id) throws Exception {
		ShopSaleOrder shopSaleOrder = null;
		// 仓库
		ShopStockInfo parm = new ShopStockInfo();
		parm.setOfficeId(UserUtils.getUser().getOffice().getId());
		List<ShopStockInfo> stockList = shopStockInfoService.findList(parm);
		// 客户
		ShopCustomerInfo parm2 = new ShopCustomerInfo();
		parm2.setOfficeId(UserUtils.getUser().getOffice().getId());
		List<ShopCustomerInfo> customerList = shopCustomerInfoService.findList(parm2);
		// 商品
		ShopProduct parm3 = new ShopProduct();
		parm3.setOfficeId(UserUtils.getUser().getOffice().getId());
		List<ShopProduct> productList = shopProductService.findList(parm3);
		if (stockList.isEmpty() || customerList.isEmpty() || productList.isEmpty()) {
			throw new Exception("请先初始化商品、客户、仓库，再进行单据录入。");
		}
		// 获取对象
		if (!StringUtils.isEmpty(id)) {
			shopSaleOrder = get(id);
		} else {
			shopSaleOrder = new ShopSaleOrder();
			shopSaleOrder.setSubjectType(ShopUtils.SUBJECT_TYPE_1000);
			shopSaleOrder.setBusinData(DateUtils.getDate());
			// 新增初始化=========
			// 默认第一个仓库
			ShopStockInfo shopStockInfo = stockList.get(0);
			shopSaleOrder.setStockId(shopStockInfo.getId());
			shopSaleOrder.setStockName(shopStockInfo.getStockName());
			// 默认第一个客户
			ShopCustomerInfo customer = customerList.get(0);
			shopSaleOrder.setCustomerId(customer.getId());
			shopSaleOrder.setCustomerName(customer.getCustomerName());
		}
		// 对象绑定回去
		shopSaleOrder.setStockList(stockList);
		shopSaleOrder.setCustomerList(customerList);
		shopSaleOrder.setProductList(productList);

		return shopSaleOrder;
	}
	
	public List<ShopSaleOrder> findList(ShopSaleOrder shopSaleOrder) {
		return super.findList(shopSaleOrder);
	}
	
	public Page<ShopSaleOrder> findPage(Page<ShopSaleOrder> page, ShopSaleOrder shopSaleOrder) {
		return super.findPage(page, shopSaleOrder);
	}
	
	@Transactional(readOnly = false)
	public void saveOrder(ShopSaleOrder shopSaleOrder) {
		shopSaleOrder.setOfficeId(UserUtils.getUser().getOffice().getId());
		shopSaleOrder.setIsNewRecord(false); // 自动生成ID
		shopSaleOrder.setState(ShopUtils.STATE_TYPE_2); //直接入库TO.
		//进货退货判断正负
		int subject = 1;
		if(StringUtils.equals(shopSaleOrder.getSubjectType(), ShopUtils.SUBJECT_TYPE_1001)) {
			subject = -1;
		}
		// 生产单据编号
		shopSaleOrder.setSaleNo(ShopUtils.generateBillCode("XS"));
		super.save(shopSaleOrder);
		for (ShopSaleOrderItem shopSaleOrderItem : shopSaleOrder.getShopSaleOrderItemList()) {
			if (shopSaleOrderItem.getProductId() == null) {
				continue;
			}
			shopSaleOrderItem.setIsNewRecord(false); // 自动生成ID
			shopSaleOrderItem.setShopSaleOrder(shopSaleOrder);
			shopSaleOrderItem.preInsert();
			shopSaleOrderItemDao.insert(shopSaleOrderItem);
			// 更新该产品库存因为销售是出库，所以这里负数
			ShopUtils.updateProductStockNum(shopSaleOrder.getOfficeId(), shopSaleOrder.getStockId(),
					shopSaleOrder.getStockName(), shopSaleOrderItem.getProductId(),
					shopSaleOrderItem.getSaleNum()*subject*-1);
		}

		// 新增客户付款单
		ShopCustomerAccount shopCustomerAccount = new ShopCustomerAccount();
		shopCustomerAccount.setOfficeId(shopSaleOrder.getOfficeId());
		shopCustomerAccount.setCustomerId(shopSaleOrder.getCustomerId());
		shopCustomerAccount.setCustomerName(shopSaleOrder.getCustomerName());
		shopCustomerAccount.setSaleId(shopSaleOrder.getId());
		shopCustomerAccount.setBusinData(shopSaleOrder.getBusinData());
		shopCustomerAccount.setAccountNo(shopSaleOrder.getSaleNo());
		shopCustomerAccount.setSubjectType(shopSaleOrder.getSubjectType());
		shopCustomerAccount.setMeetMoney(shopSaleOrder.getOrderSum()*subject);
		shopCustomerAccount.setFactMoney(shopSaleOrder.getSendSum()*subject);
		shopCustomerAccount.setLessMoney(
				ShopUtils.subtract(shopCustomerAccount.getMeetMoney(), shopCustomerAccount.getFactMoney()));
		shopCustomerAccountService.save(shopCustomerAccount);
	}
	
	@Transactional(readOnly = false)
	public void deleteOrder(ShopSaleOrder shopSaleOrder) throws Exception {
		// 还原库存量
		shopSaleOrder = this.get(shopSaleOrder.getId());
		List<ShopSaleOrderItem> orderItem = shopSaleOrder.getShopSaleOrderItemList();
		for (ShopSaleOrderItem shopSaleOrderItem : orderItem) {
			// 增加该产品库存
			ShopUtils.updateProductStockNum(shopSaleOrder.getOfficeId(), shopSaleOrder.getStockId(),
					shopSaleOrder.getStockName(), shopSaleOrderItem.getProductId(),
					shopSaleOrderItem.getSaleNum());
		}
		//删除付款客户
		ShopCustomerAccount parm = new ShopCustomerAccount();
		parm.setOfficeId(shopSaleOrder.getOfficeId());
		parm.setCustomerId(shopSaleOrder.getCustomerId());
		List<ShopCustomerAccount> customerAccountList = shopCustomerAccountService.findList(parm);
		shopCustomerAccountService.delete(customerAccountList.get(0));
		
		super.delete(shopSaleOrder);
		shopSaleOrderItemDao.delete(new ShopSaleOrderItem(shopSaleOrder));
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
	public ShopSaleOrderItem addOrderItem(String productNo, String productId, String stockId, String customerId)
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
			productId = productList.get(0).getId();
		} else { // 选择商品
		}
		//或者商品和价格表
		shopProduct = shopProductService.get(productId);
		// 仓库库存查询
		// ShopStockInfo stockInfo = shopStockInfoService.get(stockId);
		ShopStockItem stockItemParm = new ShopStockItem();
		stockItemParm.setProductId(shopProduct.getId());
		stockItemParm.setStockId(stockId);
		ShopStockItem stockItem = shopStockItemService.findProductStockNum(stockItemParm);
		// 客户查询
		ShopCustomerInfo customer = shopCustomerInfoService.get(customerId);
		//客户的优惠级别
		//ShopCustomerLevel shopCustomerLevel = shopCustomerLevelService.get(customer.getLevelId());
		List<ShopProductPrice> priceList = shopProduct.getShopProductPriceList();
		double saleMoney = 0.0;
		for (ShopProductPrice shopProductPrice : priceList) {
			if(StringUtils.equals(shopProductPrice.getLevelId(),customer.getLevelId())) {
				saleMoney = shopProductPrice.getDiscountPrice();
				break;
			}
		}
		if(saleMoney == 0) {
			throw new Exception("没有找到该优惠级别价格，请设置优惠级别价格并且关联至客户再进行销售。");
		}

		// 初始化开始
		ShopSaleOrderItem orderItem = new ShopSaleOrderItem();
		orderItem.setProductId(shopProduct.getId());
		orderItem.setProductName(shopProduct.getProductName());
		orderItem.setProductNo(shopProduct.getProductNo());
		orderItem.setUnit(shopProduct.getUnit());
		orderItem.setSpec(shopProduct.getSpec());
		orderItem.setSaleNum(1); // 默认采购1个
		orderItem.setStockNum(stockItem.getStockNum());
		//销售价格获取
		orderItem.setSaleMoney(saleMoney);
		orderItem.setDiscount(customer.getDiscount());
		orderItem.setDisMoney(ShopUtils.multiply(orderItem.getSaleMoney(), customer.getDiscount(), 0.01));
		orderItem.setAllMoney(ShopUtils.multiply(orderItem.getSaleNum(), orderItem.getSaleMoney()));
		orderItem.setCountMoney(ShopUtils.multiply(orderItem.getSaleNum(), orderItem.getSaleMoney(),
				customer.getDiscount(), 0.01));

		return orderItem;
	}
	
}