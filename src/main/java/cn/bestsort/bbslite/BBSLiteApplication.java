package cn.bestsort.bbslite;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan("cn.bestsort.bbslite.mapper")
@SpringBootApplication
//@EnableCaching
public class BBSLiteApplication {
    public static void main(String[] args) {
        SpringApplication.run(BBSLiteApplication.class, args);
    }
}