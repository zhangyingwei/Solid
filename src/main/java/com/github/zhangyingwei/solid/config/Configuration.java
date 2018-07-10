package com.github.zhangyingwei.solid.config;

import com.github.zhangyingwei.solid.SolidContext;
import com.github.zhangyingwei.solid.items.pipline.AppendSolidMethod;
import com.github.zhangyingwei.solid.items.pipline.LengthSolidMethod;
import com.github.zhangyingwei.solid.items.pipline.RangeSolidMethod;
import com.github.zhangyingwei.solid.items.pipline.SolidMethod;

/**
 * @author zhangyw
 * @date 2018/7/4
 */
public class Configuration {
    private SolidContext context;
    private SolidTemplateResourcesLoader resourcesLoader;

    public Configuration(SolidTemplateResourcesLoader resourcesLoader) {
        this.context = new SolidContext();
        this.resourcesLoader = resourcesLoader;
        this.init();
    }

    /**
     * 初始化一些配置信息
     */
    private void init() {
        this.context.bindMethod("append", new AppendSolidMethod());
        this.context.bindMethod("length", new LengthSolidMethod());
        this.context.bindMethod("range", new RangeSolidMethod());
    }

    public SolidContext getContext() {
        return context;
    }

    public SolidTemplateResourcesLoader getResourcesLoader() {
        return resourcesLoader;
    }

    public void bindMethod(String key, SolidMethod method) {
        this.context.bindMethod(key, method);
    }
}
