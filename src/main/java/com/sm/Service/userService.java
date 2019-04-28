package com.sm.Service;

import com.sm.beans.user;

public interface userService {
	
	/**
	 * 根据user中的信息查询
	 * 支持 username password id
	 * @param user 
	 * @return
	 */
	user login(user user);
	
	Integer register(user user);
	
	Integer antiDuplicate(user user);
	
	void createTable(String sign);
	
	void hire(int p_id,int id);
}
