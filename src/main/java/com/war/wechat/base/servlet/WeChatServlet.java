package com.war.wechat.base.servlet;

import com.thinkgem.jeesite.common.utils.StringUtils;
import com.war.wechat.base.message.BaseMsg;
import com.war.wechat.base.message.TextMsg;
import com.war.wechat.base.message.req.BaseEvent;
import com.war.wechat.base.message.req.LocationEvent;
import com.war.wechat.base.message.req.MenuEvent;

public class WeChatServlet extends WeixinServletSupport {
	/**
	 * 
	 */
	private static final long serialVersionUID = 6905246404765765970L;

	
	private static final String TOKEN = "K8HE6cBkdp";
	
	private static final String APPID = "wxdbc641571165fef5";
	
	private static final String AESKEY = "KeRW1ffLOybqVHfGbRGn0rWmC5EPA6ZBpF0Y6pY4X6W";
	
	// 设置TOKEN，用于绑定微信服务器
	@Override
	protected String getToken() {
		return TOKEN;
	}

	// 使用安全模式时设置：APPID
	@Override
	protected String getAppId() {
		return APPID;
	}

	// 使用安全模式时设置：密钥
	@Override
	protected String getAESKey() {
		return AESKEY;
	}

	
	@Override
	protected BaseMsg handleMenuClickEvent(MenuEvent event) {
		String fromUserName = event.getFromUserName();
		String eventKey = event.getEventKey();
		if(StringUtils.isBlank(eventKey)){
			TextMsg resMsg = new TextMsg();
			resMsg.setContent("未知的请求");
			return resMsg;
		}
		else{
			if(StringUtils.equals(WeChatConst.OPERATE_TYPE_CARD, eventKey)){//打卡
				TextMsg resMsg = new TextMsg();
				return resMsg;
			}else if(StringUtils.equals(WeChatConst.OPERATE_TYPE_LEAVE, eventKey)){//请假
				TextMsg resMsg = new TextMsg();
				StringBuffer sbContent = new StringBuffer();
				sbContent.append("<a href=\"http://www.toncent.com.cn/wechat/leave!index?fromUserName="+fromUserName+"&leaveType=1\">【事假】  </a>")
				.append("<a href=\"http://www.toncent.com.cn/wechat/leave!index?fromUserName="+fromUserName+"&leaveType=2\">  【病假】</a>");
				resMsg.setContent(sbContent.toString());
				return resMsg;
			}else if(StringUtils.equals("attendance", eventKey)){//出勤日历
				TextMsg resMsg = new TextMsg();
				StringBuffer sbContent = new StringBuffer();
				sbContent.append("<a href=\"http://www.toncent.com.cn/wechat/attendance!index?fromUserName="+fromUserName+"\">【出勤日历】</a>");
				resMsg.setContent(sbContent.toString());
				return resMsg;
			}else if(StringUtils.equals(WeChatConst.OPERATE_TYPE_PROJECT, eventKey)){//项目
				TextMsg resMsg = new TextMsg();
				StringBuffer sbContent = new StringBuffer();
				sbContent.append("<a href=\"http://www.toncent.com.cn/wechat/project!index?fromUserName="+fromUserName+"\">【项目录入】</a>");
				resMsg.setContent(sbContent.toString());
				return resMsg;
			}else if(StringUtils.equals(WeChatConst.OPERATE_TYPE_PROJECT_RBST, eventKey)){//项目费用报销
				TextMsg resMsg = new TextMsg();
				StringBuffer sbContent = new StringBuffer();
				sbContent.append("<a href=\"http://www.toncent.com.cn/wechat/project-rbst!index?fromUserName="+fromUserName+"\">【项目费用报销】</a>");
				resMsg.setContent(sbContent.toString());
				return resMsg; 
			}else if(StringUtils.equals(WeChatConst.OPERATE_TYPE_CONTRACT, eventKey)){//合同
				TextMsg resMsg = new TextMsg();
				StringBuffer sbContent = new StringBuffer();
				sbContent.append("<a href=\"http://www.toncent.com.cn/wechat/contract!index?fromUserName="+fromUserName+"\">【合同录入】</a>");
				resMsg.setContent(sbContent.toString());
				return resMsg;
			}else if(StringUtils.equals(WeChatConst.OPERATE_TYPE_CONTRACT, eventKey)){//合同1
				TextMsg resMsg = new TextMsg();
				StringBuffer sbContent = new StringBuffer();
				sbContent.append("<a href=\"http://www.toncent.com.cn/wechat/contract1!index?fromUserName="+fromUserName+"\">【合同录入1】</a>");
				resMsg.setContent(sbContent.toString());
				return resMsg;
			}
			else if(StringUtils.equals(WeChatConst.OPERATE_TYPE_RECEIVE, eventKey)){//收款
				TextMsg resMsg = new TextMsg();
				StringBuffer sbContent = new StringBuffer();
				sbContent.append("<a href=\"http://www.toncent.com.cn/wechat/receive!index?fromUserName="+fromUserName+"\">【合同收款】</a>");
				resMsg.setContent(sbContent.toString());
				return resMsg;
			}else if(StringUtils.equals(WeChatConst.OPERATE_TYPE_PAY, eventKey)){//付款
				TextMsg resMsg = new TextMsg();
				StringBuffer sbContent = new StringBuffer();
				sbContent.append("<a href=\"http://www.toncent.com.cn/wechat/pay!index?fromUserName="+fromUserName+"\">【合同付款】</a>");
				resMsg.setContent(sbContent.toString());
				return resMsg;
			}else if(StringUtils.equals(WeChatConst.OPERATE_TYPE_DAILY_COST, eventKey)){//日常费用录入
				TextMsg resMsg = new TextMsg();
				StringBuffer sbContent = new StringBuffer();
				sbContent.append("<a href=\"http://www.toncent.com.cn/wechat/daily!index?fromUserName="+fromUserName+"\">【公司日常费用】</a>");
				resMsg.setContent(sbContent.toString());
				return resMsg;
			}
		}
		return null;
	}
	/**
	 * 用户关注时将保存用户信息
	 */
	@Override
	protected BaseMsg handleSubscribe(BaseEvent event) {
		String fromUserName = event.getFromUserName();
		return new TextMsg("珠海同讯感谢您的关注!");
	}

	/**
	 * 用户取消关注时将用户状态设置为取消冻结
	 */
	@Override
	protected BaseMsg handleUnsubscribe(BaseEvent event) {
		String fromUserName = event.getFromUserName();
		return super.handleUnsubscribe(event);
	}

	@Override
	protected BaseMsg handleLocationEvent(LocationEvent event) {
		String fromUserName = event.getFromUserName();
		return super.handleLocationEvent(event);
	}
	
}