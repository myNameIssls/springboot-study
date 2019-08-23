package cn.tyrone.springboot.mail;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import cn.tyrone.springboot.mail.util.MailBean;
import cn.tyrone.springboot.mail.util.MailUtil;

@SpringBootApplication
public class Application implements CommandLineRunner {

    private static final Logger log = LoggerFactory.getLogger(Application.class);


    @Autowired
    MailUtil mailUtil;
    // 注入模板引擎
    @Autowired
    TemplateEngine templateEngine;

    public static void main(String[] args) throws Exception {
        SpringApplication.run(Application.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        log.info("------------- SpringBoot集成JavaMail实现邮件发送开始 -------------");
        MailBean mailBean = MailBean.getMailBean();
        /*
         * 简易邮件发送测试
         */
//		mailBean.setSubject("SpringBoot集成JavaMail实现邮件发送");
//		mailBean.setText("SpringBoot集成JavaMail实现简易邮件发送功能");
//		mailUtil.sendMail(mailBean);

        /**
         * HTML邮件正文发送测试
         */
//		String html = 
//				"<html>"
//				+ "<body>"
//				+ "<p><h1>SpringBoot集成JavaMail实现正文为HTML的邮件发送功能</h1></p>"
//				+ "</body>"
//				+ "</html>";
//		mailBean.setSubject("SpringBoot集成JavaMail实现邮件发送");
//		mailBean.setText(html);
//		mailUtil.sendMailHtml(mailBean);

        /**
         * 附件邮件发送测试
         */
//		String filePath="/Users/shanglishuai/Downloads/Jietu20180727-144621@2x.jpg";
//		FileSystemResource file = new FileSystemResource(new File(filePath));
//      String attachmentFilename = filePath.substring(filePath.lastIndexOf(File.separator));
//		mailBean.setSubject("SpringBoot集成JavaMail实现邮件发送");
//		mailBean.setText("SpringBoot集成JavaMail实现附件邮件发送");
//		mailBean.setFile(file);
//		mailBean.setAttachmentFilename(attachmentFilename);
//		mailUtil.sendMailAttachment(mailBean);


        /**
         * 内联资源邮件发送测试
         */
//		String filePath="/Users/shanglishuai/Downloads/Jietu20180727-144621@2x.jpg";
//		String contentId = UUID.randomUUID().toString().replace("-", "");
//		/*
//		 * <img src=\'cid:" + contentId + "\' >
//		 * cdi:是固定的，其后连接内联资源的的ID（保证唯一即可）
//		 */
//		String content = "<html><body>内联资源邮件发送：<img src=\'cid:" + contentId + "\' ></body></html>";
//		FileSystemResource file = new FileSystemResource(new File(filePath));
//		mailBean.setSubject("SpringBoot集成JavaMail实现邮件发送");
//		mailBean.setText(content);
//		mailBean.setFile(file);
//		mailBean.setContentId(contentId);
//		mailUtil.sendMailInline(mailBean);

        Context context = new Context();
        context.setVariable("user", "Tyrone");
        String content = templateEngine.process("emailTemplate", context);
        mailBean.setSubject("SpringBoot集成JavaMail实现邮件发送");
        mailBean.setText(content);
        mailUtil.sendMailTemplate(mailBean);

        log.info("------------- SpringBoot集成JavaMail实现邮件发送结束 -------------");
    }
}
