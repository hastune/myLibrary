package com.sm.Service;

import java.util.List;


import com.sm.beans.employee;
import com.sm.response.employeeInfo;


public interface employeeService {
	
	Integer add(employee e,String tableName);
	
	List<employeeInfo> search(String realName1,String realName2);
	
	Integer update(employee e,String tableName);
	
	Integer delete(int id,String tableName);
	
	List<employee> searchAll(String tablename);
}
