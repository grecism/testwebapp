package com.main.common;

import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

/**
 * 
 *<p>Title	: AddressConfig</p>
 * @Description	: 统一配置管理各种接口地址
 * @author	: admin
 * @date	: 2017年11月28日下午3:13:35
 */
public class AddressConfig {

	private static Map<String, String> address = new HashMap<String, String>();
	public static String platform;
	static {
		platform = ResourceBundle.getBundle("address/platform").getString("envir");
		ResourceBundle resource = ResourceBundle.getBundle("address/address" + "_" + platform);
		address.put("platform", platform);
		for (String k : resource.keySet()) {
			address.put(k, resource.getString(k));
		}
	}

	/**
	 * 
	 * @Description : 获得配置的值
	 * @param key
	 * @return
	 */
	public static String getConfig(String key) {
		return address.get(key);
	}

}
