# springboot-dubbo-api

## springboot-dubbo-api 工程概述
dubbo建议将服务接口，服务模型，服务异常等均放在 API 包中，因为服务模型及异常也是 API 的一部分，同时，这样做也符合分包原则：重用发布等价原则(REP)，共同重用原则(CRP)。所以这里将服务提供者和服务消费者共同使用的接口抽象到该工程中。

## 实现步骤分析
### 按需创建服务接口
```
public interface DemoService {
	
	String sayHello(String name);
	
}
```


博客地址： <br>
[SpringBoot 企业级应用实战](https://blog.csdn.net/column/details/14078.html) <br>
[SpringBoot 2.0 集成 Dubbo](https://blog.csdn.net/myNameIssls/article/details/82669224)

参考链接:  <br>
[http://dubbo.apache.org/zh-cn/docs/user/best-practice.html](http://dubbo.apache.org/zh-cn/docs/user/best-practice.html) <br>











