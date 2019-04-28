package com.sm.web;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sm.Service.attendanceService;
import com.sm.Service.employeeService;
import com.sm.Service.userService;
import com.sm.beans.attendance;
import com.sm.beans.bukaApply;
import com.sm.beans.employee;
import com.sm.beans.privateConfig;
import com.sm.beans.user;
import com.sm.redis.JedisClient;
import com.sm.response.currencyResponce;
import com.sm.utils.DateUtils;
import com.sm.utils.getRequestInfo;

@SuppressWarnings("unchecked")
@RestController
@RequestMapping("attendance")
public class attendanceController {
	
	@Resource
	private attendanceService as;
	
	@Autowired
	JedisClient JedisClient;
	
	@Autowired
	userService us;
	
	@Autowired
	employeeService es;
	
	@PostMapping("/search_num.action")
	public currencyResponce search_num(HttpServletRequest rq,String day) {
		currencyResponce cr = new currencyResponce();
		String prefix = getRequestInfo.getprefix(rq);
		cr.setMsg(as.search_num(prefix+"_attendance",day));
		cr.setStatus(200);
		return cr;
	}
	
	@GetMapping("/ss.action")
	public currencyResponce search_day(HttpServletRequest rq,String day) {
		currencyResponce cr = new currencyResponce();
		String prefix = getRequestInfo.getprefix(rq);
		cr.setMsg(as.search_info(prefix+"_attendance",day));
		cr.setStatus(200);
		return cr;
	}
	
	
	@PostMapping("/findbuka.action")
	public currencyResponce fbk(HttpServletRequest rq) {
		currencyResponce c = new currencyResponce(400);
		int id = getRequestInfo.getId(rq);
		List<bukaApply> list = (List<com.sm.beans.bukaApply>) JedisClient.getObject(id+"bk");
		c.setMsg(list);
		c.setStatus(200);
		return c;
	}
	
	@PostMapping("find_clock.action")
	public currencyResponce find_clock(HttpServletRequest rq) {
		currencyResponce c = new currencyResponce();
		int id = getRequestInfo.getId(rq);
		String stats = "";
		if(JedisClient.get(id+"_in") == null) {
			//没有上班打卡
			stats += "0";
		}else {
			stats += "1";
		}
		if(JedisClient.get(id+"_out") == null) {
			//没有下班打卡
			stats  += "0";
		}else {
			stats += "1";
		}
		c.setUrl(stats);
		Object o = JedisClient.getObject(getRequestInfo.get_pid(rq)+"");
		if(o == null) {
			c.setStatus(400);
			return c;
		}
		c.setMsg(o);
		c.setStatus(200);
		return c;
	}

	@PostMapping("clock.action")
	public currencyResponce clock(HttpServletRequest rq,int type) {
		currencyResponce c = new currencyResponce();
		SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		attendance a = new attendance();
		int id = getRequestInfo.getId(rq); //当前人物的id
		a.setEm_id(id);
		user u = new user();
		u.setId(getRequestInfo.get_pid(rq));
		String p = us.login(u).getUsername();
		//打卡前判断是否是当前超市第一个打卡的少年，如果是，则将创建当前超市的全部雇员的默认打卡记录
		createRecord(p);
		if(type==0) {
			//上班打卡
			//将本次请求的打卡时间update
			a.setInTime(sd.format(System.currentTimeMillis()));
			a.setNum(1);
			as.clock(p+"_attendance", a);
			JedisClient.set(id+"_in","1",DateUtils.getRemainSecondsOneDay(new Date()));
		}else if(type==1){
			///下班打卡
			//下班打卡时先查上班是否打卡，如果没有打卡，则设置num =1；否则设为0
			if(JedisClient.get(id+"_in") != null) {
				a.setNum(0);
			}else {
				a.setNum(1);
			}
			a.setOutTime(sd.format(System.currentTimeMillis()));
			as.clock(p+"_attendance", a);
			JedisClient.set(id+"_out","1",DateUtils.getRemainSecondsOneDay(new Date()));
		}
		return c;
	}
	
