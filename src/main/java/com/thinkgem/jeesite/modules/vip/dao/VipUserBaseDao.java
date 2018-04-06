/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.vip.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.vip.entity.VipUserBase;

/**
 * 会员基本信息DAO接口
 * @author swbssd
 * @version 2017-09-17
 */
@MyBatisDao
public interface VipUserBaseDao extends CrudDao<VipUserBase> {
	
}