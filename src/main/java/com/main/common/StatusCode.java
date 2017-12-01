package com.main.common;

import util.admin.Logger;

/**
 * 
 *<p>Title	: StatusCode</p>
 * @Description	:StatusCode
 * @author	: admin
 * @date	: 2017年11月27日下午3:21:09
 */
public class StatusCode {
	private static final Logger logger = Logger.getLogger(StatusCode.class);
	
	/****************************** 公用状态码 ******************************/
	//成功Response
	public static final String HTTP_OK = "001";
	public static final String HTTP_FAIL = "002";

	//失败Response
	public static final String HTTP_ERROR = "201";

	/**************************** 平台状态码 **********************************/
	
	//上传下载状态码 1000001-1001000
	public static final String UPLOADFILE_NULL="1000001";
	public static final String UPLOADFILE_GTSIZE="1000002";
	public static final String UPLOADFILE_UNSUFFIXS="1000003";
}
