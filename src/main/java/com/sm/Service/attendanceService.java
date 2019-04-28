package com.sm.Service;

import java.util.List;


import com.sm.beans.attendance;
import com.sm.beans.privateConfig;
import com.sm.response.attendanceResp;


public interface attendanceService {
	
	public List<attendanceResp> search_num(String tablename,String day); 
	
	public List<attendanceResp> search_info(String tablename,String day); 
	
	public void addDefultClock(String tableName,int id);
	
	void clock(String tablename,attendance attendance);
	
	Integer firstClock(String tablename);
	
	privateConfig get_config(int id);
	
	Integer update_config(privateConfig pc);
}
