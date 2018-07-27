# springboot-email

## springboot-email 工程概述
springboot-email是基于SpringBoot环境集成Java Mail，并实现了邮件发送功能，包括简易邮件发送、html正文邮件发送、内联资源（静态资源）邮件发送、模板邮件发送

## 实现步骤分析
### 引入相关依赖

```
<dependencies>

	<dependency>
		<groupId>org.springframework.boot</groupId>
		<artifactId>spring-boot-starter-mail</artifactId>
	</dependency>
	<!-- lombok 用于简化JavaBean结构 -->
	<dependency>
		<groupId>org.projectlombok</groupId>
		<artifactId>lombok</artifactId>
		<scope>provided</scope>
	</dependency>

	<dependency>
		<groupId>org.springframework.boot</groupId>
		<artifactId>spring-boot-starter-thymeleaf</artifactId>
	</dependency>

</dependencies>
```
### 创建application.yml文件
```
spring:
  mail:
    host: smtp.qq.com # QQ邮箱smtp主机
    port: 587 #端口号465或587
    username: test@qq.com
    password: dxbrcmxjktzqbbhj  # 这里QQ邮箱开通POP3/SMTP提供的授权码，如果邮箱服务商没有授权码，可以使用密码代替
    protocol: smtp
    default-encoding: UTF-8
```

### 创建springboot-email启动类
```
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
	
	@Autowired MailUtil mailUtil;
	// 注入模板引擎
	@Autowired TemplateEngine templateEngine;
	
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
```
为了便于测试，启动类直接实现`CommandLineRunner`接口，并重写`run`方法来实现邮件发送的测试

### 创建发送邮件工具类
```
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

@Component
public class MailUtil {

	@Autowired
	private JavaMailSender mailSender; // 自动注入的Bean

	@Value("${spring.mail.username}")
	private String sender; // 读取配置文件中的参数

	/**
	 * 发送邮件测试方法
	 */
	public void sendMail() {
		SimpleMailMessage mimeMessage = new SimpleMailMessage();
		mimeMessage.setFrom(sender);
		mimeMessage.setTo(sender);
		mimeMessage.setSubject("SpringBoot集成JavaMail实现邮件发送");
		mimeMessage.setText("SpringBoot集成JavaMail实现邮件发送正文");
		mailSender.send(mimeMessage);
	}
	
	/**
	 * 发送简易邮件
	 * @param mailBean
	 */
	public void sendMail(MailBean mailBean) {
		MimeMessage mimeMessage = mailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(mimeMessage);
		
		try {
			helper.setFrom(sender);
			helper.setTo(sender);
			helper.setSubject(mailBean.getSubject());
			helper.setText(mailBean.getText());
		} catch (MessagingException e) {
			e.printStackTrace();
		}
		
		mailSender.send(mimeMessage);
	}
	
	/**
	 * 发送邮件-邮件正文是HTML
	 * @param mailBean
	 */
	public void sendMailHtml(MailBean mailBean) {
		MimeMessage mimeMessage = mailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(mimeMessage);
		
		try {
			helper.setFrom(sender);
			helper.setTo(sender);
			helper.setSubject(mailBean.getSubject());
			helper.setText(mailBean.getText(), true);
		} catch (MessagingException e) {
			e.printStackTrace();
		}
		
		mailSender.send(mimeMessage);
	}
	/**
	 * 发送邮件-附件邮件
	 * @param mailBean
	 */
	public void sendMailAttachment(MailBean mailBean) {
		try {
			MimeMessage mimeMessage = mailSender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
			helper.setFrom(sender);
			helper.setTo(sender);
			helper.setSubject(mailBean.getSubject());
			helper.setText(mailBean.getText(), true);
			// 增加附件名称和附件
			helper.addAttachment(mailBean.getAttachmentFilename(), mailBean.getFile());
			mailSender.send(mimeMessage);
		} catch (MessagingException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 内联资源（静态资源）邮件发送
	 * @param mailBean
	 */
	public void sendMailInline(MailBean mailBean) {
		try {
			MimeMessage mimeMessage = mailSender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
			helper.setFrom(sender);
			helper.setTo(sender);
			helper.setSubject(mailBean.getSubject());
			
			/*
			 * 内联资源邮件需要确保先设置邮件正文，再设置内联资源相关信息
			 */
			helper.setText(mailBean.getText(), true);
			helper.addInline(mailBean.getContentId(), mailBean.getFile());
			
			mailSender.send(mimeMessage);
		} catch (MessagingException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 模板邮件发送
	 * @param mailBean
	 */
	public void sendMailTemplate(MailBean mailBean) {
		try {
			MimeMessage mimeMessage = mailSender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
			helper.setFrom(sender);
			helper.setTo(sender);
			helper.setSubject(mailBean.getSubject());
			helper.setText(mailBean.getText(), true);
			mailSender.send(mimeMessage);
		} catch (MessagingException e) {
			e.printStackTrace();
		}
	}
	
}

```
### 创建Mail实体类
```
import org.springframework.core.io.FileSystemResource;

import lombok.Data;

/**
 * 邮件实体类
 * 
 * @author shanglishuai
 *
 */
@Data // 通过lombok提供的相关注解实现简化实体构造
public class MailBean {
	/**
	 * 邮件主题
	 */
	private String subject;

	/**
	 * 邮件内容
	 */
	private String text;
	
	/**
	 * 附件
	 */
	private FileSystemResource file;
	
	/**
	 * 附件名称
	 */
	private String attachmentFilename;
	
	/**
	 * 内容ID，用于发送静态资源邮件时使用
	 */
	private String contentId;
	
	public static MailBean getMailBean() {
		return new MailBean();
	}
	
}

```
### 创建邮件模板文件
```
<!DOCTYPE html>
<html  xmlns:th="http://www.thymeleaf.org">
<head>
<meta charset="UTF-8" />
<title>Insert title here</title>
</head>
<body>
    <p>Hello!<span th:text="${user}"></span>!<br /></p>
   	这是SpringBoot集成Java Email，并通过Thymeleaf创建邮件模板，实现的模板邮件发送！！！
</body>
</html>
```
注意：根据约定大于配置，模板文件建议放在`resources`目录下。本例：`/resources/templates/emailTemplate.html`

博客地址： <br>
[SpringCloud 企业级应用实战](https://blog.csdn.net/mynameissls/article/details/81150061) <br>
[基于Eureka Server实现服务注册高可用](https://blog.csdn.net/myNameIssls/article/details/81157345)

参考链接:  <br>
[https://docs.spring.io/spring-boot/docs/2.0.3.RELEASE/reference/htmlsingle/#boot-features-email](https://docs.spring.io/spring-boot/docs/2.0.3.RELEASE/reference/htmlsingle/#boot-features-email) <br>
[https://docs.spring.io/spring/docs/5.0.7.RELEASE/spring-framework-reference/integration.html#mail](https://docs.spring.io/spring/docs/5.0.7.RELEASE/spring-framework-reference/integration.html#mail) <br>
[https://javaee.github.io/javamail/](https://javaee.github.io/javamail/)
[https://www.cnblogs.com/ityouknow/p/6823356.html](https://www.cnblogs.com/ityouknow/p/6823356.html)










