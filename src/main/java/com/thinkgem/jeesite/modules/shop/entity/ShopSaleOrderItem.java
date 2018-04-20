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
	private Double discount;		// discount
	private Double disMoney;		// dis_money
	private Double allMoney;		// 原总金额
	private Double countMoney;		// 折后总金额
	
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
	
}