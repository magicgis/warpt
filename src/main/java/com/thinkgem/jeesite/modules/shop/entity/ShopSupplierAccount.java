/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.shop.entity;

import org.hibernate.validator.constraints.Length;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 供应商付款Entity
 * @author swbssd
 * @version 2018-04-19
 */
public class ShopSupplierAccount extends DataEntity<ShopSupplierAccount> {
	
	private static final long serialVersionUID = 1L;
	private String officeId;		// 关联机构id
	private String supplierId;		// 供应商id
	private String supplierName;		// 供应商name
	private String businData;		// 业务时间
	private String orderId;		// 采购订单id
	private String accountNo;		// 单据编号
	private String subjectType;		// 账目类型
	private Double meetMoney;		// 应付金额
	private Double factMoney;		// 实付金额
	private Double lessMoney;		// 欠款金额
	
	private String beginBusinData;		// 开始 日期
	private String endBusinData;		// 结束 日期
	private Double sumMeetMoney;		// 求和应付金额
	private Double sumFactMoney;		// 求和实付金额
	private Double sumLessMoney;		// 求和欠款金额
	
	
	public ShopSupplierAccount() {
		super();
	}

	public ShopSupplierAccount(String id){
		super(id);
	}

	@Length(min=1, max=64, message="关联机构id长度必须介于 1 和 64 之间")
	public String getOfficeId() {
		return officeId;
	}

	public void setOfficeId(String officeId) {
		this.officeId = officeId;
	}
	
	@Length(min=0, max=64, message="供应商id长度必须介于 0 和 64 之间")
	public String getSupplierId() {
		return supplierId;
	}

	public void setSupplierId(String supplierId) {
		this.supplierId = supplierId;
	}
	
	@Length(min=0, max=19, message="业务时间长度必须介于 0 和 19 之间")
	public String getBusinData() {
		return businData;
	}

	public void setBusinData(String businData) {
		this.businData = businData;
	}
	
	@Length(min=0, max=19, message="采购订单id长度必须介于 0 和 19 之间")
	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	
	@Length(min=0, max=64, message="单据编号长度必须介于 0 和 64 之间")
	public String getAccountNo() {
		return accountNo;
	}

	public void setAccountNo(String accountNo) {
		this.accountNo = accountNo;
	}
	
	@Length(min=0, max=10, message="账目类型长度必须介于 0 和 10 之间")
	public String getSubjectType() {
		return subjectType;
	}

	public void setSubjectType(String subjectType) {
		this.subjectType = subjectType;
	}
	
	public Double getMeetMoney() {
		return meetMoney;
	}

	public void setMeetMoney(Double meetMoney) {
		this.meetMoney = meetMoney;
	}
	
	public Double getFactMoney() {
		return factMoney;
	}

	public void setFactMoney(Double factMoney) {
		this.factMoney = factMoney;
	}
	
	public Double getLessMoney() {
		return lessMoney;
	}

	public void setLessMoney(Double lessMoney) {
		this.lessMoney = lessMoney;
	}

	public String getBeginBusinData() {
		return beginBusinData;
	}

	public void setBeginBusinData(String beginBusinData) {
		this.beginBusinData = beginBusinData;
	}

	public String getEndBusinData() {
		return endBusinData;
	}

	public void setEndBusinData(String endBusinData) {
		this.endBusinData = endBusinData;
	}

	public String getSupplierName() {
		return supplierName;
	}

	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}

	public Double getSumMeetMoney() {
		return sumMeetMoney;
	}

	public void setSumMeetMoney(Double sumMeetMoney) {
		this.sumMeetMoney = sumMeetMoney;
	}

	public Double getSumFactMoney() {
		return sumFactMoney;
	}

	public void setSumFactMoney(Double sumFactMoney) {
		this.sumFactMoney = sumFactMoney;
	}

	public Double getSumLessMoney() {
		return sumLessMoney;
	}

	public void setSumLessMoney(Double sumLessMoney) {
		this.sumLessMoney = sumLessMoney;
	}
	
}