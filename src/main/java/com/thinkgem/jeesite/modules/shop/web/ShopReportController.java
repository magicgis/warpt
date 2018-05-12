/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.shop.web;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.shop.entity.ShopSaleOrderItem;
import com.thinkgem.jeesite.modules.shop.service.ShopSaleOrderService;
import com.thinkgem.jeesite.modules.shop.utils.ShopUtils;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;

/**
 * 报表Controller
 * @author swbssd
 * @version 2018-04-20
 */
@Controller
@RequestMapping(value = "${adminPath}/shop/shopReport")
public class ShopReportController extends BaseController {

	@Autowired
	private ShopSaleOrderService shopSaleOrderService;

	
	/**
	 * 销售明细表
	 * @param shopSaleOrderItem
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = {"saleReportList", ""})
	public String saleReportList(ShopSaleOrderItem shopSaleOrderItem, HttpServletRequest request, HttpServletResponse response, Model model) {

		shopSaleOrderItem.setOfficeId(UserUtils.getUser().getOffice().getId());
		//求和findItemPage
		List<ShopSaleOrderItem> countList = shopSaleOrderService.findSumItem(shopSaleOrderItem);
		if(!countList.isEmpty() && countList.size() == 1) {
			ShopSaleOrderItem accountObj = countList.get(0);
			shopSaleOrderItem.setSumProduct(accountObj.getSumProduct());
			shopSaleOrderItem.setSumMoney(accountObj.getSumMoney());
			shopSaleOrderItem.setSumProfit(accountObj.getSumProfit());
			shopSaleOrderItem.setPercentage(ShopUtils.divide(accountObj.getSumProfit(), accountObj.getSumMoney())*100);
		}
		//查询列表
		Page<ShopSaleOrderItem> page = shopSaleOrderService.findItemPage(new Page<ShopSaleOrderItem>(request, response), shopSaleOrderItem); 
		model.addAttribute("page", page);

		model.addAttribute("shopSaleOrderItem", shopSaleOrderItem);
		return "modules/shop/saleReportList";
	}
	
	/**
	 * 销售商品汇总
	 * @param shopSaleOrderItem
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = {"saleProductSumReport", ""})
	public String saleProductSumReport(ShopSaleOrderItem shopSaleOrderItem, HttpServletRequest request, HttpServletResponse response, Model model) {
		shopSaleOrderItem.setOfficeId(UserUtils.getUser().getOffice().getId());
		//求和findItemPage
		List<ShopSaleOrderItem> countList = shopSaleOrderService.findSumItem(shopSaleOrderItem);
		if(!countList.isEmpty() && countList.size() == 1) {
			ShopSaleOrderItem accountObj = countList.get(0);
			shopSaleOrderItem.setSumProduct(accountObj.getSumProduct());
			shopSaleOrderItem.setSumMoney(accountObj.getSumMoney());
			shopSaleOrderItem.setSumProfit(accountObj.getSumProfit());
			shopSaleOrderItem.setPercentage(ShopUtils.divide(accountObj.getSumProfit(), accountObj.getSumMoney())*100);
		}
		
		Page<ShopSaleOrderItem> page = shopSaleOrderService.findGroupByProductPage(new Page<ShopSaleOrderItem>(request, response), shopSaleOrderItem); 
		model.addAttribute("page", page);
		model.addAttribute("shopSaleOrderItem", shopSaleOrderItem);
		return "modules/shop/saleProductSumReport";
	}
	
	/**
	 * 销售客户汇总
	 * @param shopSaleOrderItem
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = {"saleCustomerSumReport", ""})
	public String saleCustomerSumReport(ShopSaleOrderItem shopSaleOrderItem, HttpServletRequest request, HttpServletResponse response, Model model) {
		shopSaleOrderItem.setOfficeId(UserUtils.getUser().getOffice().getId());
		//求和findItemPage
		List<ShopSaleOrderItem> countList = shopSaleOrderService.findSumItem(shopSaleOrderItem);
		if(!countList.isEmpty() && countList.size() == 1) {
			ShopSaleOrderItem accountObj = countList.get(0);
			shopSaleOrderItem.setSumProduct(accountObj.getSumProduct());
			shopSaleOrderItem.setSumMoney(accountObj.getSumMoney());
			shopSaleOrderItem.setSumProfit(accountObj.getSumProfit());
			shopSaleOrderItem.setPercentage(ShopUtils.divide(accountObj.getSumProfit(), accountObj.getSumMoney())*100);
		}
		
		Page<ShopSaleOrderItem> page = shopSaleOrderService.findGroupByCustomerPage(new Page<ShopSaleOrderItem>(request, response), shopSaleOrderItem); 
		model.addAttribute("page", page);
		model.addAttribute("shopSaleOrderItem", shopSaleOrderItem);
		return "modules/shop/saleCustomerSumReport";
	}

}