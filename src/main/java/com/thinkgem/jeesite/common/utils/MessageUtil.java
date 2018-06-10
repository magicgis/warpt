package com.thinkgem.jeesite.common.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.dysmsapi.model.v20170525.QuerySendDetailsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.QuerySendDetailsResponse;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import com.thinkgem.jeesite.modules.sys.entity.SysMessage;
import com.thinkgem.jeesite.modules.sys.service.SysMessageService;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import com.war.wechat.base.util.JSONUtil;

/**
 * Created on 17/6/7. 短信API产品的DEMO程序,工程中包含了一个SmsDemo类，直接通过
 * 执行main函数即可体验短信产品API功能(只需要将AK替换成开通了云通信-短信产品功能的AK即可) 工程依赖了2个jar包(存放在工程的libs目录下)
 * 1:aliyun-java-sdk-core.jar 2:aliyun-java-sdk-dysmsapi.jar
 * 
 * 备注:Demo工程编码采用UTF-8 国际短信发送请勿参照此DEMO
 */
public class MessageUtil {
	private static MessageUtil factory = new MessageUtil();

	public static MessageUtil getInterface() {
		return factory;
	}

	// private Map<String, Map<String, String>> config = new HashMap<String,
	// Map<String, String>>();

	private MessageUtil() {
		// Map<String, String> valMap = new HashMap<String, String>();
		// valMap.put("snKey", "英树美容店");
		// valMap.put("accessKeyId", "LTAIG5F31Ek1FHni");
		// valMap.put("accessKeySecret", "ky70oLyAa5wchaTEIs6d1dtZviNczX");
		// // 注册
		// valMap.put("REGISTER_CODE", "SMS_96705036");
		// // 充值
		// valMap.put("PAY_CODE", "SMS_96805039");
		// // 消费
		// valMap.put("COST_CODE", "SMS_96855027");
		// // 余额
		// valMap.put("WALLET_CODE", "SMS_101045051");
		// // 项目消费
		// valMap.put("PROJECT_CODE", "SMS_121911321");
		// //小程序注册绑定
		// valMap.put("WECHAT_CODE", "SMS_133979628");
		// config.put("13543006081", valMap);
		// //
		// valMap = new HashMap<String, String>();
		// valMap.put("snKey", "享瘦美容店");
		// valMap.put("accessKeyId", "LTAIvvIsBDPjw945");
		// valMap.put("accessKeySecret", "Sr9RkKEUSGc48wwhc1msiJ6fqYVONX");
		// // 注册
		// valMap.put("REGISTER_CODE", "SMS_121907044");
		// // 充值
		// valMap.put("PAY_CODE", "SMS_121857023");
		// // 消费
		// valMap.put("COST_CODE", "SMS_121852022");
		// // 余额
		// valMap.put("WALLET_CODE", "SMS_121852019");
		// // 项目消费
		// valMap.put("PROJECT_CODE", "SMS_121912029");
		// //小程序注册绑定
		// valMap.put("WECHAT_CODE", "xxxxxxxxxxxxx");
		// config.put("13726296735", valMap);
	}

	/****
	 * 短信模板发送对象获取
	 * 
	 * @return
	 */
	private Map<String, String> getMessagConfig() {
		Map<String, String> valMap = new HashMap<String, String>();
		SysMessageService sysMessageService = (SysMessageService) SpringContextHolder.getBean("sysMessageService");
		SysMessage parm = new SysMessage();
		parm.setOfficeId(UserUtils.getUser().getOffice().getId());
		List<SysMessage> sysMessageList = sysMessageService.findList(parm);
		if (sysMessageList.size() > 0) {
			SysMessage sysMessage = sysMessageList.get(0);
			valMap.put("snKey", sysMessage.getSnKey());
			valMap.put("accessKeyId", sysMessage.getAccessKeyId());
			valMap.put("accessKeySecret", sysMessage.getAccessKeySecret());
			// 注册
			valMap.put("REGISTER_CODE", sysMessage.getRegisterCode());
			// 充值
			valMap.put("PAY_CODE", sysMessage.getPayCode());
			// 消费
			valMap.put("COST_CODE", sysMessage.getCostCode());
			// 余额
			valMap.put("WALLET_CODE", sysMessage.getWalletCode());
			// 项目消费
			valMap.put("PROJECT_CODE", sysMessage.getProjectCode());
			// 小程序注册绑定
			valMap.put("WECHAT_CODE", sysMessage.getWalletCode());
		} else { // 默认短信
			valMap.put("snKey", "英树美容店");
			valMap.put("accessKeyId", "LTAIG5F31Ek1FHni");
			valMap.put("accessKeySecret", "ky70oLyAa5wchaTEIs6d1dtZviNczX");
			// 注册
			valMap.put("REGISTER_CODE", "SMS_96705036");
			// 充值
			valMap.put("PAY_CODE", "SMS_96805039");
			// 消费
			valMap.put("COST_CODE", "SMS_96855027");
			// 余额
			valMap.put("WALLET_CODE", "SMS_101045051");
			// 项目消费
			valMap.put("PROJECT_CODE", "SMS_121911321");
			// 小程序注册绑定
			valMap.put("WECHAT_CODE", "SMS_133979628");
			// 小程序注册绑定
			valMap.put("WECHAT_CODE", "SMS_133979628");
		}
		return valMap;
	}

