/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.vip.entity;

import org.hibernate.validator.constraints.Length;
import com.thinkgem.jeesite.modules.sys.entity.Area;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 会员基本信息Entity
 * 
 * @author swbssd
 * @version 2017-09-17
 */
public class VipUserBase extends DataEntity<VipUserBase> {

	private static final long serialVersionUID = 1L;
	private String vipName; // 会员名称
	private String vipPhone; // 会员手机
	private String vipWechat; // 会员微信号
	private String openId; // 会员微信openid
	private String vipQq; // 会员qq号
	private String levelId; // 会员等级id
	private String levelName; // 会员等级名称
	private Area area; // 所在区域
	private String areaName; // 所在区域
	private String userAddress; // 住址
	private String shopName; // 注册门店
	private String shopId; // 注册门店id
	private String officeId; // 关联机构id

	// 查询
	private Double restMoeny;
	private Double useMoeny;
	private Double restScore;
	private Double useScore;

	public VipUserBase() {
		super();
	}

	public VipUserBase(String id) {
		super(id);
	}

	@Length(min = 0, max = 100, message = "会员名称长度必须介于 0 和 100 之间")
	public String getVipName() {
		return vipName;
	}

	public void setVipName(String vipName) {
		this.vipName = vipName;
	}

	@Length(min = 0, max = 20, message = "会员手机长度必须介于 0 和 20 之间")
	public String getVipPhone() {
		return vipPhone;
	}

	public void setVipPhone(String vipPhone) {
		this.vipPhone = vipPhone;
	}

	@Length(min = 0, max = 20, message = "会员微信号长度必须介于 0 和 20 之间")
	public String getVipWechat() {
		return vipWechat;
	}

	public void setVipWechat(String vipWechat) {
		this.vipWechat = vipWechat;
	}

	@Length(min = 0, max = 20, message = "会员qq号长度必须介于 0 和 20 之间")
	public String getVipQq() {
		return vipQq;
	}

	public void setVipQq(String vipQq) {
		this.vipQq = vipQq;
	}

	@Length(min = 0, max = 64, message = "会员等级id长度必须介于 0 和 64 之间")
	public String getLevelId() {
		return levelId;
	}

	public void setLevelId(String levelId) {
		this.levelId = levelId;
	}

	@Length(min = 0, max = 100, message = "会员等级名称长度必须介于 0 和 100 之间")
	public String getLevelName() {
		return levelName;
	}

	public void setLevelName(String levelName) {
		this.levelName = levelName;
	}

	public Area getArea() {
		return area;
	}

	public void setArea(Area area) {
		this.area = area;
	}

	@Length(min = 0, max = 64, message = "所在区域长度必须介于 0 和 64 之间")
	public String getAreaName() {
		return areaName;
	}

	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}

	@Length(min = 0, max = 200, message = "住址长度必须介于 0 和 200 之间")
	public String getUserAddress() {
		return userAddress;
	}

	public void setUserAddress(String userAddress) {
		this.userAddress = userAddress;
	}

	@Length(min = 0, max = 100, message = "注册门店长度必须介于 0 和 100 之间")
	public String getShopName() {
		return shopName;
	}

	public void setShopName(String shopName) {
		this.shopName = shopName;
	}

	@Length(min = 0, max = 64, message = "注册门店id长度必须介于 0 和 64 之间")
	public String getShopId() {
		return shopId;
	}

	public void setShopId(String shopId) {
		this.shopId = shopId;
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

	public String getOpenId() {
		return openId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}

}