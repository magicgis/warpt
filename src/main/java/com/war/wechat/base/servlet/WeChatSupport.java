package com.war.wechat.base.servlet;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.thinkgem.jeesite.common.utils.StringUtils;
import com.war.wechat.base.message.Article;
import com.war.wechat.base.message.BaseMsg;
import com.war.wechat.base.message.NewsMsg;
import com.war.wechat.base.message.TextMsg;
import com.war.wechat.base.message.req.BaseEvent;
import com.war.wechat.base.message.req.LinkReqMsg;
import com.war.wechat.base.message.req.MenuEvent;

public class WeChatSupport extends WeixinSupport {

	private static final Logger LOG = LoggerFactory
			.getLogger(WeChatSupport.class);

	@Override
	protected String getToken() {
		return WeChatConst.TOKEN;
	}

	@Override
	protected String getAppId() {
		return WeChatConst.APPID;
	}

	@Override
	protected String getAESKey() {
		return null;// WeChatConst.AESKEY;否则需要加解密
	}

	/**
	 * 处理用户关注事件
	 */
	@Override
	protected BaseMsg handleSubscribe(BaseEvent event) {
		LOG.debug("进入会员关注事件处理逻辑");
//		String open_id = event.getFromUserName();
//		VipService vipService = (VipService) SpringUtil.getBeanFactory()
//				.getBean("vipService");
//		VipModel userModel = vipService.getByOpen_id(open_id);
		TextMsg resMsg = new TextMsg();
		StringBuffer sbContent = new StringBuffer();
		sbContent
				.append("欢迎进入汪之洋微信订水通道，汪之洋水源质量安全保证！为了更好的服务您，请点击->")
				.append("<a href=\""
//						+ WeChatConst.WECHAT_SERVER_URL
						+ "/wechat/fw/vip!edit?requestMark=wechat\">完善资料</a>\n")
				.append("回复1：订水流程\n")
				.append("回复2：赠送礼品流程\n")
				.append("如需帮助，请点击->在线客服");
		resMsg.setContent(sbContent.toString());
//		if (userModel == null) {
//			LOG.debug("新会员关注公众号：" + fromUserName);
//			userModel = new VipModel();
//			//userModel.setVip_name(fromUserName);
//			userModel.setOpen_id(fromUserName);
//			userModel.setVip_password("toncent");
//			userModel.setState(1);
//			vipService.save(userModel);
//		} else {
//			LOG.debug("老会员从新关注公众号：" + fromUserName);
//			userModel.setState(1);
//			vipService.save(userModel);
//		}
		LOG.debug("离开会员关注事件处理逻辑");
		return resMsg;
	}

	/**
	 * 会员取消关注事件
	 */
	@Override
	protected BaseMsg handleUnsubscribe(BaseEvent event) {
		String open_id = event.getFromUserName();
//		VipService vipService = (VipService) SpringUtil.getBeanFactory()
//				.getBean("vipService");
//		VipModel userModel = vipService.getByOpen_id(open_id);
//		if (userModel != null) {
//			userModel.setState(2);
//			vipService.save(userModel);
//		}
		return super.handleUnsubscribe(event);
	}

	/**
	 * 超链接事件
	 */
	@Override
	protected BaseMsg handleLinkMsg(LinkReqMsg msg) {
		return super.handleLinkMsg(msg);
	}

	/**
	 * Click事件
	 */
	@Override
	protected BaseMsg handleMenuClickEvent(MenuEvent event) {
		if(StringUtils.equals("",event.getEventKey())){
//			TextMsg resMsg = new TextMsg();
//			StringBuffer sbContent = new StringBuffer();
//			sbContent.append("水价查询功能开发中。。。");
//			resMsg.setContent(sbContent.toString());
			NewsMsg resMsg = new NewsMsg();
			Article topArticle =  new Article();
			topArticle.setTitle("公司简介");
			topArticle.setDescription("珠海海利科技有限公司 成立于2014年，总部设立于珠海，公司专注于为酒店、餐厅、楼宇小区、个人单位、企事业单位、政府单位等提供建筑智能化系统集成、"
					+ "计算机网络工程安装、通信网络工程安装等解决方案。 公司成立时间较短，在珠海市新海利实业发展有限公司鼎立支持下全力壮大发展。公司现有领导班子成员、管理人员、技术人员等均是计算机信息、通信网络和电子科技技术领域里的精英，"
					+ "熟悉多种建筑智能化系统设备的软硬件安装、调试、应用、运维等专业技术。 公司坚持以“灵活沟通、快乐工作、共享成果”为经营理念，以凝聚团队员工归属感为中心、开心笑容面对服务客户为核心，共同分享任务完成的硕果来获取公司最大的经济效益。");
			List<Article> articles = new ArrayList<Article>();
			articles.add(topArticle);
			resMsg.setArticles(articles);
			return resMsg;
		}else{
			return super.handleMenuClickEvent(event);	
		}
	}

	/**
	 * View事件
	 */
	@Override
	protected BaseMsg handleMenuViewEvent(MenuEvent event) {
		return super.handleMenuViewEvent(event);
	}

}