	// 注册
	// public static final String REGISTER_CODE = "SMS_96705036";
	// // 充值
	// public static final String PAY_CODE = "SMS_96805039";
	// // 消费
	// public static final String COST_CODE = "SMS_96855027";
	// // 余额
	// public static final String WALLET_CODE = "SMS_101045051";

	// 短信间隔时间TO.
	public static Date SEND_TIME = null;

	public static boolean sendMessgeTimeFn() {
		if (SEND_TIME == null) {
			SEND_TIME = new Date();
			return true;
		} else { // 判断两处短信时间是否间隔30秒
			Date nowDate = new Date();
			long beforeTime = SEND_TIME.getTime();
			long afterTime = nowDate.getTime();
			double lsdate = (afterTime - beforeTime) / 1000;
			if (lsdate > 30) {
				SEND_TIME = nowDate;
				return true;
			}
		}
		return false;
	}

	/**
	 * 随机生成6位随机验证码，并且注册进缓存，5分钟有效
	 * 
	 * @return
	 */
	public static String createRandomVcode(String phone) {
		// 验证码
		String vcode = "";
		for (int i = 0; i < 6; i++) {
			vcode = vcode + (int) (Math.random() * 9);
		}
		// 如果存在缓存则先移除
		CacheUtils.remove(phone);
		CacheUtils.put(phone + "_" + vcode, new Date());
		return vcode;
	}

	/**
	 * 验证用户输入的验证码是否正确
	 * 
	 * @param phone
	 * @return
	 */
	public static Map<String, Object> compareVcode(String phone, String setCode) {
		Map<String, Object> returnMap = new HashMap<String, Object>();
		if (CacheUtils.get(phone + "_" + setCode) != null) {
			Date oldDate = (Date) CacheUtils.get(phone + "_" + setCode);
			// 判断时间是否过5分钟
			if (DateUtils.pastMinutes(oldDate) <= 5) {
				returnMap.put("success", true);
			} else {
				returnMap.put("success", false);
				returnMap.put("msg", "验证码已失效，请重新获取.");
				// 移除之前的
				CacheUtils.remove(phone + "_" + setCode);
			}
		} else {
			returnMap.put("success", false);
			returnMap.put("msg", "验证码输入错误，请查证后输入.");
		}
		return returnMap;
	}

	// 产品名称:云通信短信API产品,开发者无需替换
	static final String product = "Dysmsapi";
	// 产品域名,开发者无需替换
	static final String domain = "dysmsapi.aliyuncs.com";

	// 此处需要替换成开发者自己的AK(在阿里云访问控制台寻找-子key[访问控制-用户管理])
	// static final String accessKeyId = "LTAIG5F31Ek1FHni";
	// static final String accessKeySecret = "ky70oLyAa5wchaTEIs6d1dtZviNczX";

	public SendSmsResponse send(String templateCode, String phoneNumbers, Map<String, String> paramMap) {
		// 短信模板发送对象获取
		UserUtils.getUser().getOffice().getId();
		// String key = null;
		// if(UserUtils.getUser().getCompany() != null) {
		// key = UserUtils.getUser().getCompany().getZipCode();
		// key = key == null ? "13543006081" : key;
		// }else {
		// key = "13543006081";
		// }

		Map<String, String> configMap = this.getMessagConfig();
		if (configMap == null) {
			return null;
		}
		templateCode = configMap.get(templateCode);
		String paramString = JSONUtil.toJson(paramMap);
		System.out.println(paramString);
		// 可自助调整超时时间
		System.setProperty("sun.net.client.defaultConnectTimeout", "10000");
		System.setProperty("sun.net.client.defaultReadTimeout", "10000");

		// 初始化acsClient,暂不支持region化
		IClientProfile profile = DefaultProfile.getProfile("cn-hangzhou", configMap.get("accessKeyId"),
				configMap.get("accessKeySecret"));
		try {
			DefaultProfile.addEndpoint("cn-hangzhou", "cn-hangzhou", product, domain);

			IAcsClient acsClient = new DefaultAcsClient(profile);

			// 组装请求对象-具体描述见控制台-文档部分内容
			SendSmsRequest request = new SendSmsRequest();
			// 必填:待发送手机号
			request.setPhoneNumbers(phoneNumbers);
			// 必填:短信签名-可在短信控制台中找到
			request.setSignName(configMap.get("snKey"));
			// 必填:短信模板-可在短信控制台中找到
			request.setTemplateCode(templateCode);
			// 可选:模板中的变量替换JSON串,如模板内容为"亲爱的${name},您的验证码为${code}"时,此处的值为
			request.setTemplateParam(paramString);

			// 选填-上行短信扩展码(无特殊需求用户请忽略此字段)
			// request.setSmsUpExtendCode("90997");

			// 可选:outId为提供给业务方扩展字段,最终在短信回执消息中将此值带回给调用者
			request.setOutId("yourOutId");

			// hint 此处可能会抛出异常，注意catch
			SendSmsResponse sendSmsResponse = acsClient.getAcsResponse(request);
			return sendSmsResponse;
		} catch (ClientException e) {
			e.printStackTrace();
		}
		return null;
	}

