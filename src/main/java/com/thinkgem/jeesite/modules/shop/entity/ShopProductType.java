/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.shop.entity;

import org.hibernate.validator.constraints.Length;
import com.thinkgem.jeesite.modules.sys.entity.Office;
import javax.validation.constraints.NotNull;
import com.fasterxml.jackson.annotation.JsonBackReference;

import com.thinkgem.jeesite.common.persistence.TreeEntity;

/**
 * 商品类型Entity
 * @author swbssd
 * @version 2018-04-06
 */
public class ShopProductType extends TreeEntity<ShopProductType> {
	
	private static final long serialVersionUID = 1L;
	private String shopId;		// 店铺id
	private Office office;		// office_id
	private Integer appType;		// 分类
	private String name;		// 名称
	private ShopProductType parent;		// 父分类id
	private String parentName;		// 父分类名称
	private String parentIds;		// parent_ids
	private Integer levelNo;		// 层级
	private Integer sort;		// sort
	
	private String queryAllName; //全称查询使用
	
	public ShopProductType() {
		super();
	}

	public ShopProductType(String id){
		super(id);
	}

	@Length(min=0, max=64, message="店铺id长度必须介于 0 和 64 之间")
	public String getShopId() {
		return shopId;
	}

	public void setShopId(String shopId) {
		this.shopId = shopId;
	}
	
	//@NotNull(message="office_id不能为空")
	public Office getOffice() {
		return office;
	}

	public void setOffice(Office office) {
		this.office = office;
	}
	
	public Integer getAppType() {
		return appType;
	}

	public void setAppType(Integer appType) {
		this.appType = appType;
	}
	
	@Length(min=0, max=200, message="名称长度必须介于 0 和 200 之间")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@JsonBackReference
	public ShopProductType getParent() {
		return parent;
	}

	public void setParent(ShopProductType parent) {
		this.parent = parent;
	}
	
	@Length(min=0, max=200, message="父分类名称长度必须介于 0 和 200 之间")
	public String getParentName() {
		return parentName;
	}

	public void setParentName(String parentName) {
		this.parentName = parentName;
	}
	
	@Length(min=0, max=2000, message="parent_ids长度必须介于 0 和 2000 之间")
	public String getParentIds() {
		return parentIds;
	}

	public void setParentIds(String parentIds) {
		this.parentIds = parentIds;
	}
	
	public Integer getLevelNo() {
		return levelNo;
	}

	public void setLevelNo(Integer levelNo) {
		this.levelNo = levelNo;
	}
	
	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}
	
	public String getParentId() {
		return parent != null && parent.getId() != null ? parent.getId() : "0";
	}

	public String getQueryAllName() {
		return queryAllName;
	}

	public void setQueryAllName(String queryAllName) {
		this.queryAllName = queryAllName;
	}

	
	
	
}