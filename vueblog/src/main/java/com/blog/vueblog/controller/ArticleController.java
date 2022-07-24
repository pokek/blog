package com.blog.vueblog.controller;

import com.blog.framwork.entity.Article;
import com.blog.framwork.entity.result.ResponseResult;
import com.blog.framwork.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/article")
public class ArticleController {

    @Autowired
    private ArticleService articleService;

//    @GetMapping("/list")
//    public List<Article> findArticleList(){
//        return articleService.list();
//    }

    @GetMapping("/hotArticleList")
    public ResponseResult hotArticleList(){
        return articleService.hotArticleList();
    }

    // 经测试  spring mvc会将请求参数 类型转换，如 请求integer转string  但string转不了integer
    @GetMapping("/articleList")
    public ResponseResult articleList(Integer categoryId, Integer pageNum, Integer pageSize){
        return articleService.articleList(categoryId, pageNum, pageSize);
    }



}
