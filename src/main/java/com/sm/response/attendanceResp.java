package com.sm.response;

public class attendanceResp {
	private int em_id;
	private String inTime;
	private String outTime;
	private int num;
	private String name;
	public int getEm_id() {
		return em_id;
	}
	public void setEm_id(int em_id) {
		this.em_id = em_id;
	}
	public String getInTime() {
		return inTime;
	}
	public void setInTime(String inTime) {
		this.inTime = inTime;
	}
	public String getOutTime() {
		return outTime;
	}
	public void setOutTime(String outTime) {
		this.outTime = outTime;
	}
	public int getNum() {
		return num;
	}
	public void setNum(int num) {
		this.num = num;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	@Override
	public String toString() {
		return "attendanceResp [em_id=" + em_id + ", inTime=" + inTime + ", outTime=" + outTime + ", num=" + num
				+ ", name=" + name + "]";
	}
	
}
