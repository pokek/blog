package com.blog.framwork.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.blog.framwork.entity.Article;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;


/*
    mybati plus 提供基本的数据表操作接口 供我们使用  BaseMapper
 */
@Repository
@Mapper
public interface ArticleMapper extends BaseMapper<Article> {
}
