package com.worksyun.commons.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.worksyun.commons.mapper.BaseMapper;
import com.worksyun.commons.service.BaseService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 *基础服务impl
 *
 * @auth:mingfly
 * @see: [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
public abstract class BaseServiceImpl<T,I> implements BaseService<T,I> {

    @Autowired
    private BaseMapper<T> baseMapper;


    public BaseMapper<T> getBaseMapper() {
        return baseMapper;
    }

    @Override
    public T save(T t) {
        baseMapper.insertSelective(t);
        return t;
    }

    @Override
    public T getById(I id) {
        return baseMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<T> getAlls() {
        return baseMapper.selectAll();
    }

    @Override
    public PageInfo<T> getAllByPage(int pageCurrent, int pageSize, T t) {
        PageHelper.startPage(pageCurrent,pageSize);
        List<T>  list=baseMapper.select(t);
        return new PageInfo<T>(list);
    }
}
