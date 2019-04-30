package com.sm.Service;

import java.util.List;


import com.sm.beans.product;

public interface productService {
	
	Integer add(String name,Integer amount,String tablename,String kind);
	
	List<product> search(String tablename,int page);
	
	List<product> newSearch(String tablename,int page);
	
	Integer sameName(String productName,String tableName);
	
	Integer changeType(int id,String tableName,float type);
	
	Integer delect(int id,String tableName);
	
	product searchById(String tablename,int id);
	
	Integer changeAmount(int id,String tableName,int amount);
	
	List<product> select_byId(String tableName,String list);
}
