/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.shop.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.shop.entity.ShopProduct;
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
			boolean isWord=productName.matches("[a-zA-Z]+");
			if(isWord) {
				char[] c = productName.toCharArray();
				String pingyinStr = "%";
				for(char yw:c) {
					pingyinStr+=yw+"%";
				}
				shopProduct.setPingyinStr(pingyinStr);
				shopProduct.setProductName(null);
			}
		}
		Page<ShopProduct> page = shopProductService.findPage(new Page<ShopProduct>(request, response), shopProduct);
		model.addAttribute("page", page);
		return "modules/shop/shopProductList";
	}

	@RequiresPermissions("shop:shopProduct:view")
	@RequestMapping(value = "form")
	public String form(ShopProduct shopProduct, Model model) {
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
		shopProduct.setOffice_id(UserUtils.getUser().getOffice().getId());
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

}