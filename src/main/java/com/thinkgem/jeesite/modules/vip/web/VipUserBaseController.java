/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.vip.web;

import java.io.File;
import java.util.HashMap;
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
import com.thinkgem.jeesite.common.utils.MessageUtil;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.shop.entity.ShopCustomerLevel;
import com.thinkgem.jeesite.modules.shop.service.ShopCustomerLevelService;
import com.thinkgem.jeesite.modules.sys.entity.Area;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import com.thinkgem.jeesite.modules.vip.entity.VipUserBase;
import com.thinkgem.jeesite.modules.vip.service.VipUserBaseService;

/**
 * 会员基本信息Controller
 * 
 * @author swbssd
 * @version 2017-09-17
 */
@Controller
@RequestMapping(value = "${adminPath}/vip/vipUserBase")
public class VipUserBaseController extends BaseController {

	@Autowired
	private VipUserBaseService vipUserBaseService;
	@Autowired
	private ShopCustomerLevelService shopCustomerLevelService;
	
	@ModelAttribute
	public VipUserBase get(@RequestParam(required = false) String id) {
		VipUserBase entity = null;
		if (StringUtils.isNotBlank(id)) {
			entity = vipUserBaseService.get(id);
		}
		if (entity == null) {
			entity = new VipUserBase();
			// 初始化默认
			Area area = new Area();
			area.setId("3");
			area.setName("珠海市");
			entity.setArea(area);
			//entity.setLevelId("1");
			//entity.setLevelName("普通用户");
			//默认初始化第一个级别
			ShopCustomerLevel parm = new ShopCustomerLevel();
			parm.setOfficeId(UserUtils.getUser().getOffice().getId());
			List<ShopCustomerLevel> list = shopCustomerLevelService.findList(parm);
			if(list==null || list.isEmpty()) {
				throw new RuntimeException("请先初始化优惠级别。");
			}
			ShopCustomerLevel oneLevel = list.get(0);
			entity.setLevelId(oneLevel.getId());
			entity.setLevelName(oneLevel.getLevelName());
		}
		return entity;
	}

	@RequiresPermissions("vip:vipUserBase:view")
	@RequestMapping(value = { "list", "" })
	public String list(VipUserBase vipUserBase, HttpServletRequest request,
			HttpServletResponse response, Model model) {
		// 登陆用户机构过滤
		vipUserBase.setOfficeId(UserUtils.getUser().getOffice().getId());
		Page<VipUserBase> page = vipUserBaseService.findPage(
				new Page<VipUserBase>(request, response), vipUserBase);
		model.addAttribute("page", page);
		return "modules/vip/vipUserBaseList";
	}

	@RequiresPermissions("vip:vipUserBase:view")
	@RequestMapping(value = "form")
	public String form(VipUserBase vipUserBase, Model model) {
		model.addAttribute("vipUserBase", vipUserBase);
		return "modules/vip/vipUserBaseForm";
	}

	@RequiresPermissions("vip:vipUserBase:edit")
	@RequestMapping(value = "save")
	public String save(VipUserBase vipUserBase, Model model,
			RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, vipUserBase)) {
			return form(vipUserBase, model);
		}
		// 登陆用户机构过滤
		vipUserBase.setOfficeId(UserUtils.getUser().getOffice().getId());
		vipUserBaseService.save(vipUserBase);
		addMessage(redirectAttributes, "保存会员基本信息成功");
		return "redirect:" + Global.getAdminPath() + "/vip/vipUserBase/?repage";
	}

	@RequiresPermissions("vip:vipUserBase:edit")
	@RequestMapping(value = "delete")
	public String delete(VipUserBase vipUserBase,
			RedirectAttributes redirectAttributes) {
		vipUserBaseService.delete(vipUserBase);
		addMessage(redirectAttributes, "删除会员基本信息成功");
		return "redirect:" + Global.getAdminPath() + "/vip/vipUserBase/?repage";
	}

	@ResponseBody
	@RequestMapping(value = "treeData")
	public List<Map<String, Object>> treeData(
			@RequestParam(required = false) String extId,
			HttpServletResponse response) {
		List<Map<String, Object>> mapList = Lists.newArrayList();
		VipUserBase parm = new VipUserBase();
		// 登陆用户机构过滤
		parm.setOfficeId(UserUtils.getUser().getOffice().getId());
		List<VipUserBase> list = vipUserBaseService.findList(parm);
		for (int i = 0; i < list.size(); i++) {
			VipUserBase e = list.get(i);
			Map<String, Object> map = Maps.newHashMap();
			map.put("id", e.getId());
			map.put("pId", "0");
			map.put("name", e.getVipName() + "[" + e.getVipPhone() + "]");
			mapList.add(map);
		}
		return mapList;
	}

	/**
	 * 余额查询
	 * 
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "sendMessge")
	public Map<String, Object> sendMessge(HttpServletRequest request) {
		String id = request.getParameter("id");
		Map<String, Object> returnMap = new HashMap<String, Object>();
		try {
			if (MessageUtil.sendMessgeTimeFn()) {
				VipUserBase vipUserBase = vipUserBaseService.get(id);
				// 发送短信
				Map<String, String> contentMap = new HashMap<String, String>();
				contentMap.put("name", vipUserBase.getVipPhone());
				contentMap.put("msg1",
						String.valueOf(vipUserBase.getRestMoeny()));
				contentMap.put("msg2",
						String.valueOf(vipUserBase.getRestScore()));
				MessageUtil.getInterface().send("WALLET_CODE",
						vipUserBase.getVipPhone(), contentMap);
				returnMap.put("success", true);
			} else {
				returnMap.put("success", false);
				returnMap.put("msg", "请30秒后再次发送。");
			}
		} catch (Exception e) {
			e.printStackTrace();
			returnMap.put("success", false);
			returnMap.put("msg", e.getMessage());
		}
		return returnMap;
	}
	
	@ResponseBody
	@RequestMapping(value = "exportExcel")
	public Map<String, Object> exportExcel(HttpServletRequest request) {
		Map<String, Object> returnMap = new HashMap<String, Object>();
		try {
			String strDirPath = request.getSession().getServletContext().getRealPath("/");
			String exportPath = strDirPath + File.separator + "userfiles" + File.separator + "expExcel_"
					+ UserUtils.getUser().getOffice().getId() + ".xlsx";
			vipUserBaseService.exportExcel(exportPath);
			returnMap.put("success", true);
			returnMap.put("urlPath", File.separator + "userfiles" + File.separator + "expExcel_"
					+ UserUtils.getUser().getOffice().getId() + ".xlsx");
		} catch (Exception e) {
			e.printStackTrace();
			returnMap.put("success", false);
			returnMap.put("msg", e.getMessage());
		}
		return returnMap;
	}	
	

}