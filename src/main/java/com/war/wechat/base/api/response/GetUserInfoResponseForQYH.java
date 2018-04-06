package com.war.wechat.base.api.response;

import com.alibaba.fastjson.annotation.JSONField;


/**
 * @author peiyu
 */
public class GetUserInfoResponseForQYH extends BaseResponse {

	@JSONField(name = "UserId")
    private String  userId;
	@JSONField(name = "DeviceId")
    private String  deviceId;
	
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getDeviceId() {
		return deviceId;
	}
	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}

    
}
