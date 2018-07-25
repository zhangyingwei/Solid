package com.github.zhangyingwei.solid.items.pipline;

/**
 * @author zhangyw
 * @date 2018/7/3
 */
public interface SolidMethod<U> {
    <T> T doFormate(U content,Object... args);
}
