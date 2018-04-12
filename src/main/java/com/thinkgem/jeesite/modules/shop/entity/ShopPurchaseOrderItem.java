/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.shop.entity;

import org.hibernate.validator.constraints.Length;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 商品采购单Entity
 * @author swbssd
 * @version 2018-04-06
 */
public class ShopPurchaseOrderItem extends DataEntity<ShopPurchaseOrderItem> {
	
	private static final long serialVersionUID = 1L;
	private ShopPurchaseOrder shopPurchaseOrder;		// 采购单id 父类
	private String productId;		// 商品id
	private String productName;		// 商品名称
	private String productNo;		// 货号(条码)
	private Integer purchaseNum;		// 采购数量
	private Double orderMoney;		// 采购单价
	
	public ShopPurchaseOrderItem() {
		super();
	}

	public ShopPurchaseOrderItem(String id){
		super(id);
	}

	public ShopPurchaseOrderItem(ShopPurchaseOrder shopPurchaseOrder){
		this.shopPurchaseOrder = shopPurchaseOrder;
	}

	
	
	public ShopPurchaseOrder getShopPurchaseOrder() {
		return shopPurchaseOrder;
	}

	public void setShopPurchaseOrder(ShopPurchaseOrder shopPurchaseOrder) {
		this.shopPurchaseOrder = shopPurchaseOrder;
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
	
	public Integer getPurchaseNum() {
		return purchaseNum;
	}

	public void setPurchaseNum(Integer purchaseNum) {
		this.purchaseNum = purchaseNum;
	}
	
	public Double getOrderMoney() {
		return orderMoney;
	}

	public void setOrderMoney(Double orderMoney) {
		this.orderMoney = orderMoney;
	}
	
}