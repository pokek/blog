package com.blog.framwork.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.support.SFunction;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.blog.framwork.entity.Article;
import com.blog.framwork.entity.Category;
import com.blog.framwork.entity.result.ResponseResult;
import com.blog.framwork.entity.vo.CategoryVo;
import com.blog.framwork.mapper.CategoryMapper;
import com.blog.framwork.service.ArticleService;
import com.blog.framwork.service.CategoryService;
import com.blog.framwork.utils.constants.BeanCopyUtils;
import com.blog.framwork.utils.constants.SystemConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.function.Predicate;
import java.util.function.ToLongFunction;
import java.util.stream.Collector;
import java.util.stream.Collectors;

/**
 * 分类表(Category)表服务实现类
 *
 * @author makejava
 * @since 2022-07-24 12:07:50
 */
@Service("categoryService")
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService {

    @Autowired
    ArticleService articleService;

    @Override
    public ResponseResult getCategoryList() {
        // 使用流 进行条件过滤，并且不进行多表查询
        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();
        // eq里面会根据类型判断  如 (string, int);
        // mysql 中会对整型字符串优化   select * from sg_article where status = 0 与
        // select * from sg_article where status = '0' and del_flag = 0  执行结果都正确
        //
        queryWrapper.eq(Article::getStatus, SystemConstants.ARTICLE_STATUS_NORMAL);
        List<Article> articleList = articleService.list(queryWrapper);
        // 流映射分类id并去重
        Set<Long> ids = articleList.stream().map(Article::getCategoryId).collect(Collectors.toSet());
        // 根据id查找分类表单数据
        List<Category> categories = this.listByIds(ids);
        // 流过滤 查找结果中的不符合要求的数据  条件为真就不过滤
        List<Category> ans = categories.stream().filter(category -> SystemConstants.Category_STATUS_NORMAL.equals(category.getStatus())).collect(Collectors.toList());
        // 封装vo
        List<CategoryVo> categoryVos = BeanCopyUtils.beanCopyList(ans, CategoryVo.class);
//        boolean equals = new Integer(1).equals("1");
//        System.out.println(equals);  // false;
        return ResponseResult.okResult(categoryVos);

    }
}

