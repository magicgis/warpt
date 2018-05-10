/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.vip.entity;

import org.hibernate.validator.constraints.Length;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 会员充值记录Entity
 * @author swbssd
 * @version 2017-09-17
 */
public class VipUserPay extends DataEntity<VipUserPay> {
	
	private static final long serialVersionUID = 1L;
	private String vipId;		// 会员id
	private String officeId; // 关联机构id
	private String vipName;		// 会员名称
	private String vipPhone;		// 会员手机
	private Double payMoeny = 0.0;		// 充值总金额
	private Double realMoeny = 0.0;		// 充值实际金额
	private Double giveMoeny = 0.0;		// 充值赠送金额
	private Double getScore = 0.0;		// 获得积分
	private String payTime;		// 充值时间
	
	public VipUserPay() {
		super();
	}

	public VipUserPay(String id){
		super(id);
	}

	@Length(min=0, max=64, message="会员id长度必须介于 0 和 64 之间")
	public String getVipId() {
		return vipId;
	}

	public void setVipId(String vipId) {
		this.vipId = vipId;
	}
	
	@Length(min=0, max=100, message="会员名称长度必须介于 0 和 100 之间")
	public String getVipName() {
		return vipName;
	}

	public void setVipName(String vipName) {
		this.vipName = vipName;
	}
	
	@Length(min=0, max=20, message="会员手机长度必须介于 0 和 20 之间")
	public String getVipPhone() {
		return vipPhone;
	}

	public void setVipPhone(String vipPhone) {
		this.vipPhone = vipPhone;
	}
	
	public Double getPayMoeny() {
		return payMoeny;
	}

	public void setPayMoeny(Double payMoeny) {
		this.payMoeny = payMoeny;
	}
	
	public Double getGetScore() {
		return getScore;
	}

	public void setGetScore(Double getScore) {
		this.getScore = getScore;
	}
	
	@Length(min=0, max=19, message="充值时间长度必须介于 0 和 19 之间")
	public String getPayTime() {
		return payTime;
	}

	public void setPayTime(String payTime) {
		this.payTime = payTime;
	}

	public String getOfficeId() {
		return officeId;
	}

	public void setOfficeId(String officeId) {
		this.officeId = officeId;
	}

	public Double getRealMoeny() {
		return realMoeny;
	}

	public void setRealMoeny(Double realMoeny) {
		this.realMoeny = realMoeny;
	}

	public Double getGiveMoeny() {
		return giveMoeny;
	}

	public void setGiveMoeny(Double giveMoeny) {
		this.giveMoeny = giveMoeny;
	}
	
}