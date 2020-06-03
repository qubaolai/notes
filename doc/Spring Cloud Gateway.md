# Spring Cloud Gateway

### 一、介绍

###### Spring Cloud Gateway是Spring Cloud 的一个子项目，该项目基于`Spring5.x`、`SpringBoot2.x`技术版本进行编写，意在提供简单方便、可扩展的统一API路由管理方式。

### 二、Gateway相关概念

1. Route(路由)： 路由是网关的基本单元，由ID、URI、一组Predicate(断言)，一组Filter(过滤器)组成，请求会根据断言进行转发，同时也会根据相关的过滤器进行处理。
2. Predicate(断言)：路由进行转发的条件，符合条件后会将请求转发到URI配置的路径，支持多种配置，[参考Route Predicate Factories](https://cloud.spring.io/spring-cloud-static/spring-cloud-gateway/2.2.2.RELEASE/reference/html/#gateway-request-predicates-factories)
3. Filter(过滤器)：路由在进行转发时需经过过滤器的处理，可以在过滤器中修改请求信息、响应内容等。[参考GatewayFilter Factories](https://cloud.spring.io/spring-cloud-static/spring-cloud-gateway/2.2.2.RELEASE/reference/html/#gatewayfilter-factories)

### 三、使用Spring Cloud Gateway

###### 1.创建一个项目作为网关配置

###### 2.在pom.xml中添加网关依赖

``` xml
<!--选择自己需要的版本-->
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-gateway</artifactId>
</dependency>
```

###### 3.在项目的application.yml中配置应用

###### <font color="orange" size="5">注意：</font>路由的配置会按照顺序进行适配，应将断言精确高的路由放置在上方

```yml
server:
  port: 9000
spring:
  application:
    name: gatewan-app
  cloud:
    gateway:
      #配置路由
      routes:
      #保证唯一性即可
      - id: baidu_route
        #符合断言之后的请求转发路径
        uri: https://cn.bing.com
        #断言配置，这里是配置的请求参数断言
        #http://localhost:9000/?param=baidu 这个请求会被转发到必应
        predicates:
        - Query=param,baidu
        #通过过滤器处理后，请求会添加一个参数param=hello
        filters:
        - AddRequestParameter=param1,hello
```

###### 4.启动项目，测试

###### 最开始的请求

[![YOlH1J.png](https://s1.ax1x.com/2020/05/22/YOlH1J.png)](https://imgchr.com/i/YOlH1J)

###### 配置断言和经过拦截器处理后的请求

[![YO1Pcd.png](https://s1.ax1x.com/2020/05/22/YO1Pcd.png)](https://imgchr.com/i/YO1Pcd)

###### 请求被转发到https://cn.bing.com并添加了param1参数

###### gateway基础配置结束，详情参照[官方网址](https://cloud.spring.io/spring-cloud-static/spring-cloud-gateway/2.2.2.RELEASE/reference/html/#gateway-starter)

### 四、网关配置跨域

######  <span id="CORS">提供SpringBoot配置跨域请求，对于跨域请参考[浏览器同源策略及跨域]()</span>

```java
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsWebFilter;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;

/**
 * 跨域配置类
 */
//声明为一个配置类， @Configuration注解中包含一个@Component注解会将配置类加入到springIOC容器中
@Configuration
public class CorsConfig {
    //@Bean注解就是将CorsWebFilter对象交给IOC容器管理
    @Bean
    public CorsWebFilter corsWebFilter(){
        //org.springframework.web.cors.reactive.CorsConfigurationSource的实现类
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        //跨域配置类
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        // 配置跨域
        //允许哪些头跨域
        corsConfiguration.addAllowedHeader("*");
        //允许哪些请求跨域
        corsConfiguration.addAllowedMethod("*");
        //允许哪些请求来源跨域 如果不实用*，可以配置多个添加请求来源
        corsConfiguration.addAllowedOrigin("*");
        //是否允许携带cookie进行跨域
        corsConfiguration.setAllowCredentials(true);
        //对接口进行配置，“/*”代表所有，“/**”代表适配的所有接口
        source.registerCorsConfiguration("/**", corsConfiguration);
        //CorsWebFilter的构造器需要传递一个
        //org.springframework.web.cors.reactive.CorsConfigurationSource的接口作为参数
        //接口不能实例化，所以选择CorsConfigurationSource的实现类
        //UrlBasedCorsConfigurationSource作为参数
        return new CorsWebFilter(source);
    }
}

```

###### 至此，跨域请求配置结束！