package com.sm.Service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.sm.beans.discount;

public interface discountService {
	
	List<discount> searchByclas(int id,String clas);

	Integer inser(discount d);
	
	List<discount> haveDiscount(HttpServletRequest rq,String  id,String kind);
	
	List<discount> search(int id);
}
