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
import com.thinkgem.jeesite.modules.shop.entity.ShopCustomerLevel;
import com.thinkgem.jeesite.modules.shop.service.ShopCustomerLevelService;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;

/**
 * 客户级别Controller
 * @author swbssd
 * @version 2018-04-17
 */
@Controller
@RequestMapping(value = "${adminPath}/shop/shopCustomerLevel")
public class ShopCustomerLevelController extends BaseController {

	@Autowired
	private ShopCustomerLevelService shopCustomerLevelService;
	
	@ModelAttribute
	public ShopCustomerLevel get(@RequestParam(required=false) String id) {
		ShopCustomerLevel entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = shopCustomerLevelService.get(id);
		}
		if (entity == null){
			entity = new ShopCustomerLevel();
		}
		return entity;
	}
	
	@RequiresPermissions("shop:shopCustomerLevel:view")
	@RequestMapping(value = {"list", ""})
	public String list(ShopCustomerLevel shopCustomerLevel, HttpServletRequest request, HttpServletResponse response, Model model) {
		shopCustomerLevel.setOfficeId(UserUtils.getUser().getOffice().getId());
		Page<ShopCustomerLevel> page = shopCustomerLevelService.findPage(new Page<ShopCustomerLevel>(request, response), shopCustomerLevel); 
		model.addAttribute("page", page);
		return "modules/shop/shopCustomerLevelList";
	}

	@RequiresPermissions("shop:shopCustomerLevel:view")
	@RequestMapping(value = "form")
	public String form(ShopCustomerLevel shopCustomerLevel, Model model) {
		//排序
		if (StringUtils.isBlank(shopCustomerLevel.getId())){
			ShopCustomerLevel parm = new ShopCustomerLevel();
			parm.setOfficeId(UserUtils.getUser().getOffice().getId());
			List<ShopCustomerLevel> list = shopCustomerLevelService.findList(parm);
			if (list.size() > 0){
				shopCustomerLevel.setSort(list.get(list.size()-1).getSort()+1);
			}else {
				shopCustomerLevel.setSort(1);
			}
		}
		model.addAttribute("shopCustomerLevel", shopCustomerLevel);
		return "modules/shop/shopCustomerLevelForm";
	}

	@RequiresPermissions("shop:shopCustomerLevel:edit")
	@RequestMapping(value = "save")
	public String save(ShopCustomerLevel shopCustomerLevel, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, shopCustomerLevel)){
			return form(shopCustomerLevel, model);
		}
		shopCustomerLevel.setOfficeId(UserUtils.getUser().getOffice().getId());
		shopCustomerLevelService.save(shopCustomerLevel);
		addMessage(redirectAttributes, "保存客户级别成功");
		return "redirect:"+Global.getAdminPath()+"/shop/shopCustomerLevel/?repage";
	}
	
	@RequiresPermissions("shop:shopCustomerLevel:edit")
	@RequestMapping(value = "delete")
	public String delete(ShopCustomerLevel shopCustomerLevel, RedirectAttributes redirectAttributes) {
		shopCustomerLevelService.delete(shopCustomerLevel);
		addMessage(redirectAttributes, "删除客户级别成功");
		return "redirect:"+Global.getAdminPath()+"/shop/shopCustomerLevel/?repage";
	}
	
	@ResponseBody
	@RequestMapping(value = "treeData")
	public List<Map<String, Object>> treeData(
			@RequestParam(required = false) String extId,
			HttpServletResponse response) {
		List<Map<String, Object>> mapList = Lists.newArrayList();
		ShopCustomerLevel parm = new ShopCustomerLevel();
		// 登陆用户机构过滤
		parm.setOfficeId(UserUtils.getUser().getOffice().getId());
		List<ShopCustomerLevel> list = shopCustomerLevelService.findList(parm);
		for (int i = 0; i < list.size(); i++) {
			ShopCustomerLevel e = list.get(i);
			Map<String, Object> map = Maps.newHashMap();
			map.put("id", e.getId());
			map.put("pId", "0");
			map.put("name", e.getLevelName());
			mapList.add(map);
		}
		return mapList;
	}	

}