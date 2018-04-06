/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.vip.web;

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
import com.thinkgem.jeesite.modules.vip.entity.VipUserLevel;
import com.thinkgem.jeesite.modules.vip.service.VipUserLevelService;

/**
 * 会员等级设置Controller
 * 
 * @author swbssd
 * @version 2017-09-17
 */
@Controller
@RequestMapping(value = "${adminPath}/vip/vipUserLevel")
public class VipUserLevelController extends BaseController {

	@Autowired
	private VipUserLevelService vipUserLevelService;

	@ModelAttribute
	public VipUserLevel get(@RequestParam(required = false) String id) {
		VipUserLevel entity = null;
		if (StringUtils.isNotBlank(id)) {
			entity = vipUserLevelService.get(id);
		}
		if (entity == null) {
			entity = new VipUserLevel();
			entity.setDiscount("1");
			entity.setIsDiscount(1);
		}
		return entity;
	}

	@RequiresPermissions("vip:vipUserLevel:view")
	@RequestMapping(value = { "list", "" })
	public String list(VipUserLevel vipUserLevel, HttpServletRequest request,
			HttpServletResponse response, Model model) {
		Page<VipUserLevel> page = vipUserLevelService.findPage(
				new Page<VipUserLevel>(request, response), vipUserLevel);
		model.addAttribute("page", page);
		return "modules/vip/vipUserLevelList";
	}

	@RequiresPermissions("vip:vipUserLevel:view")
	@RequestMapping(value = "form")
	public String form(VipUserLevel vipUserLevel, Model model) {
		model.addAttribute("vipUserLevel", vipUserLevel);
		return "modules/vip/vipUserLevelForm";
	}

	@RequiresPermissions("vip:vipUserLevel:edit")
	@RequestMapping(value = "save")
	public String save(VipUserLevel vipUserLevel, Model model,
			RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, vipUserLevel)) {
			return form(vipUserLevel, model);
		}
		vipUserLevelService.save(vipUserLevel);
		addMessage(redirectAttributes, "保存会员等级成功");
		return "redirect:" + Global.getAdminPath()
				+ "/vip/vipUserLevel/?repage";
	}

	@RequiresPermissions("vip:vipUserLevel:edit")
	@RequestMapping(value = "delete")
	public String delete(VipUserLevel vipUserLevel,
			RedirectAttributes redirectAttributes) {
		vipUserLevelService.delete(vipUserLevel);
		addMessage(redirectAttributes, "删除会员等级成功");
		return "redirect:" + Global.getAdminPath()
				+ "/vip/vipUserLevel/?repage";
	}

	@ResponseBody
	@RequestMapping(value = "treeData")
	public List<Map<String, Object>> treeData(
			@RequestParam(required = false) String extId,
			HttpServletResponse response) {
		List<Map<String, Object>> mapList = Lists.newArrayList();
		VipUserLevel parm = new VipUserLevel();
		List<VipUserLevel> list = vipUserLevelService.findList(parm);
		for (int i = 0; i < list.size(); i++) {
			VipUserLevel e = list.get(i);
			Map<String, Object> map = Maps.newHashMap();
			map.put("id", e.getId());
			map.put("pId", "0");
			map.put("name", e.getLevelName());
			// map.put("name",
			// e.getLevelName() + "[" + Double.valueOf(e.getDiscount())
			// * 10 + "折]");
			mapList.add(map);

		}
		return mapList;
	}

}