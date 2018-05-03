package com.war.wechat.app.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class WeChatUtils {

	// AppID(小程序ID)
	private static final String AppID = "wxc01270e1a22b84aa";
	// AppSecret(小程序密钥)
	private static final String AppSecret = "ecff4188760af457ddb3f51f835e4a08";

	public static String getOpenIdByLoginCode(String jsCode) throws IOException {
		StringBuilder resultData = new StringBuilder();
		// appid		小程序唯一标识
		//secret		小程序的 app secret
		//js_code		登录时获取的 code
		//grant_type	填写为 authorization_code
		String url = "https://api.weixin.qq.com/sns/jscode2session?appid="+AppID+"&secret="+AppSecret+"&js_code="+jsCode+"&grant_type=authorization_code";
		URL myURL = null;
		URLConnection httpsConn = null;
		try {
			myURL = new URL(url);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		InputStreamReader insr = null;
		BufferedReader br = null;
		try {
			httpsConn = (URLConnection) myURL.openConnection();// 不使用代理
			if (httpsConn != null) {
				insr = new InputStreamReader(httpsConn.getInputStream(),
						"UTF-8");
				br = new BufferedReader(insr);
				String data = null;
				while ((data = br.readLine()) != null) {
					resultData.append(data);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (insr != null) {
				insr.close();
			}
			if (br != null) {
				br.close();
			}
		}
		System.out.println(resultData.toString());
		return resultData.toString();
	}

}
