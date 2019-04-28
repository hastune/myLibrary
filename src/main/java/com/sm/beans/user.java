package com.sm.beans;
 
public class user {
    private int id; 
    private String username; //代表的是 店的名字 或者是员工姓名。。
    private String password; 
    private int privilege_id; 
    private int p_id;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public int getPrivilege_id() {
		return privilege_id;
	}
	public void setPrivilege_id(int privilege_id) {
		this.privilege_id = privilege_id;
	}
	public int getP_id() {
		return p_id;
	}
	public void setP_id(int p_id) {
		this.p_id = p_id;
	}
	@Override
	public String toString() {
		return "user [id=" + id + ", username=" + username + ", password=" + password + ", privilege_id=" + privilege_id
				+ ", p_id=" + p_id + "]";
	} 
    
    
}