	// 查明细
	public QuerySendDetailsResponse querySendDetails(String bizId) throws ClientException {
		// 可自助调整超时时间
		System.setProperty("sun.net.client.defaultConnectTimeout", "10000");
		System.setProperty("sun.net.client.defaultReadTimeout", "10000");
		// 短信模板key
		String key = UserUtils.getUser().getCompany().getZipCode();
		key = key == null ? "13543006081" : key;
		Map<String, String> configMap = this.getMessagConfig();
		// 初始化acsClient,暂不支持region化
		IClientProfile profile = DefaultProfile.getProfile("cn-hangzhou", configMap.get("accessKeyId"),
				configMap.get("accessKeySecret"));
		DefaultProfile.addEndpoint("cn-hangzhou", "cn-hangzhou", product, domain);
		IAcsClient acsClient = new DefaultAcsClient(profile);

		// 组装请求对象
		QuerySendDetailsRequest request = new QuerySendDetailsRequest();
		// 必填-号码
		request.setPhoneNumber("15000000000");
		// 可选-流水号
		request.setBizId(bizId);
		// 必填-发送日期 支持30天内记录查询，格式yyyyMMdd
		SimpleDateFormat ft = new SimpleDateFormat("yyyyMMdd");
		request.setSendDate(ft.format(new Date()));
		// 必填-页大小
		request.setPageSize(10L);
		// 必填-当前页码从1开始计数
		request.setCurrentPage(1L);

		// hint 此处可能会抛出异常，注意catch
		QuerySendDetailsResponse querySendDetailsResponse = acsClient.getAcsResponse(request);

		return querySendDetailsResponse;
	}

	public static void main(String[] args) throws ClientException, InterruptedException {
		Map<String, String> contentMap = new HashMap<String, String>();
		contentMap.put("name", "aaa");
		contentMap.put("msg", "金额：222 ；积分：111");
		contentMap.put("msg1", "111");
		contentMap.put("msg2", "222");
		// 发短信
		SendSmsResponse response = MessageUtil.getInterface().send("COST_CODE", "13543006081", contentMap);
		System.out.println("短信接口返回的数据----------------");
		System.out.println("Code=" + response.getCode());
		System.out.println("Message=" + response.getMessage());
		System.out.println("RequestId=" + response.getRequestId());
		System.out.println("BizId=" + response.getBizId());

		Thread.sleep(3000L);

		// 查明细
		if (response.getCode() != null && response.getCode().equals("OK")) {
			QuerySendDetailsResponse querySendDetailsResponse = MessageUtil.getInterface()
					.querySendDetails(response.getBizId());
			System.out.println("短信明细查询接口返回数据----------------");
			System.out.println("Code=" + querySendDetailsResponse.getCode());
			System.out.println("Message=" + querySendDetailsResponse.getMessage());
			int i = 0;
			for (QuerySendDetailsResponse.SmsSendDetailDTO smsSendDetailDTO : querySendDetailsResponse
					.getSmsSendDetailDTOs()) {
				System.out.println("SmsSendDetailDTO[" + i + "]:");
				System.out.println("Content=" + smsSendDetailDTO.getContent());
				System.out.println("ErrCode=" + smsSendDetailDTO.getErrCode());
				System.out.println("OutId=" + smsSendDetailDTO.getOutId());
				System.out.println("PhoneNum=" + smsSendDetailDTO.getPhoneNum());
				System.out.println("ReceiveDate=" + smsSendDetailDTO.getReceiveDate());
				System.out.println("SendDate=" + smsSendDetailDTO.getSendDate());
				System.out.println("SendStatus=" + smsSendDetailDTO.getSendStatus());
				System.out.println("Template=" + smsSendDetailDTO.getTemplateCode());
			}
			System.out.println("TotalCount=" + querySendDetailsResponse.getTotalCount());
			System.out.println("RequestId=" + querySendDetailsResponse.getRequestId());
		}

	}
}
