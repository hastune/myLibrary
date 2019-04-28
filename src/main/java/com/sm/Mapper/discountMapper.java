package com.sm.Mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.sm.beans.discount;

public interface discountMapper {
	
	List<discount> searchByclas(@Param("id")int id,@Param("clas")String clas);

	Integer insert(discount d);
	
	List<discount> search(int id);
}
