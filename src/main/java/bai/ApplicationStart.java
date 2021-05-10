package bai;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("bai.dao")
public class ApplicationStart {

    public static void main(String[] args) {

        SpringApplication.run(ApplicationStart.class,args);
    }

}
