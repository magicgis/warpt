/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.shop.entity;

import org.hibernate.validator.constraints.Length;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 商品基本信息Entity
 * @author swbssd
 * @version 2018-04-06
 */
public class ShopProductPrice extends DataEntity<ShopProductPrice> {
	
	private static final long serialVersionUID = 1L;
	private ShopProduct shopProduct;		// 商品id 父类
	private String levelId;		// 级别id
	private String levelName;		// 级别名称
	private String discount;		// 折扣比例
	private Double discountPrice;		// 折扣价格
	private Integer listNo;		// 排序
	
	public ShopProductPrice() {
		super();
	}

	public ShopProductPrice(String id){
		super(id);
	}

	public ShopProductPrice(ShopProduct shopProduct){
		this.shopProduct = shopProduct;
	}
	
	public ShopProduct getShopProduct() {
		return shopProduct;
	}

	public void setShopProduct(ShopProduct shopProduct) {
		this.shopProduct = shopProduct;
	}

	@Length(min=0, max=64, message="折扣名称长度必须介于 0 和 64 之间")
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
	
	public Double getDiscountPrice() {
		return discountPrice;
	}

	public void setDiscountPrice(Double discountPrice) {
		this.discountPrice = discountPrice;
	}
	
	public Integer getListNo() {
		return listNo;
	}

	public void setListNo(Integer listNo) {
		this.listNo = listNo;
	}

	public String getLevelId() {
		return levelId;
	}

	public void setLevelId(String levelId) {
		this.levelId = levelId;
	}
	
}