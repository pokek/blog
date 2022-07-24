package com.blog.framwork.utils.constants;

import org.springframework.beans.BeanUtils;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public class BeanCopyUtils {
    // 方法泛型
    public static <V> V beanCopy(Object src, Class<V> clazz){
        V res = null;
        try {
            res = clazz.newInstance();
            BeanUtils.copyProperties(src, res);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return res;
    }

    // 方法泛型
    // 其中不使用通配符的话，可在返回泛型上再加上一个泛型 <O,V> ...  (List(O) src)...
    public static <V> List<V> beanCopyList(List<? extends Object> src, Class<V> clazz){
        // 调用单一对象拷贝方法
        return src.stream().map(o -> beanCopy(o, clazz)).collect(Collectors.toList());
    }
}
