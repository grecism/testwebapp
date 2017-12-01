package com.main.common.utils;

import java.util.Arrays;

import org.apache.commons.lang.StringUtils;

/**
 * 
 *<p>Title	: UploadDownUtil</p>
 * @Description	:
 * @author	: admin
 * @date	: 2017年11月27日下午4:48:36
 */
public class UploadDownUtil {
	public static final String[] suffixs ={".png",".jpg",".gif",".jpeg",".bmp",".zip"};
	
	/**
	 * 
	 * @Description :判断suffixs
	 * @param src
	 * @return
	 */
	public static Boolean isFiles(String src) {
    	if(StringUtils.isBlank(src)) return false;
    	String suffix = src.substring(src.lastIndexOf("."));
        return Arrays.asList(suffixs).contains(suffix.toLowerCase());
	}
}
