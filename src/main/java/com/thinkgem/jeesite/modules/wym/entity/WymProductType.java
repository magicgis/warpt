/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.wym.entity;

import org.hibernate.validator.constraints.Length;
import com.fasterxml.jackson.annotation.JsonBackReference;
import javax.validation.constraints.NotNull;

import com.thinkgem.jeesite.common.persistence.TreeEntity;

/**
 * 商品类型Entity
 * @author swbssd
 * @version 2017-02-23
 */
public class WymProductType extends TreeEntity<WymProductType> {
	
	private static final long serialVersionUID = 1L;
	private String shopId;		// 店铺id
	private String vipId;		// vipid
	private Integer appType;		// 分类
	private String name;		// 名称
	private String remark;		// 分类描述
	private WymProductType parent;		// 选择父分类
	private String parentName;		// 父分类名称
	private String parentIds;		// parent_ids
	private Integer levelNo;		// 层级
	private Integer sort;		// sort
	
	public WymProductType() {
		super();
	}

	public WymProductType(String id){
		super(id);
	}

	@Length(min=0, max=64, message="店铺id长度必须介于 0 和 64 之间")
	public String getShopId() {
		return shopId;
	}

	public void setShopId(String shopId) {
		this.shopId = shopId;
	}
	
	@Length(min=0, max=64, message="vipid长度必须介于 0 和 64 之间")
	public String getVipId() {
		return vipId;
	}

	public void setVipId(String vipId) {
		this.vipId = vipId;
	}
	
	public Integer getAppType() {
		return appType;
	}

	public void setAppType(Integer appType) {
		this.appType = appType;
	}
	
	@Length(min=1, max=200, message="名称长度必须介于 1 和 200 之间")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	@JsonBackReference
	public WymProductType getParent() {
		return parent;
	}

	public void setParent(WymProductType parent) {
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
	
	@NotNull(message="sort不能为空")
	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}
	
	public String getParentId() {
		return parent != null && parent.getId() != null ? parent.getId() : "0";
	}
}