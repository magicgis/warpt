/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.shop.web;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.shop.entity.ShopPurchaseSupplier;
import com.thinkgem.jeesite.modules.shop.service.ShopPurchaseSupplierService;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;

/**
 * 供应商Controller
 * @author swbssd
 * @version 2018-04-12
 */
@Controller
@RequestMapping(value = "${adminPath}/shop/shopPurchaseSupplier")
public class ShopPurchaseSupplierController extends BaseController {

	@Autowired
	private ShopPurchaseSupplierService shopPurchaseSupplierService;
	
	@ModelAttribute
	public ShopPurchaseSupplier get(@RequestParam(required=false) String id) {
		ShopPurchaseSupplier entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = shopPurchaseSupplierService.get(id);
		}
		if (entity == null){
			entity = new ShopPurchaseSupplier();
		}
		return entity;
	}
	
	@RequiresPermissions("shop:shopPurchaseSupplier:view")
	@RequestMapping(value = {"list", ""})
	public String list(ShopPurchaseSupplier shopPurchaseSupplier, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<ShopPurchaseSupplier> page = shopPurchaseSupplierService.findPage(new Page<ShopPurchaseSupplier>(request, response), shopPurchaseSupplier); 
		model.addAttribute("page", page);
		return "modules/shop/shopPurchaseSupplierList";
	}

	@RequiresPermissions("shop:shopPurchaseSupplier:view")
	@RequestMapping(value = "form")
	public String form(ShopPurchaseSupplier shopPurchaseSupplier, Model model) {
		//排序
		if (StringUtils.isBlank(shopPurchaseSupplier.getId())){
			ShopPurchaseSupplier parm = new ShopPurchaseSupplier();
			parm.setOfficeId(UserUtils.getUser().getOffice().getId());
			List<ShopPurchaseSupplier> list = shopPurchaseSupplierService.findList(parm);
			if (list.size() > 0){
				shopPurchaseSupplier.setSort(list.get(list.size()-1).getSort()+1);
			}else {
				shopPurchaseSupplier.setSort(1);
			}
		}
		model.addAttribute("shopPurchaseSupplier", shopPurchaseSupplier);
		return "modules/shop/shopPurchaseSupplierForm";
	}

	@RequiresPermissions("shop:shopPurchaseSupplier:edit")
	@RequestMapping(value = "save")
	public String save(ShopPurchaseSupplier shopPurchaseSupplier, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, shopPurchaseSupplier)){
			return form(shopPurchaseSupplier, model);
		}
		shopPurchaseSupplier.setOfficeId(UserUtils.getUser().getOffice().getId());
		shopPurchaseSupplierService.save(shopPurchaseSupplier);
		addMessage(redirectAttributes, "保存供应商成功");
		return "redirect:"+Global.getAdminPath()+"/shop/shopPurchaseSupplier/?repage";
	}
	
	@RequiresPermissions("shop:shopPurchaseSupplier:edit")
	@RequestMapping(value = "delete")
	public String delete(ShopPurchaseSupplier shopPurchaseSupplier, RedirectAttributes redirectAttributes) {
		shopPurchaseSupplierService.delete(shopPurchaseSupplier);
		addMessage(redirectAttributes, "删除供应商成功");
		return "redirect:"+Global.getAdminPath()+"/shop/shopPurchaseSupplier/?repage";
	}

	@ResponseBody
	@RequestMapping(value = "treeData")
	public List<Map<String, Object>> treeData(
			@RequestParam(required = false) String extId,
			HttpServletResponse response) {
		List<Map<String, Object>> mapList = Lists.newArrayList();
		ShopPurchaseSupplier parm = new ShopPurchaseSupplier();
		// 登陆用户机构过滤
		parm.setOfficeId(UserUtils.getUser().getOffice().getId());
		List<ShopPurchaseSupplier> list = shopPurchaseSupplierService.findList(parm);
		for (int i = 0; i < list.size(); i++) {
			ShopPurchaseSupplier e = list.get(i);
			Map<String, Object> map = Maps.newHashMap();
			map.put("id", e.getId());
			map.put("pId", "0");
			map.put("name", e.getSupplierName());
			mapList.add(map);
		}
		return mapList;
	}	
}