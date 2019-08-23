package cn.tyrone.springboot.mail.util;

import org.springframework.core.io.FileSystemResource;

import lombok.Data;

/**
 * 邮件实体类
 *
 * @author shanglishuai
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
