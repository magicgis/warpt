/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.vip.entity;

import org.hibernate.validator.constraints.Length;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 会员消费记录Entity
 * @author swbssd
 * @version 2017-09-17
 */
public class VipUserCost extends DataEntity<VipUserCost> {
	
	private static final long serialVersionUID = 1L;
	private String officeId; // 关联机构id
	private String vipId;		// 会员id
	private String vipPhone;		// 会员手机
	private String vipName;		// 会员名称
	private Double costMoeny  = 0.0;		// 消费金额
	private Double costScore  = 0.0;		// 消费积分
	private String costTime;		// 消费时间
	
	public VipUserCost() {
		super();
	}

	public VipUserCost(String id){
		super(id);
	}

	@Length(min=0, max=64, message="会员id长度必须介于 0 和 64 之间")
	public String getVipId() {
		return vipId;
	}

	public void setVipId(String vipId) {
		this.vipId = vipId;
	}
	
	@Length(min=0, max=20, message="会员手机长度必须介于 0 和 20 之间")
	public String getVipPhone() {
		return vipPhone;
	}

	public void setVipPhone(String vipPhone) {
		this.vipPhone = vipPhone;
	}
	
	@Length(min=0, max=100, message="会员名称长度必须介于 0 和 100 之间")
	public String getVipName() {
		return vipName;
	}

	public void setVipName(String vipName) {
		this.vipName = vipName;
	}
	
	public Double getCostMoeny() {
		return costMoeny;
	}

	public void setCostMoeny(Double costMoeny) {
		this.costMoeny = costMoeny;
	}
	
	public Double getCostScore() {
		return costScore;
	}

	public void setCostScore(Double costScore) {
		this.costScore = costScore;
	}
	
	@Length(min=0, max=19, message="消费时间长度必须介于 0 和 19 之间")
	public String getCostTime() {
		return costTime;
	}

	public void setCostTime(String costTime) {
		this.costTime = costTime;
	}

	public String getOfficeId() {
		return officeId;
	}

	public void setOfficeId(String officeId) {
		this.officeId = officeId;
	}
	
}