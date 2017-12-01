package com.main.common;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import util.admin.Logger;

/**
 * 
 *<p>Title	: PropertyConfig</p>
 * @Description	: PropertyConfig
 * @author	: admin
 * @date	: 2017年11月27日下午4:03:34
 */
public class PropertyConfig {
	private static final Logger logger = Logger.getLogger(PropertyConfig.class);
	private static String  PROPERTYCONFIG = "property.properties";
	
	public static final String TEST = "test";
	public static String UPLOADFILE_MAXSIZE ="uploadfile.maxSize";
	
	
	private static Map<String,String> map = new HashMap<String,String>();
	static{
		try {
			Properties properties = new Properties();
			InputStream inputStream = PropertyConfig.class.getClassLoader().getResourceAsStream(PROPERTYCONFIG);
			properties.load(inputStream);
			
			for (String key: properties.stringPropertyNames()) {
				map.put(key, properties.getProperty(key));
				logger.info("key对应的value:"+properties.getProperty(key));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 
	 * @Description :获得属性值
	 * @param key
	 * @return
	 */
	public static String getProperty(String key){
		logger.info("获得的value:"+ map.get(key));
		return map.get(key);
		
	}
}
