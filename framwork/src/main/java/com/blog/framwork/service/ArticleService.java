package com.blog.framwork.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.blog.framwork.entity.Article;
import com.blog.framwork.entity.result.ResponseResult;
import com.blog.framwork.entity.vo.HotArticleVo;
import org.springframework.stereotype.Service;

import java.util.List;

public interface ArticleService extends IService<Article> {
    ResponseResult hotArticleList();

    ResponseResult articleList(Integer categoryId, Integer pageNum, Integer pageSize);

}
