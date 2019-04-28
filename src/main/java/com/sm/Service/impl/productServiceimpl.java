package com.sm.Service.impl;

import java.text.SimpleDateFormat;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sm.Mapper.productMapper;
import com.sm.Service.productService;
import com.sm.beans.product;

@Service
public class productServiceimpl implements productService{

	@Autowired
	productMapper pm;
	
	@Override
	public Integer add(String name,Integer amount,String tablename,String kind) {
		product p = new product();
		p.setName(name);
		p.setAmount(amount);
		p.setKind(kind);
		SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		p.setUpdateTime(sd.format(System.currentTimeMillis()));
		System.out.println(p.getName()+"  "+p.getAmount()+"  "+p.getUpdateTime()+"  "+tablename);
		return pm.add(p, tablename);
	}

	@Override
	public List<product> search(String tablename,int page) {
		return pm.search(tablename,(page-1)*10);
	}

	@Override
	public Integer sameName(String productName, String tableName) {
		return pm.sameName(productName, tableName);
	}

	@Override
	public Integer changeType(int id, String tableName,float type) {
		SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return pm.changeType(id, tableName,type,sd.format(System.currentTimeMillis()));
	}

	@Override
	public Integer delect(int id, String tableName) {
		return pm.delect(id, tableName);
	}

	@Override
	public product searchById(String tablename, int id) {
		return pm.searchById(tablename, id);
	}

	@Override
	@Transactional
	public Integer changeAmount(int id, String tableName, int amount) {
		SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		if(pm.changeAmount(id, tableName, amount, sd.format(System.currentTimeMillis())) != 1 ) {
			throw new RuntimeException();
		}
		return pm.changeAmount(id, tableName, amount, sd.format(System.currentTimeMillis()));
	}

	@Override
	public List<product> select_byId(String tableName, String list) {
		return pm.select_byId(tableName, list);
	}
	
}
