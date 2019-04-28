package com.sm.Mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.sm.beans.attendance;
import com.sm.beans.privateConfig;
import com.sm.response.attendanceResp;


public interface attendanceMapper {
	
	List<attendanceResp> searchNum(@Param("value")String tablename,@Param("day")String day);
	
	List<attendanceResp> search_info(@Param("tablename")String tablename,@Param("day")String day);
	
	void addDefultClock(@Param("tableName")String tableName,@Param("id")int id,@Param("createTime")String time);
	
	void clock(@Param("tableName")String tablename,@Param("a")attendance attendance);
	
	Integer firstClock(@Param("tableName")String tablename);
	
	privateConfig get_config(int id);
	
	Integer update_config(privateConfig pc);
	
	Integer insert_config(privateConfig pc);
}
