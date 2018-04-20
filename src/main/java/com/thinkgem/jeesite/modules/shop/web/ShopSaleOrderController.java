/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.shop.web;

import java.util.HashMap;
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

import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.mapper.JsonMapper;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.shop.entity.ShopSaleOrder;
import com.thinkgem.jeesite.modules.shop.entity.ShopSaleOrderItem;
import com.thinkgem.jeesite.modules.shop.service.ShopSaleOrderService;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;

/**
 * 销售订单Controller
 * 
 * @author swbssd
 * @version 2018-04-20
 */
@Controller
@RequestMapping(value = "${adminPath}/shop/shopSaleOrder")
public class ShopSaleOrderController extends BaseController {

	@Autowired
	private ShopSaleOrderService shopSaleOrderService;

	@ModelAttribute
	public ShopSaleOrder get(@RequestParam(required = false) String id) {
		ShopSaleOrder entity = null;
		if (StringUtils.isNotBlank(id)) {
			entity = shopSaleOrderService.get(id);
		}
		if (entity == null) {
			entity = new ShopSaleOrder();
		}
		return entity;
	}

	@RequiresPermissions("shop:shopSaleOrder:view")
	@RequestMapping(value = { "list", "" })
	public String list(ShopSaleOrder shopSaleOrder, HttpServletRequest request, HttpServletResponse response,
			Model model) {
		shopSaleOrder.setOfficeId(UserUtils.getUser().getOffice().getId());
		Page<ShopSaleOrder> page = shopSaleOrderService.findPage(new Page<ShopSaleOrder>(request, response),
				shopSaleOrder);
		model.addAttribute("page", page);
		return "modules/shop/shopSaleOrderList";
	}

	@RequiresPermissions("shop:shopSaleOrder:view")
	@RequestMapping(value = "form")
	public String form(ShopSaleOrder shopSaleOrder, Model model) {
		model.addAttribute("shopSaleOrder", shopSaleOrder);
		return "modules/shop/shopSaleOrderForm";
	}

	/**
	 * 加载数据
	 * 
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "loadingForm")
	public Map<String, Object> loadingForm(HttpServletRequest request) {
		String id = request.getParameter("id");
		Map<String, Object> returnMap = new HashMap<String, Object>();
		try {
			ShopSaleOrder shopSaleOrder = shopSaleOrderService.getEdit(id);
			returnMap.put("obj", shopSaleOrder);
			returnMap.put("success", true);
		} catch (Exception e) {
			returnMap.put("success", false);
			returnMap.put("msg", e.getMessage());
		}
		return returnMap;
	}

	/**
	 * 保存销售单
	 * 
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "saveOrderForm")
	public Map<String, Object> saveOrderForm(HttpServletRequest request) {
		String saveJson = request.getParameter("saveJson");
		Map<String, Object> returnMap = new HashMap<String, Object>();
		try {
			ShopSaleOrder shopSaleOrder = (ShopSaleOrder) JsonMapper.fromJsonString(saveJson,
					ShopSaleOrder.class);
			shopSaleOrderService.saveOrder(shopSaleOrder);
			returnMap.put("success", true);
		} catch (Exception e) {
			e.printStackTrace();
			returnMap.put("success", false);
			returnMap.put("msg", e.getMessage());
		}
		return returnMap;
	}

	/**
	 * 选择某产品
	 * 
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "addOrderItem")
	public Map<String, Object> addOrderItem(HttpServletRequest request) {
		String productNo = request.getParameter("productNo");
		String productId = request.getParameter("productId");
		String stockId = request.getParameter("stockId");
		String customerId = request.getParameter("customerId");
		Map<String, Object> returnMap = new HashMap<String, Object>();
		try {
			ShopSaleOrderItem orderItem = shopSaleOrderService.addOrderItem(productNo, productId, stockId,
					customerId);
			returnMap.put("obj", orderItem);
			returnMap.put("success", true);
		} catch (Exception e) {
			returnMap.put("success", false);
			returnMap.put("msg", e.getMessage());
		}
		return returnMap;
	}

	@RequiresPermissions("shop:shopSaleOrder:edit")
	@RequestMapping(value = "save")
	public String save(ShopSaleOrder shopSaleOrder, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, shopSaleOrder)) {
			return form(shopSaleOrder, model);
		}
		shopSaleOrderService.save(shopSaleOrder);
		addMessage(redirectAttributes, "保存销售订单成功");
		return "redirect:" + Global.getAdminPath() + "/shop/shopSaleOrder/?repage";
	}

	@RequiresPermissions("shop:shopSaleOrder:edit")
	@RequestMapping(value = "delete")
	public String delete(ShopSaleOrder shopSaleOrder, RedirectAttributes redirectAttributes) {
		try {
			shopSaleOrderService.deleteOrder(shopSaleOrder);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		addMessage(redirectAttributes, "删除销售订单成功");
		return "redirect:" + Global.getAdminPath() + "/shop/shopSaleOrder/?repage";
	}

}