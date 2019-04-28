package com.sm.Service.impl;

import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sm.Mapper.userMapper;
import com.sm.Service.userService;
import com.sm.beans.privateConfig;
import com.sm.beans.user;
import com.sm.redis.JedisClient;

@Service
public class userServiceimpl implements userService{
	
	@Autowired
	userMapper usermapper;
	
	@Autowired
	JedisClient jedisClient;
	
	@Override
	public user login(user user) {
		return usermapper.searchByUser(user);
	}

	@Override
	public Integer register(user user) {
		return usermapper.register(user);
		
	}

	//姓名防重复
	@Override
	public Integer antiDuplicate(user user) {
		return usermapper.antiDuplicate(user);
	}

	@Override
	public void createTable(String sign) {
		usermapper.createProduct(sign+"_product");
		usermapper.createSold(sign+"_sold");
	}
	
	@PostConstruct
	public void initRedisData() {
		List<privateConfig> list = usermapper.getConfig();
		for(privateConfig e : list) {
			System.out.println(e);
			jedisClient.setObject(e.getU_id()+"", e);
		}
	}

	@Override
	public void hire(int p_id,int id) {
		usermapper.hire(p_id, id);
	}
}
