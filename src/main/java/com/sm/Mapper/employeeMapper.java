package com.sm.Mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.sm.beans.employee;
import com.sm.response.employeeInfo;

public interface employeeMapper {
	
	Integer add(@Param("e")employee e,@Param("tableName")String tableName);
	
	List<employeeInfo> search(@Param("realName1")String realName1,@Param("realName2")String realName2);
	
	Integer update(@Param("e")employee e,@Param("tableName")String tableName);
	
	Integer delete(@Param("id")int id,@Param("tableName")String tableName);
	
	List<employee> searchAll(@Param("tableName")String tablename);
}
