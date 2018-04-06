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
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import com.thinkgem.jeesite.modules.vip.entity.VipProjectPay;
import com.thinkgem.jeesite.modules.vip.entity.VipUserBase;
import com.thinkgem.jeesite.modules.vip.entity.VipUserWallet;
import com.thinkgem.jeesite.modules.vip.service.VipProjectPayService;
import com.thinkgem.jeesite.modules.vip.service.VipUserWalletService;

/**
 * 会员项目充值Controller
 * 
 * @author swbssd
 * @version 2018-01-14
 */
@Controller
@RequestMapping(value = "${adminPath}/vip/vipProjectPay")
public class VipProjectPayController extends BaseController {

	@Autowired
	private VipProjectPayService vipProjectPayService;
	@Autowired
	private VipUserWalletService vipUserWalletService;

	@ModelAttribute
	public VipProjectPay get(@RequestParam(required = false) String id) {
		VipProjectPay entity = null;
		if (StringUtils.isNotBlank(id)) {
			entity = vipProjectPayService.get(id);
		}
		if (entity == null) {
			entity = new VipProjectPay();
		}
		return entity;
	}

	@RequiresPermissions("vip:vipProjectPay:view")
	@RequestMapping(value = { "list", "" })
	public String list(VipProjectPay vipProjectPay, HttpServletRequest request,
			HttpServletResponse response, Model model) {
		// 登陆用户机构过滤
		vipProjectPay.setOfficeId(UserUtils.getUser().getOffice().getId());
		Page<VipProjectPay> page = vipProjectPayService.findPage(
				new Page<VipProjectPay>(request, response), vipProjectPay);
		model.addAttribute("page", page);
		return "modules/vip/vipProjectPayList";
	}

	@RequiresPermissions("vip:vipProjectPay:view")
	@RequestMapping(value = "form")
	public String form(VipProjectPay vipProjectPay, Model model) {
		model.addAttribute("vipProjectPay", vipProjectPay);
		return "modules/vip/vipProjectPayForm";
	}

	@RequiresPermissions("vip:vipProjectPay:edit")
	@RequestMapping(value = "save")
	public String save(VipProjectPay vipProjectPay, Model model,
			RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, vipProjectPay)) {
			return form(vipProjectPay, model);
		}
		VipUserWallet vipUserWallet = vipUserWalletService
				.findByVipId(vipProjectPay.getVipId());
		if (vipProjectPay.getProjectMoeny() > vipUserWallet.getRestMoeny()) {
			throw new RuntimeException("用户剩余金额为["
					+ vipUserWallet.getRestMoeny() + "]，无法购买项目");
		}
		// if (vipProjectPay.getProjectMoeny() == 0) {
		// throw new RuntimeException("项目消费金额不能为0");
		// }
		if (vipProjectPay.getAllNum() == 0) {
			throw new RuntimeException("项目次数不能为0次");
		}
		vipProjectPay.setRestNum(vipProjectPay.getAllNum());
		vipProjectPay.setOfficeId(UserUtils.getUser().getOffice().getId());
		vipProjectPay.setVipName(vipUserWallet.getVipName());
		vipProjectPay.setVipPhone(vipUserWallet.getVipPhone());
		vipProjectPayService.save(vipProjectPay);
		addMessage(redirectAttributes, "保存会员项目充值成功");
		return "redirect:" + Global.getAdminPath()
				+ "/vip/vipProjectPay/?repage";
	}

	@RequiresPermissions("vip:vipProjectPay:edit")
	@RequestMapping(value = "delete")
	public String delete(VipProjectPay vipProjectPay,
			RedirectAttributes redirectAttributes) {
		vipProjectPayService.delete(vipProjectPay);
		addMessage(redirectAttributes, "删除会员项目充值成功");
		return "redirect:" + Global.getAdminPath()
				+ "/vip/vipProjectPay/?repage";
	}

	@ResponseBody
	@RequestMapping(value = "treeData")
	public List<Map<String, Object>> treeData(
			@RequestParam(required = false) String extId,
			HttpServletResponse response) {
		List<Map<String, Object>> mapList = Lists.newArrayList();
		VipProjectPay parm = new VipProjectPay();
		// 登陆用户机构过滤
		parm.setOfficeId(UserUtils.getUser().getOffice().getId());
		List<VipProjectPay> list = vipProjectPayService.findList(parm);
		for (int i = 0; i < list.size(); i++) {
			VipProjectPay e = list.get(i);
			Map<String, Object> map = Maps.newHashMap();
			map.put("id", e.getId());
			map.put("pId", "0");
			map.put("name", "[" + e.getVipPhone() + "]" + e.getProjectName());
			mapList.add(map);
		}
		return mapList;
	}

}