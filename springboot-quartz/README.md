# springboot-quartz

## springboot-quartz 工程概述
springboot-quartz是基于SpringBoot实现的定时任务工程

## 实现步骤分析
### 引入相关依赖

```
<dependencies>

	<dependency>
		<groupId>org.springframework.boot</groupId>
		<artifactId>spring-boot-starter-quartz</artifactId>
	</dependency>

</dependencies>
```

### 创建springboot-quartz启动类
```
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application {
	
	public static void main(String[] args) throws Exception {
		SpringApplication.run(Application.class, args);
	}

}
```

### 创建定时任务业务逻辑实现类
```
import org.quartz.DisallowConcurrentExecution;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.quartz.QuartzJobBean;

/**
 * 定时任务业务处理类
 * 该类需要继承QuartzJobBean类并重写executeInternal方法来实现定时任务的具体逻辑
 * @author shanglishuai
 *
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

```
`@DisallowConcurrentExecution`:用于标记定时任务禁止并发执行,可根据具体业务场景选择是否启用

### 创建Quartz配置类
```
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
```


博客地址： <br>
[SpringBoot 企业级应用实战](https://blog.csdn.net/column/details/14078.html) <br>
[SpringBoot集成Quartz实现定时任务](https://blog.csdn.net/myNameIssls/article/details/81261160)

参考链接:  <br>
[https://docs.spring.io/spring-boot/docs/2.0.3.RELEASE/reference/htmlsingle/#boot-features-quartz](https://docs.spring.io/spring-boot/docs/2.0.3.RELEASE/reference/htmlsingle/#boot-features-quartz) <br>
[http://www.quartz-scheduler.org/documentation/quartz-2.1.x/tutorials/](http://www.quartz-scheduler.org/documentation/quartz-2.1.x/tutorials/) <br>
[https://blog.csdn.net/tushuping/article/details/79636207](https://blog.csdn.net/tushuping/article/details/79636207) <br >










