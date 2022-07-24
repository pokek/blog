package com.blog.framwork.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.blog.framwork.entity.Category;
import com.blog.framwork.entity.result.ResponseResult;


/**
 * 分类表(Category)表服务接口
 *
 * @author makejava
 * @since 2022-07-24 12:07:45
 */
public interface CategoryService extends IService<Category> {

    ResponseResult getCategoryList();

}
