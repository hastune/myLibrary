package com.sm.Service;

import java.util.List;

import com.sm.beans.sold;
import com.sm.response.soldResp;
import com.sm.response.soldResponce;

public interface soldService {
	
	List<soldResp> get(String realName1,String realName2,String day,String kind,int start);
	
	List<soldResp> em_get(String realName1,String realName2,String day,String kind);
	
	double getTurnover(int p_id);
	
	List<soldResponce> EChartsData(String realName1,String realName2,String day);
	
	Integer  add(String tableName,sold s);
	
	Integer  count(String tableName,String day);
}
