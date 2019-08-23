package cn.tyrone.springboot.quartz.service;

import org.quartz.DisallowConcurrentExecution;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.quartz.QuartzJobBean;

/**
 * 定时任务业务处理类
 * 该类需要继承QuartzJobBean类并重写executeInternal方法来实现定时任务的具体逻辑
 *
 * @author shanglishuai
 */
@DisallowConcurrentExecution // 用于标记定时任务禁止并发执行
public class QuartzTaskService extends QuartzJobBean {

    private static final Logger log = LoggerFactory.getLogger(QuartzTaskService.class);

    /**
     * 该方法用于实现定时任务的具体逻辑
     */
    @Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
        log.info("------------- 定时任务开始 -------------");
        try {
            // 休眠5秒钟，模拟定时任务执行过程
            Thread.sleep(5000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        log.info("------------- 定时任务结束 -------------");
    }

}
