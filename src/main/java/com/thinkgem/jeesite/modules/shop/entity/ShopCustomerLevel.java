/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.shop.entity;

import org.hibernate.validator.constraints.Length;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 优惠级别Entity
 * @author swbssd
 * @version 2018-04-17
 */
public class ShopCustomerLevel extends DataEntity<ShopCustomerLevel> {
	
	private static final long serialVersionUID = 1L;
	private String officeId;		// 关联机构id
	private String levelName;		// level_name
	private String discount;		// 折扣比例
	private Integer sort;
	
	public ShopCustomerLevel() {
		super();
	}

	public ShopCustomerLevel(String id){
		super(id);
	}

	@Length(min=1, max=64, message="关联机构id长度必须介于 1 和 64 之间")
	public String getOfficeId() {
		return officeId;
	}

	public void setOfficeId(String officeId) {
		this.officeId = officeId;
	}
	
	@Length(min=0, max=200, message="level_name长度必须介于 0 和 200 之间")
	public String getLevelName() {
		return levelName;
	}

	public void setLevelName(String levelName) {
		this.levelName = levelName;
	}
	
	@Length(min=0, max=200, message="折扣比例长度必须介于 0 和 200 之间")
	public String getDiscount() {
		return discount;
	}

	public void setDiscount(String discount) {
		this.discount = discount;
	}

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}
	
}