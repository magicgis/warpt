/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.vip.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.vip.entity.VipUserLevel;

/**
 * 会员等级设置DAO接口
 * @author swbssd
 * @version 2017-09-17
 */
@MyBatisDao
public interface VipUserLevelDao extends CrudDao<VipUserLevel> {
	
}