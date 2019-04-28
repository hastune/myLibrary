package com.sm.Service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sm.Mapper.employeeMapper;
import com.sm.Service.employeeService;
import com.sm.beans.employee;
import com.sm.response.employeeInfo;

@Service
public class employeeServiceimpl implements employeeService{

	@Autowired
	employeeMapper em;
	
	@Override
	public Integer add(employee e,String tableName) {
		return em.add(e,tableName);
	}

	@Override
	public List<employeeInfo> search(String realName1,String realName2) {
		return em.search(realName1,realName2);
	}

	@Override
	public Integer update(employee e, String tableName) {
		return em.update(e,tableName);
	}

	@Override
	public Integer delete(int id, String tableName) {
		return em.delete(id, tableName);
	}

	@Override
	public List<employee> searchAll(String tablename) {
		return em.searchAll(tablename);
	}
	
}
