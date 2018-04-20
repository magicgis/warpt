/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.shop.entity;

import java.util.List;

import org.hibernate.validator.constraints.Length;

import com.google.common.collect.Lists;
import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 销售订单Entity
 * @author swbssd
 * @version 2018-04-20
 */
public class ShopSaleOrder extends DataEntity<ShopSaleOrder> {
	
	private static final long serialVersionUID = 1L;
	private String officeId;		// 关联机构id
	private String stockId;		// 仓库id
	private String stockName;		// 仓库名称
	private String customerId;		// 客户id
	private String customerName;		// 客户名称
	private String saleNo;		// 订单号
	private String subjectType;		// 账目类型
	private Double orderSum;		// 订单总金额
	private Double sendSum;		// 订单实付金额
	private Double freightMoney;		// 快递运费
	private Integer state;		// 状态
	private String businData;		// 销售日期
	private String beginBusinData;		// 开始 销售日期
	private String endBusinData;		// 结束 销售日期
	private List<ShopSaleOrderItem> shopSaleOrderItemList = Lists.newArrayList();		// 子表列表
	private List<ShopStockInfo> stockList = Lists.newArrayList();		// 仓库列表
	private List<ShopCustomerInfo> customerList = Lists.newArrayList();		// 客户列表
	private List<ShopProduct> productList = Lists.newArrayList();		// 商品列表
	
	public ShopSaleOrder() {
		super();
	}

	public ShopSaleOrder(String id){
		super(id);
	}

	@Length(min=1, max=64, message="关联机构id长度必须介于 1 和 64 之间")
	public String getOfficeId() {
		return officeId;
	}

	public void setOfficeId(String officeId) {
		this.officeId = officeId;
	}
	
	@Length(min=0, max=64, message="仓库id长度必须介于 0 和 64 之间")
	public String getStockId() {
		return stockId;
	}

	public void setStockId(String stockId) {
		this.stockId = stockId;
	}
	
	@Length(min=0, max=50, message="仓库名称长度必须介于 0 和 50 之间")
	public String getStockName() {
		return stockName;
	}

	public void setStockName(String stockName) {
		this.stockName = stockName;
	}
	
	@Length(min=0, max=64, message="客户id长度必须介于 0 和 64 之间")
	public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}
	
	@Length(min=0, max=64, message="客户名称长度必须介于 0 和 64 之间")
	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	
	@Length(min=0, max=64, message="订单号长度必须介于 0 和 64 之间")
	public String getSaleNo() {
		return saleNo;
	}

	public void setSaleNo(String saleNo) {
		this.saleNo = saleNo;
	}
	
	@Length(min=0, max=10, message="账目类型长度必须介于 0 和 10 之间")
	public String getSubjectType() {
		return subjectType;
	}

	public void setSubjectType(String subjectType) {
		this.subjectType = subjectType;
	}
	
	public Double getOrderSum() {
		return orderSum;
	}

	public void setOrderSum(Double orderSum) {
		this.orderSum = orderSum;
	}
	
	public Double getSendSum() {
		return sendSum;
	}

	public void setSendSum(Double sendSum) {
		this.sendSum = sendSum;
	}
	
	public Double getFreightMoney() {
		return freightMoney;
	}

	public void setFreightMoney(Double freightMoney) {
		this.freightMoney = freightMoney;
	}
	
	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}
	
	@Length(min=0, max=19, message="销售日期长度必须介于 0 和 19 之间")
	public String getBusinData() {
		return businData;
	}

	public void setBusinData(String businData) {
		this.businData = businData;
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
		
	public List<ShopSaleOrderItem> getShopSaleOrderItemList() {
		return shopSaleOrderItemList;
	}

	public void setShopSaleOrderItemList(List<ShopSaleOrderItem> shopSaleOrderItemList) {
		this.shopSaleOrderItemList = shopSaleOrderItemList;
	}

	public List<ShopStockInfo> getStockList() {
		return stockList;
	}

	public void setStockList(List<ShopStockInfo> stockList) {
		this.stockList = stockList;
	}

	public List<ShopCustomerInfo> getCustomerList() {
		return customerList;
	}

	public void setCustomerList(List<ShopCustomerInfo> customerList) {
		this.customerList = customerList;
	}

	public List<ShopProduct> getProductList() {
		return productList;
	}

	public void setProductList(List<ShopProduct> productList) {
		this.productList = productList;
	}
	
}