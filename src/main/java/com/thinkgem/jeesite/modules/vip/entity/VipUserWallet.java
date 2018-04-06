/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.vip.entity;

import org.hibernate.validator.constraints.Length;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 会员钱包Entity
 * @author swbssd
 * @version 2017-09-17
 */
public class VipUserWallet extends DataEntity<VipUserWallet> {
	
	private static final long serialVersionUID = 1L;
	private String vipId;		// 会员id
	private String officeId; // 关联机构id
	private String vipName;		// 会员名称
	private String vipPhone;		// 会员手机
	private Double allMoeny;		// 总金额
	private Double restMoeny;		// 可用金额
	private Double useMoeny;		// 已消费金额
	private Double allScore;		// 积分总额
	private Double restScore;		// 可用积分
	private Double useScore;		// 已兑积分
	
	public VipUserWallet() {
		super();
	}

	public VipUserWallet(String id){
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
	
	public Double getAllMoeny() {
		return allMoeny;
	}

	public void setAllMoeny(Double allMoeny) {
		this.allMoeny = allMoeny;
	}
	
	public Double getRestMoeny() {
		return restMoeny;
	}

	public void setRestMoeny(Double restMoeny) {
		this.restMoeny = restMoeny;
	}
	
	public Double getUseMoeny() {
		return useMoeny;
	}

	public void setUseMoeny(Double useMoeny) {
		this.useMoeny = useMoeny;
	}
	
	public Double getAllScore() {
		return allScore;
	}

	public void setAllScore(Double allScore) {
		this.allScore = allScore;
	}
	
	public Double getRestScore() {
		return restScore;
	}

	public void setRestScore(Double restScore) {
		this.restScore = restScore;
	}
	
	public Double getUseScore() {
		return useScore;
	}

	public void setUseScore(Double useScore) {
		this.useScore = useScore;
	}

	public String getOfficeId() {
		return officeId;
	}

	public void setOfficeId(String officeId) {
		this.officeId = officeId;
	}
	
}