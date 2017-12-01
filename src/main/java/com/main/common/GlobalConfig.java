package com.main.common;

import java.util.Map;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.google.common.collect.Maps;
import com.main.common.utils.PropertiesLoader;

/**
 * 
 *<p>Title	: GlobalConfig</p>
 * @Description	: GlobalConfig
 * @author	: admin
 * @date	: 2017年11月28日下午2:42:30
 */
public class GlobalConfig {
	private static final Logger logger = LoggerFactory.getLogger(GlobalConfig.class);
	
	//保存全局属性值
	private static Map<String,String> map = Maps.newHashMap();
	//属性文件加载对象
	private static PropertiesLoader loader = new PropertiesLoader("property.properties");
	
	/**
	 * 
	 * @Description :获取配置
	 * @param key
	 * @return
	 */
	public static String getConfig(String key) {
		String value = map.get(key);
		if (value == null){
			value = loader.getProperty(key);
			map.put(key, value != null ? value : StringUtils.EMPTY);
		}
		return value;
	}
	
}
