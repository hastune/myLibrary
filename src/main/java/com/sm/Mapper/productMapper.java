package com.sm.Mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.sm.beans.product;

public interface productMapper {
	
	Integer add(@Param("p")product p,@Param("tableName")String tableName);
	
	List<product> search(@Param("tableName")String tableName,@Param("page")int page);
	
	Integer sameName(@Param("productName")String productName,@Param("tableName")String tableName);
	
	Integer changeType(@Param("id")int id,@Param("tableName")String tableName,@Param("type")float type,
			@Param("updateTime")String updateTime);
	
	Integer delect(@Param("id")int id,@Param("tableName")String tableName);
	
	product searchById(@Param("tableName")String tableName,@Param("id")int id);
	
	Integer changeAmount(@Param("id")int id,@Param("tableName")String tableName,@Param("amount")int amount,
			@Param("updateTime")String updateTime);
	
	List<product> select_byId(@Param("tablename")String tableName,@Param("list")String list);
}
