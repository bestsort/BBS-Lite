package cn.bestsort.bbslite;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@MapperScan("cn.bestsort.bbslite.mapper")
@SpringBootApplication
@EnableScheduling
@EnableAsync
@EnableCaching
public class BBSLiteApplication {
    public static void main(String[] args) {
        SpringApplication.run(BBSLiteApplication.class, args);
    }
}