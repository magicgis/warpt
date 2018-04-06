/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.vip.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.vip.entity.VipUserWallet;
import com.thinkgem.jeesite.modules.vip.dao.VipUserWalletDao;

/**
 * 会员钱包Service
 * 
 * @author swbssd
 * @version 2017-09-17
 */
@Service
@Transactional(readOnly = true)
public class VipUserWalletService extends
		CrudService<VipUserWalletDao, VipUserWallet> {

	public VipUserWallet get(String id) {
		return super.get(id);
	}

	public List<VipUserWallet> findList(VipUserWallet vipUserWallet) {
		return super.findList(vipUserWallet);
	}

	public Page<VipUserWallet> findPage(Page<VipUserWallet> page,
			VipUserWallet vipUserWallet) {
		return super.findPage(page, vipUserWallet);
	}

	@Transactional(readOnly = false)
	public void save(VipUserWallet vipUserWallet) {
		super.save(vipUserWallet);
	}

	/**
	 * 同步更新钱包数据(根据用户)
	 * 
	 * @param userId
	 * @param gxWalletHb
	 * @return
	 */
	@Transactional(readOnly = false)
	public boolean saveSynByObjId(String vipId, VipUserWallet vipUserWallet) {
		synchronized (vipId) {
			this.save(vipUserWallet);
		}
		return true;
	}

	@Transactional(readOnly = false)
	public void delete(VipUserWallet vipUserWallet) {
		super.delete(vipUserWallet);
	}
	
	public VipUserWallet findByVipId(String vipId) {
		VipUserWallet parm = new VipUserWallet();
		parm.setVipId(vipId);
		List<VipUserWallet> list = findList(parm);
		if(list == null || list.isEmpty()){
			throw new RuntimeException("钱包未初始化，非法数据，请联系我。");
		}
		return list.get(0);
	}	

}