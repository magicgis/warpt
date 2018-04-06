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
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.shop.entity.ShopProductType;
import com.thinkgem.jeesite.modules.shop.service.ShopProductTypeService;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;

/**
 * 商品类型Controller
 * @author swbssd
 * @version 2018-04-06
 */
@Controller
@RequestMapping(value = "${adminPath}/shop/shopProductType")
public class ShopProductTypeController extends BaseController {

	@Autowired
	private ShopProductTypeService shopProductTypeService;
	
	@ModelAttribute
	public ShopProductType get(@RequestParam(required=false) String id) {
		ShopProductType entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = shopProductTypeService.get(id);
		}
		if (entity == null){
			entity = new ShopProductType();
		}
		return entity;
	}
	
	@RequiresPermissions("shop:shopProductType:view")
	@RequestMapping(value = {"list", ""})
	public String list(ShopProductType shopProductType, HttpServletRequest request, HttpServletResponse response, Model model) {
		shopProductType.setOffice(UserUtils.getUser().getOffice());
		List<ShopProductType> list = shopProductTypeService.findList(shopProductType); 
		model.addAttribute("list", list);
		return "modules/shop/shopProductTypeList";
	}

	@RequiresPermissions("shop:shopProductType:view")
	@RequestMapping(value = "form")
	public String form(ShopProductType shopProductType, Model model) {
		if (shopProductType.getParent()!=null && StringUtils.isNotBlank(shopProductType.getParent().getId())){
			shopProductType.setParent(shopProductTypeService.get(shopProductType.getParent().getId()));
			// 获取排序号，最末节点排序号+30
			if (StringUtils.isBlank(shopProductType.getId())){
				ShopProductType shopProductTypeChild = new ShopProductType();
				shopProductTypeChild.setParent(new ShopProductType(shopProductType.getParent().getId()));
				List<ShopProductType> list = shopProductTypeService.findList(shopProductType); 
				if (list.size() > 0){
					shopProductType.setSort(list.get(list.size()-1).getSort());
					if (shopProductType.getSort() != null){
						shopProductType.setSort(shopProductType.getSort() + 30);
					}
				}
			}
		}
		if (shopProductType.getSort() == null){
			shopProductType.setSort(30);
		}
		model.addAttribute("shopProductType", shopProductType);
		return "modules/shop/shopProductTypeForm";
	}

	@RequiresPermissions("shop:shopProductType:edit")
	@RequestMapping(value = "save")
	public String save(ShopProductType shopProductType, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, shopProductType)){
			return form(shopProductType, model);
		}
		//设置机构
		shopProductType.setOffice(UserUtils.getUser().getOffice());
		shopProductTypeService.save(shopProductType);
		addMessage(redirectAttributes, "保存商品类型成功");
		return "redirect:"+Global.getAdminPath()+"/shop/shopProductType/?repage";
	}
	
	@RequiresPermissions("shop:shopProductType:edit")
	@RequestMapping(value = "delete")
	public String delete(ShopProductType shopProductType, RedirectAttributes redirectAttributes) {
		shopProductTypeService.delete(shopProductType);
		addMessage(redirectAttributes, "删除商品类型成功");
		return "redirect:"+Global.getAdminPath()+"/shop/shopProductType/?repage";
	}

	@RequiresPermissions("user")
	@ResponseBody
	@RequestMapping(value = "treeData")
	public List<Map<String, Object>> treeData(@RequestParam(required=false) String extId, HttpServletResponse response) {
		List<Map<String, Object>> mapList = Lists.newArrayList();
		ShopProductType shopProductType = new ShopProductType();
		shopProductType.setOffice(UserUtils.getUser().getOffice());
		List<ShopProductType> list = shopProductTypeService.findList(shopProductType);
		for (int i=0; i<list.size(); i++){
			ShopProductType e = list.get(i);
			if (StringUtils.isBlank(extId) || (extId!=null && !extId.equals(e.getId()) && e.getParentIds().indexOf(","+extId+",")==-1)){
				Map<String, Object> map = Maps.newHashMap();
				map.put("id", e.getId());
				map.put("pId", e.getParentId());
				map.put("name", e.getName());
				mapList.add(map);
			}
		}
		return mapList;
	}
	
}