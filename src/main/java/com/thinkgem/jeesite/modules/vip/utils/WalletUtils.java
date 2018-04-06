package com.thinkgem.jeesite.modules.vip.utils;

import java.math.BigDecimal;
import java.text.NumberFormat;

public class WalletUtils {

	// 建立货币格式化引用
	private static final NumberFormat currency = NumberFormat
			.getCurrencyInstance();
	// 建立百分比格式化引用
	private static final NumberFormat percent = NumberFormat
			.getPercentInstance();

	/**
	 * 加法(保留2位小数四舍五入)
	 * 
	 * @param s1
	 *            ...
	 * @return
	 */
	public static double add(double... s1) {
		BigDecimal count = new BigDecimal(0);
		for (double item : s1) {
			BigDecimal itemDecimal = new BigDecimal(item);
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
		BigDecimal bigS1 = new BigDecimal(s1);
		BigDecimal bigS2 = new BigDecimal(s2);
		return bigS1.subtract(bigS2).setScale(2, BigDecimal.ROUND_HALF_UP)
				.doubleValue();
	}

	/**
	 * 乘法(保留2位小数四舍五入)
	 * 
	 * @param s1
	 *            ...
	 * @return
	 */
	public static double multiply(double... s1) {
		BigDecimal count = new BigDecimal(1); // 初始化1开始乘
		for (double item : s1) {
			BigDecimal itemDecimal = new BigDecimal(item);
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
		BigDecimal bigS1 = new BigDecimal(s1);
		BigDecimal bigS2 = new BigDecimal(s2);
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
		BigDecimal bigS1 = new BigDecimal(s1);
		BigDecimal bigS2 = new BigDecimal(s2);
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

	public static void main(String[] args) {
		double a = 1.111;
		double b = 2.123;
		System.out.println(WalletUtils.add(a, b, 5));
		System.out.println(WalletUtils.formatPercent(WalletUtils.divide(a, b)));
	}

}
