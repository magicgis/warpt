/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.shop.entity;

import org.hibernate.validator.constraints.Length;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 仓库基础信息Entity
 * @author swbssd
 * @version 2018-04-06
 */
public class ShopStockInfo extends DataEntity<ShopStockInfo> {
	
	private static final long serialVersionUID = 1L;
	private String officeId;		// 部门ID
	private String stockName;		// 仓库名称
	private String stockAddress;		// 仓库地址
	
	public ShopStockInfo() {
		super();
	}

	public ShopStockInfo(String id){
		super(id);
	}


	
	public String getOfficeId() {
		return officeId;
	}

	public void setOfficeId(String officeId) {
		this.officeId = officeId;
	}

	@Length(min=1, max=64, message="仓库名称长度必须介于 1 和 64 之间")
	public String getStockName() {
		return stockName;
	}

	public void setStockName(String stockName) {
		this.stockName = stockName;
	}
	
	@Length(min=0, max=2000, message="仓库地址长度必须介于 0 和 2000 之间")
	public String getStockAddress() {
		return stockAddress;
	}

	public void setStockAddress(String stockAddress) {
		this.stockAddress = stockAddress;
	}
	
}