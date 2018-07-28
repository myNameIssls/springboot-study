package cn.tyrone.springboot.quartz.config;

import org.quartz.CronScheduleBuilder;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import cn.tyrone.springboot.quartz.service.QuartzTaskService;

/**
 * 该类是配置类，用于Quartz配置所需要
 * @author shanglishuai
 *
 */
@Configuration 
public class QuartzConfig {
	
	/**
	 * JobDetail 用于指定定时任务具体的类
	 */
	@Bean
	public JobDetail quartzTaskServiceJobDetail() {
        return JobBuilder.newJob(QuartzTaskService.class).withIdentity("quartzTaskService").storeDurably().build();
    }
	
	/**
	 * Trigger 用于指定定时任务触发的机制
	 */
	@Bean
    public Trigger quartzTaskServiceTrigger() {
        CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule("*/5 * * * * ?");
        return TriggerBuilder.newTrigger().forJob(quartzTaskServiceJobDetail())
                .withIdentity("quartzTaskService")
                .withSchedule(scheduleBuilder)
                .build();
    }
}
