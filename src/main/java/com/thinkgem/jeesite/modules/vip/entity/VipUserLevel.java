/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.vip.entity;

import org.hibernate.validator.constraints.Length;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 会员等级设置Entity
 * @author swbssd
 * @version 2017-09-17
 */
public class VipUserLevel extends DataEntity<VipUserLevel> {
	
	private static final long serialVersionUID = 1L;
	private String levelName;		// 等级名称
	private String discount;		// 折扣比例
	private String remark;		// 等级描述
	private Integer isDiscount;		// 是否启用折扣
	private Integer listNo;		// 排序
	
	public VipUserLevel() {
		super();
	}

	public VipUserLevel(String id){
		super(id);
	}

	@Length(min=0, max=64, message="等级名称长度必须介于 0 和 64 之间")
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
	
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	public Integer getIsDiscount() {
		return isDiscount;
	}

	public void setIsDiscount(Integer isDiscount) {
		this.isDiscount = isDiscount;
	}
	
	public Integer getListNo() {
		return listNo;
	}

	public void setListNo(Integer listNo) {
		this.listNo = listNo;
	}
	
}