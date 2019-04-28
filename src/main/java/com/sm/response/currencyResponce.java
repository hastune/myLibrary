package com.sm.response;

public class currencyResponce {

	
	public currencyResponce() {};
	
	public currencyResponce(int status){
		setStatus(status);
	}
	
	private int type = 1;
	/**
	 * 返回的信息
	 */
	private Object msg =null; 
	
	private int status = 400; 
	/**
	 * 返回的URL
	 */
	private String url =null; 
	
	public int getType() {
		return type;
	}
	
	/**
	 * 返回的类型 0:跳转 1:文本
	 */
	public void setType(int type) {
		this.type = type;
	}
	public Object getMsg() {
		return msg;
	}
	public void setMsg(Object msg) {
		this.msg = msg;
	}
	public int getStatus() {
		return status;
	}
	/**
	 * 状态码 200成功
		 * 201 有获取到数据
		 * 204 有获取到数据但数据为空
	 * 400失败
	 */
	public void setStatus(int status) {
		this.status = status;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	
	
}
