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
import com.thinkgem.jeesite.modules.shop.entity.ShopStockInfo;
import com.thinkgem.jeesite.modules.shop.service.ShopStockInfoService;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;

/**
 * 仓库基础信息Controller
 * @author swbssd
 * @version 2018-04-06
 */
@Controller
@RequestMapping(value = "${adminPath}/shop/shopStockInfo")
public class ShopStockInfoController extends BaseController {

	@Autowired
	private ShopStockInfoService shopStockInfoService;
	
	@ModelAttribute
	public ShopStockInfo get(@RequestParam(required=false) String id) {
		ShopStockInfo entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = shopStockInfoService.get(id);
		}
		if (entity == null){
			entity = new ShopStockInfo();
		}
		return entity;
	}
	
	@RequiresPermissions("shop:shopStockInfo:view")
	@RequestMapping(value = {"list", ""})
	public String list(ShopStockInfo shopStockInfo, HttpServletRequest request, HttpServletResponse response, Model model) {
		shopStockInfo.setOfficeId(UserUtils.getUser().getOffice().getId());
		Page<ShopStockInfo> page = shopStockInfoService.findPage(new Page<ShopStockInfo>(request, response), shopStockInfo); 
		model.addAttribute("page", page);
		return "modules/shop/shopStockInfoList";
	}

	@RequiresPermissions("shop:shopStockInfo:view")
	@RequestMapping(value = "form")
	public String form(ShopStockInfo shopStockInfo, Model model) {
		model.addAttribute("shopStockInfo", shopStockInfo);
		return "modules/shop/shopStockInfoForm";
	}

	@RequiresPermissions("shop:shopStockInfo:edit")
	@RequestMapping(value = "save")
	public String save(ShopStockInfo shopStockInfo, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, shopStockInfo)){
			return form(shopStockInfo, model);
		}
		shopStockInfo.setOfficeId(UserUtils.getUser().getOffice().getId());
		shopStockInfoService.save(shopStockInfo);
		addMessage(redirectAttributes, "保存仓库基础信息成功");
		return "redirect:"+Global.getAdminPath()+"/shop/shopStockInfo/?repage";
	}
	
	@RequiresPermissions("shop:shopStockInfo:edit")
	@RequestMapping(value = "delete")
	public String delete(ShopStockInfo shopStockInfo, RedirectAttributes redirectAttributes) {
		shopStockInfoService.delete(shopStockInfo);
		addMessage(redirectAttributes, "删除仓库基础信息成功");
		return "redirect:"+Global.getAdminPath()+"/shop/shopStockInfo/?repage";
	}

}