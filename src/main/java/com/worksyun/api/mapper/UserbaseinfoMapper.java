package com.worksyun.api.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.worksyun.api.model.Userbaseinfo;
import com.worksyun.commons.mapper.BaseMapper;

@Mapper
public interface UserbaseinfoMapper extends BaseMapper<Userbaseinfo> {

	List<Userbaseinfo> selectUserbaseinfoByUserId(Map<String, Object> paramMap);

	List<Userbaseinfo> selectUserbaseinfoByTel(Map<String, Object> paramMap);
}