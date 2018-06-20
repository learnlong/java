package com.worksyun.api.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.worksyun.api.model.Useraddress;
import com.worksyun.commons.mapper.BaseMapper;

@Mapper
public interface UseraddressMapper extends BaseMapper<Useraddress> {

	List<Useraddress> selectUserAddressByUserId(Map<String, Object> paramMap);

	int selectUserAddressByUserId4(Map<String, Object> tmap2);

	List<Useraddress> selectUserAddressByUserId2(Map<String, Object> tmap);

	List<Useraddress> selectUserAddressByUserId3(Map<String, Object> tmap);
}