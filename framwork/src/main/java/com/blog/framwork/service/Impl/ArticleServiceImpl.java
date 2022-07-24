package com.blog.framwork.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.blog.framwork.entity.Article;
import com.blog.framwork.entity.result.ResponseResult;
import com.blog.framwork.entity.vo.ArticleListVo;
import com.blog.framwork.entity.vo.HotArticleVo;
import com.blog.framwork.entity.vo.PageVo;
import com.blog.framwork.mapper.ArticleMapper;
import com.blog.framwork.service.ArticleService;
import com.blog.framwork.service.CategoryService;
import com.blog.framwork.utils.constants.BeanCopyUtils;
import com.blog.framwork.utils.constants.SystemConstants;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article> implements ArticleService {

    @Autowired
    ArticleMapper articleMapper;

    @Autowired
    CategoryService categoryService;

    @Override
    public ResponseResult hotArticleList() {
        // 利用mybatis plus自带功能语句 解决需求
        // LambdaQueryWrapper 为条件筛选器
        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper();
        // 利用匿名内部类，获取列名
        queryWrapper.eq(Article::getStatus, SystemConstants.ARTICLE_STATUS_NORMAL);
        queryWrapper.orderByDesc(Article::getViewCount);
        // 利用分页机制，进行top10选择
        Page<Article> articlePage = new Page<>(1, 10);
        // 调用继承的page方法
        page(articlePage, queryWrapper);
        List<Article> records = articlePage.getRecords();
        List<HotArticleVo> vos = new ArrayList<>();
        // bean拷贝
//        for(Article ac : records){
//            HotArticleVo vo = new HotArticleVo();
//            BeanUtils.copyProperties(ac, vo);
//            vos.add(vo);
//        }
        // 使用BeanCopy工具类
        vos = BeanCopyUtils.beanCopyList(records, HotArticleVo.class);
        return ResponseResult.okResult(vos);

    }

    @Override
    public ResponseResult articleList(Integer categoryId, Integer pageNum, Integer pageSize) {
        // 查询正式发布文章，置顶文章显示在前面
        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();
        // 动态sql
        queryWrapper.eq(Objects.nonNull(categoryId) && categoryId > 0, Article::getCategoryId, categoryId);
        queryWrapper.eq(Article::getStatus, SystemConstants.ARTICLE_STATUS_NORMAL);
        queryWrapper.orderByDesc(Article::getIsTop);

        // 进行分页
        Page<Article> page = new Page<>(pageNum, pageSize);
        page(page, queryWrapper);
        // page中有wrapper条件了
        List<Article> articleList = page.getRecords();
        // 流操作达到表联查效果映射到 自建的name字段中
        // 流操作之后 要对操作后的流进行收集，即collect否则操作失效，流其实也就是一个对象
        articleList
                .stream().map(article -> article.setCategoryName((categoryService.getById(article.getCategoryId())).getName()))
                .collect(Collectors.toList());
        // 封装vo
        List<ArticleListVo> articleListVos = BeanCopyUtils.beanCopyList(articleList, ArticleListVo.class);
        PageVo pageVo = new PageVo(articleListVos, page.getTotal());
        return ResponseResult.okResult(pageVo);
    }
}
