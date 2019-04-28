package com.sm.web;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sm.Service.employeeService;
import com.sm.Service.userService;
import com.sm.beans.employee;
import com.sm.beans.user;
import com.sm.response.currencyResponce;
import com.sm.response.employeeInfo;
import com.sm.utils.getRequestInfo;

@Controller
@RequestMapping("employee")
public class employeeController {
	
	@Resource
	employeeService es;
	@Resource
	userService us;
	
	@RequestMapping("add.action")
	@ResponseBody
	public currencyResponce add(int id,String pay,HttpServletRequest rq) {
		currencyResponce c = new currencyResponce(400);
		//查询是否存在这个用户
		user u = new user();
		u.setId(id);
		int count = us.antiDuplicate(u);
		if(count == 1) {
			employee e = new employee();
			e.setPay(pay);
			e.setEm_id(id);
			if(es.add(e,getRequestInfo.getRealName(rq,"_employee")) == 1) {
				System.out.println(getRequestInfo.getId(rq));
				us.hire(getRequestInfo.getId(rq), id);
				c.setStatus(200);
			}
		}else if(count == 0){
			c.setMsg("没有这个用户");
		}else {
			c.setMsg("失败");
		}
		
		return c;
	}
	
	@RequestMapping("update.action")
	@ResponseBody
	public currencyResponce update(int id,String pay,HttpServletRequest rq) {
		System.out.println(id);
		System.out.println(pay);
		currencyResponce c = new currencyResponce(400);
		employee e = new employee();
		e.setPay(pay);
		e.setEm_id(id);
		if(es.update(e,getRequestInfo.getRealName(rq,"_employee")) == 1) {
			c.setStatus(200);
			c.setMsg("成功");
		}
		return c;
	}
	
	
	@RequestMapping("search.action")
	@ResponseBody
	public currencyResponce search(HttpServletRequest rq) {
		currencyResponce c = new currencyResponce(400);
		String prefix = getRequestInfo.getprefix(rq);
		List<employeeInfo> l = es.search(prefix+"_employee",prefix+"_attendance");
		if(l.size()>0) {
			c.setMsg(l);
			c.setStatus(201);
		}else {
			c.setMsg(l);
			c.setStatus(204);
		}
		c.setType(1);
		c.setUrl(null);
		return c;
	}
	
	@RequestMapping("delete.action")
	@ResponseBody
	public currencyResponce delete(HttpServletRequest rq,int id) {
		currencyResponce c = new currencyResponce(400);
		int row = es.delete(id, getRequestInfo.getRealName(rq, "_employee"));
		if(row == 1) {
			c.setStatus(200);
		}
		return c;
	}
}
