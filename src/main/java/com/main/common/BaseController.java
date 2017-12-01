package com.main.common;

import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;

import util.admin.Logger;

/**
 * 
 *<p>Title	: BaseController</p>
 * @Description	:
 * @author	: admin
 * @date	: 2017年11月27日下午3:14:34
 */
public class BaseController {
	private static final Logger logger = Logger.getLogger(BaseController.class);
	
	@Autowired
	protected MessageSource messageSource;// 获取消息资源
	
	/**
	 * 
	 * @Description :获取异常中文信息
	 * @param code
	 * @return
	 */
	protected String getMessage(String code) {
		return messageSource.getMessage(code, null, Locale.SIMPLIFIED_CHINESE);
	}
}
