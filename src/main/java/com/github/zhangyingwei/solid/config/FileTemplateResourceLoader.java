package com.github.zhangyingwei.solid.config;

import com.github.zhangyingwei.solid.common.Constants;
import com.github.zhangyingwei.solid.common.SolidUtils;

import java.io.File;

public class FileTemplateResourceLoader implements SolidTemplateResourcesLoader {
    private String basePath;

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

    private String getFilePath(String source) {
        return this.basePath + File.separator + source;
    }
}
