/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.vip.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.vip.entity.VipUserLevel;
import com.thinkgem.jeesite.modules.vip.dao.VipUserLevelDao;

/**
 * 会员等级设置Service
 * @author swbssd
 * @version 2017-09-17
 */
@Service
@Transactional(readOnly = true)
public class VipUserLevelService extends CrudService<VipUserLevelDao, VipUserLevel> {

	public VipUserLevel get(String id) {
		return super.get(id);
	}
	
	public List<VipUserLevel> findList(VipUserLevel vipUserLevel) {
		return super.findList(vipUserLevel);
	}
	
	public Page<VipUserLevel> findPage(Page<VipUserLevel> page, VipUserLevel vipUserLevel) {
		return super.findPage(page, vipUserLevel);
	}
	
	@Transactional(readOnly = false)
	public void save(VipUserLevel vipUserLevel) {
		super.save(vipUserLevel);
	}
	
	@Transactional(readOnly = false)
	public void delete(VipUserLevel vipUserLevel) {
		super.delete(vipUserLevel);
	}
	
}