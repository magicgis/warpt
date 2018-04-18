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
import com.thinkgem.jeesite.modules.shop.entity.ShopStockItem;
import com.thinkgem.jeesite.modules.shop.service.ShopStockItemService;

/**
 * 仓库库存Controller
 * @author swbssd
 * @version 2018-04-17
 */
@Controller
@RequestMapping(value = "${adminPath}/shop/shopStockItem")
public class ShopStockItemController extends BaseController {

	@Autowired
	private ShopStockItemService shopStockItemService;
	
	@ModelAttribute
	public ShopStockItem get(@RequestParam(required=false) String id) {
		ShopStockItem entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = shopStockItemService.get(id);
		}
		if (entity == null){
			entity = new ShopStockItem();
		}
		return entity;
	}
	
	@RequiresPermissions("shop:shopStockItem:view")
	@RequestMapping(value = {"list", ""})
	public String list(ShopStockItem shopStockItem, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<ShopStockItem> page = shopStockItemService.findPage(new Page<ShopStockItem>(request, response), shopStockItem); 
		model.addAttribute("page", page);
		return "modules/shop/shopStockItemList";
	}

	@RequiresPermissions("shop:shopStockItem:view")
	@RequestMapping(value = "form")
	public String form(ShopStockItem shopStockItem, Model model) {
		model.addAttribute("shopStockItem", shopStockItem);
		return "modules/shop/shopStockItemForm";
	}

	@RequiresPermissions("shop:shopStockItem:edit")
	@RequestMapping(value = "save")
	public String save(ShopStockItem shopStockItem, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, shopStockItem)){
			return form(shopStockItem, model);
		}
		shopStockItemService.save(shopStockItem);
		addMessage(redirectAttributes, "保存仓库库存成功");
		return "redirect:"+Global.getAdminPath()+"/shop/shopStockItem/?repage";
	}
	
	@RequiresPermissions("shop:shopStockItem:edit")
	@RequestMapping(value = "delete")
	public String delete(ShopStockItem shopStockItem, RedirectAttributes redirectAttributes) {
		shopStockItemService.delete(shopStockItem);
		addMessage(redirectAttributes, "删除仓库库存成功");
		return "redirect:"+Global.getAdminPath()+"/shop/shopStockItem/?repage";
	}

}