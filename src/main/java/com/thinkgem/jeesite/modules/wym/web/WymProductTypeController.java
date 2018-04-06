/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.wym.web;

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
import com.thinkgem.jeesite.modules.wym.entity.WymProductType;
import com.thinkgem.jeesite.modules.wym.service.WymProductTypeService;

/**
 * 商品类型Controller
 * @author swbssd
 * @version 2017-02-23
 */
@Controller
@RequestMapping(value = "${adminPath}/wym/wymProductType")
public class WymProductTypeController extends BaseController {

	@Autowired
	private WymProductTypeService wymProductTypeService;
	
	@ModelAttribute
	public WymProductType get(@RequestParam(required=false) String id) {
		WymProductType entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = wymProductTypeService.get(id);
		}
		if (entity == null){
			entity = new WymProductType();
		}
		return entity;
	}
	
	@RequiresPermissions("wym:wymProductType:view")
	@RequestMapping(value = {"list", ""})
	public String list(WymProductType wymProductType, HttpServletRequest request, HttpServletResponse response, Model model) {
		List<WymProductType> list = wymProductTypeService.findList(wymProductType); 
		model.addAttribute("list", list);
		return "modules/wym/wymProductTypeList";
	}

	@RequiresPermissions("wym:wymProductType:view")
	@RequestMapping(value = "form")
	public String form(WymProductType wymProductType, Model model) {
		if (wymProductType.getParent()!=null && StringUtils.isNotBlank(wymProductType.getParent().getId())){
			wymProductType.setParent(wymProductTypeService.get(wymProductType.getParent().getId()));
			// 获取排序号，最末节点排序号+30
			if (StringUtils.isBlank(wymProductType.getId())){
				WymProductType wymProductTypeChild = new WymProductType();
				wymProductTypeChild.setParent(new WymProductType(wymProductType.getParent().getId()));
				List<WymProductType> list = wymProductTypeService.findList(wymProductType); 
				if (list.size() > 0){
					wymProductType.setSort(list.get(list.size()-1).getSort());
					if (wymProductType.getSort() != null){
						wymProductType.setSort(wymProductType.getSort() + 30);
					}
				}
			}
		}
		if (wymProductType.getSort() == null){
			wymProductType.setSort(30);
		}
		model.addAttribute("wymProductType", wymProductType);
		return "modules/wym/wymProductTypeForm";
	}

	@RequiresPermissions("wym:wymProductType:edit")
	@RequestMapping(value = "save")
	public String save(WymProductType wymProductType, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, wymProductType)){
			return form(wymProductType, model);
		}
		wymProductTypeService.save(wymProductType);
		addMessage(redirectAttributes, "保存商品类型成功");
		return "redirect:"+Global.getAdminPath()+"/wym/wymProductType/?repage";
	}
	
	@RequiresPermissions("wym:wymProductType:edit")
	@RequestMapping(value = "delete")
	public String delete(WymProductType wymProductType, RedirectAttributes redirectAttributes) {
		wymProductTypeService.delete(wymProductType);
		addMessage(redirectAttributes, "删除商品类型成功");
		return "redirect:"+Global.getAdminPath()+"/wym/wymProductType/?repage";
	}

	@RequiresPermissions("user")
	@ResponseBody
	@RequestMapping(value = "treeData")
	public List<Map<String, Object>> treeData(@RequestParam(required=false) String extId, HttpServletResponse response) {
		List<Map<String, Object>> mapList = Lists.newArrayList();
		List<WymProductType> list = wymProductTypeService.findList(new WymProductType());
		for (int i=0; i<list.size(); i++){
			WymProductType e = list.get(i);
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