package cn.tyrone.springboot.freemarker.util;

import freemarker.cache.StringTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

import java.io.IOException;
import java.io.StringWriter;
import java.util.Locale;
import java.util.Map;

public class FreemarkerUtil {


    /**
     * 从模板文件中加载
     * @param templateFileName
     * @param data
     * @return
     * @throws IOException
     * @throws TemplateException
     */
    public static String loadTemplateFromFile(String templateFileName, Map<String, String> data) throws IOException, TemplateException {

        // 创建一个FreeMarker实例, 负责管理FreeMarker模板的Configuration实例
        Configuration cfg = new Configuration(Configuration.DEFAULT_INCOMPATIBLE_IMPROVEMENTS);
        // 指定FreeMarker模板文件的位置
        cfg.setClassForTemplateLoading(FreemarkerUtil.class,"/templates/freemarkers");

        // 设置模板的编码格式
        cfg.setEncoding(Locale.CHINA, "UTF-8");
        // 获取模板文件
        Template template = cfg.getTemplate(templateFileName, "UTF-8");
        StringWriter writer = new StringWriter();

        // 将数据输出到html中
        template.process(data, writer);
        writer.flush();

        String html = writer.toString();

        return html;
    }

    /**
     * 从存储单元中中加载
     * @param templateFileContent
     * @param data
     * @return
     * @throws IOException
     * @throws TemplateException
     */
    public static String loadTemplateFromStorage(String templateFileContent, Map<String, String> data) throws IOException, TemplateException {

        String templateName = "自定义模板名称";

        StringWriter stringWriter = new StringWriter();

        StringTemplateLoader loader = new StringTemplateLoader();
        loader.putTemplate(templateName, templateFileContent);

        Configuration cfg = new Configuration(Configuration.VERSION_2_3_23);
        cfg.setTemplateLoader(loader);
        cfg.setDefaultEncoding("UTF-8");
        Template template = cfg.getTemplate(templateName);
        template.process(data, stringWriter);

        String html = stringWriter.toString();

        return html;
    }


}
