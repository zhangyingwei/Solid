package com.github.zhangyingwei.solid.config;

import com.github.zhangyingwei.solid.common.Constants;
import com.github.zhangyingwei.solid.common.SolidUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class FileTemplateResourceLoader implements SolidTemplateResourcesLoader {
    private String basePath;
    /**
     * 前缀
     */
    private String prefix = Constants.TEMPLATE_PREFIX;

    /**
     * 后缀
     */
    private String suffix = Constants.TEMPLATE_SUFFIX;

    public FileTemplateResourceLoader() {
        this.basePath = "./";
    }

    public FileTemplateResourceLoader(String basePath) {
        this.basePath = basePath;
    }


    @Override
    public String load(String source) {
        String content = this.readContentFromFile(this.getFilePath(source));
        return content;
    }

    private String readContentFromFile(String filePath) {
        return SolidUtils.readContentFromFile(filePath,Constants.CHAR_SET_UTF_8);
    }

    /**
     * 根据 basepath suffix prefix sources 等拼接文件路径
     * @param source
     * @return
     */
    private String getFilePath(String source) {
        String filePath = this.basePath + File.separator;
        if (this.prefix != null && this.prefix.trim().length() > 0) {
            filePath += this.prefix;
            filePath += File.separator;
        }
        filePath += source;
        if (this.suffix != null && this.suffix.trim().length() > 0) {
            filePath += this.suffix;
        }
        return filePath;
    }

    @Override
    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    @Override
    public void setSuffix(String suffix) {
        this.suffix = suffix;
    }
}
