package com.sm.response;

import com.sm.beans.attendance;
import com.sm.beans.employee;

public class employeeInfo {
	
	private String username;
	private employee employee;
	private attendance attendance;
	
	
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public employee getEmployee() {
		return employee;
	}
	public void setEmployee(employee employee) {
		this.employee = employee;
	}
	public attendance getAttendance() {
		return attendance;
	}
	public void setAttendance(attendance attendance) {
		this.attendance = attendance;
	}
	
}
