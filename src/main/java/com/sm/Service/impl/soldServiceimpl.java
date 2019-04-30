package com.sm.Service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sm.Mapper.soldMapper;
import com.sm.Service.soldService;
import com.sm.beans.sold;
import com.sm.response.soldResp;
import com.sm.response.soldResponce;

@Service
public class soldServiceimpl implements soldService{

	
	@Autowired
	soldMapper soldmapper;
	

	@Override
	public double getTurnover(int p_id) {
		return soldmapper.getTurnover(p_id);
	}


	@Override
	public List<soldResp> get(String realName1, String realName2, String day,String kind,int start) {
		return soldmapper.get(realName1, realName2, day,kind,(start-1)*10);
	}


	@Override
	public List<soldResponce> EChartsData(String realName1, String realName2, String day) {
		return soldmapper.EChartsData(realName1, realName2, day);
	}


	@Override
	@Transactional
	public  Integer add(String tableName, sold s) {
		if( soldmapper.add(tableName, s) != 1) {
			throw new RuntimeException();
		}
		return soldmapper.add(tableName, s);
	}


	@Override
	public List<soldResp> em_get(String realName1, String realName2, String day, String kind) {
		return soldmapper.em_get(realName1, realName2, day, kind);
	}


	@Override
	public Integer count(String tableName,String day) {
		return soldmapper.count(tableName,day);
	}


	@Override
	public Integer newCount(String tableName, String day) {
		return soldmapper.newCount(tableName, day);
	}

}
