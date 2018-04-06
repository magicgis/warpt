/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.wym.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.service.TreeService;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.wym.entity.WymProductType;
import com.thinkgem.jeesite.modules.wym.dao.WymProductTypeDao;

/**
 * 商品类型Service
 * @author swbssd
 * @version 2017-02-23
 */
@Service
@Transactional(readOnly = true)
public class WymProductTypeService extends TreeService<WymProductTypeDao, WymProductType> {

	public WymProductType get(String id) {
		return super.get(id);
	}
	
	public List<WymProductType> findList(WymProductType wymProductType) {
		if (StringUtils.isNotBlank(wymProductType.getParentIds())){
			wymProductType.setParentIds(","+wymProductType.getParentIds()+",");
		}
		return super.findList(wymProductType);
	}
	
	@Transactional(readOnly = false)
	public void save(WymProductType wymProductType) {
		super.save(wymProductType);
	}
	
	@Transactional(readOnly = false)
	public void delete(WymProductType wymProductType) {
		super.delete(wymProductType);
	}
	
}