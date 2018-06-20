package com.worksyun.commons.service;

import com.github.pagehelper.PageInfo;
import com.worksyun.commons.model.BaseModel;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 *
 * 基础模块接口
 *
 * @auth:mingfly
 * @see: [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
@Service
public interface BaseService<T,I> {

    /**
     * 保存
     * @param t
     * @return
     */
    public T save(T t);

    /**
     * 根据主键查询
     * @param id
     * @return
     */
    public T getById(I id);

    /**
     * 查询所有
     * @return
     */
    public List<T> getAlls();

    /**
     * 分页查询
     * @param pageCurrent
     * @param pageSize
     * @param t
     * @return
     */
    public PageInfo<T> getAllByPage(int pageCurrent, int pageSize,T t);
}
