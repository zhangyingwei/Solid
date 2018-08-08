package com.github.zhangyingwei.solid.config;

import com.github.zhangyingwei.solid.SolidContext;
import com.github.zhangyingwei.solid.common.Constants;
import com.github.zhangyingwei.solid.items.pipline.*;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

/**
 * @author zhangyw
 * @date 2018/7/4
 */
public class SolidConfiguration {
    private SolidContext context;

    public SolidConfiguration(SolidTemplateResourcesLoader resourcesLoader) {
        this.context = new SolidContext();
        this.context.setResourcesLoader(resourcesLoader);
        this.init();
    }

    public SolidConfiguration(SolidContext context) {
        this.context = context;
    }

    public SolidConfiguration loadConfig(String filePath) {
        Properties configProperties = this.loadConfigProperties(filePath);
        return loadConfig(configProperties);
    }

    private SolidConfiguration loadConfig(Properties configProperties) {
        this.fillConfigOfConstants(configProperties,"template.suffix",Constants.TEMPLATE_SUFFIX);
        this.fillConfigOfConstants(configProperties,"template.prefix",Constants.TEMPLATE_PREFIX);
        return this;
    }

    /**
     * fill config if exits in config properties
     * @param configProperties
     * @param key
     * @param constants
     */
    private void fillConfigOfConstants(Properties configProperties, String key, String constants) {
        if (configProperties.containsKey(key)) {
            constants = configProperties.getProperty(key);
        }
    }

    private Properties loadConfigProperties(String filePath) {
        Properties configProperties = new Properties();
        try {
            configProperties.load(new FileReader(filePath));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return configProperties;
    }

    /**
     * 初始化一些配置信息
     */
    private void init() {
        this.context.bindMethod("append", new AppendSolidMethod());
        this.context.bindMethod("prepend", new PrependSolidMethod());
        this.context.bindMethod("concat", new ConcatSolidMethod());
        this.context.bindMethod("length", new LengthSolidMethod());
        this.context.bindMethod("range", new RangeSolidMethod());
        this.context.bindMethod("abs", new AbsSolidMethod());
        this.context.bindMethod("capitalize", new CapitalizeSolidMethod());
        this.context.bindMethod("ceil", new CeilSolidMethod());
        this.context.bindMethod("split", new SplitSolidMethod());
        this.context.bindMethod("date", new DateSolidMethod());
        this.context.bindMethod("default", new DefaultSolidMethod());
        this.context.bindMethod("times", new TimesSolidMethod());
        this.context.bindMethod("upcase", new UpcaseSolidMethod());
        this.context.bindMethod("url_encode", new UrlEncodeSolidMethod());
        this.context.bindMethod("url_decode", new UrlDecodeSolidMethod());
        this.context.bindMethod("uniq", new UniqSolidMethod());
        this.context.bindMethod("join", new JoinSolidMethod());
        this.context.bindMethod("downcase", new DowncaseSolidMethod());
        this.context.bindMethod("escape", new EscapeSolidMethod());
        this.context.bindMethod("newline_to_br", new NewlineToBrSolidMethod());
        this.context.bindMethod("replace", new ReplaceSolidMethod());
        this.context.bindMethod("replace_first", new ReplaceFirstSolidMethod());
        this.context.bindMethod("reverse", new ReverseSolidMethod());
    }

    public SolidContext getContext() {
        return context;
    }

    public SolidTemplateResourcesLoader getResourcesLoader() {
        return this.context.getResourcesLoader();
    }

    public void bindMethod(String key, SolidMethod method) {
        this.context.bindMethod(key, method);
    }
}