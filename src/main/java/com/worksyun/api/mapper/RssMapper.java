package com.worksyun.api.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.worksyun.api.model.Rss;
import com.worksyun.commons.mapper.BaseMapper;

@Mapper
public interface RssMapper extends BaseMapper<Rss>{
	
	List<Rss> selectRssByDate(Map<String, Object> paramMap);
	
	List<Rss> selectRss();
	
	List<Rss> selectRssById(Map<String, Object> paramMap);
}
