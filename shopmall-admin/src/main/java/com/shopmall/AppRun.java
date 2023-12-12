package com.shopmall;


import com.binarywang.spring.starter.wxjava.miniapp.config.WxMaAutoConfiguration;
import com.shopmall.annotation.AnonymousAccess;
import com.shopmall.utils.SpringContextHolder;
import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.servlet.server.ServletWebServerFactory;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @description:
 * @author: zhuxiying
 * @date: 2023/11/30 19:45

 **/
@Slf4j
@EnableAsync
@RestController
@SpringBootApplication(exclude = {WxMaAutoConfiguration.class})
@EnableTransactionManagement
@MapperScan(basePackages ={"com.shopmall.mapper",
                           "com.shopmall.*.mapper",
                           "com.shopmall.modules.*.mapper",
                           "com.shopmall.config"})
public class AppRun {

    public static void main(String[] args) {
        ConfigurableApplicationContext application = SpringApplication.run(AppRun.class, args);
        Environment env = application.getEnvironment();
        String name = env.getProperty("spring.application.name");
        String active = env.getProperty("spring.profiles.active");
        log.info("\n----------------------------------------------------------\n\t" +
                " _                                 _ _                       \n"+
                " ___| |__   ___  _ __  _ __ ___   __ _| | |                  \n"+
                "/ __| '_ \\ / _ \\| '_ \\| '_ ` _ \\ / _` | | |              \n"+
                "\\__ \\ | | | (_) | |_) | | | | | | (_| | | |                \n"+
                "|___/_| |_|\\___/| .__/|_| |_| |_|\\__,_|_|_|                \n"+
                "                |_|                                          \n"+
                "Application \'" + name +"\' is running in: "+active+"        \n"+
                "----------------------------------------------------------");
    }
    @Bean
    public SpringContextHolder springContextHolder() {
        return new SpringContextHolder();
    }

    @Bean
    public ServletWebServerFactory webServerFactory() {
        TomcatServletWebServerFactory fa = new TomcatServletWebServerFactory();
        fa.addConnectorCustomizers(connector -> connector.setProperty("relaxedQueryChars", "[]{}"));
        return fa;
    }

    /**
     * 访问首页提示
     * @return /
     */
    @GetMapping("/")
    @AnonymousAccess
    public String index() {
        return "Backend service started successfully";
    }
}
