package com.sm.Mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.sm.beans.sold;
import com.sm.response.soldResp;
import com.sm.response.soldResponce;

public interface soldMapper {
	
	List<soldResp> get(@Param("tableName1")String tableName1,@Param("tableName2")String tableName2,
			@Param("day")String day,@Param("kind")String kind,@Param("start")int start);
	
	double getTurnover(@Param("p_id")int p_id);
	
	List<soldResponce> EChartsData(@Param("tableName1")String tableName1,@Param("tableName2")String tableName2,
			@Param("day")String day);

	Integer add(@Param("tableName")String tableName,@Param("s")sold s);
	
	List<soldResp> em_get(@Param("tableName1")String tableName1,@Param("tableName2")String tableName2,
			@Param("day")String day,@Param("kind")String kind);
	
	Integer count(@Param("tableName")String tablename,@Param("day")String day);
}
