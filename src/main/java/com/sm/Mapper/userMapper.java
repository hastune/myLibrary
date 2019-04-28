package com.sm.Mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.sm.beans.privateConfig;
import com.sm.beans.user;

public interface userMapper {
	
	user searchByUser(user user);
	
	Integer register(user user);
	
	Integer antiDuplicate(user user);
	
	void createSold(@Param("newTableName")String newTableName);
	
	void createProduct(@Param("newTableName")String newTableName);
	
	List<privateConfig> getConfig();
	
	void hire(@Param("p_id")int p_id,@Param("id")int id);
}
