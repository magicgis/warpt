/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.shop.entity;

import org.hibernate.validator.constraints.Length;
import java.util.List;
import com.google.common.collect.Lists;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 商品基本信息Entity
 * 
 * @author swbssd
 * @version 2018-04-06
 */
public class ShopProduct extends DataEntity<ShopProduct> {

	private static final long serialVersionUID = 1L;
	private String shopId; // 店铺id
	private String officeId; // 机构ID
	private String productTypeId; // 商品类型
	private String productTypeName; // 商品类型名称
	private String productName; // 商品名称
	private String productNo; // 货号(条码)
	private Double buyPrice; // 采购价
	private Double shopPrice; // 销售价
	private Integer warnStock; // 库存预警数
	private String unit; // 单位
	private String spec; // 规格
	private Integer listNo; // 排序
	private String remark; // 商品描述
	private String oneUrl; // 首图url
	private List<ShopProductPrice> shopProductPriceList = Lists.newArrayList(); // 子表列表
	// 拼音查询：%b%n%s%
	private String pingyinStr;

	public ShopProduct() {
		super();
	}

	public ShopProduct(String id) {
		super(id);
	}

	@Length(min = 0, max = 64, message = "店铺id长度必须介于 0 和 64 之间")
	public String getShopId() {
		return shopId;
	}

	public void setShopId(String shopId) {
		this.shopId = shopId;
	}

	public String getOfficeId() {
		return officeId;
	}

	public void setOfficeId(String officeId) {
		this.officeId = officeId;
	}

	@Length(min = 0, max = 64, message = "商品类型长度必须介于 0 和 64 之间")
	public String getProductTypeId() {
		return productTypeId;
	}

	public void setProductTypeId(String productTypeId) {
		this.productTypeId = productTypeId;
	}

	@Length(min = 0, max = 200, message = "商品名称长度必须介于 0 和 200 之间")
	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	@Length(min = 0, max = 500, message = "货号(条码)长度必须介于 0 和 500 之间")
	public String getProductNo() {
		return productNo;
	}

	public void setProductNo(String productNo) {
		this.productNo = productNo;
	}

	public Double getBuyPrice() {
		return buyPrice;
	}

	public void setBuyPrice(Double buyPrice) {
		this.buyPrice = buyPrice;
	}

	public Integer getWarnStock() {
		return warnStock;
	}

	public void setWarnStock(Integer warnStock) {
		this.warnStock = warnStock;
	}

	@Length(min = 0, max = 64, message = "单位长度必须介于 0 和 64 之间")
	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	@Length(min = 0, max = 64, message = "规格长度必须介于 0 和 64 之间")
	public String getSpec() {
		return spec;
	}

	public void setSpec(String spec) {
		this.spec = spec;
	}

	public Integer getListNo() {
		return listNo;
	}

	public void setListNo(Integer listNo) {
		this.listNo = listNo;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getOneUrl() {
		return oneUrl;
	}

	public void setOneUrl(String oneUrl) {
		this.oneUrl = oneUrl;
	}

	public List<ShopProductPrice> getShopProductPriceList() {
		return shopProductPriceList;
	}

	public void setShopProductPriceList(List<ShopProductPrice> shopProductPriceList) {
		this.shopProductPriceList = shopProductPriceList;
	}

	public String getProductTypeName() {
		return productTypeName;
	}

	public void setProductTypeName(String productTypeName) {
		this.productTypeName = productTypeName;
	}

	public String getPingyinStr() {
		return pingyinStr;
	}

	public void setPingyinStr(String pingyinStr) {
		this.pingyinStr = pingyinStr;
	}

	public Double getShopPrice() {
		return shopPrice;
	}

	public void setShopPrice(Double shopPrice) {
		this.shopPrice = shopPrice;
	}

}