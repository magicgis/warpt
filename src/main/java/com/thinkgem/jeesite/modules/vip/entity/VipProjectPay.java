/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.vip.entity;

import org.hibernate.validator.constraints.Length;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 会员项目充值Entity
 * @author swbssd
 * @version 2018-01-14
 */
public class VipProjectPay extends DataEntity<VipProjectPay> {
	
	private static final long serialVersionUID = 1L;
	private String officeId;		// office_id
	private String vipId;		// 会员id
	private String vipPhone;		// 会员手机
	private String vipName;		// 会员名称
	private String projectName;		// 项目名称
	private Double projectMoeny = 0.0;		// 项目金额
	private Integer allNum;		// 总次数
	private Integer restNum;		// 可用次数
	private Integer useNum = 0;		// 已用次数
	
	public VipProjectPay() {
		super();
	}

	public VipProjectPay(String id){
		super(id);
	}

	@Length(min=0, max=64, message="office_id长度必须介于 0 和 64 之间")
	public String getOfficeId() {
		return officeId;
	}

	public void setOfficeId(String officeId) {
		this.officeId = officeId;
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
	
	@Length(min=0, max=100, message="项目名称长度必须介于 0 和 100 之间")
	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
	
	
	
	public Double getProjectMoeny() {
		return projectMoeny;
	}

	public void setProjectMoeny(Double projectMoeny) {
		this.projectMoeny = projectMoeny;
	}

	public Integer getAllNum() {
		return allNum;
	}

	public void setAllNum(Integer allNum) {
		this.allNum = allNum;
	}
	
	public Integer getRestNum() {
		return restNum;
	}

	public void setRestNum(Integer restNum) {
		this.restNum = restNum;
	}
	
	public Integer getUseNum() {
		return useNum;
	}

	public void setUseNum(Integer useNum) {
		this.useNum = useNum;
	}
	
}