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
import com.thinkgem.jeesite.modules.shop.entity.ShopSupplierAccount;
import com.thinkgem.jeesite.modules.shop.service.ShopSupplierAccountService;

/**
 * 供应商付款Controller
 * @author swbssd
 * @version 2018-04-19
 */
@Controller
@RequestMapping(value = "${adminPath}/shop/shopSupplierAccount")
public class ShopSupplierAccountController extends BaseController {

	@Autowired
	private ShopSupplierAccountService shopSupplierAccountService;
	
	@ModelAttribute
	public ShopSupplierAccount get(@RequestParam(required=false) String id) {
		ShopSupplierAccount entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = shopSupplierAccountService.get(id);
		}
		if (entity == null){
			entity = new ShopSupplierAccount();
		}
		return entity;
	}
	
	@RequiresPermissions("shop:shopSupplierAccount:view")
	@RequestMapping(value = {"list", ""})
	public String list(ShopSupplierAccount shopSupplierAccount, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<ShopSupplierAccount> page = shopSupplierAccountService.findPage(new Page<ShopSupplierAccount>(request, response), shopSupplierAccount); 
		model.addAttribute("page", page);
		return "modules/shop/shopSupplierAccountList";
	}

	@RequiresPermissions("shop:shopSupplierAccount:view")
	@RequestMapping(value = "form")
	public String form(ShopSupplierAccount shopSupplierAccount, Model model) {
		model.addAttribute("shopSupplierAccount", shopSupplierAccount);
		return "modules/shop/shopSupplierAccountForm";
	}

	@RequiresPermissions("shop:shopSupplierAccount:edit")
	@RequestMapping(value = "save")
	public String save(ShopSupplierAccount shopSupplierAccount, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, shopSupplierAccount)){
			return form(shopSupplierAccount, model);
		}
		shopSupplierAccountService.save(shopSupplierAccount);
		addMessage(redirectAttributes, "保存供应商付款成功");
		return "redirect:"+Global.getAdminPath()+"/shop/shopSupplierAccount/?repage";
	}
	
	@RequiresPermissions("shop:shopSupplierAccount:edit")
	@RequestMapping(value = "delete")
	public String delete(ShopSupplierAccount shopSupplierAccount, RedirectAttributes redirectAttributes) {
		shopSupplierAccountService.delete(shopSupplierAccount);
		addMessage(redirectAttributes, "删除供应商付款成功");
		return "redirect:"+Global.getAdminPath()+"/shop/shopSupplierAccount/?repage";
	}

}