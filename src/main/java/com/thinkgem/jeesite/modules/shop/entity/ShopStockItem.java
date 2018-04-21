/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.shop.entity;

import org.hibernate.validator.constraints.Length;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 仓库库存Entity
 * @author swbssd
 * @version 2018-04-17
 */
public class ShopStockItem extends DataEntity<ShopStockItem> {
	
	private static final long serialVersionUID = 1L;
	private String stockId;		// 仓库id
	private String stockName;		// 仓库名称
	private String officeId;		// 机构ID
	private String productTypeId;		// 商品类型ID
	private String productTypeName;		// 商品类型
	private String productId;		// 商品id
	private String productName;		// 商品名称
	private String productNo;		// 条码
	private Integer stockNum;		// 库存量
	private Integer warnStock;		// 库存预警数
	private Integer listNo;		// 排序
	
	private String pingyinStr;
	
	public ShopStockItem() {
		super();
	}

	public ShopStockItem(String id){
		super(id);
	}

	@Length(min=0, max=64, message="仓库id长度必须介于 0 和 64 之间")
	public String getStockId() {
		return stockId;
	}

	public void setStockId(String stockId) {
		this.stockId = stockId;
	}
	
	@Length(min=1, max=64, message="仓库名称长度必须介于 1 和 64 之间")
	public String getStockName() {
		return stockName;
	}

	public void setStockName(String stockName) {
		this.stockName = stockName;
	}
	
	@Length(min=1, max=64, message="机构ID长度必须介于 1 和 64 之间")
	public String getOfficeId() {
		return officeId;
	}

	public void setOfficeId(String officeId) {
		this.officeId = officeId;
	}
	
	@Length(min=0, max=64, message="商品类型ID长度必须介于 0 和 64 之间")
	public String getProductTypeId() {
		return productTypeId;
	}

	public void setProductTypeId(String productTypeId) {
		this.productTypeId = productTypeId;
	}
	
	@Length(min=0, max=64, message="商品类型长度必须介于 0 和 64 之间")
	public String getProductTypeName() {
		return productTypeName;
	}

	public void setProductTypeName(String productTypeName) {
		this.productTypeName = productTypeName;
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
	
	@Length(min=0, max=500, message="条码长度必须介于 0 和 500 之间")
	public String getProductNo() {
		return productNo;
	}

	public void setProductNo(String productNo) {
		this.productNo = productNo;
	}
	
	public Integer getStockNum() {
		return stockNum;
	}

	public void setStockNum(Integer stockNum) {
		this.stockNum = stockNum;
	}
	
	public Integer getWarnStock() {
		return warnStock;
	}

	public void setWarnStock(Integer warnStock) {
		this.warnStock = warnStock;
	}
	
	public Integer getListNo() {
		return listNo;
	}

	public void setListNo(Integer listNo) {
		this.listNo = listNo;
	}

	public String getPingyinStr() {
		return pingyinStr;
	}

	public void setPingyinStr(String pingyinStr) {
		this.pingyinStr = pingyinStr;
	}
	
}