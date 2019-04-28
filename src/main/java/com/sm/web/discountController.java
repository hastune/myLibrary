package com.sm.web;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sm.Service.discountService;
import com.sm.beans.discount;
import com.sm.response.currencyResponce;
import com.sm.utils.getRequestInfo;

@RestController
@RequestMapping("d")
public class discountController {
	
	@Autowired
	private discountService ds;
	
	
	@RequestMapping("search.action")
	public currencyResponce search(HttpServletRequest rq) {
		currencyResponce c = new currencyResponce();
		c.setMsg(ds.search(getRequestInfo.getId(rq)));
		return c;
	}
	
	@RequestMapping("haveDiscount.action")
	public currencyResponce haveDiscount(HttpServletRequest rq,String id,String kind) {
		currencyResponce c = new currencyResponce();
		c.setMsg(ds.haveDiscount(rq, id, kind));
		return c;
	}
	
	@RequestMapping("searchByclas.action")
	public currencyResponce searchByclas(HttpServletRequest rq,String clas) {
		currencyResponce c = new currencyResponce();
		c.setMsg(ds.searchByclas(getRequestInfo.get_pid(rq),clas));
		return c;
	}
	
	@RequestMapping("insert.action")
	public currencyResponce insert(@RequestBody discount discount,HttpServletRequest rq) {
		currencyResponce c = new currencyResponce();
		discount.setP_id(getRequestInfo.getId(rq));
		
		if(ds.inser(discount) == 1){
			c.setMsg("成功");
			c.setStatus(200);
		}
		
		return c;
	}
}
