package com.worksyun.api.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.worksyun.api.model.Information;
import com.worksyun.commons.mapper.BaseMapper;

@Mapper
public interface InformationMapper extends BaseMapper<Information> {
}