package com.beijin.limengya.back.doc;

import com.github.houbb.markdown.toc.core.impl.AtxMarkdownToc;
import org.junit.Test;

import java.io.File;


public class HelloTests {
    @Test
    public void markdown_toc() {
        AtxMarkdownToc.newInstance()
                .charset("UTF-8")
                .write(true)
                .subTree(true);
//        AtxMarkdownToc.newInstance().genTocDir("G:\\IdeaProjects\\shebei-erp\\shebei-ERP-bus\\doc");
        String path = new File("").getAbsolutePath();
        System.out.println(path);
        AtxMarkdownToc.newInstance().genTocDir( path );
    }
}
