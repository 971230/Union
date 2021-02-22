package com.ztesoft.cms.page.file.vo;

import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 12-11-6
 * Time: 上午10:09
 * To change this template use File | Settings | File Templates.
 */
public class FileBean implements Serializable {
    private String path;//文件完整路径

    private String folder;//文件目录

    private String name;//文件名称

    private String postfix;

    public FileBean() {
    }

    public FileBean(String path, String name, String postfix) {
        this.path = path;
        this.name = name;
        this.postfix = postfix;
    }

    public String getFolder() {
        return folder;
    }

    public void setFolder(String folder) {
        this.folder = folder;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPostfix() {
        return postfix;
    }

    public void setPostfix(String postfix) {
        this.postfix = postfix;
    }
}
