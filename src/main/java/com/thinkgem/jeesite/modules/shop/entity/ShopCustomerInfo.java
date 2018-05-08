/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.shop.entity;

import org.hibernate.validator.constraints.Length;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 销售客户Entity
 * @author swbssd
 * @version 2018-04-17
 */
public class ShopCustomerInfo extends DataEntity<ShopCustomerInfo> {
	
	private static final long serialVersionUID = 1L;
	private String officeId;		// 机构ID
	private String levelId;		// level_id
	private String levelName;		// 折扣名称
	private String customerName;		// 客户名称
	private String phone;		// 电话
	private String mobile;		// 手机
	private String email;		// 邮箱
	private String fax;		// 传真
	private String wechat;		// 微信
	private String qq;		// qq
	private String bankName;		// 银行账户
	private String bankNo;		// 银行账号
	private String bankAddress;		// 开户行
	private String address;		// 地址
	private Integer sort;		// sort
	private Double discount = 100.00;		// discount
	
	public ShopCustomerInfo() {
		super();
	}

	public ShopCustomerInfo(String id){
		super(id);
	}

	@Length(min=1, max=64, message="机构ID长度必须介于 1 和 64 之间")
	public String getOfficeId() {
		return officeId;
	}

	public void setOfficeId(String officeId) {
		this.officeId = officeId;
	}
	
	@Length(min=0, max=64, message="level_id长度必须介于 0 和 64 之间")
	public String getLevelId() {
		return levelId;
	}

	public void setLevelId(String levelId) {
		this.levelId = levelId;
	}
	
	@Length(min=0, max=64, message="折扣名称长度必须介于 0 和 64 之间")
	public String getLevelName() {
		return levelName;
	}

	public void setLevelName(String levelName) {
		this.levelName = levelName;
	}
	
	@Length(min=0, max=255, message="客户名称长度必须介于 0 和 255 之间")
	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	
	@Length(min=0, max=20, message="电话长度必须介于 0 和 20 之间")
	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}
	
	@Length(min=0, max=20, message="手机长度必须介于 0 和 20 之间")
	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	
	@Length(min=0, max=20, message="邮箱长度必须介于 0 和 20 之间")
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	@Length(min=0, max=20, message="传真长度必须介于 0 和 20 之间")
	public String getFax() {
		return fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}
	
	@Length(min=0, max=20, message="微信长度必须介于 0 和 20 之间")
	public String getWechat() {
		return wechat;
	}

	public void setWechat(String wechat) {
		this.wechat = wechat;
	}
	
	@Length(min=0, max=20, message="qq长度必须介于 0 和 20 之间")
	public String getQq() {
		return qq;
	}

	public void setQq(String qq) {
		this.qq = qq;
	}
	
	@Length(min=0, max=50, message="银行账户长度必须介于 0 和 50 之间")
	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}
	
	@Length(min=0, max=50, message="银行账号长度必须介于 0 和 50 之间")
	public String getBankNo() {
		return bankNo;
	}

	public void setBankNo(String bankNo) {
		this.bankNo = bankNo;
	}
	
	@Length(min=0, max=50, message="开户行长度必须介于 0 和 50 之间")
	public String getBankAddress() {
		return bankAddress;
	}

	public void setBankAddress(String bankAddress) {
		this.bankAddress = bankAddress;
	}
	
	@Length(min=0, max=255, message="地址长度必须介于 0 和 255 之间")
	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}
	
	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

	public Double getDiscount() {
		return discount;
	}

	public void setDiscount(Double discount) {
		this.discount = discount;
	}
	
}