package com.blog.framwork.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.blog.framwork.entity.Category;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;


/**
 * 分类表(Category)表数据库访问层
 *
 * @author makejava
 * @since 2022-07-24 12:01:38
 */
@Repository
@Mapper
public interface CategoryMapper extends BaseMapper<Category> {

}

