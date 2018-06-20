package com.worksyun.api.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.worksyun.api.model.Lucenenews;

import com.worksyun.commons.mapper.BaseMapper;

@Mapper
public interface LucenenewsMapper extends BaseMapper<Lucenenews>{
	List<Lucenenews> selectNewsByDate(Map<String, Object> paramMap);
	
	List<Lucenenews> selectLuceneNews();
	
	int selectCountLucenenews();
	
	List<Lucenenews> selectLucenenewsPage(Map<String, Object> paramMap);
	
	List<Lucenenews> selectNewsByUrl(Map<String, Object> paramMap);
}
