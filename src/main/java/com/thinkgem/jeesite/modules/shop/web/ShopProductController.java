/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.shop.web;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Row;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.utils.excel.ImportExcel;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.shop.entity.ShopCustomerLevel;
import com.thinkgem.jeesite.modules.shop.entity.ShopProduct;
import com.thinkgem.jeesite.modules.shop.entity.ShopProductPrice;
import com.thinkgem.jeesite.modules.shop.service.ShopCustomerLevelService;
import com.thinkgem.jeesite.modules.shop.service.ShopProductService;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;

/**
 * 商品基本信息Controller
 * 
 * @author swbssd
 * @version 2018-04-06
 */
@Controller
@RequestMapping(value = "${adminPath}/shop/shopProduct")
public class ShopProductController extends BaseController {

	@Autowired
	private ShopProductService shopProductService;
	@Autowired
	private ShopCustomerLevelService shopCustomerLevelService;

	@ModelAttribute
	public ShopProduct get(@RequestParam(required = false) String id) {
		ShopProduct entity = null;
		if (StringUtils.isNotBlank(id)) {
			entity = shopProductService.get(id);
		}
		if (entity == null) {
			entity = new ShopProduct();
		}
		return entity;
	}

	@RequiresPermissions("shop:shopProduct:view")
	@RequestMapping(value = { "list", "" })
	public String list(ShopProduct shopProduct, HttpServletRequest request, HttpServletResponse response, Model model) {
		// 判断查询如果是字母，则激活拼音查询
		if (shopProduct.getProductName() != null) {
			String productName = shopProduct.getProductName();
			boolean isWord = productName.matches("[a-zA-Z]+");
			if (isWord) {
				// char[] c = productName.toCharArray();
				// String pingyinStr = "%";
				// for(char yw:c) {
				// pingyinStr+=yw+"%";
				// }
				shopProduct.setPingyinStr(productName.toLowerCase());
				shopProduct.setProductName(null);
			}
		}
		shopProduct.setOfficeId(UserUtils.getUser().getOffice().getId());
		Page<ShopProduct> page = shopProductService.findPage(new Page<ShopProduct>(request, response), shopProduct);
		model.addAttribute("page", page);
		return "modules/shop/shopProductList";
	}

	@RequiresPermissions("shop:shopProduct:view")
	@RequestMapping(value = "form")
	public String form(ShopProduct shopProduct, Model model) {
		// 新增初始化客户级别
		if (StringUtils.isEmpty(shopProduct.getId())) {
			ShopCustomerLevel pram = new ShopCustomerLevel();
			pram.setOfficeId(UserUtils.getUser().getOffice().getId());
			List<ShopCustomerLevel> leveList = shopCustomerLevelService.findList(pram);
			List<ShopProductPrice> priceList = new ArrayList<ShopProductPrice>();
			// 初始化价格
			for (ShopCustomerLevel level : leveList) {
				ShopProductPrice price = new ShopProductPrice();
				price.setLevelId(level.getId());
				price.setLevelName(level.getLevelName());
				price.setDiscount(level.getDiscount());
				price.setListNo(level.getSort());
				priceList.add(price);
			}
			shopProduct.setShopProductPriceList(priceList);
		}
		// 排序
		if (StringUtils.isBlank(shopProduct.getId())) {
			ShopProduct parm = new ShopProduct();
			parm.setOfficeId(UserUtils.getUser().getOffice().getId());
			List<ShopProduct> list = shopProductService.findList(parm);
			if (list.size() > 0) {
				shopProduct.setListNo(list.get(list.size() - 1).getListNo() + 1);
			} else {
				shopProduct.setListNo(1);
			}
		}
		model.addAttribute("shopProduct", shopProduct);
		return "modules/shop/shopProductForm";
	}

	@RequiresPermissions("shop:shopProduct:edit")
	@RequestMapping(value = "save")
	public String save(ShopProduct shopProduct, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, shopProduct)) {
			return form(shopProduct, model);
		}
		// 设置机构
		shopProduct.setOfficeId(UserUtils.getUser().getOffice().getId());
		shopProductService.save(shopProduct);
		addMessage(redirectAttributes, "保存商品基本信息成功");
		return "redirect:" + Global.getAdminPath() + "/shop/shopProduct/?repage";
	}

	@RequiresPermissions("shop:shopProduct:edit")
	@RequestMapping(value = "delete")
	public String delete(ShopProduct shopProduct, RedirectAttributes redirectAttributes) {
		shopProductService.delete(shopProduct);
		addMessage(redirectAttributes, "删除商品基本信息成功");
		return "redirect:" + Global.getAdminPath() + "/shop/shopProduct/?repage";
	}
	
	@ResponseBody
	@RequestMapping(value = "impExcel")
	public String impExcel(
			@RequestParam(required = false) String extId,
			HttpServletResponse response) {
		shopProductService.impExcel();
		return "ok";
	}	

}