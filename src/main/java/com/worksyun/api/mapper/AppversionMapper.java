package com.worksyun.api.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.worksyun.api.model.Appversion;
import com.worksyun.commons.mapper.BaseMapper;

@Mapper
public interface AppversionMapper extends BaseMapper<Appversion>{

	List<Appversion> selectAppversion(Map<String, Object> paramMap);
}
