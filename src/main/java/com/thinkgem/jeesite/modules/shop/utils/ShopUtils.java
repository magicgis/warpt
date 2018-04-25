package com.thinkgem.jeesite.modules.shop.utils;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import org.apache.poi.ss.usermodel.Row;

import com.google.common.collect.Lists;
import com.thinkgem.jeesite.common.utils.IdGen;
import com.thinkgem.jeesite.common.utils.SpringContextHolder;
import com.thinkgem.jeesite.common.utils.excel.ImportExcel;
import com.thinkgem.jeesite.modules.shop.entity.ShopCustomerLevel;
import com.thinkgem.jeesite.modules.shop.entity.ShopProduct;
import com.thinkgem.jeesite.modules.shop.entity.ShopProductPrice;
import com.thinkgem.jeesite.modules.shop.entity.ShopProductType;
import com.thinkgem.jeesite.modules.shop.entity.ShopStockInfo;
import com.thinkgem.jeesite.modules.shop.entity.ShopStockItem;
import com.thinkgem.jeesite.modules.shop.service.ShopCustomerLevelService;
import com.thinkgem.jeesite.modules.shop.service.ShopProductService;
import com.thinkgem.jeesite.modules.shop.service.ShopProductTypeService;
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
	/** 账目类型：付款供应商 **/
	public static final String SUBJECT_TYPE_1004 = "1004";
	/** 账目类型：客户收款 **/
	public static final String SUBJECT_TYPE_1005 = "1005";
	
	/** 新增单据 **/
	public static final int STATE_TYPE_1 = 1;
	/** 单据入库 **/
	public static final int STATE_TYPE_2 = 2;

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
			shopStockItem.setProductTypeName(shopProduct.getProductTypeName());
			shopStockItem.setProductName(shopProduct.getProductName());
			shopStockItem.setProductId(shopProduct.getId());
			shopStockItem.setProductName(shopProduct.getProductName());
			shopStockItem.setProductNo(shopProduct.getProductNo());
			shopStockItem.setWarnStock(shopProduct.getWarnStock());
			shopStockItem.setListNo(shopProduct.getListNo());
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
			shopStockItem.setProductTypeName(shopProduct.getProductTypeName());
			shopStockItem.setProductId(shopProduct.getId());
			shopStockItem.setProductName(shopProduct.getProductName());
			shopStockItem.setProductNo(shopProduct.getProductNo());
			shopStockItem.setWarnStock(shopProduct.getWarnStock());
			shopStockItem.setListNo(shopProduct.getListNo());
			shopStockItemService.save(shopStockItem);
		}
	}
	
	/**
	 * 更新某产品库存量
	 * 
	 * @param officeId
	 * @param stockId
	 * @param productId
	 */
	public static void updateProductStockNum(String officeId, String stockId, String stockName, String productId, int addStockNum){
		ShopStockItemService shopStockItemService = SpringContextHolder.getBean("shopStockItemService");
		ShopProductService shopProductService = SpringContextHolder.getBean("shopProductService");
		ShopStockItem parm = new ShopStockItem();
		parm.setOfficeId(officeId);
		parm.setStockId(stockId);
		parm.setProductId(productId);
		List<ShopStockItem> stockItemList = shopStockItemService.findList(parm);
		if (stockItemList.isEmpty()) { // 如果不存在商品，则创建
			if(addStockNum < 0) {
				throw new RuntimeException("商品无库存，无法出货，请联系管理员处理！");
			}
			ShopProduct shopProduct = shopProductService.get(productId);
			ShopStockItem shopStockItem = new ShopStockItem();
			shopStockItem.setStockNum(addStockNum); // 库存初始化
			shopStockItem.setOfficeId(officeId);
			shopStockItem.setStockId(stockId);
			shopStockItem.setStockName(stockName);
			shopStockItem.setProductTypeId(shopProduct.getProductTypeId());
			shopStockItem.setProductId(productId);
			shopStockItem.setProductName(shopProduct.getProductName());
			shopStockItem.setProductNo(shopProduct.getProductNo());
			shopStockItem.setWarnStock(shopProduct.getWarnStock());
			shopStockItemService.save(shopStockItem);
		} else if (stockItemList.size() == 1) { // 增加库存
			ShopStockItem shopStockItem = stockItemList.get(0);
			int setStockNum = addStockNum + shopStockItem.getStockNum();
			if(setStockNum < 0) {
				throw new RuntimeException("商品目前库存为"+shopStockItem.getStockNum()+"，无法出货"+addStockNum);
			}
			shopStockItem.setStockNum(setStockNum);
			shopStockItemService.save(shopStockItem);
		} else {
			throw new RuntimeException("库存存在多个该商品，不合法，请联系管理员处理！");
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
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmssSSS");
		return fi + sb.toString() + UserUtils.getUser().getLoginName() + formatter.format(new Date());
	}
	
	/**
	 * 导入excel TO.
	 */
	public static void impShopExcel() {
		ShopProductService shopProductService = SpringContextHolder.getBean("shopProductService");
		ShopProductTypeService shopProductTypeService = SpringContextHolder.getBean("shopProductTypeService");
		ShopCustomerLevelService shopCustomerLevelService = SpringContextHolder.getBean("shopCustomerLevelService");
		try {
			ImportExcel ei = new ImportExcel("F:/tmp/商品信息.xls", 0);
			for (int i = ei.getDataRowNum(); i < ei.getLastDataRowNum(); i++) {
				Row row = ei.getRow(i);
				for (int j = 0; j < ei.getLastCellNum(); j++) {
					Object val = ei.getCellValue(row, j);
					System.out.print(val+", ");
				}
				System.out.print("\n");
				//保存产品
				ShopProduct shopProduct = new ShopProduct();
				//shopProduct.setIsNewRecord(true);
				//shopProduct.setId(IdGen.uuid());
				shopProduct.setOfficeId(UserUtils.getUser().getOffice().getId());
				//商品类型
				String productTypeName = String.valueOf(ei.getCellValue(row,0)).trim();
				ShopProductType pram0 = new ShopProductType();
				pram0.setOffice(UserUtils.getUser().getOffice());
				pram0.setQueryAllName(productTypeName);
				List<ShopProductType> shopProductTypeList = shopProductTypeService.findList(pram0);
				ShopProductType shopProductType = shopProductTypeList.get(0);
				shopProduct.setProductTypeId(shopProductType.getId());
				shopProduct.setProductTypeName(productTypeName);
				shopProduct.setProductName(String.valueOf(ei.getCellValue(row,1)).trim()); //名称
				shopProduct.setProductNo(String.valueOf(ei.getCellValue(row,2)).trim()); //条码
				shopProduct.setBuyPrice(Double.valueOf(String.valueOf(ei.getCellValue(row,3)).trim())); //采购价
				shopProduct.setShopPrice(Double.valueOf(String.valueOf(ei.getCellValue(row,4)).trim())); //销售价
				BigDecimal warnStock = new BigDecimal(String.valueOf(ei.getCellValue(row,5)).trim());
				shopProduct.setWarnStock(warnStock.intValue()); //库存预警数
				shopProduct.setUnit(String.valueOf(ei.getCellValue(row,6)).trim()); //单位
				shopProduct.setSpec(String.valueOf(ei.getCellValue(row,7)).trim()); //规格
				BigDecimal listNo = new BigDecimal(String.valueOf(ei.getCellValue(row,8)).trim());
				shopProduct.setListNo(listNo.intValue()); //排序
				//分类价格设置
				int indexKey = 9;
				ShopCustomerLevel pram = new ShopCustomerLevel();
				pram.setOfficeId(UserUtils.getUser().getOffice().getId());
				List<ShopCustomerLevel> leveList = shopCustomerLevelService.findList(pram);
				List<ShopProductPrice> priceList = new ArrayList<ShopProductPrice>();
				// 初始化价格
				for (ShopCustomerLevel level : leveList) {
					ShopProductPrice price = new ShopProductPrice();
					price.setId("");
					//price.setIsNewRecord(true);
					price.setLevelId(level.getId());
					price.setLevelName(level.getLevelName());
					price.setDiscount(level.getDiscount());
					price.setListNo(level.getSort());
					//price.setShopProduct(shopProduct);
					if(indexKey > 12) {
						price.setDiscountPrice(ShopUtils.multiply(Double.valueOf(level.getDiscount()), 0.01,shopProduct.getShopPrice()));
					}else {
						price.setDiscountPrice(Double.valueOf(String.valueOf(ei.getCellValue(row,indexKey)).trim()));
					}
					priceList.add(price);
					indexKey++;
				}
				shopProduct.setShopProductPriceList(priceList);
				//保存
				shopProductService.save(shopProduct);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e.getMessage());
		}
	}
}