	/**
	 * 员工提交补卡申请，记录保存在缓存中。
	 * key  = 店主id+bk
	 * value 用list保存申请人信息
	 * 	申请人信息包括申请人的id，发起时间。
	 * @param rq
	 * @return
	 */
	@RequestMapping("/bukaApply.action")
	public currencyResponce bukaApply(HttpServletRequest rq) {
		currencyResponce c = new currencyResponce();
		int id = getRequestInfo.getId(rq);  //当前人物的id
		bukaApply b = new bukaApply();
		b.setId(id);
		SimpleDateFormat s = new SimpleDateFormat();
		b.setTime(s.format(System.currentTimeMillis()));
		int pid = getRequestInfo.get_pid(rq);
		List<bukaApply> list =(List<bukaApply>) JedisClient.getObject(pid+"bk");
		if( list == null) {
			list = new ArrayList<bukaApply>();
		}
		list.add(b);
		JedisClient.setObject(pid+"bk", list);
		c.setStatus(200);
		return c;
	}
	
	/**
	 * 点击同意补卡按钮，先判断是否是本超市本日第一个提交打卡记录的人，
	 * 如果是则创建所有的打卡记录，否则update数据并删除redis中的记录
	 * @param rq
	 * @return 通用返回格式
	 */
	@RequestMapping("/buka.action")
	public currencyResponce bula(HttpServletRequest rq,int id) {
		currencyResponce c = new currencyResponce();
		attendance a= new attendance();
		//查询是否是第一个是则会创建记录
		String  p= getRequestInfo.getRealName(rq, "");//当前店主名
		createRecord(p);
		//updata数据默认为上班打卡 时间为9.00
		a.setInTime("9:00:00");
		as.clock(p+"_attendance", a);
		JedisClient.set(id+"_in","1",DateUtils.getRemainSecondsOneDay(new Date()));
		//删除补卡申请记录
		List<bukaApply> list = (List<bukaApply>)JedisClient.getObject(getRequestInfo.getId(rq)+"bk");
		for(int i = 0;i<list.size();i++) {
			if(list.get(i).getId() == id) {
				list.remove(i);
				break;
			}
		}
		JedisClient.setObject(getRequestInfo.getId(rq)+"bk", list);
		return c;
	}
	
	/**
	 * 判断是 否是当前超市第一个打卡的少年，如果是，则将创建当前超市的全部雇员的默认打卡记录
	 * 默认打卡记录 em_id,null,null,1
	 * @param p 超市名
	 */
	private void createRecord(String p) {
		//默认打卡记录 em_id,null,null,1
		if(as.firstClock(p+"_attendance")== 0) {
			//说明是第一个打卡的boy
			//创建其他人的默认打卡记录
			//1.先查到该店的雇员名单
			List<employee> el = es.searchAll(p+"_employee");
			//2.根据雇员名单循环创建默认打卡记录
			for(int i = 0; i<el.size();i++) {
				as.addDefultClock(p+"_attendance",el.get(i).getEm_id());
			}
		}
	}
	
	@RequestMapping("get_private_config.action")
	public currencyResponce get_private_config(HttpServletRequest rq){
		currencyResponce c= new currencyResponce();
		int id = getRequestInfo.getId(rq);
		c.setMsg(as.get_config(id));
		c.setStatus(200);
		return c;
	}
	
	@RequestMapping("update_private_config.action")
	public currencyResponce update_config(@RequestBody privateConfig pc,HttpServletRequest rq) {
		currencyResponce c= new currencyResponce();
		pc.setU_id(getRequestInfo.getId(rq));
		if(as.update_config(pc) ==1) {
			c.setMsg("成功");
			c.setType(200);
		}else {
			c.setMsg("失败");
			c.setType(400);
		}
		return c;
	}
	
}
