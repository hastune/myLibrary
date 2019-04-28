package com.sm.web;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sm.Service.userService;
import com.sm.beans.user;
import com.sm.redis.JedisClient;
import com.sm.response.currencyResponce;

@Controller
@RequestMapping("user")
public class userController {
	
	@Resource
	private userService us;
	
	@Autowired
	JedisClient JedisClient;
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@RequestMapping("/login.action")
	@ResponseBody
	public currencyResponce login(String username,String password,int role,
			HttpSession HttpSession,HttpServletRequest request) {
		logger.info("username: "+username+"  password: "+password+"  role: "+role);
		user u1 = new user();
		u1.setPassword(password);
		u1.setUsername(username);
		user u = us.login(u1);
		currencyResponce cr = new currencyResponce();
		HttpSession sh = request.getSession();
		if(sh.getAttribute("userInfo") != null) {
			if(((user)sh.getAttribute("userInfo")).getUsername().equals(username)) {
				//账号已经登录 //拒绝登录
				cr.setMsg("不允许重复登录");
				cr.setType(1);
				cr.setStatus(400);
				return cr;
			}
		}
		if(u != null) {
			if(u.getPrivilege_id() == role) {
				cr.setMsg("成功");
				cr.setType(0);
				cr.setStatus(200);
				if(u.getPrivilege_id() == 0) {
					//店员
					cr.setUrl("employee.html");
					//user u2 = new user();
					//u2.setId(u.getP_id());
					//redisUtils.setString(u.getId()+"",us.login(u2).getUsername());
				}else if(u.getPrivilege_id() == 1) {
					//店长
					cr.setUrl("Backstage.html");
					cr.setMsg(u.getId()+"%"+u.getP_id());
				}
				JedisClient.set(u.getId()+"info", u.getUsername());
				sh.setAttribute("userInfo", u);
				//p_id指的是当前用户的上级 0则当前用户为店长 ，-1是无雇佣状态的店员,>0是有雇佣状态的店员
				//Cookie identity = new Cookie("identity",u.getId()+"%"+u.getP_id());
				//identity.setMaxAge(60*60*24*30);// 设置存在时间为5分钟
				//identity.setPath("/");
				//response.addCookie(identity);
			}else {
				cr.setMsg("权限错误");
				cr.setType(1);
				cr.setStatus(400);
			}
		}else {
			cr.setMsg("查无此人");
			cr.setType(1);
			cr.setStatus(400);
		}
		return cr;
	}
	
	@RequestMapping("register.action")
	@ResponseBody
	//1是店家 0是店员
	public currencyResponce register(String username,String password,int role) {
		System.out.println(username+":username re");
		System.out.println(password+":password re");
		System.out.println(role+":role re");
		user u = new user();
		u.setPassword(password);
		u.setUsername(username);
		u.setPrivilege_id(role);
		if(role == 1) {
			u.setP_id(0);
		}else {
			u.setP_id(-1);
		}
		currencyResponce cr = new currencyResponce();
		//姓名防重复
		if(us.antiDuplicate(u) == 0) {
			//等于0意味着这个username没有记录 允许注册
			cr.setType(1);
			if(us.register(u)>0) {
				//大于0表示成功(大概?)
				us.createTable(username);
				cr.setMsg("注册成功");
				cr.setStatus(200);
			}else {
				cr.setMsg("因不明原因,注册失败");
				cr.setStatus(400);
			}
		}else {
			cr.setMsg("注册失败,重复的用户名");
			cr.setStatus(400);
		}
		return cr;
	}
	
	@RequestMapping("signOut.action")
	@ResponseBody
	public currencyResponce signOut(HttpServletRequest rq) {
		currencyResponce cr = new currencyResponce();
		user u =(user)rq.getSession().getAttribute("userInfo");
		if(u!=null) {
			rq.getSession().removeAttribute("userInfo");
			Cookie[] c= rq.getCookies();
			for(Cookie e : c) {
				if(e.getName().equals("identity")) {
					e.setMaxAge(-1);
				}
			}
			cr.setMsg("注销成功");
			cr.setStatus(200);
		}else {
			cr.setMsg("注销失败");
			cr.setStatus(400);
			cr.setType(1);
		}
		return cr;
	}
	
	@RequestMapping("text.action")
	public void text(String key) {
		System.out.println(JedisClient.get(key));
	}
	
	@RequestMapping("add.action")
	public void add(String key,String value) {
		System.out.println(JedisClient.set(key, value));
	}
}
