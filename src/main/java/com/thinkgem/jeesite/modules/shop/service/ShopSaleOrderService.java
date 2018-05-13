/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.shop.service;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.ss.usermodel.Row;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Lists;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.common.utils.DateUtils;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.utils.excel.ExportExcel;
import com.thinkgem.jeesite.modules.shop.dao.ShopSaleOrderDao;
import com.thinkgem.jeesite.modules.shop.dao.ShopSaleOrderItemDao;
import com.thinkgem.jeesite.modules.shop.entity.ShopCustomerAccount;
import com.thinkgem.jeesite.modules.shop.entity.ShopCustomerInfo;
import com.thinkgem.jeesite.modules.shop.entity.ShopProduct;
import com.thinkgem.jeesite.modules.shop.entity.ShopProductPrice;
import com.thinkgem.jeesite.modules.shop.entity.ShopSaleOrder;
import com.thinkgem.jeesite.modules.shop.entity.ShopSaleOrderItem;
import com.thinkgem.jeesite.modules.shop.entity.ShopStockInfo;
import com.thinkgem.jeesite.modules.shop.entity.ShopStockItem;
import com.thinkgem.jeesite.modules.shop.utils.ShopUtils;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import com.thinkgem.jeesite.modules.vip.entity.VipUserBase;
import com.thinkgem.jeesite.modules.vip.entity.VipUserCost;
import com.thinkgem.jeesite.modules.vip.service.VipUserBaseService;
import com.thinkgem.jeesite.modules.vip.service.VipUserCostService;

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
//	@Autowired
//	private ShopCustomerLevelService shopCustomerLevelService;
	@Autowired
	private ShopStockInfoService shopStockInfoService;
	@Autowired
	private ShopStockItemService shopStockItemService;
	@Autowired
	private ShopProductService shopProductService;
	@Autowired
	private ShopCustomerAccountService shopCustomerAccountService;
	@Autowired
	private VipUserCostService vipUserCostService;
	@Autowired
	private VipUserBaseService vipUserBaseService;
	
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
		for (ShopCustomerInfo shopCustomerInfo : customerList) {
			if(!StringUtils.isEmpty(shopCustomerInfo.getPhone())) {
				shopCustomerInfo.setCustomerName(shopCustomerInfo.getCustomerName()+"["+shopCustomerInfo.getPhone()+"]");
			}
		}
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
	
	public Page<ShopSaleOrderItem> findItemPage(Page<ShopSaleOrderItem> page, ShopSaleOrderItem shopSaleOrderItem) {
		shopSaleOrderItem.setPage(page);
		page.setList(shopSaleOrderItemDao.findItemPage(shopSaleOrderItem));
		return page;
	}
	
	public List<ShopSaleOrderItem> findSumItem(ShopSaleOrderItem shopSaleOrderItem) {
		return shopSaleOrderItemDao.findSumItem(shopSaleOrderItem);
	}	
	
	public Page<ShopSaleOrderItem> findGroupByProductPage(Page<ShopSaleOrderItem> page, ShopSaleOrderItem shopSaleOrderItem) {
		shopSaleOrderItem.setPage(page);
		page.setList(shopSaleOrderItemDao.findGroupByProductPage(shopSaleOrderItem));
		return page;
	}
	
	public Page<ShopSaleOrderItem> findGroupByCustomerPage(Page<ShopSaleOrderItem> page, ShopSaleOrderItem shopSaleOrderItem) {
		shopSaleOrderItem.setPage(page);
		page.setList(shopSaleOrderItemDao.findGroupByCustomerPage(shopSaleOrderItem));
		return page;
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
		boolean subInsertFn = false;
		String orderTxt = "";
		for (ShopSaleOrderItem shopSaleOrderItem : shopSaleOrder.getShopSaleOrderItemList()) {
			if (shopSaleOrderItem.getProductId() == null) {
				continue;
			}
			subInsertFn = true;
			shopSaleOrderItem.setIsNewRecord(false); // 自动生成ID
			shopSaleOrderItem.setShopSaleOrder(shopSaleOrder);
			shopSaleOrderItem.preInsert();
			shopSaleOrderItemDao.insert(shopSaleOrderItem);
			// 更新该产品库存因为销售是出库，所以这里负数
			ShopUtils.updateProductStockNum(shopSaleOrder.getOfficeId(), shopSaleOrder.getStockId(),
					shopSaleOrder.getStockName(), shopSaleOrderItem.getProductId(),
					shopSaleOrderItem.getSaleNum()*subject*-1);
			orderTxt += "["+shopSaleOrderItem.getProductName()+"-数量:"+shopSaleOrderItem.getSaleNum()+"];";
		}
		if(!subInsertFn) {
			throw new RuntimeException("请选择商品录入");
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
		//如果该客户是会员，新增会员消费
		ShopCustomerInfo shopCustomerInfo = shopCustomerInfoService.get(shopSaleOrder.getCustomerId());
		if(shopCustomerInfo.getIsVip() == 1) {
			VipUserBase vipUserBase = vipUserBaseService.get(shopCustomerInfo.getVipId());
			VipUserCost vipUserCost = new VipUserCost();
			vipUserCost.setOfficeId(vipUserBase.getOfficeId());
			vipUserCost.setSaleId(shopSaleOrder.getId());
			vipUserCost.setSaleNo(shopSaleOrder.getSaleNo());
			vipUserCost.setVipId(vipUserBase.getId());
			vipUserCost.setVipName(vipUserBase.getVipName());
			vipUserCost.setVipPhone(vipUserBase.getVipPhone());
			vipUserCost.setCostMoeny(shopCustomerAccount.getFactMoney());
			vipUserCost.setCostScore(0.00);
			vipUserCost.setRemarks(orderTxt);
			vipUserCostService.save(vipUserCost);
		}
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
		//parm.setOfficeId(shopSaleOrder.getOfficeId());
		//parm.setCustomerId(shopSaleOrder.getCustomerId());
		parm.setSaleId(shopSaleOrder.getId());
		List<ShopCustomerAccount> customerAccountList = shopCustomerAccountService.findList(parm);
		shopCustomerAccountService.delete(customerAccountList.get(0));
		//删除会员消费记录
		ShopCustomerInfo shopCustomerInfo = shopCustomerInfoService.get(shopSaleOrder.getCustomerId());
		if(shopCustomerInfo.getIsVip() == 1) {
			VipUserCost costParm = new VipUserCost();
			costParm.setSaleId(shopSaleOrder.getId());
			List<VipUserCost> costList = vipUserCostService.findList(costParm);
			if(costList!=null && !costList.isEmpty()) {
				VipUserCost vipUserCost = costList.get(0);
				vipUserCostService.delete(vipUserCost,true);
			}
		}
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
		boolean levelFn = false;
		for (ShopProductPrice shopProductPrice : priceList) {
			if(StringUtils.equals(shopProductPrice.getLevelId(),customer.getLevelId())) {
				levelFn = true;
				saleMoney = shopProductPrice.getDiscountPrice();
				break;
			}
		}
		if(!levelFn) {
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

	public void exportExcel(ShopSaleOrderItem queryParm, String exportPath, int type) throws FileNotFoundException, IOException {
		//设置表头
		List<String> headerList = Lists.newArrayList();
		//汇总值
		//求和findItemPage
//		ShopSaleOrderItem accountObj = null;
//		List<ShopSaleOrderItem> countList = this.findSumItem(queryParm);
//		if(!countList.isEmpty() && countList.size() == 1) {
//			//accountObj = countList.get(0);
//			//accountObj.setPercentage(ShopUtils.multiply(ShopUtils.divide(accountObj.getSumProfit(), accountObj.getSumMoney()),100));
//			//accountObj.getSumProduct());
//			//accountObj.getSumMoney());
//			//accountObj.getSumProfit());
//		}
		//查询数据列表
		ExportExcel ee = null;
		if(type == 1) { //销售明细
			List<ShopSaleOrderItem> list = shopSaleOrderItemDao.findItemPage(queryParm);
			headerList.add("商品名称");
			headerList.add("条码");
			headerList.add("单位");
			headerList.add("规格");
			headerList.add("销售数量");
			headerList.add("销售单价");
			headerList.add("折扣(%)");
			headerList.add("折后单价");
			headerList.add("原总金额");
			headerList.add("折后总额");
			headerList.add("销售客户");
			headerList.add("订单号");
			ee = new ExportExcel("销售明细", headerList);
			//设置表体
			for (ShopSaleOrderItem shopSaleOrderItem : list) {
				Row row = ee.addRow();
				ee.addCell(row, 0,shopSaleOrderItem.getProductName());
				ee.addCell(row, 1,shopSaleOrderItem.getProductNo());
				ee.addCell(row, 2,shopSaleOrderItem.getUnit());
				ee.addCell(row, 3,shopSaleOrderItem.getSpec());
				ee.addCell(row, 4,shopSaleOrderItem.getSaleNum());
				ee.addCell(row, 5,shopSaleOrderItem.getSaleMoney());
				ee.addCell(row, 6,shopSaleOrderItem.getDiscount());
				ee.addCell(row, 7,shopSaleOrderItem.getDisMoney());
				ee.addCell(row, 8,shopSaleOrderItem.getAllMoney());
				ee.addCell(row, 9,shopSaleOrderItem.getCountMoney());
				ee.addCell(row, 10,shopSaleOrderItem.getShopSaleOrder().getCustomerName());
				ee.addCell(row, 11,shopSaleOrderItem.getShopSaleOrder().getSaleNo());
			}
		}else if(type == 2) { //商品汇总
			List<ShopSaleOrderItem> list = shopSaleOrderItemDao.findGroupByProductPage(queryParm);
			headerList.add("商品名称");
			headerList.add("条码");
			headerList.add("单位");
			headerList.add("规格");
			headerList.add("销售单价");
			headerList.add("折扣(%)");
			headerList.add("折后单价");
			headerList.add("数量统计");
			headerList.add("原总金额统计");
			headerList.add("折后总额统计");
			ee = new ExportExcel("商品汇总", headerList);
			//设置第一行合计
			
			//设置表体
			for (ShopSaleOrderItem shopSaleOrderItem : list) {
				Row row = ee.addRow();
				ee.addCell(row, 0,shopSaleOrderItem.getProductName());
				ee.addCell(row, 1,shopSaleOrderItem.getProductNo());
				ee.addCell(row, 2,shopSaleOrderItem.getUnit());
				ee.addCell(row, 3,shopSaleOrderItem.getSpec());
				ee.addCell(row, 4,shopSaleOrderItem.getSaleMoney());
				ee.addCell(row, 5,shopSaleOrderItem.getDiscount());
				ee.addCell(row, 6,shopSaleOrderItem.getDisMoney());
				ee.addCell(row, 7,shopSaleOrderItem.getSaleNum());
				ee.addCell(row, 8,shopSaleOrderItem.getAllMoney());
				ee.addCell(row, 9,shopSaleOrderItem.getCountMoney());
			}
		}else if(type == 3) { //客户汇总
			List<ShopSaleOrderItem> list = shopSaleOrderItemDao.findGroupByCustomerPage(queryParm);
			headerList.add("客户名称");
			headerList.add("采购成本");
			headerList.add("销售数量");
			headerList.add("折后金额");
			headerList.add("毛利润");
			headerList.add("毛利率(%)");
			ee = new ExportExcel("客户汇总", headerList);
			//设置第一行合计
			
			//设置表体
			for (ShopSaleOrderItem shopSaleOrderItem : list) {
				Row row = ee.addRow();
				ee.addCell(row, 0,shopSaleOrderItem.getShopSaleOrder().getCustomerName());
				ee.addCell(row, 1,shopSaleOrderItem.getSumBuyPrice());
				ee.addCell(row, 2,shopSaleOrderItem.getSaleNum());
				ee.addCell(row, 3,shopSaleOrderItem.getCountMoney());
				ee.addCell(row, 4,shopSaleOrderItem.getSumProfit());
				ee.addCell(row, 5,shopSaleOrderItem.getPercentage());
				
			}
		}
		ee.writeFile(exportPath);
		ee.dispose();
	}
	
}