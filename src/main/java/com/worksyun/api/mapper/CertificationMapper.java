package com.worksyun.api.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.worksyun.api.model.Certification;
import com.worksyun.commons.mapper.BaseMapper;

@Mapper
public interface CertificationMapper extends BaseMapper<Certification> {
	
	List<Certification> SelectCertificationByUserId(Map<String, Object> paramMap);
}