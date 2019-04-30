package com.sm.web;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sm.Service.productService;
import com.sm.Service.userService;
import com.sm.beans.product;
import com.sm.beans.user;
import com.sm.response.currencyResponce;
import com.sm.utils.getRequestInfo;

@Controller
@RequestMapping("product")
public class productController {
	
	
	@Resource
	productService ps;
	
	@Resource
	userService us;
	
	@RequestMapping("add.action")
	@ResponseBody
	public currencyResponce add(String name,int num,String kind,HttpServletRequest rq) {
		currencyResponce cr = new currencyResponce();
		//String prefix = ((user)rq.getSession().getAttribute("userInfo")).getUsername();
		String realName = getRequestInfo.getRealName(rq,"_product");
		//名字防重复 不允许添加同一商品
		if(ps.sameName(name, realName) == null) {
			if(ps.add(name, num,realName,kind) == 1) {
				cr.setMsg("添加成功");
				cr.setStatus(200);
			}else {
				cr.setMsg("因未知原因失败");
				cr.setStatus(400);
			}
		}else {
			cr.setMsg("不能重复添加商品");
			cr.setStatus(400);
		}
		cr.setType(1);
		return cr;
	}
	
	@RequestMapping("search.action")
	@ResponseBody
	public currencyResponce search(HttpServletRequest rq,int page) {
		currencyResponce cr = new currencyResponce();
		List<product> l = ps.search(getRequestInfo.getRealName(rq,"_product"),page);
		if(l.size()>0) {
			cr.setMsg(l);
			cr.setStatus(201);
		}else {
			cr.setMsg(l);
			cr.setStatus(204);
		}
		cr.setType(1);
		cr.setUrl(null);
		return cr;
	}
	
	@RequestMapping("em_search.action")
	@ResponseBody
	public currencyResponce em_search(HttpServletRequest rq,int page) {
		currencyResponce cr = new currencyResponce();
		user u = new user();
		u.setId(	getRequestInfo.get_pid(rq));
		List<product> l = ps.search(us.login(u).getUsername()+"_product",page);
		if(l.size()>0) {
			cr.setMsg(l);
			cr.setStatus(201);
		}else {
			cr.setMsg(l);
			cr.setStatus(204);
		}
		cr.setType(1);
		cr.setUrl(null);
		return cr;
	}
	
	
	@RequestMapping("new_search.action")
	@ResponseBody
	public currencyResponce new_search(HttpServletRequest rq,int page) {
		currencyResponce cr = new currencyResponce();
		user u = new user();
		u.setId(	getRequestInfo.get_pid(rq));
		List<product> l = ps.newSearch(us.login(u).getUsername()+"_product",page);
		if(l.size()>0) {
			cr.setMsg(l);
			cr.setStatus(201);
		}else {
			cr.setMsg(l);
			cr.setStatus(204);
		}
		cr.setType(1);
		cr.setUrl(null);
		return cr;
	}
	
	@RequestMapping("delect.action")
	@ResponseBody
	public currencyResponce delect(int id,HttpServletRequest rq) {
		currencyResponce cr = new currencyResponce(400);
		if(ps.delect(id, getRequestInfo.getRealName(rq,"_product")) == 1) {
			cr.setMsg("成功");
			cr.setStatus(200);
			cr.setType(1);
		}
		return cr;
	}
	
	@RequestMapping("changeType.action")
	@ResponseBody
	public currencyResponce changeType(int id,float type,HttpServletRequest rq) {
		currencyResponce cr = new currencyResponce(400);
		System.out.println("id:"+id+"   type:"+type);
		/*if(type == 1) {
			type = 0;
		}else if(type == 0) {
			type = 1;
		}
		if(type != 0) {
			
		}*/
		if(ps.changeType(id, getRequestInfo.getRealName(rq,"_product"),type) == 1) {
			cr.setMsg("成功");
			cr.setStatus(200);
			cr.setType(1);
		}
		return cr;
	}
	
	@RequestMapping("select.action")
	@ResponseBody
	public currencyResponce select(String list,HttpServletRequest rq) {
		currencyResponce c = new currencyResponce();
		//查当前用户是谁
		list =list.substring(0, list.length()-1);
		user u = new user();
		u.setId(getRequestInfo.getId(rq));
		u = us.login(u);
		if(u.getPrivilege_id() == 0) {
			//店员
			int id = getRequestInfo.get_pid(rq);
			user u1 = new user();
			u1.setId(id);
			c.setMsg(ps.select_byId( us.login(u1).getUsername()+"_product", list));
		}else {
			c.setMsg(ps.select_byId(getRequestInfo.getRealName(rq, "_product"), list));
		}
		return c;
	}
}
