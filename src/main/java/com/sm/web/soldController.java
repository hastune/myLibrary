package com.sm.web;


import java.text.SimpleDateFormat;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sm.Service.productService;
import com.sm.Service.soldService;
import com.sm.Service.userService;
import com.sm.beans.sold;
import com.sm.beans.user;
import com.sm.response.currencyResponce;
import com.sm.utils.getRequestInfo;

@RequestMapping("sold")
@RestController
public class soldController {
	
	@Resource
	private soldService ss;
	
	@Resource
	private userService us;
	
	@Resource
	private productService ps;
	
	@RequestMapping("/get.action")
	public currencyResponce get(String day,HttpServletRequest request,int page) {
		currencyResponce cr = new currencyResponce();
		String prefix = getRequestInfo.getprefix(request);
		cr.setMsg(ss.get(prefix+"_sold", prefix+"_product", day,null,page));
		cr.setStatus(200);
		return cr;
	}
	
	
	@RequestMapping("/EChartsData.action")
	public currencyResponce EChartsData(String day,HttpServletRequest request) {
		currencyResponce cr = new currencyResponce();
		String prefix = getRequestInfo.getprefix(request);
		cr.setMsg(ss.EChartsData(prefix+"_sold", prefix+"_product", day));
		cr.setStatus(200);
		return cr;
	}
	
	
	@RequestMapping("/em_EChartsData.action")
	public currencyResponce em_EChartsData(String day,HttpServletRequest request) {
		currencyResponce cr = new currencyResponce();
		user u = new user();
		u.setId(getRequestInfo.get_pid(request));
		String prefix = us.login(u).getUsername();
		cr.setMsg(ss.EChartsData(prefix+"_sold", prefix+"_product", day));
		cr.setStatus(200);
//		System.out.println(redisUtils.getString(getRequestInfo.getId(request)+""));
		return cr;
	}
	
	@RequestMapping("/em_get.action")
	public currencyResponce em_get(String day,HttpServletRequest rq,String kind,String name,int page) {
		currencyResponce cr = new currencyResponce();
		int pid = getRequestInfo.get_pid(rq);
		//查商店名
		user user = new user();
		user.setId(pid);
		user = us.login(user);
		String prefix = user.getUsername();
		if(name.equals("get")) {
			cr.setMsg(ss.get(prefix+"_sold", prefix+"_product", day,kind,page));
		}else if(name.equals("em_get")){
			cr.setMsg(ss.em_get(prefix+"_sold", prefix+"_product", day,kind));
		}
		cr.setStatus(200);
		return cr;
	}
	
	@RequestMapping("/add.action")
	public synchronized currencyResponce add(int id,double sum ,int amount,HttpServletRequest rq) {
		sold s = new sold();
		s.setAmount(amount);
		s.setId(id);
		s.setSum(sum);
		SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		s.setTime(sd.format(System.currentTimeMillis()));
		currencyResponce c = new currencyResponce();
		//先查
		user u = new user();
		u.setId(getRequestInfo.get_pid(rq));
		String p = us.login(u).getUsername();
		int a =ps.searchById(p+"_product", s.getId()).getAmount();
		System.out.println("库存量:  "+a+"  需求量："+s.getAmount());
		if(a >= s.getAmount()) {
			//库存足够, 先减少库存，再增加售出记录
			if(ps.changeAmount(s.getId(), p+"_product", a - s.getAmount()) == 1) {
				if(ss.add(p+"_sold",s) ==1 ) {
					c.setStatus(200);
				}else {
					c.setMsg("插入失败");
				}
			}else {
				c.setMsg("修改失败");
			}
		}else {
			c.setMsg("库存不足");
		}
		return c;
	}
	
	@RequestMapping("/count.action")
	public currencyResponce count(HttpServletRequest rq,String  name,String day) {
		currencyResponce c = new currencyResponce(200);
		user u = getRequestInfo.getUser(rq);
		//1是店主 0是店员 
		if(u.getPrivilege_id() == 1) {
			c.setMsg(ss.count(u.getUsername()+name,day));
		}else {
			int id = u.getP_id();
			u = new user();
			u.setId(id);
			c.setMsg(ss.count(us.login(u).getUsername()+name,day));
		}
		return c;
	}
	
	
	/*
	@RequestMapping("/Turnover.action")
	public double getTurnover(HttpServletRequest hr) {
		Cookie[] Cookies = hr.getCookies();
		int userid = -1;//店主id
		for(Cookie e : Cookies) {
			if(e.getName().equals("identity")) {
				String[] s = e.getValue().split("%");
				//cookie合法性校验
				if(s.length == 2) {
					if(s[1].equals("0")) {
						//如果是0 则说明当前是店长 id在第一位
						userid = new Integer(s[0]);
					}else{
						userid = new Integer(s[1]);
					}
				}
			}
		}
		return ss.getTurnover(userid);
	}
	*/
}
