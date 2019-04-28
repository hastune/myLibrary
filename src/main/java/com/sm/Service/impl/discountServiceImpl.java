package com.sm.Service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sm.Mapper.discountMapper;
import com.sm.Service.discountService;
import com.sm.beans.discount;
import com.sm.utils.getRequestInfo;

@Service
public class discountServiceImpl implements discountService{

	@Autowired
	private discountMapper dc;
	
	@Override
	public List<discount> search(int id) {
		return dc.search(id);
	}

	@Override
	public Integer inser(discount d) {
		return dc.insert(d);
	}

	@Override
	public List<discount> haveDiscount(HttpServletRequest rq, String id, String kind) {
		//获得当前超市的id
		int s_id	=getRequestInfo.get_pid(rq);
		//根据当前超市id获取所有的优惠活动
		List<discount> list = dc.search(s_id);
		//循环list 查找符合的优惠活动
		//一类商品可能符合多个优惠活动，需要通过计算选择最佳的优惠活动,该动作交由前端完成
		//后台只返回符合的活动
		List<discount> discount = new ArrayList<discount>();
		loop:for(int i = 0;i<list.size();i++) {
			String[] suit = list.get(i).getSuitable().split(",");
			//分别获取适合的商品id和种类名
			String[] p_id = suit[0].split("!");
			String[] allKind= suit[1].split("!");
			//查id
			
			for(int j = 0 ; j<p_id.length;j++) {
				System.out.println(p_id[j]);
				if(p_id[j].equals(id)) {
					//id相同
					//查找discount列表里有没有当前的优惠 如果有 不添加，如果没有则添加
					if(discount.size() == 0) {
						discount.add(list.get(i));
						break loop;
					}
					for(int q = 0 ; q<discount.size();q++) {
						//对象不相等则不存在
						if(discount.get(q) != list.get(i)) {
							discount.add(list.get(i));
							break loop;
						}
					}
				}
			}
			//查种类
			for(int j = 0 ; j<allKind.length;j++) {
				System.out.println(allKind[j]);
				if(allKind[j].equals(kind)) {
					//种类相同
					//查找discount列表里有没有当前的优惠 如果有 不添加，如果没有则添加
					if(discount.size() == 0) {
						discount.add(list.get(i));
						break loop;
					}
					for(int q = 0 ; q<discount.size();q++) {
						//对象不相等则不存在
						if(discount.get(q) != list.get(i)) {
							discount.add(list.get(i));
							break loop;
						}
					}
				}
			}
			
		}
		return discount;
	}

	@Override
	public List<discount> searchByclas(int id, String clas) {
		return dc.searchByclas(id, clas);
	}


}
