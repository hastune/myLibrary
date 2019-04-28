package com.sm.Service.impl;

import java.text.SimpleDateFormat;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sm.Mapper.attendanceMapper;
import com.sm.Service.attendanceService;
import com.sm.beans.attendance;
import com.sm.beans.privateConfig;
import com.sm.response.attendanceResp;

import com.sm.redis.JedisClient;

@Service
public class attendanceServiceimpl implements attendanceService{
	
	@Autowired
	attendanceMapper am;
	
	@Autowired
	JedisClient JedisClient;

	@Override
	public List<attendanceResp> search_num(String tablename,String day) {
		return am.searchNum(tablename,day);
	}

	@Override
	public List<attendanceResp> search_info(String tablename,String day) {
		return am.search_info(tablename,day);
	}


	@Override
	public void clock(String tablename, attendance attendance) {
		am.clock(tablename, attendance);
	}

	@Override
	public void addDefultClock(String tableName, int id) {
		SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		am.addDefultClock(tableName, id, sd.format(System.currentTimeMillis()));
	}

	@Override
	public Integer firstClock(String tablename) {
		return am.firstClock(tablename);
	}

	@Override
	public privateConfig get_config(int id) {
		return am.get_config(id);
	}

	@Override
	public Integer update_config(privateConfig pc) {
		if(am.get_config(pc.getU_id())== null) {
			JedisClient.setObject(""+pc.getU_id(), pc);
			return am.insert_config(pc);
		}else {
			JedisClient.setObject(""+pc.getU_id(), pc);
			return am.update_config(pc);
		}
		
	}
	
	
	

}
