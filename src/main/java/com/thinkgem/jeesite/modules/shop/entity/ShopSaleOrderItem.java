/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.shop.entity;

import org.hibernate.validator.constraints.Length;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 销售订单Entity
 * @author swbssd
 * @version 2018-04-20
 */
public class ShopSaleOrderItem extends DataEntity<ShopSaleOrderItem> {
	
	private static final long serialVersionUID = 1L;
	private ShopSaleOrder shopSaleOrder;		// 订单id 父类
	private String productId;		// 商品id
	private String productName;		// 商品名称
	private String productNo;		// 货号(条码)
	private String unit;		// 单位
	private String spec;		// 规格
	private Integer saleNum;		// 销售数量
	private Integer stockNum;		// stock_num
	private Double saleMoney;		// 销售单价
	private Double discount;		// 折扣
	private Double disMoney;		// 折后单价
	private Double allMoney;		// 原总金额
	private Double countMoney;		// 折后总金额
	//查询和报表
	private String officeId;		// 关联机构id
	private String customerId;		// 客户id
	private String customerName;		// 客户名称
	private String saleNo;		// 订单号
	private String beginBusinData;		// 开始 销售日期
	private String endBusinData;		// 结束 销售日期
	private Integer sumProduct;//销售商品数
	private Double sumMoney;//销售额汇总
	private Double sumProfit;//销售毛利汇总
	private Double percentage;//销售毛利率(%)
	private Double sumBuyPrice; //采购成本求和
	
	public ShopSaleOrderItem() {
		super();
	}

	public ShopSaleOrderItem(String id){
		super(id);
	}

	public ShopSaleOrderItem(ShopSaleOrder shopSaleOrder){
		this.shopSaleOrder = shopSaleOrder;
	}
	
	public ShopSaleOrder getShopSaleOrder() {
		return shopSaleOrder;
	}

	public void setShopSaleOrder(ShopSaleOrder shopSaleOrder) {
		this.shopSaleOrder = shopSaleOrder;
	}

	@Length(min=0, max=64, message="商品id长度必须介于 0 和 64 之间")
	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}
	
	@Length(min=0, max=200, message="商品名称长度必须介于 0 和 200 之间")
	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}
	
	@Length(min=0, max=500, message="货号(条码)长度必须介于 0 和 500 之间")
	public String getProductNo() {
		return productNo;
	}

	public void setProductNo(String productNo) {
		this.productNo = productNo;
	}
	
	@Length(min=0, max=64, message="单位长度必须介于 0 和 64 之间")
	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}
	
	@Length(min=0, max=64, message="规格长度必须介于 0 和 64 之间")
	public String getSpec() {
		return spec;
	}

	public void setSpec(String spec) {
		this.spec = spec;
	}
	
	public Integer getSaleNum() {
		return saleNum;
	}

	public void setSaleNum(Integer saleNum) {
		this.saleNum = saleNum;
	}
	
	public Integer getStockNum() {
		return stockNum;
	}

	public void setStockNum(Integer stockNum) {
		this.stockNum = stockNum;
	}
	
	public Double getSaleMoney() {
		return saleMoney;
	}

	public void setSaleMoney(Double saleMoney) {
		this.saleMoney = saleMoney;
	}
	
	public Double getDiscount() {
		return discount;
	}

	public void setDiscount(Double discount) {
		this.discount = discount;
	}
	
	public Double getDisMoney() {
		return disMoney;
	}

	public void setDisMoney(Double disMoney) {
		this.disMoney = disMoney;
	}
	
	public Double getAllMoney() {
		return allMoney;
	}

	public void setAllMoney(Double allMoney) {
		this.allMoney = allMoney;
	}
	
	public Double getCountMoney() {
		return countMoney;
	}

	public void setCountMoney(Double countMoney) {
		this.countMoney = countMoney;
	}

	public Integer getSumProduct() {
		return sumProduct;
	}

	public void setSumProduct(Integer sumProduct) {
		this.sumProduct = sumProduct;
	}

	public Double getSumMoney() {
		return sumMoney;
	}

	public void setSumMoney(Double sumMoney) {
		this.sumMoney = sumMoney;
	}

	public Double getSumProfit() {
		return sumProfit;
	}

	public void setSumProfit(Double sumProfit) {
		this.sumProfit = sumProfit;
	}

	public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getSaleNo() {
		return saleNo;
	}

	public void setSaleNo(String saleNo) {
		this.saleNo = saleNo;
	}

	public String getBeginBusinData() {
		return beginBusinData;
	}

	public void setBeginBusinData(String beginBusinData) {
		this.beginBusinData = beginBusinData;
	}

	public String getEndBusinData() {
		return endBusinData;
	}

	public void setEndBusinData(String endBusinData) {
		this.endBusinData = endBusinData;
	}

	public String getOfficeId() {
		return officeId;
	}

	public void setOfficeId(String officeId) {
		this.officeId = officeId;
	}

	public Double getPercentage() {
		return percentage;
	}

	public void setPercentage(Double percentage) {
		this.percentage = percentage;
	}

	public Double getSumBuyPrice() {
		return sumBuyPrice;
	}

	public void setSumBuyPrice(Double sumBuyPrice) {
		this.sumBuyPrice = sumBuyPrice;
	}
	
}