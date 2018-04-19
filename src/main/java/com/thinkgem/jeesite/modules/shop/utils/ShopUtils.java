package com.thinkgem.jeesite.modules.shop.utils;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Random;

import com.thinkgem.jeesite.common.utils.SpringContextHolder;
import com.thinkgem.jeesite.modules.shop.entity.ShopProduct;
import com.thinkgem.jeesite.modules.shop.entity.ShopStockInfo;
import com.thinkgem.jeesite.modules.shop.entity.ShopStockItem;
import com.thinkgem.jeesite.modules.shop.service.ShopProductService;
import com.thinkgem.jeesite.modules.shop.service.ShopStockInfoService;
import com.thinkgem.jeesite.modules.shop.service.ShopStockItemService;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;

public class ShopUtils {
	/** 账目类型：销售收入 **/
	public static final String SUBJECT_TYPE_1000 = "1000";
	/** 账目类型：销售退货 **/
	public static final String SUBJECT_TYPE_1001 = "1001";
	/** 账目类型：采购支出 **/
	public static final String SUBJECT_TYPE_1002 = "1002";
	/** 账目类型：采购退货 **/
	public static final String SUBJECT_TYPE_1003 = "1003";

	// 建立货币格式化引用
	private static final NumberFormat currency = NumberFormat.getCurrencyInstance();
	// 建立百分比格式化引用
	private static final NumberFormat percent = NumberFormat.getPercentInstance();

	/**
	 * 加法(保留2位小数四舍五入)
	 * 
	 * @param s1
	 *            ...
	 * @return
	 */
	public static double add(double... s1) {
		// 浮点数不能传入BigDecimal否则会出现计算误差TO.
		BigDecimal count = new BigDecimal("0");
		for (double item : s1) {
			BigDecimal itemDecimal = new BigDecimal(String.valueOf(item));
			count = count.add(itemDecimal);
		}
		return count.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
	}

	/**
	 * 减法(保留2位小数四舍五入)
	 * 
	 * @param s1
	 *            -s2
	 * @return
	 */
	public static double subtract(double s1, double s2) {
		// 浮点数不能传入BigDecimal否则会出现计算误差TO.
		BigDecimal bigS1 = new BigDecimal(String.valueOf(s1));
		BigDecimal bigS2 = new BigDecimal(String.valueOf(s2));
		return bigS1.subtract(bigS2).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
	}

	/**
	 * 乘法(保留2位小数四舍五入)
	 * 
	 * @param s1
	 *            ...
	 * @return
	 */
	public static double multiply(double... s1) {
		// 浮点数不能传入BigDecimal否则会出现计算误差TO.
		BigDecimal count = new BigDecimal("1"); // 初始化1开始乘
		for (double item : s1) {
			BigDecimal itemDecimal = new BigDecimal(String.valueOf(item));
			count = count.multiply(itemDecimal);
		}
		return count.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
	}

	/**
	 * 除法(保留2位小数四舍五入)
	 * 
	 * @param s1
	 *            /s2
	 * @return
	 */
	public static double divide(double s1, double s2) {
		// 浮点数不能传入BigDecimal否则会出现计算误差TO.
		BigDecimal bigS1 = new BigDecimal(String.valueOf(s1));
		BigDecimal bigS2 = new BigDecimal(String.valueOf(s2));
		return bigS1.divide(bigS2, 2, BigDecimal.ROUND_HALF_UP).doubleValue();
	}

	/**
	 * 除法(不保留小数)
	 * 
	 * @param s1
	 *            /s2
	 * @return
	 */
	public static double divideMin(double s1, double s2) {
		// 浮点数不能传入BigDecimal否则会出现计算误差TO.
		BigDecimal bigS1 = new BigDecimal(String.valueOf(s1));
		BigDecimal bigS2 = new BigDecimal(String.valueOf(s2));
		return bigS1.divide(bigS2).doubleValue();
	}

	/**
	 * 0.xx格式化成百分比
	 * 
	 * @param s
	 * @return
	 */
	public static String formatPercent(double s) {
		return percent.format(s);
	}

	/**
	 * 格式化货币
	 * 
	 * @param s
	 * @return
	 */
	public static String formatCurrency(double s) {
		return currency.format(s);
	}

