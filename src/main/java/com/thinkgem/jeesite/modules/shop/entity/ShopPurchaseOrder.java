/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.shop.entity;

import org.hibernate.validator.constraints.Length;
import java.util.List;
import com.google.common.collect.Lists;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 商品采购单Entity
 * @author swbssd
 * @version 2018-04-06
 */
public class ShopPurchaseOrder extends DataEntity<ShopPurchaseOrder> {
	
	private static final long serialVersionUID = 1L;
	private String officeId;		// 机构ID
	private String stockId;		// 仓库id
	private String stockName;		// 仓库
	private String supplierId;		// 供应商id
	private String supplierName;		// 供应商
	private String orderNo;		// 采购单号
	private Double orderSum;		// 订单总金额
	private Double freightMoney;		// 额外运费
	private String businData;		// 采购日期
	private Integer state;		// 采购状态
	private String orderName;		// 销售员
	private String orderId;		// order_id
	private String beginBusinData;		// 开始 采购日期
	private String endBusinData;		// 结束 采购日期
	private List<ShopPurchaseOrderItem> shopPurchaseOrderItemList = Lists.newArrayList();		// 子表列表
	
	public ShopPurchaseOrder() {
		super();
	}

	public ShopPurchaseOrder(String id){
		super(id);
	}

	@Length(min=1, max=64, message="机构ID长度必须介于 1 和 64 之间")
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
	
	@Length(min=0, max=50, message="仓库长度必须介于 0 和 50 之间")
	public String getStockName() {
		return stockName;
	}

	public void setStockName(String stockName) {
		this.stockName = stockName;
	}
	
	@Length(min=0, max=64, message="采购单号长度必须介于 0 和 64 之间")
	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
	
	public Double getOrderSum() {
		return orderSum;
	}

	public void setOrderSum(Double orderSum) {
		this.orderSum = orderSum;
	}
	
	public Double getFreightMoney() {
		return freightMoney;
	}

	public void setFreightMoney(Double freightMoney) {
		this.freightMoney = freightMoney;
	}
	
	@Length(min=0, max=19, message="采购日期长度必须介于 0 和 19 之间")
	public String getBusinData() {
		return businData;
	}

	public void setBusinData(String businData) {
		this.businData = businData;
	}
	
	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}
	
	@Length(min=0, max=50, message="销售员长度必须介于 0 和 50 之间")
	public String getOrderName() {
		return orderName;
	}

	public void setOrderName(String orderName) {
		this.orderName = orderName;
	}
	
	@Length(min=0, max=64, message="order_id长度必须介于 0 和 64 之间")
	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
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
		
	public List<ShopPurchaseOrderItem> getShopPurchaseOrderItemList() {
		return shopPurchaseOrderItemList;
	}

	public void setShopPurchaseOrderItemList(List<ShopPurchaseOrderItem> shopPurchaseOrderItemList) {
		this.shopPurchaseOrderItemList = shopPurchaseOrderItemList;
	}

	public String getSupplierId() {
		return supplierId;
	}

	public void setSupplierId(String supplierId) {
		this.supplierId = supplierId;
	}

	public String getSupplierName() {
		return supplierName;
	}

	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}
	
	
}