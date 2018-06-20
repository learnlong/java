package com.worksyun.api.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.worksyun.api.model.Telecommunication;
import com.worksyun.commons.mapper.BaseMapper;

@Mapper
public interface TelecommunicationMapper extends BaseMapper<Telecommunication> {
}