	/**
	 * 新增或修改仓库之后，初始化产品当前仓库库存
	 * 
	 * @param shopStockInfo
	 */
	public static void saveStockItemByStock(ShopStockInfo shopStockInfo) {
		ShopProductService shopProductService = SpringContextHolder.getBean("shopProductService");
		ShopStockItemService shopStockItemService = SpringContextHolder.getBean("shopStockItemService");
		// 判断是修改还是新增
		ShopProduct parm = new ShopProduct();
		parm.setOfficeId(shopStockInfo.getOfficeId());
		List<ShopProduct> productList = shopProductService.findList(parm);
		for (ShopProduct shopProduct : productList) {
			// 判断是修改还是新增
			ShopStockItem pram = new ShopStockItem();
			pram.setOfficeId(shopStockInfo.getOfficeId());
			pram.setProductId(shopProduct.getId());
			pram.setStockId(shopStockInfo.getId());
			List<ShopStockItem> itemList = shopStockItemService.findList(pram);
			ShopStockItem shopStockItem = null;
			if (itemList.isEmpty()) { // 产品无库存表初始化
				shopStockItem = new ShopStockItem();
				shopStockItem.setStockNum(0); // 库存初始化
			} else { // 产品无有库存表
				shopStockItem = itemList.get(0);
			}
			shopStockItem.setOfficeId(shopStockInfo.getOfficeId());
			shopStockItem.setStockId(shopStockInfo.getId());
			shopStockItem.setStockName(shopStockInfo.getStockName());
			shopStockItem.setProductTypeId(shopProduct.getProductTypeId());
			shopStockItem.setProductName(shopProduct.getProductName());
			shopStockItem.setProductId(shopProduct.getId());
			shopStockItem.setProductName(shopProduct.getProductName());
			shopStockItem.setProductNo(shopProduct.getProductNo());
			shopStockItem.setWarnStock(shopProduct.getWarnStock());
			shopStockItemService.save(shopStockItem);
		}
	}

	/**
	 * 新增或修改产品之后，初始化各个仓库该产品的库存
	 * 
	 * @param shopStockInfo
	 */
	public static void saveStockItemByProduct(ShopProduct shopProduct) {
		ShopStockItemService shopStockItemService = SpringContextHolder.getBean("shopStockItemService");
		ShopStockInfoService shopStockInfoService = SpringContextHolder.getBean("shopStockInfoService");
		// 仓库
		ShopStockInfo parm = new ShopStockInfo();
		parm.setOfficeId(shopProduct.getOfficeId());
		List<ShopStockInfo> stockList = shopStockInfoService.findList(parm);
		for (ShopStockInfo shopStockInfo : stockList) {
			// 判断是修改还是新增
			ShopStockItem pram = new ShopStockItem();
			pram.setOfficeId(shopStockInfo.getOfficeId());
			pram.setProductId(shopProduct.getId());
			pram.setStockId(shopStockInfo.getId());
			List<ShopStockItem> itemList = shopStockItemService.findList(pram);
			ShopStockItem shopStockItem = null;
			if (itemList.isEmpty()) {
				shopStockItem = new ShopStockItem();
				shopStockItem.setStockNum(0); // 库存初始化
			} else {
				shopStockItem = itemList.get(0);
			}
			shopStockItem.setOfficeId(shopStockInfo.getOfficeId());
			shopStockItem.setStockId(shopStockInfo.getId());
			shopStockItem.setStockName(shopStockInfo.getStockName());
			shopStockItem.setProductTypeId(shopProduct.getProductTypeId());
			shopStockItem.setProductId(shopProduct.getId());
			shopStockItem.setProductName(shopProduct.getProductName());
			shopStockItem.setProductNo(shopProduct.getProductNo());
			shopStockItem.setWarnStock(shopProduct.getWarnStock());
			shopStockItemService.save(shopStockItem);
		}
	}

	/**
	 * 根据当前时间生成订单号码
	 * 
	 * @fi 单据前缀
	 * @return
	 */
	public static String generateBillCode(String fi) {
		String str = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
		StringBuilder sb = new StringBuilder(2);
		for (int i = 0; i < 3; i++) {
			char ch = str.charAt(new Random().nextInt(str.length()));
			sb.append(ch);
		}
		SimpleDateFormat formatter = new SimpleDateFormat("yyMMddHHmmss", Locale.getDefault());
		return fi + UserUtils.getUser().getLoginName() + sb.toString() + formatter.format(new Date());
	}
}
