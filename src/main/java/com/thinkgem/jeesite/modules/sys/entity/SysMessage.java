/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.sys.entity;

import org.hibernate.validator.constraints.Length;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 短信平台Entity
 * @author swbssd
 * @version 2018-06-10
 */
public class SysMessage extends DataEntity<SysMessage> {
	
	private static final long serialVersionUID = 1L;
	private String officeId;		// 关联机构ID
	private String snKey;		// 短信签名
	private String accessKeyId;		// accesskey
	private String accessKeySecret;		// accesskeysecret
	private String registerCode;		// 注册模板
	private String payCode;		// 充值模板
	private String costCode;		// 消费模板
	private String walletCode;		// 余额模板
	private String projectCode;		// 项目消费模板
	private String wechatCode;		// 小程序注册绑定模板
	
	public SysMessage() {
		super();
	}

	public SysMessage(String id){
		super(id);
	}

	@Length(min=0, max=64, message="关联机构ID长度必须介于 0 和 64 之间")
	public String getOfficeId() {
		return officeId;
	}

	public void setOfficeId(String officeId) {
		this.officeId = officeId;
	}
	
	@Length(min=0, max=100, message="短信签名长度必须介于 0 和 100 之间")
	public String getSnKey() {
		return snKey;
	}

	public void setSnKey(String snKey) {
		this.snKey = snKey;
	}
	
	@Length(min=0, max=100, message="accessKey长度必须介于 0 和 100 之间")
	public String getAccessKeyId() {
		return accessKeyId;
	}

	public void setAccessKeyId(String accessKeyId) {
		this.accessKeyId = accessKeyId;
	}
	
	@Length(min=0, max=100, message="accessKeysecret长度必须介于 0 和 100 之间")
	public String getAccessKeySecret() {
		return accessKeySecret;
	}

	public void setAccessKeySecret(String accessKeySecret) {
		this.accessKeySecret = accessKeySecret;
	}
	
	@Length(min=0, max=100, message="注册模板长度必须介于 0 和 100 之间")
	public String getRegisterCode() {
		return registerCode;
	}

	public void setRegisterCode(String registerCode) {
		this.registerCode = registerCode;
	}
	
	@Length(min=0, max=100, message="充值模板长度必须介于 0 和 100 之间")
	public String getPayCode() {
		return payCode;
	}

	public void setPayCode(String payCode) {
		this.payCode = payCode;
	}
	
	@Length(min=0, max=100, message="消费模板长度必须介于 0 和 100 之间")
	public String getCostCode() {
		return costCode;
	}

	public void setCostCode(String costCode) {
		this.costCode = costCode;
	}
	
	@Length(min=0, max=100, message="余额模板长度必须介于 0 和 100 之间")
	public String getWalletCode() {
		return walletCode;
	}

	public void setWalletCode(String walletCode) {
		this.walletCode = walletCode;
	}
	
	@Length(min=0, max=100, message="项目消费模板长度必须介于 0 和 100 之间")
	public String getProjectCode() {
		return projectCode;
	}

	public void setProjectCode(String projectCode) {
		this.projectCode = projectCode;
	}
	
	@Length(min=0, max=100, message="小程序注册绑定模板长度必须介于 0 和 100 之间")
	public String getWechatCode() {
		return wechatCode;
	}

	public void setWechatCode(String wechatCode) {
		this.wechatCode = wechatCode;
	}
	
}