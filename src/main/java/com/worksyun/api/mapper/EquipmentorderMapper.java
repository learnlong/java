package com.worksyun.api.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.worksyun.api.model.Equipmentorder;
import com.worksyun.commons.mapper.BaseMapper;

@Mapper
public interface EquipmentorderMapper extends BaseMapper<Equipmentorder> {

	int selectCountOrder(Map<String, Object> paramMap);

	List<Equipmentorder> selectEquipmentOrder(Map<String, Object> paramMap);
	
	void updateByPrimaryKeyEquipmentorder(Equipmentorder equipmentorder);
	
	int selectCountOrderByhard(Map<String, Object> paramMap);
}