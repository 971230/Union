package org.directwebremoting.util;

import freemarker.cache.StringTemplateLoader;
import freemarker.template.Configuration;

import java.io.StringWriter;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: guangping
 * Date: 2013-09-11 17:40
 * To change this template use File | Settings | File Templates.
 */
public class FreeMarkerStringTempleteUtils {
    private static Configuration cfg = null;
    private static StringTemplateLoader loader = null;

    static {
        cfg = new Configuration();
        loader = new StringTemplateLoader();
        cfg.setTemplateLoader(loader);
        cfg.setDefaultEncoding("UTF-8");
    }

    private FreeMarkerStringTempleteUtils() {
    }

    private static class FreeMarkerUtilsLoader {
        private static FreeMarkerStringTempleteUtils instance = new FreeMarkerStringTempleteUtils();
    }

    public static FreeMarkerStringTempleteUtils instance() {
        return FreeMarkerUtilsLoader.instance;
    }

    //获取模板内容
    public String getTempleteContent(String name, Map param) {
        StringWriter writer = new StringWriter(10000);
        try {
            cfg.getTemplate(name).process(param, writer);
        } catch (Exception e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        return writer.toString();
    }

    //添加模板
    public void addTemplete(String name, String arg) {
        loader.putTemplate(name, arg);
    }

}
