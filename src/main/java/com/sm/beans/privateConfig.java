package com.sm.beans;

import java.io.Serializable;

public class privateConfig implements Serializable{
	
	/**
	 * 序列化id
	 */
	private static final long serialVersionUID = 1L;
	private int u_id;
	private String go_to_work;
	private String go_off_work;
	private int deviation;
	public int getU_id() {
		return u_id;
	}
	public void setU_id(int u_id) {
		this.u_id = u_id;
	}
	public String getGo_to_work() {
		return go_to_work;
	}
	public void setGo_to_work(String go_to_work) {
		this.go_to_work = go_to_work;
	}
	public String getGo_off_work() {
		return go_off_work;
	}
	public void setGo_off_work(String go_off_work) {
		this.go_off_work = go_off_work;
	}
	public int getDeviation() {
		return deviation;
	}
	public void setDeviation(int deviation) {
		this.deviation = deviation;
	}
	@Override
	public String toString() {
		return "privateConfig [u_id=" + u_id + ", go_to_work=" + go_to_work + ", go_off_work=" + go_off_work
				+ ", deviation=" + deviation + "]";
	}
	
	
	
	
	
}
