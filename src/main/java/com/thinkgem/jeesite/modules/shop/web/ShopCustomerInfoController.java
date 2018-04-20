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
import com.thinkgem.jeesite.modules.shop.entity.ShopCustomerInfo;
import com.thinkgem.jeesite.modules.shop.service.ShopCustomerInfoService;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;

/**
 * 销售客户Controller
 * @author swbssd
 * @version 2018-04-17
 */
@Controller
@RequestMapping(value = "${adminPath}/shop/shopCustomerInfo")
public class ShopCustomerInfoController extends BaseController {

	@Autowired
	private ShopCustomerInfoService shopCustomerInfoService;
	
	@ModelAttribute
	public ShopCustomerInfo get(@RequestParam(required=false) String id) {
		ShopCustomerInfo entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = shopCustomerInfoService.get(id);
		}
		if (entity == null){
			entity = new ShopCustomerInfo();
		}
		return entity;
	}
	
	@RequiresPermissions("shop:shopCustomerInfo:view")
	@RequestMapping(value = {"list", ""})
	public String list(ShopCustomerInfo shopCustomerInfo, HttpServletRequest request, HttpServletResponse response, Model model) {
		shopCustomerInfo.setOfficeId(UserUtils.getUser().getOffice().getId());
		Page<ShopCustomerInfo> page = shopCustomerInfoService.findPage(new Page<ShopCustomerInfo>(request, response), shopCustomerInfo); 
		model.addAttribute("page", page);
		return "modules/shop/shopCustomerInfoList";
	}

	@RequiresPermissions("shop:shopCustomerInfo:view")
	@RequestMapping(value = "form")
	public String form(ShopCustomerInfo shopCustomerInfo, Model model) {
		//排序
		if (StringUtils.isBlank(shopCustomerInfo.getId())){
			ShopCustomerInfo parm = new ShopCustomerInfo();
			parm.setOfficeId(UserUtils.getUser().getOffice().getId());
			List<ShopCustomerInfo> list = shopCustomerInfoService.findList(parm);
			if (list.size() > 0){
				shopCustomerInfo.setSort(list.get(list.size()-1).getSort()+1);
			}else {
				shopCustomerInfo.setSort(1);
			}
		}
		model.addAttribute("shopCustomerInfo", shopCustomerInfo);
		return "modules/shop/shopCustomerInfoForm";
	}

	@RequiresPermissions("shop:shopCustomerInfo:edit")
	@RequestMapping(value = "save")
	public String save(ShopCustomerInfo shopCustomerInfo, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, shopCustomerInfo)){
			return form(shopCustomerInfo, model);
		}
		shopCustomerInfo.setOfficeId(UserUtils.getUser().getOffice().getId());
		shopCustomerInfoService.save(shopCustomerInfo);
		addMessage(redirectAttributes, "保存客户成功");
		return "redirect:"+Global.getAdminPath()+"/shop/shopCustomerInfo/?repage";
	}
	
	@RequiresPermissions("shop:shopCustomerInfo:edit")
	@RequestMapping(value = "delete")
	public String delete(ShopCustomerInfo shopCustomerInfo, RedirectAttributes redirectAttributes) {
		shopCustomerInfoService.delete(shopCustomerInfo);
		addMessage(redirectAttributes, "删除客户成功");
		return "redirect:"+Global.getAdminPath()+"/shop/shopCustomerInfo/?repage";
	}
	
	@ResponseBody
	@RequestMapping(value = "treeData")
	public List<Map<String, Object>> treeData(
			@RequestParam(required = false) String extId,
			HttpServletResponse response) {
		List<Map<String, Object>> mapList = Lists.newArrayList();
		ShopCustomerInfo parm = new ShopCustomerInfo();
		// 登陆用户机构过滤
		parm.setOfficeId(UserUtils.getUser().getOffice().getId());
		List<ShopCustomerInfo> list = shopCustomerInfoService.findList(parm);
		for (int i = 0; i < list.size(); i++) {
			ShopCustomerInfo e = list.get(i);
			Map<String, Object> map = Maps.newHashMap();
			map.put("id", e.getId());
			map.put("pId", "0");
			map.put("name", e.getCustomerName()+"["+e.getPhone()+"]");
			mapList.add(map);
		}
		return mapList;
	}	

}