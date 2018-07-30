package com.github.zhangyingwei.solid.items;

import com.github.zhangyingwei.solid.result.SolidResult;

/**
 * @author zhangyw
 * @date 2018/7/3
 */
public interface Block {
    Block setFlag(boolean flag);
    SolidResult render();
    String text();
}
