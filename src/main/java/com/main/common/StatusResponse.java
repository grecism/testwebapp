package com.main.common;

/**
 * 
 *<p>Title	: StatusResponse</p>
 * @Description	: 全局状态定义
 * @author	: admin
 * @date	: 2017年11月27日下午3:04:41
 */
public enum StatusResponse {

	SUCESS("0", "成功"), FAILED("1", "失败");

	public final String errCode;
	public final String message;

	private StatusResponse(String errCode, String message) {
		this.errCode = errCode;
		this.message = message;
	}

	public static StatusResponse getEnum(String status) {
		for (StatusResponse c : StatusResponse.values()) {
			if (c.errCode.equalsIgnoreCase(status)) {
				return c;
			}
		}
		return null;
	}

	public static String getDesc(String errCode) {
		if (getEnum(errCode) != null)
			return getEnum(errCode).message;
		else
			return null;
	}
}
