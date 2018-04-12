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
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.shop.entity.ShopProductType;
import com.thinkgem.jeesite.modules.shop.entity.ShopStockInfo;
import com.thinkgem.jeesite.modules.shop.service.ShopStockInfoService;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import com.thinkgem.jeesite.modules.vip.entity.VipUserBase;

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
		//排序
		if (StringUtils.isBlank(shopStockInfo.getId())){
			ShopStockInfo parm = new ShopStockInfo();
			parm.setOfficeId(UserUtils.getUser().getOffice().getId());
			List<ShopStockInfo> list = shopStockInfoService.findList(parm);
			if (list.size() > 0){
				shopStockInfo.setSort(list.get(list.size()-1).getSort()+1);
			}else {
				shopStockInfo.setSort(1);
			}
		}
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
	
	@ResponseBody
	@RequestMapping(value = "treeData")
	public List<Map<String, Object>> treeData(
			@RequestParam(required = false) String extId,
			HttpServletResponse response) {
		List<Map<String, Object>> mapList = Lists.newArrayList();
		ShopStockInfo parm = new ShopStockInfo();
		// 登陆用户机构过滤
		parm.setOfficeId(UserUtils.getUser().getOffice().getId());
		List<ShopStockInfo> list = shopStockInfoService.findList(parm);
		for (int i = 0; i < list.size(); i++) {
			ShopStockInfo e = list.get(i);
			Map<String, Object> map = Maps.newHashMap();
			map.put("id", e.getId());
			map.put("pId", "0");
			map.put("name", e.getStockName());
			mapList.add(map);
		}
		return mapList;
	}	

}