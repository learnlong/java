package com.worksyun.api.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.worksyun.api.model.Mall;
import com.worksyun.commons.mapper.BaseMapper;

@Mapper
public interface MallMapper extends BaseMapper<Mall> {
}