package com.worksyun.api.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.worksyun.api.model.Hardwareinfo;
import com.worksyun.commons.mapper.BaseMapper;

@Mapper
public interface HardwareinfoMapper extends BaseMapper<Hardwareinfo> {

	List<Hardwareinfo> selectHardwareinfoByStsatus();
	
	List<Hardwareinfo> selectHardwareinfoByStsatus2();
	
	List<Hardwareinfo> SelectHardwareinfoValite(Map<String, Object> paramMap);
	
	void updateByPrimaryKeyHardwareinfo(Hardwareinfo hardwareinfo);
	
	List<Hardwareinfo> SelectHardwareinfoValite2(Map<String, Object> paramMap);
	
	List<Hardwareinfo> SelectHardwareinfoByUserId(Map<String, Object> paramMap);
}